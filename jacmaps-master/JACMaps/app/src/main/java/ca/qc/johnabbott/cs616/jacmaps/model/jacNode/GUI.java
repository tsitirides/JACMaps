package ca.qc.johnabbott.cs616.jacmaps.model.jacNode;

import android.graphics.drawable.Drawable;

public class GUI {

    //The map the node is on.
    private Drawable map;
    //The X location of the node on the map image
    private Integer X;
    //The y location of the node on the map image
    private Integer y;

    public GUI(){}

    public GUI(Drawable map, Integer x, Integer y){
        this.map = map;
        this.X = x;
        this.y = y;
    }

    //Getters and Setters

    public Drawable getMap() {
        return map;
    }

    public void setMap(Drawable map) {
        this.map = map;
    }

    public Integer getX() {
        return X;
    }

    public void setX(Integer x) {
        X = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
