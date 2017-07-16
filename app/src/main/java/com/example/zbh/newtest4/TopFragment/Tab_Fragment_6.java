    package com.example.zbh.newtest4.TopFragment;

    import android.content.Intent;
    import android.os.Bundle;
    import android.support.v4.app.Fragment;
    import android.support.v7.widget.RecyclerView;
    import android.support.v7.widget.StaggeredGridLayoutManager;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.bumptech.glide.Glide;
    import com.example.zbh.newtest4.HttpUrl.HttpAddr;
    import com.example.zbh.newtest4.HttpUrl.HttpUtil;
    import com.example.zbh.newtest4.PhotoContent;
    import com.example.zbh.newtest4.PhotoItem;
    import com.example.zbh.newtest4.R;

    import org.json.JSONArray;
    import org.json.JSONObject;

    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.List;

    import okhttp3.Call;
    import okhttp3.Callback;
    import okhttp3.Response;


    public class Tab_Fragment_6 extends Fragment {


        String data2;
       private List<PhotoItem> photoItems=new ArrayList<>();
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view= inflater.inflate(R.layout.fragment_tab__fragment_6, container, false);
            initPhotos();
            return view;
        }

        private void initPhotos(){
            HttpUtil.sendOkhttpRequest(HttpAddr.getPhotoall(), new Callback() {
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
    //                        Toast.makeText(getActivity(),data2,Toast.LENGTH_SHORT).show();
                            RecyclerView recyclerView= (RecyclerView) getView().findViewById(R.id.recycler_view);
                            StaggeredGridLayoutManager layoutManager=new
                                    StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(layoutManager);
                            PhotoAdapter adapter=new PhotoAdapter(photoItems);
                            recyclerView.setAdapter(adapter);
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

                    PhotoItem photoItem=new PhotoItem();
                   photoItem.setPhotourl(jsonObject2.getString("photourl"));
                    photoItem.setPhototheme(jsonObject2.getString("theme"));
                    photoItems.add(photoItem);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{

            private List<PhotoItem> mphotoItems;
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item,parent,false);
                final ViewHolder holder=new ViewHolder(view);
                holder.photoImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position=holder.getAdapterPosition();
                        Intent intent=new Intent(getActivity(), PhotoContent.class);
                        intent.putExtra("position",position+1);
                        startActivity(intent);
                    }
                });
                return holder;
            }

            @Override
            public void onBindViewHolder(PhotoAdapter.ViewHolder holder, int position) {
                PhotoItem photoItem=mphotoItems.get(position);
                Glide.with(getContext()).load(photoItem.getPhotourl()).into(holder.photoImage);
                holder.photoText.setText(photoItem.getPhototheme());
            }

            @Override
            public int getItemCount() {
                return mphotoItems.size();
            }

            public PhotoAdapter(List<PhotoItem> photoItems) {
                mphotoItems=photoItems;
            }
            public class ViewHolder extends RecyclerView.ViewHolder{
                ImageView photoImage;
                TextView photoText;
                public ViewHolder(View itemView) {
                    super(itemView);
                    photoImage= (ImageView) itemView.findViewById(R.id.photo_image);
                    photoText= (TextView) itemView.findViewById(R.id.photo_text);
                }
            }
        }

    }
