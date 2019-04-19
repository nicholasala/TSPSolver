
public class MapHandler {
    private String name;
    private String type;
    private String comment;
    private int dimension;
    private String edge_weight_type;
    private int best_known;
    private int c = 0;
    public City[] cities;
    public int[][] distances;
    public int[][] candidates;

    public String getName() {
        return name;
    }

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

    //TODO
    //le candidate per ogni città sono le 15 più vicine + quelle appartenenti al minimum spanning tree (prim o kruskal)
    //durante la costruzione dell'albero, mantenere in una variabile il numero massimo di città collegate da una città, così da avere il numero di righe
    //della matrice
    public void genCandidateMatrix(){
        //Prim minimum spanning tree
        Prim primTree = new Prim(this);
        primTree.generateMinSpanningTree();

        candidates = new int[15 + primTree.getMaxConnections()][dimension];

        //trovare per ogni città un array delle 15 più vicine, utilizzando direttamente la matrice
        //e poi aggiungere le appartenenti alle connessioni prim (se non già presenti)

        for(int i=1; i<=dimension; i++){

        }
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
