package dmsmith.example.org.ga_challenge;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    //Adapter used to display the grocery items in the list
    ArrayAdapter groceryCursor;

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
        final ArrayList<String> groceryItems = new ArrayList<String>();
        Button addButton = (Button) findViewById(R.id.addButton);

        //Bind an adapter to the arraylist and set the listview to use this adapter
        groceryCursor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groceryItems);
        groceryList.setAdapter(groceryCursor);

        //Set an item listener if a grocery item is clicked you can switch to edit the item
        groceryList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(MainActivity.this, EditItem.class);
                        i.putExtra("editItem", groceryItems.get(position));
                        startActivity(i);
                    }
                }
        );

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditItem.class);
            }
        });
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
}
