public class AntSystem extends TSPAlgorithm {

    AntSystem(MapHandler map, Tour defaultTour) {
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

    più una città è distante, meno possibilità ha di essere scelta (la "visibilità");
    più l'intensità del percorso di feromone situato sul crinale tra due città è maggiore, più ha possibilità di essere scelto;
    una volta completato il suo percorso, la formica deposita, su tutti i bordi attraversati, più feromone se il percorso è breve;
    i percorsi di feromone evaporano ad ogni iterazione.

*/
