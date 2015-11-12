package dmsmith.example.org.ga_challenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity implements Serializable {
    //Adapter used to display the grocery items in the list
    ArrayAdapter groceryCursor;

    //Popup Window Global Variables - Set as Global Variables to avoid adding "final" modifier
    PopupWindow popupWindow;
    EditText groceryItemName;
    EditText description;
    EditText quantity;
    Button deleteButton;
    Button addButton;
    Button saveButton;
    CheckBox checkBox;

    //Grocery List Global Variables - Set as Global Variables to avoid adding "final" modifier
    ArrayList<String> groceryItems;
    /* Since we're using a listView we don't want to store the entire groceryNode
    object in the Adapter as this will be illegible to the user. Therefore, in the Adapter we store only the
    groceryNode name and use a HashMap to retrieve the other node properties when needed.
     */
    HashMap<String, groceryNode> index;
    ListView groceryList;
    TextView groceryListHeader;
    groceryNode grocery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Initialize the UI objects and Global Variables
        groceryList = (ListView) findViewById(R.id.groceryList);
        groceryListHeader = (TextView) findViewById(R.id.groceryListHeader);
        groceryItems = new ArrayList<String>();
        index = new HashMap<String, groceryNode>();
        addButton = (Button) findViewById(R.id.addButton);


        //Retrieve Data Passed from Edit Item Activity
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            groceryItems = (ArrayList<String>) extras.get("groceryList");
            index = (HashMap<String, groceryNode>) extras.get("groceries");
        }

        //Create the Cursor for the List View
        loadGroceryCursor(groceryItems);

        //Set an item click listener to edit the grocery item
        groceryList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        onPause();
                        initializePopupWindow(view, position);
                    }
                }
        );

        //Switch to Edit Item Activity so user can add grocery item
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditItem.class);
                i.putExtra("groceryList", groceryItems);
                i.putExtra("groceries", index);
                startActivity(i);
            }
        });
    }

    private void loadGroceryCursor(ArrayList<String> items) {
        //Bind an adapter to the arraylist and set the listview to use this adapter
        groceryCursor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        groceryList.setAdapter(groceryCursor);
    }

    //Method to initialize and instantiate the pop up window
    private void initializePopupWindow(View view, final int position) {

        //Inflate the view
        View popupView = getLayoutInflater().inflate(R.layout.popupwindow, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //Set Popup Variables
        deleteButton = (Button) popupView.findViewById(R.id.deleteButton);
        saveButton = (Button) popupView.findViewById(R.id.saveButton);
        description = (EditText) popupView.findViewById(R.id.description);
        groceryItemName = (EditText) popupView.findViewById(R.id.groceryItemName);
        quantity = (EditText) popupView.findViewById(R.id.quantity);
        checkBox = (CheckBox) popupView.findViewById(R.id.checkbox);

        //Popup Window needs to be focusable in order to interact with it
        popupWindow.setFocusable(true);

        //Overshadow the current Activity with the popup window
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1] + view.getHeight());

        //Manipulation of the UI elements
        final String item = groceryItems.get(position);
        grocery = index.get(item);
        groceryItemName.setText(grocery.name);
        if (grocery.description != null) {
            description.setText(grocery.description);
        }
        quantity.setText(Integer.toString(grocery.quantity));
        checkBox.setChecked(grocery.isChecked);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                grocery.setName(groceryItemName.getText().toString());
                grocery.setDescription(description.getText().toString());
                grocery.setQuantity(Integer.parseInt(quantity.getText().toString()));
                grocery.setChecked(checkBox.isChecked());
                groceryItems.set(position, grocery.name);
                index.remove(item);
                index.put(grocery.name, grocery);
                Toast.makeText(getApplication().getBaseContext(), grocery.name + " modified!",
                        Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                onResume();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groceryItems.remove(position);
                Toast.makeText(getApplication().getBaseContext(), grocery.name + " removed!",
                        Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                onResume();
            }
        });
    }

    //onPause -> Used when Activity goes out of focus partially
    @Override
    public void onPause() {
        super.onPause();

        groceryList.setVisibility(View.GONE);
        groceryListHeader.setVisibility(View.GONE);
        addButton.setVisibility(View.GONE);
    }

    //onResume -> used when Activity comes back into focus
    @Override
    public void onResume() {
        super.onResume();

        groceryList.setVisibility(View.VISIBLE);
        groceryListHeader.setVisibility(View.VISIBLE);
        addButton.setVisibility(View.VISIBLE);

        loadGroceryCursor(groceryItems);
    }

    //Node Class created to store properties of the grocery object
    public class groceryNode implements Serializable {
        String name;
        String description;
        int quantity;
        boolean isChecked;

        //Constructor
        public groceryNode(String name, String description, int quantity) {
            this.name = name;
            this.description = description;
            this.quantity = quantity;
        }

        //Set Methods
        public void setName(String name) {
            this.name = name;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        public void setChecked(boolean bool) {
            this.isChecked = bool;
        }
    }
}
