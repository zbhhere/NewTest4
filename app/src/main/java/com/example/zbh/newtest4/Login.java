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
import android.widget.TextView;
import android.widget.Toast;

import com.example.zbh.newtest4.HttpUrl.HttpAddr;
import com.example.zbh.newtest4.UserName.UserName;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {

    private EditText user=null;
    private EditText password=null;
    private Button logbtn=null;
    private String struesr=null;
    private String strpassword=null;
    private String responseData;
    private String statue=null;
    private String user2=null;
    private String password2=null;
    private String register_name=null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 3:
                if(resultCode==RESULT_OK){
                    EditText editText= (EditText) findViewById(R.id.user_edit);
                    register_name=data.getStringExtra("user");
                    editText.setText(register_name);
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
        TextView textView= (TextView) findViewById(R.id.register_tview);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);

               startActivityForResult(intent,3);
            }
        });

       user= (EditText) findViewById(R.id.user_edit);
        password= (EditText) findViewById(R.id.pass_edit);
        logbtn= (Button) findViewById(R.id.login_btn);
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                struesr=user.getText().toString().replace(" ", "");;
                strpassword=password.getText().toString().replace(" ", "");;
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
                                   if(statue.equals("1"))
                                   { Toast.makeText(getApplicationContext(),"密码正确",Toast.LENGTH_SHORT).show();
//                                       Intent intent=new Intent(Login.this,MainActivity.class);
//                                       intent.putExtra("user",struesr) ;
//                                       setResult(RESULT_OK,intent);
                                       UserName.setFlag(1);
                                       UserName.setUsername(struesr);
                                       Intent intent=new Intent(Login.this,MainActivity.class);
                                       startActivity(intent);

                                   }
                                   else if(statue.equals("0"))
                                   {
                                       Toast.makeText(getApplicationContext(),"密码错误",Toast.LENGTH_SHORT).show();

                                   }else {
                                       Toast.makeText(getApplicationContext(),"无该用户",Toast.LENGTH_SHORT).show();

                                   }
                               }
                           });


                    }
                }).start();

            }
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
                    .url(HttpAddr.getHttpProcess())
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
            user2=jsonObject.getString("user");
            password2=jsonObject.getString("password");



            //                        zbh="hehehhea";
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
