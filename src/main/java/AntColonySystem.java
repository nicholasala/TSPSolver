public class AntColonySystem extends TSPAlgorithm {
    private float[][] pheromone;
    private float initialTao;
    private float rho;
    private float alpha;
    public int size;
    private long startTime;
    private long maxTime;

    AntColonySystem(MapHandler map, NearestNeighbour initialAlg, long startTime, long maxTime) {
        super(map);
        this.startTime = startTime;
        this.maxTime = maxTime;
        tour = initialAlg.startTour();
        size = map.getDimension();
        pheromone = new float[size][size];
        totalDistance = initialAlg.totalDistance;
        initialTao = 1/((float)initialAlg.getError() * size);
        rho = 0.1f;
        alpha = 0.4f;

        for(int r=0; r<size; r++)
            for(int c=0; c<size; c++)
                pheromone[r][c] = initialTao;


        //TODO, partire da un soluzione ottimizzata ? (twoOpt) chiamando già in fase di inizializzazione phModifyThroughBest()
        //phGlobalUpdate();
    }

    AntColonySystem(MapHandler map, NearestNeighbour initialAlg, long startTime, long maxTime, long randSeed) {
        super(map, randSeed);
        this.startTime = startTime;
        this.maxTime = maxTime;
        tour = initialAlg.startTour();
        size = map.getDimension();
        pheromone = new float[size][size];
        totalDistance = initialAlg.totalDistance;
        initialTao = 1/((float)initialAlg.getError() * size);
        rho = 0.1f;
        alpha = 0.4f;

        for(int r=0; r<size; r++)
            for(int c=0; c<size; c++)
                pheromone[r][c] = initialTao;


            //TODO, partire da un soluzione ottimizzata ? (twoOpt) chiamando già in fase di inizializzazione phModifyThroughBest()
        //phGlobalUpdate();
    }

    @Override
    public Tour startTour() {
        Ant panoramix;
        Tour panoTour;
        int panoDist;

        while(!timeFinished() && totalDistance != map.getBest_known()){
            //posiziono ed avvio 3 formiche
            for(int i=0; i<3; i++){
                panoramix = new Ant(rand.nextInt(size));
                panoTour = panoramix.run();
                panoDist = panoramix.getDist();

                if(panoDist < totalDistance){
                    totalDistance = panoDist;
                    tour = panoTour;
                }

                //if(timeFinished()) break;
            }

            //aumento il feromone secondo il tragitto vincitore
            phGlobalUpdate();
        }

        return tour;
    }

    private float phById(int from, int to){ return pheromone[from-1][to-1]; }

    //( )=(1−ρ)⋅τ(r,s)+ρ⋅∆τ(r,s)
    private void phLocalUpdate(int from, int to){ pheromone[from-1][to-1] = (1 - rho)*pheromone[from-1][to-1] + rho*initialTao; }

    //()=(1−α)⋅τ(r,s) + α ⋅ ∆τ(r,s)global
    private void phIncrement(int from, int to){ pheromone[from-1][to-1] += (1 - alpha)*pheromone[from-1][to-1] + alpha * (1 / totalDistance); }

    private void phGlobalUpdate(){
        for(int i=0; i < size-1; i++)
            phIncrement(tour.get(i), tour.get(i+1));

        phIncrement(tour.get(size-1), tour.get(0));
    }

    private boolean timeFinished(){
        return (System.currentTimeMillis() - startTime) > maxTime;
    }



    ////////////////////////////////////////////     ANT     ////////////////////////////////////////////
    private class Ant{
        private int startIndex, dist = 0;
        private byte[] visited;
        private int next;
        private Tour antTour;

        public Ant(int startIndex){
            this.startIndex = startIndex;
            visited = new byte[size];
            antTour = new Tour(size);
        }

        public Tour run(){
            next = map.cities[startIndex].id;

            while(next != -1){
                visit(next);
                if(rand.nextFloat() < 0.93f) //TODO applicare calcolo probabilistico
                    next = exploitation();
                else
                    next = exploration();
            }

            return new TwoOpt(map, antTour).startTour();
        }

        //TODO
        private int exploitation(){
            int ret = -1;

            for(int i=0; i<visited.length; i++)
                if(visited[i] == 0 && phById(next, i+1) > 2 && (dist += map.distById(next, i+1)) > 20) //TODO applicare calcolo probabilistico
                    ret = i+1;

            if(ret > 0)
                phLocalUpdate(next, ret);
            return ret;
        }

        //TODO
        private int exploration(){
            int ret = -1;

            return ret;
        }

        private void visit(int id){
            antTour.add(next);
            visited[id - 1] = 1;
        }

        public int getDist() {
            return dist;
        }
    }
}


/*

    più una città è distante, meno possibilità ha di essere scelta (la "visibilità");
    più l'intensità del percorso di feromone situato sul crinale tra due città è maggiore, più ha possibilità di essere scelto;
    una volta completato il suo percorso, la formica deposita, su tutti i bordi attraversati, più feromone se il percorso è breve;
    i percorsi di feromone evaporano ad ogni iterazione.



    All'inizio si parte da un nearest neighbour, il feromone iniziale per tutti gli archi -> //TODO tao0 = 1/(nn * ncittà)
    Il feromone sugli archi non deve mai andare al di sotto di tao0

    Matrice del feromone (per ogni arco abbiamo un valore di feromone) parte uguale per ogni arco, dinamicamente cerheremo di cambiare il valore
    del feromone dando più feromone agli archi che portano alla soluzione ottima

    -Una decina di formiche
    -Buttiamo (posizioniamo) random queste formiche sulle città
    -Ogni formica costruisce una soluzione ammissibile partendo da dovè (è arrivata ii random) si guarda in giro e al primo passo puo andare in qualsiasi città
    secondo due meccanismi (tirare un dado per scegliere tra i due, nel 95% dei casi exploitation) :
        1 exploitation: scelgo il nodo che massimizza il rapporto feromone/costo, j = fer/cost per ogni città, next = max(j)
        2 exploration: considero le altre città ancora da visitare e utilizzo un meccanismo molto simile alla scelta dei genitori nel genetico, secondo una probabilità
        che tiene conto del feromone e del costo

     Ogni formica restituirà alla fine un percorso che avrà un costo
        local best: considerà la miglior formica dall'inizio dell'algoritmo ad ogni iterazione + le dieci attuali e scelgo la premiata


     La soluzione vincitrice, porterà ad incrementare il feromone di ogni arco della soluzione premiata
     La matrice storica di come sta andando l'algoritmo è nel feromone, ad ogni iterazione infatti gli unici oggetti rimasti sono la formica migliore e l matrice del feromone

*/
