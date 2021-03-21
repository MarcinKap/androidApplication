package com.example.projekt_android.MenuViews.News;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_android.Model.News;
import com.example.projekt_android.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<News> newsList;


    public NewsAdapter(List<News> newsList){
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_news, parent,false);

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        News news = newsList.get(position);

        if (news==null){
            return;
        }
        holder.bind(holder.itemView.getContext(), news);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

//    public NewsViewHolder(@NonNull View itemView)
//    {
//        super(itemView);
//
//
//
//
//    }

    class NewsViewHolder extends RecyclerView.ViewHolder
    {
        private TextView newsLinkToMovie, newsShortText, newsCreationData;
        private YouTubePlayerView youTubePlayerView;

        public NewsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            //To skopiować do poszczególnych aktualnosci
//            youTubePlayerView = itemView.findViewById(R.id.videoPlayer);
//            youTubePlayerView.enterFullScreen();
//            youTubePlayerView.toggleFullScreen();
//            youTubePlayerView.getPlayerUiController();
//            youTubePlayerView.enterFullScreen();
//            youTubePlayerView.toggleFullScreen();
//            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//                @Override
//                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                    // loading the selected video into the YouTube Player
//                    youTubePlayer.loadVideo("vG2PNdI8axo", 0);
//                }
//
//                @Override
//                public void onStateChange(@NonNull YouTubePlayer youTubePlayer,
//                                          @NonNull PlayerConstants.PlayerState state) {
//                    // this method is called if video has ended,
//                    super.onStateChange(youTubePlayer, state);
//                }
//            });
///


            newsLinkToMovie = itemView.findViewById(R.id.newsTitle);
            newsShortText = itemView.findViewById(R.id.newsShortText);
            newsCreationData = itemView.findViewById(R.id.newsCreationData);


        }

        void bind(Context context , final News singleAndroidAlarm)
        {

            newsLinkToMovie.setText(singleAndroidAlarm.getTitle());
            newsShortText.setText(singleAndroidAlarm.getShortText());
            newsCreationData.setText(singleAndroidAlarm.getCreatedDate());
        }



//        public class CustomListener implements View.OnClickListener, View.OnLongClickListener
//        {

//            void updateMessage(AndroidAlarmMessage androidAlarmMessage)
//            {
//            }

//            @Override
//            public void onClick(View v)
//            {
//            }

//            @Override
//            public boolean onLongClick(View v)
//            {
//            }

//        }

    }

//
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = this.inflater.inflate(R.layout.content, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        News news = newsList.get
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }



}
