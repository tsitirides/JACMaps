package ca.qc.johnabbott.cs616.jacmaps.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ca.qc.johnabbott.cs616.jacmaps.model.data.jacNodeData;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.Destination;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.GUI;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.JACNode;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.RecentTripsNode;
import ca.qc.johnabbott.cs616.jacmaps.networking.HttpRequest;
import ca.qc.johnabbott.cs616.jacmaps.networking.HttpRequestTask;
import ca.qc.johnabbott.cs616.jacmaps.networking.HttpResponse;
import ca.qc.johnabbott.cs616.jacmaps.networking.OnResponseListener;
import ca.qc.johnabbott.cs616.jacmaps.sqlite.Table;
import ca.qc.johnabbott.cs616.jacmaps.sqlite.TableFactory;

public class JACNodeDatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "jacnode.db";
    private static final int DATABASE_VERSION = 1;
    private static List<JACNode> nodes;
    private static jacNodeData jacNodeData;
    private static Table<JACNode> JACNodeTable;
    private static Table<RecentTripsNode> recentTripsTable;
    private JACNodeDatabaseHandler c;

    public JACNodeDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        c = this;
        nodes = new ArrayList<>();
        nodes.add(new JACNode());
        jacNodeData = new jacNodeData();
        HttpRequest request = new HttpRequest("http://10.0.2.2:9999/jacnode", HttpRequest.Method.GET);
        HttpRequestTask httpRequestTask = new HttpRequestTask();
        httpRequestTask.setOnResponseListener(new OnResponseListener<HttpResponse>() {
            @Override
            public void onResponse(HttpResponse data) {

                if (data.getResponseCode() != 200)
                    return;

                JACNode[] nodeArray = JACNode.parseArray(data.getResponseBody());

                for (int i = 0; i < nodeArray.length; i++) {

                    // Neighbours
                    String[] neighArr =  nodeArray[i].getNeighbourNodes().split("/");
                    List<JACNode> neighbours = new LinkedList<>();
                    List<JACNode> nodeArr = jacNodeData.getTestData();
                    
                    int counter = 0;

                    for (int j = 0; j < neighArr.length; j++) {
                        for (JACNode node : nodeArr) {
                            if (node.getLocation().equals(neighArr[j])) {
                                neighbours.add(node);
                                break;
                            }
                        }
                    }

                    nodeArray[i].setNeighbourNodesArray(neighbours.toArray(new JACNode[neighbours.size()]));

                    // GUI
                    String[] guiArr = nodeArray[i].getGuiString().split("/");
                    GUI gui = new GUI(null, Integer.parseInt(guiArr[0]), Integer.parseInt(guiArr[1]));
                    nodeArray[i].setGuiG(gui);

                    nodes.add(nodeArray[i]);

                }
                Destination des = Destination.getTheDes();
                JACNodeTable = TableFactory.makeFactory(c, JACNode.class)
                        .setSeedData(nodes)
                        .getTable();
                nodes.remove(0);
                des.setTestData(nodes);

            }
        });
        httpRequestTask.execute(request);
        recentTripsTable = TableFactory.makeFactory(c, RecentTripsNode.class).getTable();
    }

    public Table<JACNode> getJACNodeTable() { return JACNodeTable; }

    public Table<RecentTripsNode> getRecentTripsTable() { return recentTripsTable; }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(recentTripsTable.getCreateTableStatement());
        if (recentTripsTable.hasInitialData())
            recentTripsTable.initialize(db);

        db.execSQL(JACNodeTable.getCreateTableStatement());
        if (JACNodeTable.hasInitialData())
            JACNodeTable.initialize(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(JACNodeTable.getDropTableStatement());
        db.execSQL(JACNodeTable.getCreateTableStatement());

        db.execSQL(recentTripsTable.getDropTableStatement());
        db.execSQL(recentTripsTable.getCreateTableStatement());
    }
}
