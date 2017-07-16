package com.example.zbh.newtest4.UserName;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zbh.newtest4.HttpUrl.HttpAddr;
import com.example.zbh.newtest4.HttpUrl.HttpUtil3;
import com.example.zbh.newtest4.R;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AdminContent extends AppCompatActivity {
String statue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_content);
        final EditText photourl= (EditText) findViewById(R.id.video_url_admin);
        EditText photoname= (EditText) findViewById(R.id.video_name_admin);
        EditText photo= (EditText) findViewById(R.id.video_photo_admin);
        Button button= (Button) findViewById(R.id.video_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strphotourl=photourl.getText().toString();
                String strphotoname=photourl.getText().toString();
                String strphoto=photourl.getText().toString();
                HttpUtil3.sendOkhttpRequest(HttpAddr.getVideoadmin(), "videourl", strphotourl, "videotheme", strphotoname, "photourl", strphoto, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responsedate=response.body().string();
                        parseJson(responsedate);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                 if(statue.equals("1")){
                                     Toast.makeText(getApplicationContext(),"提交成功",Toast.LENGTH_SHORT).show();
                                 }
                            }
                        });
                    }
                });

            }
        });
    }

    ////////////////////////////////json解析/////////////////////////////////////
    private void parseJson(String jsondata) {

        try {

            JSONObject json2 = new JSONObject(jsondata);
            statue=json2.getString("statue");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
