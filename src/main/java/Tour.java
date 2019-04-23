import java.util.HashMap;

public class Tour {
    private int[] tour;
    private HashMap<Integer, Integer> positions;
    private int c = 0;

    Tour(int dimension){
        tour = new int[dimension];
    }

    public void add(int id){ tour[c++] = id; }

    public int get(int index){ return tour[index]; }

    public int size(){ return tour.length; }

    public int[] getAsArray(){  return tour; }

    public int getPosition(int id){ return positions.get(id); }

    public void generatePositionsMap(){
        positions = new HashMap<>(size());
        for(int i=0; i<size(); i++)
            positions.put(tour[i], i);
    }

    //swap two cities position in a tour, by index
    public void swap(int firstIndex, int secondIndex){
        int firstId = tour[firstIndex];
        tour[firstIndex] = tour[secondIndex];
        tour[secondIndex] = firstId;
    }

    public void swapAndRefreshPos(int firstIndex, int secondIndex){
        int firstId = tour[firstIndex];
        tour[firstIndex] = tour[secondIndex];
        tour[secondIndex] = firstId;
        positions.put(firstId, secondIndex);
        positions.put(tour[firstIndex], firstIndex);
    }

    public boolean isFull() { return c == tour.length; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<size(); i++){
            sb.append(" --> " + tour[i]);
            if(i % 20 == 0 && i != 0)
                sb.append("\n");
        }

        return sb.toString();
    }
}
