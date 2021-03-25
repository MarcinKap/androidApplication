package com.example.projekt_android;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projekt_android.MenuViews.NewsList.NewsListFragment;
import com.example.projekt_android.MenuViews.Question.QuestionFragment;
import com.example.projekt_android.Model.News;
import com.example.projekt_android.api.ApiUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView pageTitle;

    private String mSelectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        pageTitle = findViewById(R.id.toolbar_selected_item_menu_drawer_txt);

//        setSupportActionBar(toolbar);

        initNavigationDrawer();
        buttonsListeners();
        viewManagement(savedInstanceState);



//        initControls();
//        initListener();
    }

    private void viewManagement(Bundle savedInstanceState){
        if (savedInstanceState != null) {
            mSelectedFragment = savedInstanceState.getString("SelectedFragment");
            if (mSelectedFragment != null) {
                switch (mSelectedFragment) {
                    case "homePage":
                        getNewsListAndSaveToRecyclerView();
                        pageTitle.setText(R.string.home);
                        break;
                    case "questions":
                        pageTitle.setText(R.string.questions);
                        break;
                    case "savingsIdeas":
                        break;
                    case "forum":
                        break;
                    case "chat":
                        break;
                    case "myAccount":
                        break;
                    case "LogIn":
                        break;
                    case "logOut":
                        break;

                }
            }

        } else {
            getNewsListAndSaveToRecyclerView();
            pageTitle.setText(R.string.home);
        }
    }

    private void initNavigationDrawer() {

        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer_menu, R.string.close_drawer_menu);
        drawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mActionBarDrawerToggle.syncState();

//        TextView userIDTxt = navigationView.findViewById(R.id.toolbar_userID_textView);
        TextView userIDToolbar = findViewById(R.id.toolbar_userID_textView);
        String userID = "User Id";


        if (userID == null) {
//            userIDTxt.setText(getString(R.string.no_userID_selected));
//            userIDToolbar.setText(getString(R.string.no_userID_selected));
        } else {
//            userIDTxt.setText(userID);
            userIDToolbar.setText(userID);
        }

    }


    // Wlaczanie recyxler view odpalający newsy
    private void saveNewsToNewsFragment(List<News> newsList) {
        if (newsList == null) {
            System.out.println("lista jest null");
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_fragment, new NewsListFragment(newsList, getApplicationContext()))
                .commit();
    }

    // Zdobywanie listy news do home page
    private void getNewsListAndSaveToRecyclerView() {
        ApiUtils.getApiService().getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Response<List<News>>>() {
                    @Override
                    public void onNext(@NonNull Response<List<News>> response) {
                        if (ApiUtils.getResponseStatusCode(response) == 200) {
                            saveNewsToNewsFragment(response.body());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("API_CALL", e.getMessage(), e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void questionPage(){

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_fragment, new QuestionFragment(getApplicationContext()))
                .commit();


    }



    public void closeOrOpenNavigationDrawer(View view) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    //Listenery dla buttonow
    private void buttonsListeners() {

        Button homeButton = findViewById(R.id.home_btn);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);

                getNewsListAndSaveToRecyclerView();

                mSelectedFragment = "homePage";

            }
        });

        Button questionButton = findViewById(R.id.question_btn);
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);

                questionPage();
                pageTitle.setText(R.string.questions);
                mSelectedFragment = "questions";

            }
        });


    }





}