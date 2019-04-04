public class AntColonySystem extends TSPAlgorithm {
    private float[][] pheromone;
    private float initialTao;
    public int size;
    private long startTime;
    private long maxTime;

    AntColonySystem(MapHandler map, NearestNeighbour initialAlg, long startTime, long maxTime, long randSeed) {
        super(map, randSeed);
        this.startTime = startTime;
        this.maxTime = maxTime;
        tour = initialAlg.startTour();
        size = map.getDimension();
        pheromone = new float[size][size];
        totalDistance = initialAlg.totalDistance;
        initialTao = 1/((float)initialAlg.getError() * size);

        for(int r=0; r<size; r++)
            for(int c=r+1; c<size; c++)
                pheromone[r][c] = initialTao;


            //TODO, partire da un soluzione ottimizzata ? (twoOpt) chiamando già in fase di inizializzazione phModifyThroughBest()
        phModifyThroughBest();
    }

    @Override
    public Tour startTour() {
        Ant panoramix;
        Tour panoTour;
        int panoDist;

        while(!timeFinished() && totalDistance != map.getBest_known()){
            //posiziono ed avvio 10 formiche
            for(int i=0; i<10; i++){
                panoramix = new Ant(rand.nextInt(size), this);
                panoTour = panoramix.run();
                panoDist = getTotDist(panoTour);

                if(panoDist < totalDistance){
                    totalDistance = panoDist;
                    tour = panoTour;
                }

                if(timeFinished()) break;
            }

            //aumento il feromone secondo il tragitto vincitore
            phModifyThroughBest();

            //evaporazione del feromone in tutta la mappa
            phEvaporation();
        }

        return tour;
    }

    public float phById(int id1, int id2){
        return id1 <= id2 ? pheromone[id1-1][id2-1] : pheromone[id2-1][id1-1];
    }

    private void phModifyThroughBest(){
        for(int i=0; i < size-1; i++)
            phIncrement(tour.get(i), tour.get(i+1));
    }

    //TODO
    private void phIncrement(int id1, int id2){
        if(id1 <= id2)
            pheromone[id1-1][id2-1] += 56;
        else
            pheromone[id2-1][id1-1] += 56;
    }

    //TODO
    private void phEvaporation(){
        for(int r=0; r<size; r++)
            for(int c=r+1; c<size; c++)
                pheromone[r][c] -= 1 ;
    }

    private boolean timeFinished(){
        return (System.currentTimeMillis() - startTime) > maxTime;
    }

    ////////////////////////////////////////////     ANT     ////////////////////////////////////////////
    private class Ant{
        private int startIndex;
        private AntColonySystem system;

        public Ant(int startIndex, AntColonySystem system){
            this.startIndex = startIndex;
            this.system = system;
        }

        //TODO
        public Tour run(){
            Tour antTour = new Tour(system.size);

            float cisiamo = system.phById(2,1);

            //ritorno la soluzione ottimizzata con un due opt
            return new TwoOpt(system.map, antTour).startTour();
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
