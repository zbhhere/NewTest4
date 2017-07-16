package com.example.zbh.newtest4.TopFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zbh.newtest4.Adapter.NewstitleAdapter;
import com.example.zbh.newtest4.NewItem;
import com.example.zbh.newtest4.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hejingzhou on 2016/4/9.
 */
public class Tab_Fragment_4 extends Fragment {

    private List<NewItem> newItemList= new ArrayList<>();
   ListView listView;
    NewstitleAdapter adapter;
    TextView textView;
    String data2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewTab_4 = inflater.inflate(R.layout.tab_4, container, false);

        Httpurl httpurl=new Httpurl("https://api.tianapi.com/huabian/?key=a82fb2fbbe6ec51d731413cea9325cdb&num=50&rand=1",viewTab_4,4);
        httpurl.execute();
     return viewTab_4;
    }

}
