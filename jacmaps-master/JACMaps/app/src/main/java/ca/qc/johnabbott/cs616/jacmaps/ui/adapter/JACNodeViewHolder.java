package ca.qc.johnabbott.cs616.jacmaps.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.qc.johnabbott.cs616.jacmaps.R;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.JACNode;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.Path;

public class JACNodeViewHolder extends RecyclerView.ViewHolder {
    //UI
    private TextView title;
    private ImageView map;
    //Adapter
    private JACNodeAdapter jacNodeAdapter;
    //Data
    private String path;
    //root
    private View root;

    public JACNodeViewHolder(@NonNull View root) {
        super(root);
        //grab ui
        title = root.findViewById(R.id.displayTitle_textView);
        map = root.findViewById(R.id.map_imageView);
        this.root = root;
    }

    public void set(Path path){
        List<JACNode> jacNodeList = path.getNodes();
        //node
        Paint nodePaint = new Paint();
        nodePaint.setColor(Color.BLACK);
        nodePaint.setStyle(Paint.Style.FILL);
        //start color set
        Paint startPaint = new Paint();
        startPaint.setColor(Color.BLUE);
        startPaint.setStyle(Paint.Style.FILL);
        //path set
        Paint pathPaint = new Paint();
        pathPaint.setColor(Color.BLACK);
        pathPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        pathPaint.setStrokeWidth(15);
        title.setText(path.getTitle());
        //Draw Path
        Bitmap image = BitmapFactory.decodeResource(root.getContext().getResources(),R.drawable.p3);
        Bitmap temp = Bitmap.createBitmap(image.getWidth(),image.getHeight(),Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(temp);
        canvas.drawBitmap(image,0,0,null);
        JACNode lastNode = null;
        for(int i =0; i < jacNodeList.size(); i++){
            Integer theX = jacNodeList.get(i).getGuiG().getX();
            Integer theY = jacNodeList.get(i).getGuiG().getY();
            if(lastNode == null){
                canvas.drawCircle(theX,theY,15,startPaint);
                if(jacNodeList.get(i).getLocation() == jacNodeList.get(jacNodeList.size()-1).getLocation()){
                    title.setText("Looks like you're already here!");
                    break;
                }
            }
            else{
                canvas.drawCircle(theX,theY,15,nodePaint);
                //TODO update to drawPath
                canvas.drawLine(lastNode.getGuiG().getX(),lastNode.getGuiG().getY(),theX,theY,pathPaint);
            }
            lastNode = jacNodeList.get(i);
        }

        //map.setImageBitmap(image);
        map.setImageDrawable(new BitmapDrawable(root.getContext().getResources(),temp));
    }
}
