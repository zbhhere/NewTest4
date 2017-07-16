package com.example.zbh.newtest4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.zbh.newtest4.UserName.UserName;

public class Logout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        Button logout= (Button) findViewById(R.id.logout_btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SessionApplication sessionApplication= new SessionApplication();
//                sessionApplication.setFlag(0);
//                sessionApplication.setUsername("");
                UserName.setFlag(0);
                UserName.setUsername("");
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        Button search_his= (Button) findViewById(R.id.history);
        search_his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SeachSaveAndHistory.class);
                intent.putExtra("style","history");
                startActivity(intent);
            }
        });

        Button search_save= (Button) findViewById(R.id.save);
        search_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SeachSaveAndHistory.class);
                intent.putExtra("style","save");
                startActivity(intent);
            }
        });
    }
}
