package com.example.zbh.newtest4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zbh.newtest4.HttpUrl.HttpAddr;
import com.example.zbh.newtest4.HttpUrl.HttpUtil1;
import com.example.zbh.newtest4.UserName.UserName;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.zbh.newtest4.UserName.UserName.getFlag;

public class SelfContentActivity extends AppCompatActivity {

    private String responsedate;
    private String titlename;//本文的标题
    private String content;
    private String picurl;


    private ImageView save_btn;
    private TextView save_text;
    private EditText send_msg;
    private ImageView send_btn;
    private int flag;
    private int save_statue=0;
    private String url;
    private String titleid;//本文的id
    //    private String username;
//    private String title;//本文的标题名
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

        save_btn= (ImageView) findViewById(R.id.save_btn);
        save_text= (TextView) findViewById(R.id.save_view);
        send_msg= (EditText) findViewById(R.id.sendmsg_edt);
        send_btn= (ImageView) findViewById(R.id.sendmsg_btn);

        //收藏/取消收藏功能
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(save_statue==0) {
                   flag = getFlag();
//                   username = sessionApplication.getUsername();
                   url = HttpAddr.getSave();
                   JusticeLogOrNot(url);
               }else{
                   flag = UserName.getFlag();
//                   username = sessionApplication.getUsername();
                   url = HttpAddr.getSave_delete();
                   JusticeLog(url);
               }
            }
        });

        //发送评论
//        send_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        //显示内容/////////////////////////////////////////////////////////////////////////////////////////////
        titleid = getIntent().getStringExtra("titleid");
        //显示内容
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(getApplicationContext(),"nimei",Toast.LENGTH_SHORT).show();
                try {
//                    Toast.makeText(getApplicationContext(),"kewu",Toast.LENGTH_SHORT).show();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("title", titleid)
                            .build();
                    Request request = new Request.Builder()
                            .url(HttpAddr.getContent())
                            .post(requestBody)
                            .build();

                    Response response = client.newCall(request).execute();

                    responsedate=response.body().string();

                    parseJson(responsedate);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(getApplicationContext(),responsedate+"heheheheh",Toast.LENGTH_SHORT).show();
                            TextView title1= (TextView) findViewById(R.id.content_title);
                            TextView content_text= (TextView) findViewById(R.id.content_view);
                            ImageView imageView= (ImageView) findViewById(R.id.content_image);
                            title1.setText(titlename);
                            content_text.setText(content);
                            Glide.with(SelfContentActivity.this).load(picurl).into(imageView);
                        }
                    });

                    savehistory();

                }catch (Exception e){
                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),"123",Toast.LENGTH_SHORT).show();
                }
            }
        }).start();


    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
//进行浏览记录保存 保存在数据库
    public void savehistory(){
        flag= getFlag();
        if(flag==1){
            HttpUtil1.sendOkhttpRequest(HttpAddr.getHistory(), "titlename",titlename , new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });
        }
    }
/////////////////////////////////////////点击了收藏后 再点击取消收藏///////////////////////////////////////////
    public void JusticeLog(String url){

            //            Toast.makeText(getApplicationContext(),"heheh",Toast.LENGTH_SHORT).show();
            HttpUtil1.sendOkhttpRequest(url, "username", titlename, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(getApplicationContext(),"取消收藏失败",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
//                                        Toast.makeText(getApplicationContext(),"kekek",Toast.LENGTH_SHORT).show();
                    String responsedate=response.body().string();
                    try{
                        JSONObject object=new JSONObject(responsedate);
                        String statue=object.getString("statue");
                        if(statue.equals("0")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    Toast.makeText(getApplicationContext(),"kekek",Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),"已取消收藏",Toast.LENGTH_SHORT).show();
                                    save_btn.setImageResource(R.mipmap.save_img);
                                    save_statue=0;
                                }
                            });

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////
    //点击收藏前 先判断是否登录 没登录的话跳转到登陆界面
    public void JusticeLogOrNot(String url){
        if(flag==0){
            Intent intent=new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
        }else{
//            Toast.makeText(getApplicationContext(),"heheh",Toast.LENGTH_SHORT).show();
               HttpUtil1.sendOkhttpRequest(url, "username", titlename, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(getApplicationContext(),"收藏失败",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
//                    Toast.makeText(getApplicationContext(),"kekek",Toast.LENGTH_SHORT).show();
                    String responsedate=response.body().string();
                    try{
                        JSONObject object=new JSONObject(responsedate);
                        String statue=object.getString("statue");
                        if(statue.equals("1")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"您已收藏",Toast.LENGTH_SHORT).show();
                                    save_btn.setImageResource(R.mipmap.save_img2);
                                    save_statue=1;
                                    flag=1;
                                }
                            });

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
        }
    }
    //json解析
    public void parseJson(String data){
        try {
            JSONObject jsonObject = new JSONObject(data);
            titlename=jsonObject.getString("titlename");
            content=jsonObject.getString("content");
            picurl=jsonObject.getString("imageurl");
//            responsedate="zbh   ";

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
}
