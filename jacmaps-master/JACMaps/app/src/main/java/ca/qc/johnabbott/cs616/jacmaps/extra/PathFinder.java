package ca.qc.johnabbott.cs616.jacmaps.extra;

import java.util.LinkedList;
import java.util.List;

import ca.qc.johnabbott.cs616.jacmaps.model.collections.Queue;
import ca.qc.johnabbott.cs616.jacmaps.model.collections.Stack;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.Destination;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.JACNode;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.Status;

public class PathFinder {
    //Target Locations
    private String start;
    private String end;
    //PathFinder queue and ReturnPath stack
    private Queue<JACNode> next;
    private Stack<JACNode> finalPath;
    //JACNodes
    private List<JACNode> nodes;

    public PathFinder(List<JACNode> jacNodes) {
        nodes = jacNodes;
    }

    public List<JACNode> solve(String start, String end){

        this.start = start;
        this.end = end;

        next = new Queue<>();
        finalPath = new Stack<>();
        List<String> path = new LinkedList<>();

        JACNode curr = null;
        Destination des = Destination.getTheDes();
        //curr = des.getFrom();
        //Stating node
        for (JACNode s : nodes) {
            if(s.getLocation().equals(start)){
                curr = s;
            }
        }

        while(!curr.getLocation().equals(end)){
            //mark as visited
            curr.setStatus(Status.Visited);
            for (JACNode n : curr.getNeighbourNodesArray()) {
                if(n.getStatus() != Status.Seen && n.getStatus() != Status.Visited){
                    n.setStatus(Status.Seen);
                    if(!next.isFull()){
                        next.enqueue(n);
                        n.setFoundFrom(curr);
                    }
                }
            }
            if(!next.isEmpty()){
                curr = next.dequeue();
            }
            else{
                //Path not found
                System.out.println("Error");
                return null;
            }

        }

        while(!curr.getLocation().equals(start)){
            finalPath.push(curr);
            curr = curr.getFoundFrom();
        }

        finalPath.push(curr);
        List<JACNode> temp = new LinkedList<>();

        while(!finalPath.isEmpty()){
            JACNode n = finalPath.pop();
            temp.add(n);
        }

        return temp;
    }

}
