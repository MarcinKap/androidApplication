package com.example.projekt_android.MenuViews.NewsList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_android.MenuViews.News.NewsFragment;
import com.example.projekt_android.Model.News;
import com.example.projekt_android.R;
import com.example.projekt_android.api.ApiUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    private List<News> newsList;
    private Context context;


    public NewsListAdapter(List<News> newsList, Context context){
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsListAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_news_for_news_list, parent,false);

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListAdapter.NewsViewHolder holder, int position) {
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
        private Button readMoreButton;
        private YouTubePlayerView youTubePlayerView;
        private ReadNewsListener readNewsListener;

        public NewsViewHolder(@NonNull View itemView)
        {
            super(itemView);



            newsLinkToMovie = itemView.findViewById(R.id.if_you_have_a_question_write_to_us);
            newsShortText = itemView.findViewById(R.id.newsText);
            newsCreationData = itemView.findViewById(R.id.newsCreationData);

            readMoreButton = itemView.findViewById(R.id.readMoreButton);



        }

        void bind(Context context , final News singleAndroidAlarm)
        {

            newsLinkToMovie.setText(singleAndroidAlarm.getTitle());
            newsShortText.setText(singleAndroidAlarm.getShortText());
            newsCreationData.setText(singleAndroidAlarm.getCreatedDate());




            readMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("wysyłanie zapytania newstoread");
                    System.out.println(singleAndroidAlarm.getId());
                    ApiUtils.getApiService().getNewsToRead(singleAndroidAlarm.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DisposableObserver<Response<News>>() {
                                @Override
                                public void onNext(@io.reactivex.annotations.NonNull Response<News> response) {
                                    if (ApiUtils.getResponseStatusCode(response) == 200) {

                                        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.container_fragment, new NewsFragment(response.body(), context))
                                                .commit();
                                    }

                                }

                                @Override
                                public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                    Log.e("API_CALL", e.getMessage(), e);
                                }

                                @Override
                                public void onComplete() {

                                }
                            });

                }
            });

        }

        public class ReadNewsListener implements View.OnClickListener
        {




            @Override
            public void onClick(View view) {




            }
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
