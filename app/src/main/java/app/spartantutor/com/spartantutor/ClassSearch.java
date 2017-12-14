package app.spartantutor.com.spartantutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Aaron on 12/14/2017.
 */

public class ClassSearch extends AppCompatActivity{
    ListView userList;
    TextView noUsersText;
    EditText searchArea;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    ImageView searchButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_search);

        userList = (ListView) findViewById(R.id.searchList);
        noUsersText = (TextView) findViewById(R.id.noUsersFoundClass);
        searchButton = (ImageView)findViewById(R.id.searchButton);
        searchArea = (EditText) findViewById(R.id.searchArea);


        /*pd = new ProgressDialog(ClassSearch.this);
        pd.setMessage("Loading...");
        pd.show();*/

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://spartantutor-d15cf.firebaseio.com/users.json";
                final String searchText = searchArea.getText().toString();
                searchText.toUpperCase();
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        doOnSuccess(s, searchText);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("" + error);
                    }
                });

                RequestQueue queue = Volley.newRequestQueue(ClassSearch.this);
                queue.add(request);
            }
        });

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = al.get(position);
                startActivity(new Intent(ClassSearch.this, Profile.class));
            }
        });
    }

    public void doOnSuccess(String s, String searchText){
        try{
            al.clear();
            totalUsers = 0;
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();
                if(obj.getJSONObject(key).getString("class").contains(searchText) && !(UserDetails.username.equals(key))){
                    al.add(key);
                    totalUsers++;
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

        if(totalUsers == 0){
            noUsersText.setVisibility(View.VISIBLE);
            userList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            userList.setVisibility(View.VISIBLE);
            userList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }

        /*pd.dismiss();*/
    }
}
