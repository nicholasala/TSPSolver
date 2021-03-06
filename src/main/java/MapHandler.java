public class MapHandler {
    private String name;
    private String type;
    private String comment;
    private int dimension;
    private String edge_weight_type;
    private int best_known;
    private int c = 0;
    private int numCandidates = 15;
    private Prim primTree;
    private int[] relCandidates;
    public String getName() {
        return name;
    }
    public City[] cities;
    public int[][] distances;
    public int[][] candidates;

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEdge_weight_type() {
        return edge_weight_type;
    }

    public void setEdge_weight_type(String edge_weight_type) {
        this.edge_weight_type = edge_weight_type;
    }

    public int getBest_known() {
        return best_known;
    }

    public void setBest_known(int best_known) {
        this.best_known = best_known;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
        cities = new City[dimension];
    }

    //setDimension deve essere chiamato prima dell'aggiunta di una città
    public void addCity(int id, double x, double y){
        cities[c] = new City(id, x, y);
        c++;
    }

    //matrice triangolare superiore
    public void genDistanceMatrix(){
        distances = new int[dimension][dimension];
        for(int r=0; r<dimension; r++)
            for(int c=r+1; c<dimension; c++)
                distances[r][c] = distance(cities[r], cities[c]);
    }

    public int distById(int id1, int id2){
        return id1 <= id2 ? distances[id1-1][id2-1] : distances[id2-1][id1-1];
    }

    private int distance(City a, City b){
        return (int)(Math.sqrt(Math.pow((b.x - a.x), 2) + Math.pow((b.y - a.y), 2))+0.5);
    }

    //le candidate per ogni città sono le 15 più vicine + quelle appartenenti al minimum spanning tree (prim o kruskal)
    public void genCandidateMatrix(){
        //Prim minimum spanning tree
        primTree = new Prim(this);
        primTree.generateMinSpanningTree();
        candidates = new int[dimension][numCandidates + primTree.getMaxConnections()];
        relCandidates = new int[dimension];
        int dist, max, c, actualDist;

        //trovare per ogni città un array delle 15 più vicine, utilizzando direttamente la matrice
        //e poi aggiungere le appartenenti alle connessioni prim
        for(int i=1; i<=dimension; i++){
            for(int k=1; k<=dimension; k++){
                if(i != k){
                    dist = distById(i, k);
                    max = -1;

                    for(int j=0; j<numCandidates; j++){
                        if(candidates[i-1][j] == 0){
                            candidates[i-1][j] = k;
                            break;
                        }else{
                            actualDist = distById(i, candidates[i-1][j]);

                            if(actualDist > dist && (max == -1 || distById(i, candidates[i-1][max]) < actualDist))
                                max = j;

                            if(j == (numCandidates - 1) && max != -1)
                                candidates[i-1][max] = k;
                        }
                    }
                }
            }

            //aggiunta città del minimum spanning tree, se non presenti nelle candidate
            c = numCandidates;
            boolean add;
            for(Integer rel : primTree.getRelations(i)){
                add = false;
                for(int cand=0; cand<numCandidates; cand++){
                    if(cand == (numCandidates - 1) && rel != candidates[i-1][cand])
                        add = true;
                    else if(rel == candidates[i-1][cand])
                        break;
                }

                if(add)
                    candidates[i-1][c++] = rel;
            }

            relCandidates[i - 1] = c;
        }

    }

    //ritorna il numero di candidate connesse ad una città
    public int candidateLinkNum(int cityId){
        return relCandidates[cityId - 1];
    }

    @Override
    public String toString() {
        return "MapHandler{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", comment='" + comment + '\'' +
                ", dimension=" + dimension +
                ", edge_weight_type='" + edge_weight_type + '\'' +
                ", best_known=" + best_known +
                '}';
    }
}
