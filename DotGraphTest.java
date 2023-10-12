import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class DotGraphTest {

    DotGraph dg;

    @Before
    public void setup() {
        dg = new DotGraph();
    }

    @Test
    public void testParseGraph() throws IOException {
        dg.parseGraph("path/to/sample.dot");
        String expected = Files.readString(Paths.get("expected.txt"));
        assertEquals(expected, dg.toString());
    }

    @Test
    public void testAddNode() {
        dg.addNode("X");
        assertTrue(dg.toString().contains("X"));
    }

    @Test
    public void testAddNodes() {
        dg.addNodes(new String[]{"X", "Y"});
        assertTrue(dg.toString().contains("X"));
        assertTrue(dg.toString().contains("Y"));
    }

    @Test
    public void testAddEdge() {
        dg.addEdge("X", "Y");
        assertTrue(dg.toString().contains("X -> Y"));
    }
}
