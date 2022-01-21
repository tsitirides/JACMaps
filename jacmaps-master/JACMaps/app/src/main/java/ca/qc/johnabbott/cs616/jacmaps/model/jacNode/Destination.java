package ca.qc.johnabbott.cs616.jacmaps.model.jacNode;

import java.util.LinkedList;
import java.util.List;

public class Destination {

    private static Destination theDes;

    public static Destination getTheDes(){
        return theDes;
    }

    static {
        theDes = new Destination();
    }

    private JACNode from;
    private JACNode to;
    private List<JACNode> testData;

    private Destination(){
        this.testData = new LinkedList<>();
    }

    public Destination(JACNode from, JACNode to) {
        this.from = from;
        this.to = to;
    }

    public JACNode getFrom() {
        return from;
    }

    public void setFrom(JACNode from) {
        this.from = from;
    }

    public JACNode getTo() {
        return to;
    }

    public void setTo(JACNode to) {
        this.to = to;
    }

    public void reset(){
        this.from = null;
        this.to = null;
    }

    public List<JACNode> getTestData() {
        return testData;
    }

    public void setTestData(List<JACNode> testData) {
        this.testData = testData;
    }
}
