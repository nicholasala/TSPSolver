import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static BufferedReader reader;
    public static CityConfParser parser;
    public static MapHandler map;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        long randSeed;
        long maxTime = 179000;
        TSPAlgorithm algorithm = null;
        reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/ALGO_cup_2019_problems/"+args[0]));
        parser = new CityConfParser(reader);
        map = parser.parse();

        //IMPOSTAZIONE DEL SEED E ALGORITMO

        switch(args[0]){
            case "eil76.tsp":
                randSeed = 1553608541473l;
                algorithm = new SimulatedAnnealing(map, new NearestNeighbour(map, randSeed).startTour(), start, maxTime, randSeed);
                break;
            case "ch130.tsp":
                randSeed = 1553609078779l;
                algorithm = new SimulatedAnnealing(map, new NearestNeighbour(map, randSeed).startTour(), start, maxTime, randSeed);
                break;
            case "kroA100.tsp":
                randSeed = 1553681004370l;
                algorithm = new SimulatedAnnealing(map, new NearestNeighbour(map, 0).startTour(), start, maxTime, randSeed);
                break;
            case "d198.tsp":
                randSeed = 1553615360785l;
                algorithm = new SimulatedAnnealing(map, new NearestNeighbour(map, randSeed).startTour(), start, maxTime, randSeed);
                break;
            case "lin318.tsp":
                randSeed = 1553643040717l;
                algorithm = new SimulatedAnnealing(map, new NearestNeighbour(map, 0).startTour(), start, maxTime, randSeed);
                break;
            case "pr439.tsp":
                randSeed = 1553662026587l;
                algorithm = new SimulatedAnnealing(map, new NearestNeighbour(map, randSeed).startTour(), start, maxTime, randSeed);
                break;
            case "pcb442.tsp":
                randSeed = 1553670262082l;
                algorithm = new SimulatedAnnealing(map, new NearestNeighbour(map, randSeed).startTour(), start, maxTime, randSeed);
                break;
            case "rat783.tsp":
                randSeed = 1553691220990l;
                algorithm = new SimulatedAnnealing(map, new NearestNeighbour(map, randSeed).startTour(), start, maxTime, randSeed);
                break;
            case "u1060.tsp":
                randSeed = 1553712201382l;
                algorithm = new SimulatedAnnealing(map, new NearestNeighbour(map, randSeed).startTour(), start, maxTime, randSeed);
                break;
            case "fl1577.tsp":
                randSeed = 1553717420504l;
                algorithm = new SimulatedAnnealing(map, new NearestNeighbour(map, randSeed).startTour(), start, maxTime, randSeed);
                break;
            default:
                randSeed = start;
                algorithm = new SimulatedAnnealing(map, new NearestNeighbour(map, randSeed).startTour(), start, maxTime, randSeed);
                break;
        }

        //TSP
        //algorithm.startTour();
        //algorithm.printInfo();




        //nearest neighbour
        TSPAlgorithm nn = new NearestNeighbour(map, randSeed);
        nn.startTour();


        //Two Opt
        TSPAlgorithm to = new TwoOpt(map, nn.getTour());
        to.startTour();
        to.printInfo();
/*
        //Random
        //TSPAlgorithm rand = new RandomTour(map, randSeed);
        //rand.startTour();

        //simulated annealing
        TSPAlgorithm sa = new SimulatedAnnealing(map, to.getTour(), start, maxTime, randSeed);
        sa.startTour();*/
        reader.close();
        long time = System.currentTimeMillis() - start;
        System.out.println(time + " ms " + time/1000 + " sec");
    }
}

//Soluzioni ottime in:
//eil76, ch130, kroA100, d198