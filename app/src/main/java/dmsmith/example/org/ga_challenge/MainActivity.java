package dmsmith.example.org.ga_challenge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {
    //Adapter used to display the grocery items in the list
    ArrayAdapter groceryCursor;

    //Popup Window Global Variables
    PopupWindow popupWindow;
    RelativeLayout relativeLayout;
    EditText groceryItemName;
    EditText description;
    EditText quantity;
    Button deleteButton;
    Button saveButton;
    CheckBox checkBox;

    //Grocery List Global Variables
    ArrayList<groceryNode> groceryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Progress Bar displayed while list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true);
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);

        //Initialize the UI objects
        ListView groceryList = (ListView) findViewById(R.id.groceryList);
        groceryItems = new ArrayList<groceryNode>();
        Button addButton = (Button) findViewById(R.id.addButton);

        //Initialize the PopupWindow
        initializePopupWindow();

        //Bind an adapter to the arraylist and set the listview to use this adapter
        groceryCursor = new ArrayAdapter<groceryNode>(this, android.R.layout.simple_list_item_1, groceryItems);
        groceryList.setAdapter(groceryCursor);

        //Set an item listener if a grocery item is clicked you can switch to edit the item
        groceryList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        setupPopupWindow(position);
                    }
                }
        );

        //Switch to the new Activity if the user clicks the Add Button. Save the state of this activity
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditItem.class);
                i.putExtra("grocerylist", groceryItems);
                startActivity(i);
            }
        });
    }


    private void initializePopupWindow() {
        popupWindow = new PopupWindow(this);

        relativeLayout = (RelativeLayout) this.findViewById(R.id.popupWindow);
        popupWindow.setContentView(relativeLayout);

        deleteButton = (Button) this.findViewById(R.id.deleteButton);
        saveButton = (Button) this.findViewById(R.id.saveButton);
        description = (EditText) this.findViewById(R.id.description);
        groceryItemName = (EditText) this.findViewById(R.id.groceryItemName);
        quantity = (EditText) this.findViewById(R.id.quantity);
        checkBox = (CheckBox) this.findViewById(R.id.checkbox);
    }

    private void setupPopupWindow(final int position) {
        final groceryNode item = groceryItems.get(position);
        groceryItemName.setText(item.name);
        description.setText(item.description);
        quantity.setText(item.quantity);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setName(groceryItemName.getText().toString());
                item.setDescription(description.getText().toString());
                item.setQuantity(Integer.parseInt(quantity.getText().toString()));
                item.setChecked(checkBox.isChecked());
                groceryItems.add(position, item);
                Toast.makeText(getApplication().getBaseContext(), "Item Modified",
                        Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groceryItems.remove(position);
                Toast.makeText(getApplication().getBaseContext(), "Item Deleted",
                        Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });

        popupWindow.showAsDropDown(this.getCurrentFocus());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class groceryNode {
        String name;
        String description;
        int quantity;
        boolean isChecked;

        public groceryNode(String name, String description, int quantity) {
            this.name = name;
            this.description = description;
            this.quantity = quantity;
        }

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
