import java.util.ArrayList;

public class RandomTour extends TSPAlgorithm {
    private ArrayList<Integer> unvisited = new ArrayList<Integer>();

    RandomTour(MapHandler map, long randSeed) {
        super(map, randSeed);

        for(int i=1; i<=map.getDimension(); i++)
            unvisited.add(i);
    }

    @Override
    public Tour startTour() {
        int randPos;

        while(unvisited.size() > 0){
            randPos = rand.nextInt(unvisited.size());
            tour.add(unvisited.get(randPos));
            unvisited.remove(randPos);
        }

        calculateTotDist();
        return tour;
    }

}
