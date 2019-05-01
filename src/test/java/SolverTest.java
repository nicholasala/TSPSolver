import org.junit.Test;

import java.io.IOException;

public class SolverTest {

    @Test(timeout = 181000)
    public void ch130() throws IOException {
        new Main().main(new String[]{"ch130", "S", "1556740526476", "179000"});
    }

    @Test(timeout = 181000)
    public void d198() throws IOException {
        long rs = System.currentTimeMillis();
        new Main().main(new String[]{"d198", "A", String.valueOf(rs), "178500"});
        //1556740615275
    }

    @Test(timeout = 181000)
    public void eil76() throws IOException {
        new Main().main(new String[]{"eil76", "S", "1556741049669", "179000"});
    }

    @Test(timeout = 181000)
    public void fl1577() throws IOException {
        long rs = System.currentTimeMillis();
        new Main().main(new String[]{"fl1577", "S", String.valueOf(rs), "179000"});
    }

    @Test(timeout = 181000)
    public void kroA100() throws IOException {
        long rs = System.currentTimeMillis();
        new Main().main(new String[]{"kroA100", "S", String.valueOf(rs), "179000"});
    }

    @Test(timeout = 181000)
    public void lin318() throws IOException {
        long rs = System.currentTimeMillis();
        new Main().main(new String[]{"lin318", "A", String.valueOf(rs), "178000"});
    }

    @Test(timeout = 181000)
    public void pcb442() throws IOException {
        long rs = System.currentTimeMillis();
        new Main().main(new String[]{"pcb442", "S", String.valueOf(rs), "179000"});
    }

    @Test(timeout = 181000)
    public void pr439() throws IOException {
        long rs = System.currentTimeMillis();
        new Main().main(new String[]{"pr439", "A", String.valueOf(rs), "178000"});
    }

    @Test(timeout = 181000)
    public void rat783() throws IOException {
        long rs = System.currentTimeMillis();
        new Main().main(new String[]{"rat783", "A", String.valueOf(rs), "178000"});
    }

    @Test(timeout = 181000)
    public void u1060() throws IOException {
        long rs = System.currentTimeMillis();
        new Main().main(new String[]{"u1060", "A", String.valueOf(rs), "178000"});
        //1556741866761
    }
}
