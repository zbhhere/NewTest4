package com.example.zbh.newtest4.TopFragment;

//import android.app.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zbh.newtest4.R;

/**
 * Created by zbh on 2017/6/10.
 */

public class Tab_Fragment_3 extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View viewTab_3 = inflater.inflate(R.layout.tab_3, container, false);
        Httpurl httpurl=new Httpurl("https://api.tianapi.com/world/?key=a82fb2fbbe6ec51d731413cea9325cdb&num=50&rand=1",viewTab_3,3);
        httpurl.execute();
        return viewTab_3;
    }

}
