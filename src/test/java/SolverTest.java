import org.junit.Test;

import java.io.IOException;

public class SolverTest {

    //long rs = System.currentTimeMillis();
    //String.valueOf(rs);

    @Test(timeout = 181000)
    public void ch130() throws IOException {
        new Main().main(new String[]{"ch130", "S", "1556740526476", "179000"});
        //dist:6110.0 error:0.0 seed:1556740526476 S
    }

    @Test(timeout = 181000)
    public void d198() throws IOException {
        new Main().main(new String[]{"d198", "A", "1556972900135", "179000"});
        //dist:15787.0 error:0.044359949302915085 seed:1556972900135 A
    }

    @Test(timeout = 181000)
    public void eil76() throws IOException {
        new Main().main(new String[]{"eil76", "S", "1556741049669", "179000"});
        //dist:538.0 error:0.0 seed:1556741049669 S
    }

    @Test(timeout = 181000)
    public void fl1577() throws IOException {
        new Main().main(new String[]{"fl1577", "A", "1556961982074", "179000"});
        //dist:22728.0 error:2.1529057485729695 seed:1556961982074 A
    }

    @Test(timeout = 181000)
    public void kroA100() throws IOException {
        new Main().main(new String[]{"kroA100", "S", "1556976683785", "179000"});
        //dist:21282.0 error:0.0 seed:1556976683785 S
    }

    @Test(timeout = 181000)
    public void lin318() throws IOException {
        new Main().main(new String[]{"lin318", "S", "1556977213646", "179000"});
        //dist:42259.0 error:0.547241190606486 seed:1556977213646 S
    }

    @Test(timeout = 181000)
    public void pcb442() throws IOException {
        new Main().main(new String[]{"pcb442", "S", "1556989038974", "179000"});
        //dist:51591.0 error:1.6010870849580527 seed:1556989038974 S
    }

    @Test(timeout = 181000)
    public void pr439() throws IOException {
        new Main().main(new String[]{"pr439", "A", "1556989503755", "179000"});
        //dist:107830.0 error:0.5717376908512642 seed:1556989503755 A
    }

    @Test(timeout = 181000)
    public void rat783() throws IOException {
        new Main().main(new String[]{"rat783", "A", "1556990197717", "179000"});
        //dist:9127.0 error:3.645241880535998 seed:1556990197717 A
    }

    @Test(timeout = 181000)
    public void u1060() throws IOException {
        new Main().main(new String[]{"u1060", "A", "1556991158804", "179000"});
        //dist:228650.0 error:2.033075405856471 seed:1556991158804 A
    }
}
