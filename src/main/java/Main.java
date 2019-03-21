import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static BufferedReader reader;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/ALGO_cup_2019_problems/rat783.tsp"));

        CityConfParser parser = new CityConfParser(reader);
        MapHandler map = parser.parse();

        //TSP
        //nearest neighbour
        TSPAlgorithm nn = new NearestNeighbour(map, 0);
        nn.startTour();

        //two opt
        TSPAlgorithm to = new TwoOpt(map, nn.getTour());
        to.startTour();

        //simulated annealing
        TSPAlgorithm sa = new SimulatedAnnealing(map, to.getTour(), start, 179000);
        sa.startTour();
        sa.printInfo();

        reader.close();
        long time = System.currentTimeMillis() - start;
        System.out.println(time + " ms " + time/1000 + " sec");
    }
}

//rat783 4.5537 % sa
//u1060 5.2469 % sa
//fl1577 2.8360 % sa