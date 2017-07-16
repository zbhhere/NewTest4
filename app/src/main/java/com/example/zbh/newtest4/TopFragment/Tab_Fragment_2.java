package com.example.zbh.newtest4.TopFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zbh.newtest4.R;

/**
 * Created by Hejingzhou on 2016/4/9.
 */
public class Tab_Fragment_2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewTab_2 = inflater.inflate(R.layout.tab_2, container, false);
        //通过天行数据api连接
        Httpurl httpurl=new Httpurl("https://api.tianapi.com/guonei/?key=a82fb2fbbe6ec51d731413cea9325cdb&num=50&rand=1",viewTab_2,2);
        httpurl.execute();
        return viewTab_2;
    }

}
