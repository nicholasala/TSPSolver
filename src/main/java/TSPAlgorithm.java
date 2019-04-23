import java.util.Random;

public abstract class TSPAlgorithm {
    protected MapHandler map;
    protected Tour tour;
    protected double totalDistance = 0;
    protected Random rand;

    TSPAlgorithm(MapHandler map){
        this.map = map;
        tour = new Tour(this.map.getDimension());
        rand = new Random();
    }

    TSPAlgorithm(MapHandler map, long randomSeed){
        this.map = map;
        tour = new Tour(this.map.getDimension());
        rand = new Random(randomSeed);
    }

    public abstract Tour startTour();

    public void printInfo(){
        System.out.println("Algorithm: " +this.getClass().getName()+"\n"+
                            "Name: "+map.getName()+"\n"+
                            "Type: "+map.getType()+"\n"+
                            "Best know: "+map.getBest_known()+"\n"+
                            "Total distance found: "+totalDistance+"\n"+
                            "Error: "+((totalDistance - map.getBest_known())/map.getBest_known())*100+"%");
    }

    protected void calculateTotDist(){
        totalDistance = 0;

        for(int i=0; i<tour.size()-1; i++)
            totalDistance += map.distById(tour.get(i), tour.get(i+1));

        totalDistance += map.distById(tour.get(tour.size()-1), tour.get(0));
    }

    protected int getTotDist(Tour t){
        int d = 0;

        for(int i=0; i<t.size()-1; i++)
            d += map.distById(t.get(i), t.get(i+1));

        d += map.distById(t.get(tour.size()-1), t.get(0));
        return d;
    }

    @Override
    public String toString(){
        return "\n\nName: "+map.getName()+"\n"+
                "Type: "+map.getType()+"\n"+
                "Best know: "+map.getBest_known()+"\n"+
                "Total distance found: "+totalDistance+"\n"+
                "Error: "+((totalDistance - map.getBest_known())/map.getBest_known())*100+"%";
    }
}
