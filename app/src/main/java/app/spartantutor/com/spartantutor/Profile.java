package app.spartantutor.com.spartantutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by Aaron on 12/12/2017.
 */

public class Profile extends AppCompatActivity{
    Button sendMessage;
    String user;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        user = UserDetails.chatWith;
        //ref = new Firebase("https://spartantutor-d15cf.firebaseio.com/users/" + UserDetails.chatWith + "/status");

        final TextView uStat = (TextView)findViewById(R.id.user_status);
        TextView dName = (TextView)findViewById(R.id.DisplayName);
        final TextView lfClass = (TextView)findViewById(R.id.lf_class);
        dName.setText(user);


        Firebase.setAndroidContext(this);
        String url = "https://spartantutor-d15cf.firebaseio.com/users.json";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);
                    UserDetails.status = obj.getJSONObject(user).getString("status");
                    uStat.setText(obj.getJSONObject(user).getString("status"));
                    if(uStat.getText().equals("Tutor")){
                        lfClass.setText("Looking to Tutor " + obj.getJSONObject(user).getString("class"));
                    }
                    else{
                        lfClass.setText("Looking for a tutor for " + obj.getJSONObject(user).getString("class"));
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

        if(UserDetails.username == UserDetails.chatWith){
            Button editProfile = (Button)findViewById(R.id.edit_profile);
            editProfile.setVisibility(View.VISIBLE);
            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Profile.this, EditProfile.class);
                    startActivityForResult(intent, 1);
                }
            });
        } else{
            sendMessage = (Button) findViewById(R.id.send_button);
            sendMessage.setVisibility(View.VISIBLE);
            sendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Profile.this, Chat.class));
                }
            });
        }


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Intent refresh = new Intent(Profile.this, Profile.class);
            startActivity(refresh);
            this.finish();
        }
    }
}
