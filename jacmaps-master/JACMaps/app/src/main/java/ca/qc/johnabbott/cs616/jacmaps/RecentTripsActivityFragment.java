package ca.qc.johnabbott.cs616.jacmaps;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.qc.johnabbott.cs616.jacmaps.model.JACNodeDatabaseHandler;
import ca.qc.johnabbott.cs616.jacmaps.model.data.jacNodeData;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.Destination;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.DisplayNodes;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.JACNode;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.RecentTripsNode;
import ca.qc.johnabbott.cs616.jacmaps.sqlite.DatabaseException;
import ca.qc.johnabbott.cs616.jacmaps.sqlite.Table;
import ca.qc.johnabbott.cs616.jacmaps.ui.displayPath.DisplayPathActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecentTripsActivityFragment extends Fragment {

    private RecyclerView recView;
    private List<JACNode> nodes;
    private GridLayoutManager mGridLayoutManager;
    private Table<RecentTripsNode> rtTable;
    private JACNodeDatabaseHandler dbh;
    private List<RecentTripsNode> rtNode;
    private  List<DisplayNodes> displayNodesList;
    public RecentTripsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        jacNodeData dataNodes = new jacNodeData();
        View root =  inflater.inflate(R.layout.fragment_recent_trips, container, false);
        recView = root.findViewById(R.id.recyclerView);
        dbh = new JACNodeDatabaseHandler(getContext());
        displayNodesList = new ArrayList<>();
        try {
            rtNode = dbh.getRecentTripsTable().readAll();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        nodes = dataNodes.getTestData();
        for (RecentTripsNode rtN:rtNode) {
            DisplayNodes displayNodes = new DisplayNodes();
            for (JACNode node : nodes) {
                if(rtN.getFromNode().equals(node.getLocation()))
                    displayNodes.setFromNode(node);
                else if(rtN.getToNode().equals(node.getLocation()))
                    displayNodes.setToNode(node);
            }
            displayNodesList.add(displayNodes);
        }
        if(displayNodesList != null) {
            NoteAdapter adapter = new NoteAdapter(displayNodesList, this); //set note adapter
            recView.setAdapter(adapter); //set adapter
        }
        mGridLayoutManager = new GridLayoutManager(getContext(), 1); //set grid layout, 2 columns
        recView.setLayoutManager(mGridLayoutManager); //set layout of rec View
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recView.getContext(),
                mGridLayoutManager.getOrientation());
        recView.addItemDecoration(dividerItemDecoration);
        //mGridLayoutManager = new GridLayoutManager(getContext(), 2); //set grid layout, 2 columns
       // recView.setLayoutManager(mGridLayoutManager); //set layout of rec View
        return root;
    }

    private class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
        private List<DisplayNodes> data;
        private Fragment fragment;
        public NoteAdapter(List<DisplayNodes> data, Fragment fragment){
            this.data = data;
            this.fragment = fragment;
        }
        @NonNull
        @Override
        public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View root = LayoutInflater.from((parent.getContext())).inflate(R.layout.list_notes_content,parent, false);
            NoteViewHolder holder = new NoteViewHolder(root, parent, fragment);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
            if(data !=null)
                holder.set(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTextView;
        private final View root;

        public NoteViewHolder(@NonNull View root, @NonNull ViewGroup rv, Fragment fragment) {
            super(root);
            this.root = root;
            titleTextView = root.findViewById(R.id.title_textView);
        }
        public void set(final DisplayNodes node){
            String holder = node.getFromNode().getLocation() + " TO " + node.getToNode().getLocation();
            titleTextView.setText(holder); //set the title
            Resources res = root.getResources(); //get resources
            root.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Intent intent = new Intent(getContext(), DisplayPathActivity.class);
                    Destination des = Destination.getTheDes();
                    des.setFrom(node.getFromNode());
                    des.setTo(node.getToNode());
                   intent.putExtra("from", node.getFromNode().getLocation());
//                    intent.putExtra("to",  node2.getLocation());
                    startActivityForResult(intent, 1);
                    return true;
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}

