package com.example.projekt_android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.projekt_android.api.ApiUtils;
import com.example.projekt_android.database.database.AndroidDatabase;
import com.example.projekt_android.database.service.NewsService;
import com.example.projekt_android.menuViews.logIn.LogInFragment;
import com.example.projekt_android.menuViews.newsList.NewsListFragment;
import com.example.projekt_android.menuViews.question.QuestionFragment;
import com.example.projekt_android.menuViews.savingsIdeasList.SavingsIdeasListFragment;
import com.example.projekt_android.model.News;
import com.example.projekt_android.model.SavingsIdea;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity
{
    private static final String mCode = "StartActivity";
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView pageTitle;

    private String mSelectedFragment;
    private AndroidDatabase androidDatabase;
    private NewsService newsService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        System.out.println("Tworzenie StartActivity");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        androidDatabase = AndroidDatabase.getAndroidDatabase(getApplicationContext());


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        pageTitle = findViewById(R.id.toolbar_selected_item_menu_drawer_txt);

        initNavigationDrawer();
        buttonsListeners();
        viewManagement(savedInstanceState);
    }

    private void viewManagement(Bundle savedInstanceState){
        if (savedInstanceState != null) {
            mSelectedFragment = savedInstanceState.getString("SelectedFragment");
            if (mSelectedFragment != null) {
                switch (mSelectedFragment) {
                    case "homePage":
                        homePage_getNewsList();
                        pageTitle.setText(R.string.home);
                        break;
                    case "questions":
                        questionFragment();
                        pageTitle.setText(R.string.questions);
                        break;
                    case "savingsIdeas":
                        break;
                    case "forum":
                        break;
                    case "LogIn":
                        logInFragment();
                        pageTitle.setText(R.string.questions);
                        break;
                }
            }

        } else {
            homePage_getNewsList();
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

        // Home
        Button homeButton = findViewById(R.id.home_btn);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                pageTitle.setText(R.string.home);
                mSelectedFragment = "homePage";

                homePage_getNewsList();
            }
        });

        // Pytania
        Button questionButton = findViewById(R.id.question_btn);
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                pageTitle.setText(R.string.questions);
                mSelectedFragment = "questions";

                questionFragment();
            }
        });

        // Pomysły oszczędności
        Button savingsIdeasButton = findViewById(R.id.savingsIdeas_btn);
        savingsIdeasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);

                savingsIdeasFragment();
                pageTitle.setText(R.string.savingsIdeas);
                mSelectedFragment = "savingsIdeas";

            }
        });

        // logowanie
        Button logInButton = findViewById(R.id.drawer_log_in_button);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                pageTitle.setText(R.string.log_in);
                mSelectedFragment = "LogIn";

                logInFragment();
            }
        });







    }



    // Zdobywanie listy news do home page
    private void homePage_getNewsList() {
        newsService = new NewsService(androidDatabase);


        ApiUtils.getApiService().getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Response<List<News>>>() {
                    @Override
                    public void onNext(@NonNull Response<List<News>> response) {
                        if (ApiUtils.getResponseStatusCode(response) == 200) {
                            // zapis news do bazy danych
                            newsService.saveNews(response.body());
                            // włączanie fragmentu z newsami
                            runNewsFragment(response.body());
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("API_CALL", e.getMessage(), e);
                        runNewsFragment(newsService.getNewsList());
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    // Wlaczanie fragmentu z newsami
    private void runNewsFragment(List<News> newsList) {
        if (newsList == null) {
            System.out.println("lista jest null");
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_fragment, new NewsListFragment(newsList, getApplicationContext()))
                .commit();
    }

    private void questionFragment(){

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_fragment, new QuestionFragment(getApplicationContext()))
                .commit();


    }
    private void savingsIdeasFragment() {
        ApiUtils.getApiService().getSavingsIdeas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Response<List<SavingsIdea>>>() {
                    @Override
                    public void onNext(@NonNull Response<List<SavingsIdea>> response) {
                        if (ApiUtils.getResponseStatusCode(response) == 200) {
                            runSavingsIdeasFragment(response.body());
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
    private void logInFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_fragment, new LogInFragment(getApplicationContext()))
                .commit();
    }



    // Wlaczanie fragmentu z pomyslami
    private void runSavingsIdeasFragment(List<SavingsIdea> savingsIdeaList) {
        if (savingsIdeaList == null) {
            System.out.println("lista jest null");
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_fragment, new SavingsIdeasListFragment(savingsIdeaList, getApplicationContext()))
                .commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }



    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0)
        {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
            {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        }
        else
        {
            super.onBackPressed();
        }

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (mSelectedFragment != null) {
            switch (mSelectedFragment) {
                case "homePage":
                    mSelectedFragment = "homePage";
//                    getFragmentManager().beginTransaction().remove()
                    homePage_getNewsList();
                    pageTitle.setText(R.string.home);
                    break;
                case "questions":
                    mSelectedFragment = "homePage";
//                    questionFragment();
                    pageTitle.setText(R.string.questions);
                    break;
                case "savingsIdeas":
                    mSelectedFragment = "homePage";
//                    savingsIdeasFragment();
                    break;
                case "logOut":
                    break;

            }
        }else{
            finish();
        }



    }


}