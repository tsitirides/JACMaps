package ca.qc.johnabbott.cs616.jacmaps.model.jacNode;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ca.qc.johnabbott.cs616.jacmaps.sqlite.Identifiable;

public class JACNode implements Identifiable<Long>, Parcelable {
    private long id;
    //Room Address (Location)
    private String building;
    private Integer floor;
    private Integer room;
    private String location;
    //Node Information
    private JACNode[] neighbourNodesArray;

    public String getNeighbourNodes() {
        return neighbourNodes;
    }

    public void setNeighbourNodes(String neighbourNodes) {
        this.neighbourNodes = neighbourNodes;
    }

    public String getGuiString() {
        return gui;
    }

    public void setGuiString(String guiString) {
        this.gui = guiString;
    }

    private String neighbourNodes;
    private String gui;
    private Status status;
    private Type type;
    //How was this node found
    private JACNode foundFrom;
    //GUI
    private GUI guiG;

    public JACNode(String building, Integer floor, Integer room, Type type){
        this.building = building;
        this.floor = floor;
        this.room = room;
        this.location = building + floor.toString() + room.toString();
        this.status = Status.Unseen;
        this.type = type;
    }

    public JACNode() {}

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
        return location.toString();
    }

    public void setLocation(String location) { this.location = location; }

    public JACNode[] getNeighbourNodesArray() {
        return neighbourNodesArray;
    }

    public void setNeighbourNodesArray(JACNode[] neighbourNodesArray) {
        this.neighbourNodesArray = neighbourNodesArray;
    }

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

    public JACNode getFoundFrom() {
        return foundFrom;
    }

    public void setFoundFrom(JACNode foundFrom) {
        this.foundFrom = foundFrom;
    }

    @Override
    public String toString() {
        return location;
    }

    public GUI getGuiG() {
        return guiG;
    }

    public void setGuiG(GUI guiG) {
        this.guiG = guiG;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public static JACNode[] parseArray(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        JACNodes jacNodes = gson.fromJson(json, JACNodes.class);
        JACNode[] nodeArray = jacNodes._embedded.jACNodes;
        return nodeArray;
    }

    // Embedded

    public static class Embedded {
        private JACNode[] jACNodes;
    }

    public static class JACNodes {
        private Embedded _embedded;
    }

    //TODO - fix loop

//    //Parcel

    @Override
    public int describeContents() {
        return 0;
    }
//
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(building);
        dest.writeInt(floor);
        dest.writeInt(room);
        dest.writeString(location);
        dest.writeArray(neighbourNodesArray);
        dest.writeString(type.toString());
        dest.writeString(building+"/"+floor.toString()+"/"+ getGuiG().getX().toString()+"/"+ getGuiG().getY().toString());
    }
//
    public JACNode(Parcel parcel){
        this.building = parcel.readString();
        this.floor = parcel.readInt();
        this.room = parcel.readInt();
        location = parcel.readString();
        this.neighbourNodesArray = (JACNode[]) parcel.readArray(JACNode.class.getClassLoader());
        this.status = Status.Unseen;
        String type = parcel.readString();
        switch (type){
            case "Room":
                this.type = Type.Room;
                break;
            case "Hallway":
                this.type = Type.Hallway;
                break;
            case "S":
                this.type = Type.S;
                break;
            case "B":
                this.type = Type.B;
                break;
            default:
                throw new RuntimeException("Type Enum Error.....!");
        }
        this.foundFrom = null;
        String gui[] = parcel.readString().split("/");
        this.guiG = new GUI(null,Integer.parseInt(gui[2]),Integer.parseInt(gui[3]));
    }
//
//    // Method to recreate a Question from a Parcel
    public static Creator<JACNode> CREATOR = new Creator<JACNode>() {

        @Override
        public JACNode createFromParcel(Parcel source) {
            return new JACNode(source);
        }

        @Override
        public JACNode[] newArray(int size) {
            return new JACNode[size];
        }

    };
}
