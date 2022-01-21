package ca.qc.johnabbott.cs616.jacmaps.model.jacNode;

public class Node {
    private String location;
    private Node[] nn;
    private Status status;
    private Type nodeType;
    private Node from;

    public Node(String location, Status status, Type nodeType) {
        this.location = location;
        this.status = status;
        this.nodeType = nodeType;
    }

    public String getLocation() {
        return location;
    }

    public Type getNodeType() {
        return nodeType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Node[] getNn() {
        return nn;
    }

    public void setNn(Node[] nn) {
        this.nn = nn;
    }

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }
}
