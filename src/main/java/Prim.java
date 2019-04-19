import java.util.ArrayList;

public class Prim {
    private MapHandler map;
    private ArrayList<Integer> visited;
    private ArrayList<Integer> unvisited;
    private ArrayList<Integer>[] relations;
    private int maxConnections = 0;

    public Prim(MapHandler map){
        this.map = map;
        visited = new ArrayList<Integer>();
        unvisited = new ArrayList<>();
        for(int i=1; i<=map.getDimension(); i++)
            unvisited.add(i);

        relations = new ArrayList[map.getDimension()];
        for(int i=0; i<relations.length; i++)
            relations[i] = new ArrayList<>();
    }

    public void generateMinSpanningTree(){
        int actual = 0, next = 0, min, dist;
        visit(map.cities[0].id);

        while(visited.size() != map.getDimension()){
            min = 0;
            for(Integer i : visited){
                for(Integer k : unvisited){
                    dist = map.distById(i, k);
                    if(dist < min || min == 0){
                        min = dist;
                        actual = i;
                        next = k;
                    }
                }
            }

            relations[actual-1].add(next);
            relations[next - 1].add(actual);
            visit(next);
        }

        for(ArrayList a : relations)
            if(maxConnections == 0 || a.size() > maxConnections)
                maxConnections = a.size();
    }

    public ArrayList<Integer> getRelations(int cityId){
        return relations[cityId - 1];
    }

    public int relationsNum(int cityId){
        return relations[cityId - 1].size();
    }

    public int getMaxConnections(){
        return maxConnections;
    }

    private void visit(int next){
        visited.add(next);
        unvisited.remove((Object)next);
    }
}
