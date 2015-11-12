package dmsmith.example.org.ga_challenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dmsmith.example.org.ga_challenge.MainActivity.groceryNode;

import java.util.ArrayList;
import java.util.HashMap;

public class EditItem extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //Initialize UI Elements
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final EditText description = (EditText) findViewById(R.id.description_edit);
        final EditText quantity = (EditText) findViewById(R.id.quantity_edit);

        //Grab GroceryList from MainActivity Activity
        Bundle extras = getIntent().getExtras();

        final ArrayList<String> groceryList = (ArrayList<String>) extras.get("groceryList");
        final HashMap<String, groceryNode> groceryItems = (HashMap<String, groceryNode>) extras.get("groceries");

        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    //If the Quantity is left empty just add 1 to the node property
                    if (quantity.getText().toString().equals(null) || quantity.getText().toString().equals("")) {
                        quantity.setText(Integer.toString(1));
                    }
                    groceryNode node =  new MainActivity().new groceryNode(editText.getText().toString(), description.getText().toString(), Integer.parseInt(quantity.getText().toString()));
                    if (!node.name.equals(null) && !node.name.equals("")) {
                        groceryList.add(node.name);
                        groceryItems.put(node.name, node);
                        Intent i = new Intent(EditItem.this, MainActivity.class);
                        i.putExtra("groceryList", groceryList);
                        i.putExtra("groceries", groceryItems);
                        Toast.makeText(getApplication().getBaseContext(), node.name + " added!",
                                Toast.LENGTH_LONG).show();
                        startActivity(i);
                        //Finalize the Activity to free resources
                        try {
                            finalize();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                    else {
                        Toast.makeText(getApplication().getBaseContext(), "Please Enter a Name Before Trying to Submit",
                                Toast.LENGTH_LONG).show();
                    }
            }
        });

        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditItem.this, MainActivity.class);
                startActivity(i);
                try {
                    finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }
}
