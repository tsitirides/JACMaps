package ca.qc.johnabbott.cs616.jacmaps.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ca.qc.johnabbott.cs616.jacmaps.model.data.jacNodeData;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.GUI;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.JACNode;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.RecentTripsNode;
import ca.qc.johnabbott.cs616.jacmaps.networking.HttpRequest;
import ca.qc.johnabbott.cs616.jacmaps.networking.HttpRequestTask;
import ca.qc.johnabbott.cs616.jacmaps.networking.HttpResponse;
import ca.qc.johnabbott.cs616.jacmaps.networking.OnResponseListener;
import ca.qc.johnabbott.cs616.jacmaps.sqlite.Table;
import ca.qc.johnabbott.cs616.jacmaps.sqlite.TableFactory;


public class RecentTripsDatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "jacnode.db";
    private static final int DATABASE_VERSION = 1;
    private static Table<RecentTripsNode> recentTripsTable;
    private RecentTripsDatabaseHandler c;

    public RecentTripsDatabaseHandler(@Nullable Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    c = this;
    recentTripsTable = TableFactory.makeFactory(c, RecentTripsNode .class).getTable();
}

    public Table<RecentTripsNode> getRecentTripsTable() { return recentTripsTable; }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(recentTripsTable.getCreateTableStatement());
        if (recentTripsTable.hasInitialData())
            recentTripsTable.initialize(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(recentTripsTable.getDropTableStatement());
        db.execSQL(recentTripsTable.getCreateTableStatement());
    }
}
