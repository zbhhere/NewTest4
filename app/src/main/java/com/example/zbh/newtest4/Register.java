package com.example.zbh.newtest4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zbh.newtest4.HttpUrl.HttpAddr;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register extends AppCompatActivity {

    private EditText registername=null;
    private EditText registerpassword=null;
    private EditText reRegisterpassword=null;
    private Button register_btn =null;
    private String struesr=null;
    private String strpassword=null;
    private String strrepassword=null;
    private String responseData;
    private String statue=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
        setContentView(R.layout.activity_register);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
        registername= (EditText) findViewById(R.id.register_name);
        registerpassword= (EditText) findViewById(R.id.register_pass);
        reRegisterpassword= (EditText) findViewById(R.id.re_register_pass);
        register_btn = (Button) findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                struesr=registername.getText().toString().replace(" ", "");
                strpassword=registerpassword.getText().toString().replace(" ", "");
                strrepassword=reRegisterpassword.getText().toString().replace(" ","");
                if(!strpassword.equals(strrepassword)){
                    Toast.makeText(getApplicationContext(),"两次输入密码不正确",Toast.LENGTH_SHORT).show();

                }else{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RequestBody requestBody=new FormBody.Builder()
                                .add("user",struesr)
                                .add("password",strpassword)
                                .build();
                        httpUil(requestBody);
                        //                    zbh="hehehhea";
                        parseJSONWithJSONObject(responseData);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

//                                Toast.makeText(getApplicationContext(),statue+"   "+responseData,Toast.LENGTH_SHORT).show();
                                if(statue.equals("1"))
                                { Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(Register.this,Login.class);
                                    intent.putExtra("user",struesr) ;
                                    setResult(RESULT_OK,intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"该用户已被注册",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(Register.this,Login.class);
                                    startActivity(intent);

                                }
                            }
                        });


                    }
                }).start();

            }}

        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
    private void httpUil(RequestBody requestBody){
        try {
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                    .url(HttpAddr.getHttpRegister())
                    .post(requestBody)
                    .build();

            Response response=client.newCall(request).execute();
            responseData=response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void parseJSONWithJSONObject(String jsonData){
        //        zbh="heheheh";
        try{
            //                        zbh="hehehhea";
            JSONObject jsonObject=new JSONObject(jsonData);
            statue=jsonObject.getString("statue");

            //                        zbh="hehehhea";
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
