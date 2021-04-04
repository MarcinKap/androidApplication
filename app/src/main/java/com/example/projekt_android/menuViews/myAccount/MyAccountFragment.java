package com.example.projekt_android.menuViews.myAccount;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.projekt_android.R;
import com.example.projekt_android.database.entity.UserEntity;
import com.example.projekt_android.model.Question;

public class MyAccountFragment extends Fragment {
    private Question question;
    private Context context;


    private TextView loggedUserNameValue;
    private TextView loggedUserLastNameValue;
    private TextView loggedUserEmailValue;

    UserEntity loggedUser;

    public MyAccountFragment() {
        // Required empty public constructor
    }


    public MyAccountFragment(Context context, UserEntity userEntity) {
        this.loggedUser = userEntity;
        this.context = context;
    }

    /**
     * Method used to count down duration time of the alarm.
     * Is called when CurrentListMsgFragment is created.
     */
    @Override
    public void onResume() {
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
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return null;
        }

        View rootView = inflater.inflate(R.layout.fragment_my_account, container, false);

        this.loggedUserNameValue = rootView.findViewById(R.id.loggedUserNameValue);
        this.loggedUserLastNameValue = rootView.findViewById(R.id.loggedUserLastNameValue);
        this.loggedUserEmailValue = rootView.findViewById(R.id.loggedUserEmailValue);

        if (loggedUser.getName() != null){
            this.loggedUserNameValue.setText(loggedUser.getName());
        }
        if (loggedUser.getLastName()!= null){
            this.loggedUserLastNameValue.setText(loggedUser.getLastName());
        }

        this.loggedUserEmailValue.setText(loggedUser.getEmail());




        if (context != null) {
        } else {
            return null;
        }


        return rootView;
    }







    /**
     * Method register EventBus
     */
    @Override
    public void onStart() {
        super.onStart();

    }

    /**
     * Method unregister EventBus
     */
    @Override
    public void onStop() {
        super.onStop();

    }





}