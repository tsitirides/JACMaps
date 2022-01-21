package ca.qc.johnabbott.cs616.jacmaps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import ca.qc.johnabbott.cs616.jacmaps.model.JACNodeDatabaseHandler;
import ca.qc.johnabbott.cs616.jacmaps.model.RecentTripsDatabaseHandler;
import ca.qc.johnabbott.cs616.jacmaps.model.data.jacNodeData;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.JACNode;
import ca.qc.johnabbott.cs616.jacmaps.model.jacNode.RecentTripsNode;
import ca.qc.johnabbott.cs616.jacmaps.sqlite.DatabaseException;
import ca.qc.johnabbott.cs616.jacmaps.sqlite.Table;

/**
 * A dialog fragment that lets the user choose
 * a starting point to a destination.
 *
 * Listener returns a starting JACNode location
 * and a destination JACNode location.
 *
 * @author Vincent Cordiano
 */
public class DestinationPickerDialogFragment extends DialogFragment {

    // Event Listener Interface
    public interface OnDestinationPickedListener {
        void OnDestinationPicked(JACNode from, JACNode to);
    }

    // References To UI Components
    private LinearLayout starting_floor_layout;
    private LinearLayout starting_room_layout;
    private LinearLayout destination_building_layout;
    private LinearLayout destination_floor_layout;
    private LinearLayout destination_room_layout;
    private TextView destination_textView;
    private Spinner starting_building_spinner;
    private Spinner starting_floor_spinner;
    private Spinner starting_room_spinner;
    private Spinner destination_building_spinner;
    private Spinner destination_floor_spinner;
    private Spinner destination_room_spinner;
    private Button  cancel_button;
    private Button  go_button;
    private Table<RecentTripsNode> recentTripsTable;
    // Nodes to return
    private JACNode from;
    private JACNode to;

    // JAC data
    private jacNodeData data;

    // Adapters
    private ArrayAdapter<String> startBuildAdapter;
    private ArrayAdapter<String> startFloorAdapter;
    private ArrayAdapter<JACNode> startRoomAdapter;
    private ArrayAdapter<String> destBuildAdapter;
    private ArrayAdapter<String> destFloorAdapter;
    private ArrayAdapter<JACNode> destRoomAdapter;

    // Spinner items
    private List<String> buildings;
    private List<String> starting_floors;
    private List<JACNode> starting_rooms;
    private List<String> destination_floors;
    private List<JACNode> destination_rooms;

    // Accept a handler
    private OnDestinationPickedListener onDestinationPickedListener;

    // Database
    private JACNodeDatabaseHandler dbh;
    private RecentTripsDatabaseHandler rtDbh;

    // Allow user to register their handler
    public void setOnDestinationPickedListener(OnDestinationPickedListener onDestinationPickedListener) {
        this.onDestinationPickedListener = onDestinationPickedListener;
    }

    /**
     * Create a destination picker dialog
     */
    public DestinationPickerDialogFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_destination_picker, container, false);

        /** Initialize UI References **/

        // Layouts
        starting_floor_layout = root.findViewById(R.id.starting_floor_LinearLayout);
        starting_room_layout = root.findViewById(R.id.starting_room_LinearLayout);

        destination_building_layout = root.findViewById(R.id.destination_building_LinearLayout);
        destination_floor_layout = root.findViewById(R.id.destination_floor_LinearLayout);
        destination_room_layout = root.findViewById(R.id.destination_room_LinearLayout);

        // Spinners
        starting_building_spinner = root.findViewById(R.id.starting_building_spinner);
        starting_floor_spinner = root.findViewById(R.id.starting_floor_spinner);
        starting_room_spinner = root.findViewById(R.id.starting_room_spinner);

        destination_building_spinner = root.findViewById(R.id.destination_building_spinner);
        destination_floor_spinner = root.findViewById(R.id.destination_floor_spinner);
        destination_room_spinner = root.findViewById(R.id.destination_room_spinner);

        // Text View
        destination_textView = root.findViewById(R.id.destination_textView);

        // Buttons
        cancel_button = root.findViewById(R.id.cancel_button);
        go_button = root.findViewById(R.id.go_button);

        // Initialize jacNodeData
        data = new jacNodeData();

        // Database
        dbh = new JACNodeDatabaseHandler(getContext());
        /** Listener **/

        // Cancel Button Listener
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // Go Button Listener
        go_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDestinationPickedListener != null) {
                    RecentTripsNode rTripsNode = new RecentTripsNode();
                    rTripsNode.setFromNode(from.getLocation());
                    rTripsNode.setToNode(to.getLocation());
                    rTripsNode.setId(to.getId());
                    try {
                        dbh.getRecentTripsTable().create(rTripsNode);
                    } catch (DatabaseException e) {
                        e.printStackTrace();
                    }
                    onDestinationPickedListener.OnDestinationPicked(from, to);
                }
                dismiss();
            }
        });
        /** Starting building **/
        buildings = data.getBuildings();
        startBuildAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item_destination, R.id.destination_item_textView);
        startBuildAdapter.addAll(buildings);
        starting_building_spinner.setAdapter(startBuildAdapter);
        starting_building_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String value = adapterView.getItemAtPosition(i).toString();

                if (value != "")
                    starting_floor_layout.setVisibility(View.VISIBLE);

                switch (value) {

                    case "":
                        startFloorAdapter.clear();
                        starting_floor_spinner.setAdapter(startFloorAdapter);
                        startRoomAdapter.clear();
                        starting_room_spinner.setAdapter(startRoomAdapter);
                        from = null;
                        break;

                    case "Penfield":
                        starting_floors = data.getPenfieldFloors();
                        startFloorAdapter.clear();
                        startFloorAdapter.addAll(starting_floors);
                        starting_floor_spinner.setAdapter(startFloorAdapter);
                        break;
                }

                if (validNodes())
                    go_button.setEnabled(true);
                else
                    go_button.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /** Starting floor **/
        startFloorAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item_destination, R.id.destination_item_textView);
        starting_floor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String value = adapterView.getItemAtPosition(i).toString();

                // No actions taken if not selected
                if (value != "")
                    starting_room_layout.setVisibility(View.VISIBLE);

                switch (value) {

                    case "":
                        startRoomAdapter.clear();
                        starting_room_spinner.setAdapter(startRoomAdapter);
                        from = null;
                        break;

                    case "3rd":
                        try {
                            starting_rooms = dbh.getJACNodeTable().readAll();
                        } catch (DatabaseException e) {
                            e.printStackTrace();
                        }
                        for (int c = 0; c < starting_rooms.size(); c++) {
                            if (starting_rooms.get(c) != null) {
                                if (starting_rooms.get(c).getBuilding().equals("hp"))
                                    starting_rooms.remove(c);
                            }
                        }
                        starting_rooms.remove(starting_rooms.size()-1);
                        startRoomAdapter.clear();
                        startRoomAdapter.addAll(starting_rooms);
                        starting_room_spinner.setAdapter(startRoomAdapter);
                        break;
                }

                if (validNodes())
                    go_button.setEnabled(true);
                else
                    go_button.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /** Starting room **/
        startRoomAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item_destination, R.id.destination_item_textView);
        starting_room_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                JACNode node = (JACNode) adapterView.getItemAtPosition(i);

                if (to != null && node == to) {
                    starting_room_spinner.setAdapter(startRoomAdapter);
                    Toast.makeText(getContext(), "Locations are the same", Toast.LENGTH_LONG).show();
                    from = null;
                }
                else if (node.getBuilding() != null) {
                    destination_textView.setVisibility(View.VISIBLE);
                    destination_building_layout.setVisibility(View.VISIBLE);

                    from = node;
                }

                if (validNodes())
                    go_button.setEnabled(true);
                else
                    go_button.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /** destination building **/
        destBuildAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item_destination, R.id.destination_item_textView);
        destBuildAdapter.addAll(buildings);
        destination_building_spinner.setAdapter(destBuildAdapter);
        destination_building_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String value = adapterView.getItemAtPosition(i).toString();

                // No actions taken if not selected
                if (value != "")
                    destination_floor_layout.setVisibility(View.VISIBLE);

                switch (value) {

                    case "":
                        destFloorAdapter.clear();
                        destination_floor_spinner.setAdapter(destFloorAdapter);
                        destRoomAdapter.clear();
                        destination_room_spinner.setAdapter(destRoomAdapter);
                        to = null;
                        break;

                    case "Penfield":
                        destination_floors = data.getPenfieldFloors();
                        destFloorAdapter.clear();
                        destFloorAdapter.addAll(destination_floors);
                        destination_floor_spinner.setAdapter(destFloorAdapter);
                        break;
                }

                if (validNodes())
                    go_button.setEnabled(true);
                else
                    go_button.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /** destination floor **/
        destFloorAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item_destination, R.id.destination_item_textView);
        destination_floor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String value = adapterView.getItemAtPosition(i).toString();

                // No actions taken if not selected
                if (value != "")
                    destination_room_layout.setVisibility(View.VISIBLE);

                switch (value) {

                    case "":
                        destRoomAdapter.clear();
                        destination_room_spinner.setAdapter(destRoomAdapter);
                        to = null;
                        break;

                    case "3rd":
                        try {
                            destination_rooms = dbh.getJACNodeTable().readAll();
                        } catch (DatabaseException e) {
                            e.printStackTrace();
                        }
                        for (int c = 0; c < destination_rooms.size(); c++) {
                            if (destination_rooms.get(c) != null) {
                                if (destination_rooms.get(c).getBuilding().equals("hp"))
                                    destination_rooms.remove(c);
                            }
                        }
                        destination_rooms.remove(destination_rooms.size()-1);
                        destRoomAdapter.clear();
                        destRoomAdapter.addAll(destination_rooms);
                        destination_room_spinner.setAdapter(destRoomAdapter);
                        break;
                }

                if (validNodes())
                    go_button.setEnabled(true);
                else
                    go_button.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /** destination floor **/
        destRoomAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item_destination, R.id.destination_item_textView);
        destination_room_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                JACNode node = (JACNode) adapterView.getItemAtPosition(i);

                if (from != null && node == from) {
                    destination_room_spinner.setAdapter(destRoomAdapter);
                    Toast.makeText(getContext(), "Locations are the same", Toast.LENGTH_LONG).show();
                    to = null;
                }
                else if (node.getBuilding() != null)
                    to = node;

                if (validNodes())
                    go_button.setEnabled(true);
                else
                    go_button.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return root;
    }

    /**
     *  Will enable 'GO' button ONLY IF
     *  both JACNodes are valid.
     *
     * @return True if both nodes are populated, False otherwise.
     */
    private boolean validNodes() {
        return from != null && to != null;
    }
}
