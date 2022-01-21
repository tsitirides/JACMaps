package ca.qc.johnabbott.cs616.server.model;

import javax.persistence.*;

@Entity
@Table(name = "jacnode")
public class JACNode {

    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    //Room Address (Location)
    @Column(name = "building")
    private String building;
    @Column(name = "floor")
    private Integer floor;
    @Column(name = "room")
    private Integer room;
    @Column(name = "location")
    private String Location;
    //Node Information

    //@Column(name = "neighbournodes")
    //private JACNode[] neighbourNodes;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private Type type;
    //How was this node found
    //@Column(name = "foundfrom")
    //private JACNode foundFrom;


    @Column(name = "neighbournodesstring")
    private String neighbourNodes;

    @Column(name = "guistring")
    private String gui;

    public JACNode(){

    }

//    public JACNode(String building, Integer floor, Integer room, Type type){
//        this.building = building;
//        this.floor = floor;
//        this.room = room;
//        setLocation();
//        this.status = Status.Unseen;
//        this.type = type;
//    }

    //Getter and Setters
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public String getLocation() {
        return Location.toString();
    }

    public void setLocation() {
        Location = building+floor.toString()+room.toString();
    }

    public String getNeighbourNodes() {
        return neighbourNodes;
    }

    public void setNeighbourNodes(String neighbourNodes) {
        this.neighbourNodes = neighbourNodes;
    }

    //    public JACNode[] getNeighbourNodes() {
//        return neighbourNodes;
//    }

//    public void setNeighbourNodes(JACNode[] neighbourNodes) {
//        this.neighbourNodes = neighbourNodes;
//    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getGui() {        return gui;
    }

    public void setGui(String gui) {
        this.gui = gui;
    }

    //    public JACNode getFoundFrom() {
//        return foundFrom;
//    }
//
//    public void setFoundFrom(JACNode foundFrom) {
//        this.foundFrom = foundFrom;
//    }
}
