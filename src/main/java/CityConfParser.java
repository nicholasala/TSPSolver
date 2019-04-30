import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

enum Label{
    NAME,
    TYPE,
    COMMENT,
    DIMENSION,
    EDGE_WEIGHT_TYPE,
    BEST_KNOWN,
    NODE_COORD_SECTION;
}

public class CityConfParser {
    private Reader r;
    private MapHandler info;
    private int c;
    private String endOfLabels = "NODE_COORD_SECTION";

    CityConfParser(Reader r){
        this.r = r;
    }

    public MapHandler parse() throws IOException {
        info = new MapHandler();
        StringBuilder sb = new StringBuilder();

        //getting initial information
        while(!sb.toString().equals(endOfLabels) && c != -1){
            sb = new StringBuilder();

            while(next() != ':' && c != ' ' && c != '\n' && c != -1)
                sb.append((char)c);

            try{
                switch(Label.valueOf(sb.toString())){
                    case NAME:
                        info.setName(getString());
                        break;
                    case TYPE:
                        info.setType(getString());
                        break;
                    case COMMENT:
                        info.setComment(getString());
                        break;
                    case DIMENSION:
                        info.setDimension(getValue());
                        break;
                    case EDGE_WEIGHT_TYPE:
                        info.setEdge_weight_type(getString());
                        break;
                    case BEST_KNOWN:
                        info.setBest_known(getValue());
                        break;
                }
            }catch (IllegalArgumentException e){
                System.err.print("Label not existing: "+sb.toString()+"\n");
            }
        }

        //getting cities information
        r.mark(1);
        while((char)next() != 'E' && c != -1){
            r.reset();
            getCity();
            r.mark(1);
        }

        info.genDistanceMatrix();
        return info;
    }

    public void saveOptTour(Tour tour){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/OptTour/"+info.getName()+".opt.tour"));
            String header = "NAME : "+info.getName()+".opt.tour\n" +
                    "COMMENT : Optimum tour for "+info.getName()+".tsp ("+info.getBest_known()+")\n" +
                    "TYPE : "+info.getType()+"\n" +
                    "DIMENSION : "+info.getDimension()+"\n" +
                    "TOUR_SECTION\n";
            writer.write(header);

            for(Integer i : tour.getAsArray())
                writer.write(String.valueOf(i)+"\n");

            writer.write("-1\n" + "EOF");
            writer.flush();
            writer.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    private String getString() throws IOException {
        skipCharacters();
        StringBuilder sb = new StringBuilder();

        while(next() != '\n')
            sb.append((char)c);

        return sb.toString();
    }

    private int getValue() throws IOException {
        skipCharacters();
        int ret = 0;

        while(next() != '\n')
            ret = (ret * 10) + Character.getNumericValue((char)c);;

        return ret;
    }

    private void getCity() throws IOException {
        skipCharacters();
        StringBuilder sb = new StringBuilder();

        while(next() != '\n')
            sb.append((char)c);

        String[] values = sb.toString().split(" ");
        info.addCity(Integer.valueOf(values[0]), Double.valueOf(values[1]), Double.valueOf(values[2]));
    }

    private int next() throws IOException {
        c = r.read();
        return c;
    }

    private void skipCharacters() throws IOException {
        r.mark(1);

        while((Character.isWhitespace((char)next()) || (char)c == ':') && c != -1)
            r.mark(1);

        r.reset();
    }
}
