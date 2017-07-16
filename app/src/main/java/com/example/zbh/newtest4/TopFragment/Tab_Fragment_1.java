package com.example.zbh.newtest4.TopFragment;

//import android.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zbh.newtest4.HttpUrl.HttpAddr;
import com.example.zbh.newtest4.R;




/**
 * Created by zbh on 2017/6/10.
 */

public class Tab_Fragment_1 extends Fragment{
        @Nullable

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View viewTab_1 = inflater.inflate(R.layout.tab_1, container, false);
            //1为标签172.25.86.1
           Httpurl httpurl=new Httpurl(HttpAddr.getHttpselftitle(),viewTab_1,1);
            httpurl.execute();

           return viewTab_1;
        }


}
