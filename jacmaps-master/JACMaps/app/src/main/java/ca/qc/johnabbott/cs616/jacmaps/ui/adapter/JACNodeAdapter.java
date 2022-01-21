package ca.qc.johnabbott.cs616.jacmaps.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import ca.qc.johnabbott.cs616.jacmaps.R;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.JACNode;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.Path;

public class JACNodeAdapter extends RecyclerView.Adapter<JACNodeViewHolder> {
//TODO: Currently only works with 1 floor...
    private List<JACNode> pathData;
    private List<Path>  pathOutput;

    public JACNodeAdapter(List<JACNode> jacNodeList){
        this.pathData = jacNodeList;
        this.pathOutput = new LinkedList<>();
        Path temp = new Path();
        for(int i =0; i < pathData.size(); i++) {
            if(i==0){
                temp.setStartLocation(pathData.get(i).getLocation());
            }
            temp.addNode(pathData.get(i));
        }
        temp.setEndLocation(pathData.get(pathData.size()-1).getLocation());
        pathOutput.add(temp);
    }

    @NonNull
    @Override
    public JACNodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.path_item, parent,false);
        JACNodeViewHolder holder = new JACNodeViewHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull JACNodeViewHolder holder, int position) {
        //TODO: Use 'position' to set floors to each view
        //TODO: Currently only works with 1 floor
        holder.set(pathOutput.get(position));
    }

    @Override
    public int getItemCount() {
        return pathOutput.size();
    }
}
