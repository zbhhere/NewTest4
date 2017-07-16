package com.example.zbh.newtest4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zbh.newtest4.TopFragment.Tab_Fragment_1;
import com.example.zbh.newtest4.TopFragment.Tab_Fragment_2;
import com.example.zbh.newtest4.TopFragment.Tab_Fragment_3;
import com.example.zbh.newtest4.TopFragment.Tab_Fragment_4;
import com.example.zbh.newtest4.TopFragment.Tab_Fragment_5;
import com.example.zbh.newtest4.TopFragment.Tab_Fragment_6;
import com.example.zbh.newtest4.TopFragment.Tab_Fragment_7;
import com.example.zbh.newtest4.UserName.AdminContent;
import com.example.zbh.newtest4.UserName.UserName;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private List<Fragment> fragmentsList;//fragment容器
    private List<String> titleList;//标签容器
    private NavigationView navigationView;
    RelativeLayout view;
    String returnedData;//返回的user名字
    private int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("我的新闻");

        returnedData= UserName.getUsername();
        flag=UserName.getFlag();
        navigationView= (NavigationView) findViewById(R.id.nav_view);
        View drawview=navigationView.inflateHeaderView(R.layout.nav_header);
        view= (RelativeLayout) drawview.findViewById(R.id.head_view_rela);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==0) {
                    Intent intent = new Intent(MainActivity.this, AdminContent.class);
                    //                startActivity(intent);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, Logout.class);
                    //                startActivity(intent);
                    //                    intent.putExtra("user",returnedData);
                    TextView textView= (TextView) findViewById(R.id.user_name);
                    textView.setText(returnedData);

                    startActivity(intent);
                }
            }
        });
        load();//加载
        //        inistuser();

    }





    private void load() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        fragmentsList = new ArrayList<>();
        titleList = new ArrayList<>();

        fragmentsList.add(new Tab_Fragment_1());
        fragmentsList.add(new Tab_Fragment_2());//将fragment添加到fragmentList的list容器里
        fragmentsList.add(new Tab_Fragment_3());//将fragment添加到fragmentList的list容器里
        fragmentsList.add(new Tab_Fragment_4());//将fragment添加到fragmentList的list容器里
        fragmentsList.add(new Tab_Fragment_5());//将fragment添加到fragmentList的list容器里
        fragmentsList.add(new Tab_Fragment_6());
        fragmentsList.add(new Tab_Fragment_7());
        titleList.add("社会");
        titleList.add("国内");
        titleList.add("国际");
        titleList.add("娱乐");
        titleList.add("体育");
        titleList.add("图片");
        titleList.add("视频");

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//tab的模式如果标签多的话用MODE_SCROLLABLE  少的话用MODE_FIXED
        //tabLayout.setBackgroundColor(Color.BLUE);

        FragViewAdapter adapter = new FragViewAdapter(getSupportFragmentManager(), fragmentsList, titleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);//虽然过时了但是不能去掉，去掉后

    }

    /**
     * 创建适配器，主要是为了fragmet与标题进行匹配的 继承FragmentPagerAdapter
     */
    class FragViewAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList_;
        List<String> titleList_;

        public FragViewAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
            super(fm);
            fragmentList_ = fragmentList;
            titleList_ = titleList;
        }

        @Override//fragment匹配
        public Fragment getItem(int position) {
            Log.i(TAG, "getItem  " + fragmentList_.get(position));
            return fragmentList_.get(position);
        }

        @Override//获取fragment的数量
        public int getCount() {
            return titleList_.size();
        }

        @Override//对标题进行匹配
        public CharSequence getPageTitle(int position) {
            Log.i(TAG, "getPageTitle  " + titleList_.get(position));
            return titleList_.get(position);
        }

        @Override//销毁 不知道这样做行不行
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            fragmentList_.get(position).onDestroy();
        }
    }
}
