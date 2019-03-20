import java.util.ArrayList;
import java.util.Arrays;

public class NearestNeighbour extends TSPAlgorithm{
    private ArrayList<City> unvisited;
    private City next;
    private int startIndex;

    NearestNeighbour(MapHandler map) {
        super(map);
        this.unvisited = new ArrayList<City>(Arrays.asList(map.cities));
        this.startIndex = rand.nextInt(map.getDimension());
    }

    NearestNeighbour(MapHandler map, int startIndex) {
        super(map);
        this.unvisited = new ArrayList<City>(Arrays.asList(map.cities));
        this.startIndex = startIndex;
    }

    @Override
    public Tour startTour() {
        //visit first vity
        next = map.cities[startIndex];
        visitNext();

        //visit all unvisited cities
        while((next = getUnvisitedNearest()) != null)
            visitNext();

        //add distance between first and last
        totalDistance += map.distById(tour.get(tour.size()-1), tour.get(0));
        return tour;
    }

    private City getUnvisitedNearest(){
        City ret = null;
        int min = 0, dist = 0;

        for(City c : unvisited){
            if((dist = map.distById(next.id, c.id)) < min || min == 0){
                ret = c;
                min = dist;
            }
        }

        totalDistance += min;
        return ret;
    }

    private void visitNext(){
        tour.add(next.id);
        unvisited.remove(next);
    }
}
