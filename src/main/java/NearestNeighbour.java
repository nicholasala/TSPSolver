import java.util.ArrayList;

public class NearestNeighbour extends TSPAlgorithm{
    private ArrayList<Integer> unvisited;
    private int next;
    private int startIndex;

    NearestNeighbour(MapHandler map, long randSeed) {
        super(map, randSeed);
        this.unvisited = new ArrayList<Integer>();

        for(int i=1; i<=map.getDimension(); i++)
            unvisited.add(i);

        this.startIndex = rand.nextInt(map.getDimension());
    }

    NearestNeighbour(MapHandler map, int startIndex) {
        super(map);
        this.unvisited = new ArrayList<Integer>();

        for(int i=1; i<=map.getDimension(); i++)
            unvisited.add(i);

        this.startIndex = startIndex;
    }

    @Override
    public Tour startTour() {
        //visit first city
        next = map.cities[startIndex].id;
        visitNext();

        //visit all unvisited cities
        while((next = getUnvisitedNearest()) != -1)
            visitNext();

        //add distance between first and last
        totalDistance += map.distById(tour.get(tour.size()-1), tour.get(0));
        return tour;
    }

    private Integer getUnvisitedNearest(){
        int ret = -1;
        int min = 0, dist = 0;

        for(Integer nId : unvisited){
            if((dist = map.distById(next, nId)) < min || min == 0){
                ret = nId;
                min = dist;
            }
        }

        totalDistance += min;
        return ret;
    }

    private void visitNext(){
        tour.add(next);
        unvisited.remove((Object)next);
    }
}
