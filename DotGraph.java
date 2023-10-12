import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.parse.Parser;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DotGraph {

    private Graph<String, DefaultEdge> graph;

    public DotGraph() {
        this.graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    public void parseGraph(String filepath) throws IOException {
        String dotContent = new String(Files.readAllBytes(Paths.get(filepath)));
        Parser.read(dotContent).nodes().forEach(n -> {
            graph.addVertex(n.name().toString());
            n.links().forEach(l -> graph.addEdge(n.name().toString(), l.node().name().toString()));
        });
    }

    public void addNode(String label) {
        graph.addVertex(label);
    }

    public void addNodes(String[] labels) {
        for (String label : labels) {
            addNode(label);
        }
    }

    public void addEdge(String srcLabel, String dstLabel) {
        graph.addEdge(srcLabel, dstLabel);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nodes:\n");
        graph.vertexSet().forEach(n -> sb.append(n).append("\n"));
        sb.append("Edges:\n");
        graph.edgeSet().forEach(e -> sb.append(graph.getEdgeSource(e)).append(" -> ").append(graph.getEdgeTarget(e)).append("\n"));
        return sb.toString();
    }

    public void outputDOTGraph(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n");
        graph.edgeSet().forEach(e -> sb.append(graph.getEdgeSource(e)).append(" -> ").append(graph.getEdgeTarget(e)).append(";\n"));
        sb.append("}\n");
        Files.write(Paths.get(path), sb.toString().getBytes());
    }

    public void outputGraphics(String path, String format) throws IOException {
        if (!format.equals("png")) {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
        MutableGraph g = new Parser().read(this.toString());
        Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(path));
    }
}
