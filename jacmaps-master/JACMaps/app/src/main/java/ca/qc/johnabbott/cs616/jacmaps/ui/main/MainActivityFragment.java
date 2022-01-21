package ca.qc.johnabbott.cs616.jacmaps.ui.main;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ca.qc.johnabbott.cs616.jacmaps.DestinationPickerDialogFragment;
import ca.qc.johnabbott.cs616.jacmaps.R;
import ca.qc.johnabbott.cs616.jacmaps.RecentTripsActivity;
import ca.qc.johnabbott.cs616.jacmaps.model.JACNodeDatabaseHandler;
import ca.qc.johnabbott.cs616.jacmaps.model.RecentTripsDatabaseHandler;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.Destination;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.JACNode;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.RecentTripsNode;
import ca.qc.johnabbott.cs616.jacmaps.sqlite.DatabaseException;
import ca.qc.johnabbott.cs616.jacmaps.sqlite.Table;
import ca.qc.johnabbott.cs616.jacmaps.ui.displayPath.DisplayPathActivity;
/**
 * Main activity of the app.
 * Opens up Destination Picker or Recent Trips
 * dialog fragments, and creates an intent to
 * Display PAth Activity.
 *
 * @autor Vincent Cordiano
 * @author Knealand Yates
 * @author Brayden Tsitirides
 */
public class MainActivityFragment extends Fragment {

    // UI Components
    private Button destination_button;
    private Button fab;
    private JACNodeDatabaseHandler dbh;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        // Initialize UI Components
        destination_button = root.findViewById(R.id.destination_button);
        fab = root.findViewById(R.id.RT_btn);
        dbh = new JACNodeDatabaseHandler(getContext());
        Table<RecentTripsNode> rtTable = dbh.getRecentTripsTable();
        // Destination Button Listener
        destination_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DestinationPickerDialogFragment destinationPickerDialogFragment = new DestinationPickerDialogFragment();
                destinationPickerDialogFragment.setOnDestinationPickedListener(new DestinationPickerDialogFragment.OnDestinationPickedListener() {
                    @Override
                    public void OnDestinationPicked(JACNode from, JACNode to) {
                        Destination des = Destination.getTheDes();
                        Intent intent = new Intent(getContext(), DisplayPathActivity.class);
                        intent.putExtra("from",from.getLocation());
                        des.setFrom(from);
                        des.setTo(to);
                        startActivity(intent);
                        fab.setEnabled(true);
                    }
                });
                destinationPickerDialogFragment.show(getFragmentManager(), "destination-picker");
            }
        });

        // Recent Trips Button Listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RecentTripsActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        return root;
    }
}
