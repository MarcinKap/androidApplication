package com.example.projekt_android.MenuViews.News;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_android.MenuViews.WrapContentLinearLayoutManager;
import com.example.projekt_android.Model.News;
import com.example.projekt_android.R;

import java.util.List;
import java.util.Vector;

public class NewsListFragment extends Fragment {

    private static final String mCode = "NewsFragment";
    private List<News> newsVector;
    private RecyclerView.Adapter mAdapter;



    public NewsListFragment()
    {
        // Required empty public constructor
    }


    public NewsListFragment(List<News> newsListFragment)
    {
        this.newsVector = newsListFragment;
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

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.newsRecyclerView);

        Context context = getContext();

        if(context != null)
        {
                recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(context));
        }
        else
        {
            return null;
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setHasFixedSize(true);
        mAdapter = new NewsAdapter(newsVector);
        recyclerView.setAdapter(mAdapter);

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