package app.spartantutor.com.spartantutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Aaron on 12/14/2017.
 */

public class MainMenu extends AppCompatActivity {
    Button myProfile, userList, classSearch, logout;
    TextView welcomeMsg;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();

        welcomeMsg = (TextView)findViewById(R.id.welcome_ins);
        welcomeMsg.setText("Welcome, " + UserDetails.username);

        myProfile = (Button)findViewById(R.id.my_profile);
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDetails.chatWith = UserDetails.username;
                startActivity(new Intent(MainMenu.this, Profile.class));
            }
        });

        userList = (Button)findViewById(R.id.user_list);
        userList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, Users.class));
            }
        });

        classSearch = (Button)findViewById(R.id.class_searc);
        classSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, ClassSearch.class));
            }
        });

        logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, Login.class));
            }
        });
    }
}
