package app.spartantutor.com.spartantutor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

/**
 * Created by Aaron on 12/14/2017.
 */

public class EditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView disName, status;
    EditText classChoice;
    Button saveChanges;
    String classCh, tutStatus;
    String [] statusList={"No Changes","Looking for Tutor","Looking to be a Tutor"};
    Spinner spinner;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_profile);

        classChoice = (EditText)findViewById(R.id.class_choice);
        saveChanges = (Button)findViewById(R.id.save_changes);
        disName = (TextView)findViewById(R.id.DisplayNameEdit);
        status = (TextView)findViewById(R.id.user_status_edit);

        disName.setText(UserDetails.username);
        status.setText(UserDetails.status);

        spinner = (Spinner)findViewById(R.id.status_drop_down_bar_profile);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfile.this, android.R.layout.simple_dropdown_item_1line, statusList);

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(EditProfile.this);

        saveChanges.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                classCh = classChoice.getText().toString();

                if (classCh.equals("")) {
                    classChoice.setError("Class choice cannot be blank.");
                } else if (!classCh.matches("[A-Za-z0-9]+")) {
                    classChoice.setError("Class choice must only contain letters and numbers.");
                } else {
                    String url = "https://spartantutor-d15cf.firebaseio.com/users.json";

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Firebase reference = new Firebase("https://spartantutor-d15cf.firebaseio.com/users");
                            reference.child(UserDetails.username).child("class").setValue(classCh);
                            reference.child(UserDetails.username).child("status").setValue(tutStatus);
                            Toast.makeText(EditProfile.this, "Successfully Updated!", Toast.LENGTH_LONG).show();
                            setResult(RESULT_OK,null);
                            finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError);
                        }
                    });
                    RequestQueue queue = Volley.newRequestQueue(EditProfile.this);
                    queue.add(request);
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id){
        switch (position) {
            case 0:
                break;
            case 1:
                tutStatus = "Tutee";
                break;
            case 2:
                tutStatus = "Tutor";
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent){

    }

}
