public class GeneticSystem extends TSPAlgorithm {

    GeneticSystem(MapHandler map, Tour defaultTour) {
        super(map);
        tour = defaultTour;
    }

    @Override
    public Tour startTour() {

        calculateTotDist();
        return tour;
    }
}

/*

    Utilizzare una matrice di incidenza (1 è presente l'arco, 0 non è presente)
    Inizializzare la popolazione con 100 individui

*/
