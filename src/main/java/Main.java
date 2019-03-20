import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static BufferedReader reader;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/ALGO_cup_2019_problems/ch130.tsp"));

        CityConfParser parser = new CityConfParser(reader);
        MapHandler map = parser.parse();

        //TSP
        //nearest neighbour
        TSPAlgorithm nn = new NearestNeighbour(map, 0);
        nn.startTour();

        //simulated annealing
        TSPAlgorithm sa = new SimulatedAnnealing(map, nn.getTour(), start);
        sa.startTour();
        sa.printInfo();

        reader.close();
        long time = System.currentTimeMillis() - start;
        System.out.println(time + " ms " + time/1000 + " sec, (max 180 sec)");
    }
}