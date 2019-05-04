import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static BufferedReader reader;
    public static CityConfParser parser;
    public static MapHandler map;

    //args to pass -> fileName AlgorithmType randSeed maxTime
    //TODO, TwoOptCandidate ?

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/ALGO_cup_2019_problems/"+args[0]+".tsp"));
        parser = new CityConfParser(reader);
        map = parser.parse();
        TSPAlgorithm algorithm;
        long randSeed = Long.valueOf(args[2]);
        long maxTime = Long.valueOf(args[3]);
        Random firstCity = new Random(randSeed);

        switch(args[1]){
            case "S":
                //SimulatedAnnealing
                algorithm = new SimulatedAnnealing(map, new NearestNeighbour(map, firstCity.nextInt(map.getDimension())).startTour(), start, maxTime, randSeed);
                break;
            case "A":
                //AntColonySystem
                map.genCandidateMatrix();
                algorithm = new AntColonySystem(map, new NearestNeighbour(map, firstCity.nextInt(map.getDimension())), start, maxTime, randSeed);
                break;
            default:
                algorithm = new SimulatedAnnealing(map, new NearestNeighbour(map, firstCity.nextInt(map.getDimension())).startTour(), start, maxTime, randSeed);
                break;
        }

        algorithm.startTour();
        System.out.println("dist:"+algorithm.totalDistance+ " error:" +algorithm.getError()+ " seed:"+randSeed);

        reader.close();
        parser.saveOptTour(algorithm.getTour());
    }
}