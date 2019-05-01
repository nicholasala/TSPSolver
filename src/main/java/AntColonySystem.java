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
        //parto da un tour già ottimizzato
        tour = new TwoOpt(map, initialAlg.startTour()).startTour();
        size = map.getDimension();
        pheromone = new float[size][size];
        totalDistance = initialAlg.totalDistance;
        initialTao = 1/((float)totalDistance * size);
        rho = 0.1f;
        alpha = 0.4f;

        for(int r=0; r<size; r++)
            for(int c=0; c<size; c++)
                pheromone[r][c] = initialTao;

        globalTrailUpdate();
    }

    AntColonySystem(MapHandler map, NearestNeighbour initialAlg, long startTime, long maxTime, long randSeed) {
        super(map, randSeed);
        this.startTime = startTime;
        this.maxTime = maxTime;
        //parto da un tour già ottimizzato
        tour = new TwoOpt(map, initialAlg.startTour()).startTour();
        size = map.getDimension();
        pheromone = new float[size][size];
        totalDistance = initialAlg.totalDistance;
        initialTao = 1/((float)totalDistance * size);
        rho = 0.1f;
        alpha = 0.4f;

        for(int r=0; r<size; r++)
            for(int c=0; c<size; c++)
                pheromone[r][c] = initialTao;

        globalTrailUpdate();
    }

    @Override
    public Tour startTour() {
        int antsNum = 3;
        Ant[] ants = new Ant[antsNum];
        boolean run;

        while(!timeFinished() && totalDistance != map.getBest_known()){
            //posiziono le formiche
            for(int i=0; i<ants.length; i++)
                ants[i] = new Ant(rand.nextInt(size));

            //muovo le formiche
            run = true;
            while(run)
                for(Ant a : ants)
                    run = a.run();

            //seleziono il tour migliore
            for(Ant a : ants){
                if(timeFinished())
                    break;

                Tour optTour = a.getTwoOptTour();
                int aDist = getTotDist(optTour);

                if(aDist < totalDistance){
                    tour = optTour;
                    totalDistance = aDist;
                }
            }

            if(timeFinished())
                break;

            //aumento il feromone secondo il tragitto vincitore
            globalTrailUpdate();
        }

        return tour;
    }

    private float phById(int from, int to){ return pheromone[from-1][to-1]; }

    //( )=(1−ρ)⋅τ(r,s)+ρ⋅∆τ(r,s)
    private void localTrailUpdate(int from, int to){ pheromone[from-1][to-1] = (1 - rho)*pheromone[from-1][to-1] + rho*initialTao; }

    //()=(1−α)⋅τ(r,s) + α ⋅ ∆τ(r,s)global
    private void phIncrement(int from, int to){ pheromone[from-1][to-1] += (1 - alpha)*pheromone[from-1][to-1] + alpha * (1 / totalDistance); }

    private void globalTrailUpdate(){
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
        private int previus;
        private int next;
        private Tour antTour;

        public Ant(int startIndex){
            this.startIndex = startIndex;
            visited = new byte[size];
            antTour = new Tour(size);
            next = map.cities[startIndex].id;
            visit(next);
        }

        public boolean run(){
            if(antTour.isFull()){
                return false;
            }else{
                previus = next;
                if(rand.nextFloat() < 0.93f)
                    next = exploitation();
                else
                    next = exploration();

                localTrailUpdate(previus, next);
                visit(next);
                return true;
            }
        }

        //assumiamo che venga chiamato solo quando ci sono ancora città da aggiungere
        //(ph * (1/distArco)) / (ph * (1/distArco)) per ogni arco candidato
        private int exploitation(){
            int bestCand = -1;
            float denSum = 0, bestChance = -Float.MAX_VALUE, actualChance;

            for(int i=0; i<map.candidateLinkNum(previus); i++)
                denSum += phById(previus, map.candidates[previus - 1][i]) * (1/(float)map.distById(previus, map.candidates[previus - 1][i]));

            //ciclo attraverso le candidate della città attuale, chi massimizza la probabilità verrà scelta come miglior candidata
            for(int i=0; i<map.candidateLinkNum(previus); i++){
                actualChance = (phById(previus, map.candidates[previus - 1][i]) * (1/(float)map.distById(previus, map.candidates[previus - 1][i]))) / denSum;

                if(isUnvisited(map.candidates[previus - 1][i]) && actualChance > bestChance){
                    bestChance = actualChance;
                    bestCand = i;
                }
            }

            if(bestCand != -1)
                return map.candidates[previus - 1][bestCand];
            else
                return getFirstUnvisited();
        }

        //assumiamo che venga chiamato solo quando ci sono ancora città da aggiungere
        private int exploration(){
            int bestCand = -1;
            float denSum = 0, bestChance = -Float.MAX_VALUE, actualChance;

            for(int i=0; i<map.candidateLinkNum(previus); i++)
                denSum += phById(previus, map.candidates[previus - 1][i]) * (1/(float)map.distById(previus, map.candidates[previus - 1][i]));

            //ciclo attraverso le candidate della città attuale, chi massimizza la probabilità verrà scelta come miglior candidata
            for(int i=0; i<map.candidateLinkNum(previus); i++){
                actualChance = (phById(previus, map.candidates[previus - 1][i]) * (1/(float)map.distById(previus, map.candidates[previus - 1][i]))) / denSum;

                if(isUnvisited(map.candidates[previus - 1][i]) && (actualChance - rand.nextFloat()) > bestChance){
                    bestChance = actualChance;
                    bestCand = i;
                }
            }

            if(bestCand != -1)
                return map.candidates[previus - 1][bestCand];
            else
                return getFirstUnvisited();
        }

        private int getFirstUnvisited(){
            for(int i=0; i<visited.length; i++)
                if(visited[i] == 0)
                    return (i + 1);

            return 0;
        }

        private void visit(int id){
            antTour.add(next);
            visited[id - 1] = 1;
        }

        private boolean isUnvisited(int id){
            return visited[id - 1] == 0;
        }

        public Tour getTwoOptTour() {
            return new TwoOpt(map, antTour).startTour();
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
