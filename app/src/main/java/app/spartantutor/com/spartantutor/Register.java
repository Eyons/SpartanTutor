package app.spartantutor.com.spartantutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aaron on 12/11/2017.
 */

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText username, password;
    Button registerButton;
    String user, pass, tutStatus;
    TextView login;
    String [] statusList={"Looking for Tutor", "Looking to be a Tutor"};
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        registerButton = (Button)findViewById(R.id.registerButton);
        login = (TextView)findViewById(R.id.login);

        spinner = (Spinner)findViewById(R.id.status_drop_down_bar);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Register.this, android.R.layout.simple_dropdown_item_1line, statusList);

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        /*MaterialBetterSpinner betterSpinner = (MaterialBetterSpinner)findViewById(R.id.status_drop_down_bar);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,StatusList);

        betterSpinner.setAdapter(arrayAdapter);*/

        Firebase.setAndroidContext(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();

                if(user.equals("")){
                    username.setError("Username cannot be blank.");
                }
                else if(pass.equals("")){
                    password.setError("Password cannot be blank.");
                }
                else if(!user.matches("[A-Za-z0-9]+")){
                    username.setError("Only Letters and Numbers allowed!");
                }
                else if(user.length()<5){
                    username.setError("Username must be at least 5 characters long");
                }
                else if(pass.length()<5){
                    password.setError("Password must be at least 5 characters long");
                }
                else {
                    final ProgressDialog pd = new ProgressDialog(Register.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    String url = "https://spartantutor-d15cf.firebaseio.com/users.json";

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            Firebase reference = new Firebase("https://spartantutor-d15cf.firebaseio.com/users");

                            if(s.equals("null")) {
                                reference.child(user).child("password").setValue(pass);
                                reference.child(user).child("status").setValue(tutStatus);
                                Toast.makeText(Register.this, "Registration was successful!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else {
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if (!obj.has(user)) {
                                        reference.child(user).child("password").setValue(pass);
                                        reference.child(user).child("status").setValue(tutStatus);
                                        Toast.makeText(Register.this, "Registration was successful!", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(Register.this, "Username already taken", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }

                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError );
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(Register.this);
                    rQueue.add(request);
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id){
        switch (position) {
            case 0:
                tutStatus = "Tutee";
                break;
            case 1:
                tutStatus = "Tutor";
                break;
            default:
                tutStatus = "Tutee";
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent){

    }
}
