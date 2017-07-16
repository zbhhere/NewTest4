package com.example.zbh.newtest4.TopFragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zbh.newtest4.HttpUrl.HttpAddr;
import com.example.zbh.newtest4.HttpUrl.HttpUtil;
import com.example.zbh.newtest4.R;
import com.example.zbh.newtest4.VideoItem;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Tab_Fragment_7 extends Fragment {

    private ListView listView;
        private List<VideoItem> videoItemList=new ArrayList<>();
    private ArrayList<String> datas=new ArrayList<String>();
    private JCVideoPlayerStandard currPlayer;
//    private VideoAdapter adapter;

    private VideosAdapter adapter;
    private AbsListView.OnScrollListener onScrollListener;
    private int firstVisible;//当前第一个可见的item
    private int visibleCount;//当前可见的item个数

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab__fragment_7, container, false);
        listView= (ListView) view.findViewById(R.id.video_listview);
        initDatas();
        initListener();
        return view;
    }

    private void initDatas() {
        HttpUtil.sendOkhttpRequest(HttpAddr.getVideocontent(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responsedate = response.body().string();
                parseJson(responsedate);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        adapter = new VideosAdapter(getContext(), R.layout.video_item, videoItemList);
                        listView.setAdapter(adapter);
                    }
                });
            }
        });

        //        adapter = new VideoAdapter(getContext(), datas, R.layout.item_video);
        //        listView.setAdapter(adapter);
    }


    private void initListener() {
        onScrollListener = new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        //滑动停止自动播放视频
                        autoPlayVideo(view);
                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisible == firstVisibleItem) {
                    return;
                }
//               Toast.makeText(getContext(),String.valueOf(firstVisible)+"    "+String.valueOf(visibleCount),Toast.LENGTH_SHORT).show();
                firstVisible = firstVisibleItem;
                visibleCount = visibleItemCount;
            }
        };

        listView.setOnScrollListener(onScrollListener);
    }

    /**
     * 滑动停止自动播放视频
     */
    private void autoPlayVideo(AbsListView view) {

        for (int i = 0; i < visibleCount; i++) {
            if (view != null && view.getChildAt(i) != null && view.getChildAt(i).findViewById(R.id.player_list_video) != null) {
                currPlayer = (JCVideoPlayerStandard) view.getChildAt(i).findViewById(R.id.player_list_video);
                Rect rect = new Rect();
                //获取当前view 的 位置
                currPlayer.getLocalVisibleRect(rect);
                int videoheight = currPlayer.getHeight();
                if (rect.top == 0 && rect.bottom == videoheight) {
                    if (currPlayer.currentState == JCVideoPlayer.CURRENT_STATE_NORMAL
                            || currPlayer.currentState == JCVideoPlayer.CURRENT_STATE_ERROR) {
                        currPlayer.startButton.performClick();
                    }
                    return;
                }
            }
        }
        //释放其他视频资源
        JCVideoPlayer.releaseAllVideos();
    }

//    @Override
//    public void onBackPressed() {
//        if (JCVideoPlayer.backPress()) {
//            return;
//        }
//        super.onBackPressed();
//    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }


    private void parseJson(String jsondata) {

        try {

            JSONArray jsonArray2 = new JSONArray(jsondata);
            for (int i = 0; i < jsonArray2.length(); i++) {
                JSONObject jsonObject2 = jsonArray2.getJSONObject(i);
                //                data2+=jsonObject2.getString("ctime") + "\n";
                //                data2+=jsonObject2.getString("title") + "\n";
                //                data2+=jsonObject2.getString("description") + "\n";
                //                                data2+=jsonObject2.getString("photourl") + "\n";
                //                                data2+=jsonObject2.getString("theme") + "\n";
                VideoItem videoItem=new VideoItem();
                videoItem.setVideotheme(jsonObject2.getString("videotheme"));
                videoItem.setVideourl(jsonObject2.getString("videourl"));
                videoItem.setVideoowner(jsonObject2.getString("videoowner"));
                videoItem.setVideoplaytimes(jsonObject2.getString("videoplaytimes"));
                videoItem.setPhotourl(jsonObject2.getString("photourl"));


//                String data=jsonObject2.getString("videourl");
//                datas.add(data);
                videoItemList.add(videoItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class VideosAdapter extends CommonAdapter<VideoItem>{
        private Context mContext;
        private List<VideoItem> datas;
        public VideosAdapter(Context context, int layoutId, List<VideoItem> datas) {
            super(context, layoutId, datas);
            this.mContext = context;
            this.datas=datas;
        }

        @Override
        protected void convert(ViewHolder viewHolder, VideoItem item, int position) {
            JCVideoPlayerStandard player = viewHolder.getView(R.id.player_list_video);
            TextView textView=viewHolder.getView(R.id.video_theme);
            TextView username=viewHolder.getView(R.id.tv_video_userName);
            TextView playtimes=viewHolder.getView(R.id.videoplaytimes);
            if (player != null) {
                player.release();
            }
            textView.setText(datas.get(position).getVideotheme());
            username.setText(datas.get(position).getVideoowner());
            playtimes.setText(datas.get(position).getVideoplaytimes()+"万次播放");
            boolean setUp = player.setUp(datas.get(position).getVideourl(), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
            if (setUp) {
                Glide.with(mContext).load(datas.get(position).getPhotourl()).into(player.thumbImageView);

            }
        }
    }


}
