package ca.qc.johnabbott.cs616.jacmaps.ui.displayPath;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ca.qc.johnabbott.cs616.jacmaps.R;
import ca.qc.johnabbott.cs616.jacmaps.extra.PathFinder;
import ca.qc.johnabbott.cs616.jacmaps.model.JACNodeDatabaseHandler;
import ca.qc.johnabbott.cs616.jacmaps.model.data.jacNodeData;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.Destination;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.JACNode;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.Type;
import ca.qc.johnabbott.cs616.jacmaps.sqlite.DatabaseException;
import ca.qc.johnabbott.cs616.jacmaps.ui.adapter.JACNodeAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class DisplayPathActivityFragment extends Fragment {

    //ui
    private TextView title;

    private Context context;
    private RecyclerView recyclerView;
    private JACNodeAdapter jacNodeAdapter;
    private String from;
    private String to;
    private JACNode start;
    private JACNode end;
    private List<JACNode> testData;
    private JACNodeDatabaseHandler dbh;

    public DisplayPathActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_display_path, container, false);
        //grab elements
        title = root.findViewById(R.id.mainDisplayTitle_textView);
        context = getContext();
        recyclerView = root.findViewById(R.id.mainPath_RecyclerView);

        //test data class
        jacNodeData jnd = new jacNodeData();

        //node database data
//        dbh = new JACNodeDatabaseHandler(getContext());
//        try {
//            testData = dbh.getJACNodeTable().readAll();
//        } catch (DatabaseException e) {
//            e.printStackTrace();
//        }

        //TODO
        Destination destination = Destination.getTheDes();
        testData = destination.getTestData();

        //test nodes
//        JACNode start = new JACNode("P",3,28, Type.S);
//        JACNode end = new JACNode("P",3,13,Type.Room);





        return root;
    }

    public void setStart(JACNode from){
        this.start = from;
//        for (JACNode s : this.testData) {
//            if(s.getLocation().equals(from)){
//                start = s;
//                break;
//            }
//        }
    }

    public void setEnd(JACNode to){
        this.end = to;
//        for (JACNode s : testData) {
//            if(s.getLocation().equals(to)){
//                end = s;
//                break;
//            }
//        }
        if(start != null){
            Setup();
        }
    }
    public void Setup(){
        //solver
        PathFinder pathFinder = new PathFinder(testData);
        List<JACNode> finalPath = pathFinder.solve(start.getLocation(),end.getLocation());

        //set ui
        title.setText("Trip: "+start.getLocation()+" To "+end.getLocation());
        jacNodeAdapter = new JACNodeAdapter(finalPath);
        RecyclerView.LayoutManager noteRecyclerManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(noteRecyclerManager);
        recyclerView.setAdapter(jacNodeAdapter);
    }
}
