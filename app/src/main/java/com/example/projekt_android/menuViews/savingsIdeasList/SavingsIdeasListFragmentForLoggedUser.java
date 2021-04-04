package com.example.projekt_android.menuViews.savingsIdeasList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_android.R;
import com.example.projekt_android.database.entity.UserEntity;
import com.example.projekt_android.menuViews.WrapContentLinearLayoutManager;
import com.example.projekt_android.model.SavingsIdea;

import java.util.List;

public class SavingsIdeasListFragmentForLoggedUser extends Fragment {

    private static final String mCode = "SavingsIdeaFragmentForLoggedUser";
    private List<SavingsIdea> savingsIdeaList;
    private RecyclerView.Adapter mAdapter;
    private Context context;
    private UserEntity loggedUser;



    public SavingsIdeasListFragmentForLoggedUser()
    {
        // Required empty public constructor
    }


    public SavingsIdeasListFragmentForLoggedUser(List<SavingsIdea> savingsIdeaList, Context context, UserEntity userEntity)
    {
        this.savingsIdeaList = savingsIdeaList;
        this.context = context;
        this.loggedUser = userEntity;
    }

    /**
     * Method used to count down duration time of the alarm.
     * Is called when CurrentListMsgFragment is created.
     */
    @Override
    public void onResume()
    {
        super.onResume();

        System.out.println("on resume sie zadziało");


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

        View rootView = inflater.inflate(R.layout.fragment_savings_ideas_list_for_logged_user, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.savingsIdeasRecyclerView);

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
        mAdapter = new SavingsIdeasListAdapterForLoggedUser (savingsIdeaList , context, loggedUser );
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