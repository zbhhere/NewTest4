package com.example.zbh.newtest4.TopFragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.zbh.newtest4.Adapter.NewstitleAdapter;
import com.example.zbh.newtest4.ContentActivity;
import com.example.zbh.newtest4.NewItem;
import com.example.zbh.newtest4.R;
import com.example.zbh.newtest4.SelfContentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zbh on 2017/6/10.
 */

///////////////////////////开线程对fragment1，2，3，4，5要填充的内容进行网络请求  并填充在listview里显示
class Httpurl extends AsyncTask<Void,Integer,Boolean>

{

    private String data=null;
    //   private ListView listView=null;
    private List<NewItem> newItemList=new ArrayList<>();
    private View context;
    private String url;
    private int id;
    private String data2;
    String responseData;

    public Httpurl(String url, View view, int id) {
        this.url=url;
        context=view;
        this.id=id;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean b) {

        //        mtextview.setText(data2);
        //        int i=newItemList.size();
        NewstitleAdapter adapter=new NewstitleAdapter(context.getContext(), R.layout.recycler_view,newItemList);
        switch (id)
        {
            case 1:
                //                Toast.makeText(context.getContext(),responseData,Toast.LENGTH_SHORT).show();
                ListView listView1= (ListView) context.findViewById(R.id.list_view1);
                listView1.setAdapter(adapter);
                onClicktoSelfContent(listView1);
                break;
            case 2:
                ListView listView2= (ListView) context.findViewById(R.id.list_view2);
                listView2.setAdapter(adapter);
                onclicktocontent(listView2);
                break;
            case 3:
                ListView listView3= (ListView) context.findViewById(R.id.list_view3);
                listView3.setAdapter(adapter);
                onclicktocontent(listView3);
                break;

            case 4:
                ListView listView4= (ListView) context.findViewById(R.id.list_view4);
                listView4.setAdapter(adapter);
                onclicktocontent(listView4);
                break;
            case 5:
                ListView listView5= (ListView) context.findViewById(R.id.list_view5);
                listView5.setAdapter(adapter);
                onclicktocontent(listView5);
                break;
        }




    }

    public void onClicktoSelfContent(ListView listView){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = new Intent(context.getContext(),SelfContentActivity.class);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewItem title = newItemList.get(position);
                intent.putExtra("title",title.getTitle());
                intent.putExtra("titleid",String.valueOf(position+1));
                //                intent.putExtra("uri",title.getUrl());
                //                intent.putExtra("image",title.getPictur eurl());
                context.getContext().startActivity(intent);
            }
        });
    }
    public void onclicktocontent(ListView listView){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = new Intent(context.getContext(), ContentActivity.class);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewItem title = newItemList.get(position);
                intent.putExtra("title",title.getTitle());
                intent.putExtra("uri",title.getUrl());
                context.getContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            responseData = response.body().string();
            parseJson(responseData);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    private void parseJson(String jsondata){
        if(id!=1) {
            try {

                JSONObject jsonObject = new JSONObject(jsondata);

                data = jsonObject.getString("newslist");


                jsonParse(data);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {//内容为死新闻 区别对待
            try{

                jsonParse(jsondata);


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void jsonParse(String data){
        try{
            JSONArray jsonArray2 = new JSONArray(data);
            for (int i = 0; i < jsonArray2.length(); i++) {
                JSONObject jsonObject2 = jsonArray2.getJSONObject(i);
                //                data2+=jsonObject2.getString("ctime") + "\n";
                //                data2+=jsonObject2.getString("title") + "\n";
                //                data2+=jsonObject2.getString("description") + "\n";
                //                data2+=jsonObject2.getString("picUrl") + "\n";
                //                data2+=jsonObject2.getString("url") + "\n";
                NewItem item = new NewItem();
                item.setTime(jsonObject2.getString("ctime"));
                item.setTitle(jsonObject2.getString("title"));
                item.setPictureurl(jsonObject2.getString("picUrl"));
                item.setUrl(jsonObject2.getString("url"));
                newItemList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

