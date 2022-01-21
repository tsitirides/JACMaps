package ca.qc.johnabbott.cs616.jacmaps.model.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.GUI;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.JACNode;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.Type;

public class jacNodeData {
    //Test data for prototype
    private List<JACNode> testData;
    private List<String> buildings;
    private List<String> penfieldFloors;
    private List<JACNode> penfieldThirdFloor;

    public jacNodeData() {
        JACNode p328 = new JACNode("P",3,28, Type.S);
        JACNode hp328 = new JACNode("Ph",3,28,Type.Hallway);
        JACNode p327 = new JACNode("P",3,27,Type.Room);
        JACNode p326 = new JACNode("P",3,26,Type.Room);
        JACNode hp322 = new JACNode("Ph",3,22,Type.Hallway);
        JACNode p322 = new JACNode("P",3,22,Type.Room);
        JACNode p325 = new JACNode("P",3,25,Type.Room);
        JACNode hp313 = new JACNode("Ph",3,13,Type.Hallway);
        JACNode p313 = new JACNode("P",3,13,Type.Room);

        p328.setNeighbourNodesArray(new JACNode[]{hp328});
        hp328.setNeighbourNodesArray(new JACNode[]{p328,p327,p326,hp322});
        p327.setNeighbourNodesArray(new JACNode[]{hp328});
        p326.setNeighbourNodesArray(new JACNode[]{hp328});
        p325.setNeighbourNodesArray(new JACNode[]{hp322});
        hp322.setNeighbourNodesArray(new JACNode[]{hp328,p322,p325,hp313});
        p322.setNeighbourNodesArray(new JACNode[]{hp322});
        p313.setNeighbourNodesArray(new JACNode[]{hp313});
        hp313.setNeighbourNodesArray(new JACNode[]{p313,hp322});

        p328.setGuiG(new GUI(null,60,490));
        hp328.setGuiG(new GUI(null,200,490));
        p327.setGuiG(new GUI(null,200,650));
        p326.setGuiG(new GUI(null, 200, 320));
        p325.setGuiG(new GUI(null,450,620));
        p322.setGuiG(new GUI(null,450,320));
        hp322.setGuiG(new GUI(null,450,490));
        hp313.setGuiG(new GUI(null,950,490));
        p313.setGuiG(new GUI(null,950,620));



        testData = new LinkedList<JACNode>();
        testData.add(p328);
        testData.add(hp328);
        testData.add(p327);
        testData.add(p326);
        testData.add(hp322);
        testData.add(p322);
        testData.add(p325);
        testData.add(hp313);
        testData.add(p313);

        /** Data for spinners **/

        // Buildings
        buildings = new ArrayList<>();
        buildings.add(""); // Default value
        buildings.add("Penfield");

        // Floors
        penfieldFloors = new ArrayList<>();
        penfieldFloors.add(""); // Default value
        penfieldFloors.add("3rd");

        // Rooms
        penfieldThirdFloor = new ArrayList<>();
        penfieldThirdFloor.add(new JACNode()); // Default value
        penfieldThirdFloor.add(p313);
        penfieldThirdFloor.add(p322);
        penfieldThirdFloor.add(p325);
        penfieldThirdFloor.add(p326);
        penfieldThirdFloor.add(p327);
    }

    public List<JACNode> getTestData() {
        return testData;
    }

    public void setTestData(List<JACNode> testData) {
        this.testData = testData;
    }

    /** Getters **/

    public List<String> getBuildings() { return buildings; }

    public List<String> getPenfieldFloors() { return penfieldFloors; }

    public List<JACNode> getPenfieldThirdFloor() { return penfieldThirdFloor; }
}
