package ca.qc.johnabbott.cs616.server.model;

public enum Status {
    Unseen(0), Seen(1), Visited(2);

    private int id;

    Status(int id){this.id=id;}

    public int getStatus() {
        return id;
    }
}
