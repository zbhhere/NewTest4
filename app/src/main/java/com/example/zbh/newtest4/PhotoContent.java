package com.example.zbh.newtest4;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zbh.newtest4.HttpUrl.HttpAddr;
import com.example.zbh.newtest4.HttpUrl.HttpUtil1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PhotoContent extends AppCompatActivity {

    private List<PhotoContentItem> photoContentItems=new ArrayList<>();
    private String position;
    ViewPager pager;
    String data2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.photo_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
        position=String.valueOf(getIntent().getIntExtra("position",0));
        initPhotoContents();
    }
    private void initPhotoContents(){
        HttpUtil1.sendOkhttpRequest(HttpAddr.getPhotocontent(), "position", position, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               final String responsedate=response.body().string();
                parseJson(responsedate);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getApplicationContext(),data2,Toast.LENGTH_SHORT).show();
                         pager= (ViewPager) findViewById(R.id.viewPager);
                        PhotoContentAdapter photoContentAdapter=new PhotoContentAdapter();
                        pager.setAdapter(photoContentAdapter);
//                        pager.setCurrentItem(0);
//                        pager.setPageTransformer(true,null);
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
                //                                data2+=jsonObject2.getString("photourl") + "\n";
                data2+=jsonObject2.getString("photocontenturl") + "\n";

                PhotoContentItem photoItem=new PhotoContentItem();

                photoItem.setPhotoContentItemUrl(jsonObject2.getString("photocontenturl"));

                photoContentItems.add(photoItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class PhotoContentAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//           View view= LayoutInflater.from(container.getContext()).inflate(R.layout.photocontentiem,container,false);
            ImageView imageView= new ImageView(PhotoContent.this);
            PhotoContentItem photoContentItem=photoContentItems.get(position);
            Glide.with(getApplicationContext()).load(photoContentItem.getPhotoContentItemUrl()).into(imageView);
//            imageView.setImageResource(R.mipmap.a4);
            container.addView(imageView);
            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeViewAt(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return photoContentItems.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
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
