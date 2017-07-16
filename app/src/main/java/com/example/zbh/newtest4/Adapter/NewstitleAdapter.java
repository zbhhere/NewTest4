package com.example.zbh.newtest4.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zbh.newtest4.NewItem;
import com.example.zbh.newtest4.R;

import java.util.List;

/**
 * Created by zbh on 2017/6/10.
 */
////////////////////////////适配器放新闻
public class NewstitleAdapter extends ArrayAdapter<NewItem> {
    private int item_resourse;
    Activity activity;
    public NewstitleAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<NewItem> objects) {
        super(context, resource, objects);
        item_resourse=resource;
        activity=(Activity)context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        NewItem title = getItem(position);
        View view;
        ViewHolder viewHolder;
        /**
         * 缓存布局和实例，优化 listView
         */
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(item_resourse,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.titleText = (TextView)view.findViewById(R.id.title);
            viewHolder.titlePic = (ImageView) view.findViewById(R.id.news_image);
            viewHolder.titleTime = (TextView)view.findViewById(R.id.time);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        Glide.with(getContext()).load(title.getPictureurl()).into(viewHolder.titlePic);
        viewHolder.titleText.setText(title.getTitle());
        viewHolder.titleTime.setText(title.getTime());

        return view;

    }

    public class ViewHolder{
        TextView titleText;


        TextView titleTime;
        ImageView titlePic;
    }


    }

    


