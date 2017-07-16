package com.example.zbh.newtest4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zbh.newtest4.HttpUrl.HttpAddr;
import com.example.zbh.newtest4.HttpUrl.HttpUtil1;
import com.example.zbh.newtest4.HttpUrl.HttpUtil2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

////////////////////////////数据库没什么差别，放在一个地方实现//////////////////////////////////////////////////
public class SeachSaveAndHistory extends AppCompatActivity {

    private ListView listView=null;
    private List<String>list= new ArrayList<>();
    private String historyandSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_save_and_history);
        Intent intent = getIntent();
        final String style = intent.getStringExtra("style");
//       Toast.makeText(getApplicationContext(),style,Toast.LENGTH_SHORT).show();

        ////////////////请求查看/////////////////
        HttpUtil1.sendOkhttpRequest(HttpAddr.getHttpsearhistoryandsave(),"style",style, new Callback() {
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
                      /////////////////////////////////////////////////将记录放在listview
                      ArrayAdapter<String> adapter=new ArrayAdapter<String>(SeachSaveAndHistory.this,android.R.layout.simple_list_item_1,list);
                      listView= (ListView) findViewById(R.id.save_history_view);
                      listView.setAdapter(adapter);
                      ///////////////////////////////点击事件
                      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                          @Override
                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                             final String titlename=list.get(position);
                                  Snackbar.make(view,"删除该记录",Snackbar.LENGTH_SHORT)
                                          .setAction("删除", new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {

                                                  //                              Toast.makeText(getApplicationContext(),titlename,Toast.LENGTH_SHORT).show();
                                                  HttpUtil2.sendOkhttpRequest(HttpAddr.getHttpDeletehistoryorsave(), "style", style, "titlename", titlename, new Callback() {
                                                      @Override
                                                      public void onFailure(Call call, IOException e) {

                                                      }

                                                      @Override
                                                      public void onResponse(Call call, Response response) throws IOException {
                                                          String str=response.body().string();
                                                          final String str2;
                                                          try {
                                                              JSONObject jsonObject = new JSONObject(str);
                                                              str2=jsonObject.getString("statue");
                                                              runOnUiThread(new Runnable() {
                                                                  @Override
                                                                  public void run() {
                                                                      if(str2.equals("0")){
                                                                          Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                                                                      }
                                                                  }
                                                              });


                                                          }catch (Exception e){
                                                              e.printStackTrace();
                                                          }

                                                      }
                                                  });
                                              }
                                          })
                                          .show();

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

            JSONArray jsonArray2 = new JSONArray(jsondata);
            for (int i = 0; i < jsonArray2.length(); i++) {
                JSONObject jsonObject2 = jsonArray2.getJSONObject(i);
                //                data2+=jsonObject2.getString("ctime") + "\n";
                //                data2+=jsonObject2.getString("title") + "\n";
                //                data2+=jsonObject2.getString("description") + "\n";
                //                data2+=jsonObject2.getString("picUrl") + "\n";
                //                data2+=jsonObject2.getString("url") + "\n";

                historyandSave=jsonObject2.getString("titlename");
                list.add(historyandSave);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
