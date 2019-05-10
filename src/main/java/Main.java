import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static BufferedReader reader;
    public static CityConfParser parser;
    public static MapHandler map;

    //TODO, TwoOptCandidate ?

    public static void main(String[] args){
        long start = System.currentTimeMillis();
        if(args.length != 4){
            System.out.println("Error, args needed: fileName AlgorithmType(A or S) randSeed maxTime");
            return;
        }

        if(!args[0].contains(".tsp")){
            args[0] = args[0] + ".tsp";
        }

        try {
            reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/ALGO_cup_2019_problems/"+args[0]));
            parser = new CityConfParser(reader);
            map = parser.parse();
        } catch (IOException e) {
            System.out.println("Error, file "+args[0]+" not found");
            return;
        }

        TSPAlgorithm algorithm;
        long randSeed;
        long maxTime;

        try{
            randSeed = Long.valueOf(args[2]);
        }catch(NumberFormatException e){ System.out.println("Random seed not correct, the algorithm will use zero"); randSeed = 0;}

        try{
            maxTime = Long.valueOf(args[3]);
        }catch(NumberFormatException e){ System.out.println("Max time not correct, the algorithm will use three minutes"); maxTime = 180000;}

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

        try {
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }

        parser.saveOptTour(algorithm.getTour());
    }
}