public class SimulatedAnnealing extends TSPAlgorithm{
    private long startTime;

    SimulatedAnnealing(MapHandler map, Tour defaultTour, long startTime) {
        super(map);
        tour = defaultTour;
        this.startTime = startTime;
    }

    @Override
    public Tour startTour() {
        float T = 100, alpha = 0.95f;
        Tour current = new Tour(tour.size()), candidate;

        for(int id : tour.getAsArray())
            current.add(id);

        //TODO migliorare la gestione del tempo, proporzionalmente ad n del problema. fl1577 ha impiegato 31 minuti, e non aveva finito
        while(T > 0.16f && (System.currentTimeMillis() - startTime) < 178000){

            for(int i=1; i<100; i++){
                candidate = new TwoOpt(this.map, doubleBridge(current)).startTour();
                int currentDist = getTotDist(current);
                int candidateDist = getTotDist(candidate);

                if(candidateDist < currentDist){
                    current = candidate;
                    if(currentDist < getTotDist(tour)){
                        tour = current;
                    }
                }else if(rand.nextFloat() < Math.pow(Math.E, -((candidateDist - currentDist)/T))){
                    current = candidate;
                }
            }

            T *= alpha;
        }

        calculateTotDist();
        return tour;
    }

    private Tour doubleBridge(Tour source){
        int bound = source.size()/4;
        int a = rand.nextInt(bound);
        int c = rand.nextInt(bound) + bound;
        int e = rand.nextInt(bound) + bound*2;
        int g = rand.nextInt(bound) + bound*3;

        Tour next = new Tour(source.size());

        for(int i=0; i<=a; i++)
            next.add(source.get(i));

        for(int i=e+1; i<=g; i++)
            next.add(source.get(i));

        for(int i=c+1; i<=e; i++)
            next.add(source.get(i));

        for(int i=a+1; i<=c; i++)
            next.add(source.get(i));

        for(int i=g+1; i<source.size(); i++)
            next.add(source.get(i));

        return next;
    }
}

/*

    Parte da una soluzione, calcola una soluzione next vicina,(una mossa casuale del double bridge) se è migliore la prende,
    Se è peggiore, la valuta secondo la probabilità

    Abbiamo una temperatura che descresce ad ogni iterazione, a zero l'algoritmo si ferma
    Il senso della temperatura è che ad inizio algoritmo ho più flessibilità di saltare a soluzioni molto diverse anche peggiori, mentre a fine algoritmo ho meno flessibilità
    Quindi l'accettabilità di una soluzione vicina dipende dalla temperatura

    T temperatura
    DeltaE differenza (in termini di distanza) tra la soluzione attuale e la soluzione prossima

    e^(-deltaE/T) formula per il calcolo della probabilità di saltare alla soluzione prossima peggiore

 */