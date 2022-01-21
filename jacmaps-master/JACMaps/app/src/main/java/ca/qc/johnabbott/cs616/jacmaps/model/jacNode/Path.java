package ca.qc.johnabbott.cs616.jacmaps.model.jacNode;

import java.util.LinkedList;
import java.util.List;

public class Path {

    //private static Path thePath;

//    public static Path getThePath() {
//        return thePath;
//    }


//    static {
//        thePath = new Path();
//    }

    private List<JACNode> nodes;
    private String startLocation;
    private String endLocation;

    //public Path(){}
    public Path(){
        this.nodes = new LinkedList<>();
    }
    private Path(List<JACNode> nodes, String startNode, String endNode){
        this.nodes = nodes;
        this.startLocation = startNode;
        this.endLocation = endNode;
    }

    public List<JACNode> getNodes() {
        return nodes;
    }

    public void addNode(JACNode node) {
        this.nodes.add(node);
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getTitle(){
        return "Path: "+ startLocation+" to "+endLocation;
    }

//    public void reset() {
//        nodes = new LinkedList<>();
//        startLocation = endLocation = null;
//    }
}
