package ca.qc.johnabbott.cs616.jacmaps.model.jacNode;

import ca.qc.johnabbott.cs616.jacmaps.sqlite.Identifiable;

public class RecentTripsNode implements Identifiable<Long> {
    private String toNode;
    private  String fromNode;
    private long id;

    public RecentTripsNode(long id, String to, String from) {
        this.id = id;
        this.fromNode = from;
        this.toNode = to;
    }
    public RecentTripsNode() {}
    public String getToNode() {
        return toNode;
    }

    public void setToNode(String toNode) {
        this.toNode = toNode;
    }

    public String getFromNode() {
        return fromNode;
    }

    public void setFromNode(String fromNode) {
        this.fromNode = fromNode;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
