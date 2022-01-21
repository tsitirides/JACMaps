package ca.qc.johnabbott.cs616.jacmaps.ui.displayPath;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.View;

import ca.qc.johnabbott.cs616.jacmaps.R;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.Destination;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.JACNode;

public class DisplayPathActivity extends AppCompatActivity {

    private DisplayPathActivityFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_path);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment = (DisplayPathActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        Destination des = Destination.getTheDes();
        Intent intent = getIntent();
        if(intent.hasExtra("from")){

           fragment.setStart(des.getFrom());
           fragment.setEnd(des.getTo());
           des.reset();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

        }
        else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){

        }
    }
}
