package com.example.projekt_android.menuViews.news;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_android.model.News;
import com.example.projekt_android.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class NewsFragment extends Fragment {

    private static final String mCode = "News";
    private News news;
    private RecyclerView.Adapter mAdapter;
    private Context context;

    private TextView newsTitle;
    private TextView newsDate;
    private TextView newsText;
    private YouTubePlayerView youTubePlayerView;


    public NewsFragment()
    {
        // Required empty public constructor
    }


    public NewsFragment(News news, Context context)
    {
        this.news = news;
        this.context = context;
    }

    /**
     * Method used to count down duration time of the alarm.
     * Is called when CurrentListMsgFragment is created.
     */
    @Override
    public void onResume()
    {
        super.onResume();
    }

    /**
     * Fragment method to prepare screen for display messages
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if(savedInstanceState != null)
        {
            return null;
        }

        View rootView = inflater.inflate(R.layout.single_news, container, false);

        newsTitle = rootView.findViewById(R.id.savingsIdeaDescription);
        newsDate = rootView.findViewById(R.id.savingsIdeaCreationData);
        newsText = rootView.findViewById(R.id.savingsIdeaDescriptionFromDatabase);

        newsTitle.setText(news.getTitle());
        newsDate.setText(news.getCreatedDate());
        newsText.setText(news.getText());

        //To skopiować do poszczególnych aktualnosci
        youTubePlayerView = rootView.findViewById(R.id.videoPlayer);
        youTubePlayerView.enterFullScreen();
        youTubePlayerView.toggleFullScreen();

        // here we are adding observer to our youtubeplayerview.
        getLifecycle().addObserver(youTubePlayerView);

        // below method will provides us the youtube player
        // ui controller such as to play and pause a video
        // to forward a video
        // and many more features.
        youTubePlayerView.getPlayerUiController();

        String[] video = news.getMovieLink().split("/");
        // adding listener for our youtube player view.
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                // loading the selected video into the YouTube Player
                youTubePlayer.loadVideo(video[video.length-1], 0);
                System.out.println(video[video.length-1]);
            }

            @Override
            public void onStateChange(@NonNull YouTubePlayer youTubePlayer,
                                      @NonNull PlayerConstants.PlayerState state) {
                // this method is called if video has ended,
                super.onStateChange(youTubePlayer, state);
            }
        });
///


        if(context != null)
        {
        }
        else
        {
            return null;
        }


        return rootView;
    }



    /**
     * Method register EventBus
     */
    @Override
    public void onStart()
    {
        super.onStart();

    }

    /**
     * Method unregister EventBus
     */
    @Override
    public void onStop()
    {
        super.onStop();

    }




}