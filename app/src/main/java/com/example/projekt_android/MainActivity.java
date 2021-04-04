package com.example.projekt_android;

import android.content.Intent;
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
import com.example.projekt_android.database.entity.UserEntity;
import com.example.projekt_android.database.service.NewsService;
import com.example.projekt_android.database.service.UserService;
import com.example.projekt_android.menuViews.myAccount.MyAccountFragment;
import com.example.projekt_android.menuViews.newsList.NewsListFragment;
import com.example.projekt_android.menuViews.question.QuestionFragmentForLoggedUser;
import com.example.projekt_android.menuViews.savingsIdeasList.SavingsIdeasListFragmentForLoggedUser;
import com.example.projekt_android.model.News;
import com.example.projekt_android.model.SavingsIdea;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private AndroidDatabase androidDatabase;
    private NewsService newsService;
    private UserService userService;

    private List<UserEntity> userEntityListToCheck;
    private TextView userIDToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelectedFragment = "homePage";

        androidDatabase = AndroidDatabase.getAndroidDatabase(getApplicationContext());
        userEntityListToCheck = new ArrayList<>();


        runStartActivityIfTokenIsUnavailable();

        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        pageTitle = findViewById(R.id.toolbar_selected_item_menu_drawer_txt);

        initNavigationDrawer();
        buttonsListeners();
        viewManagement(savedInstanceState);
    }

    private void viewManagement(Bundle savedInstanceState) {
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
                    case "chat":
                        break;
                    case "myAccount":
                        break;
                    case "logOut":
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
        userIDToolbar = findViewById(R.id.toolbar_userID_textView);
        setUserNameInToolbar();

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
                mSelectedFragment = "homePage";
                drawerLayout.closeDrawer(GravityCompat.START);
                pageTitle.setText(R.string.home);


                homePage_getNewsList();
            }
        });

        Button questionButton = findViewById(R.id.question_btn);
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedFragment = "questions";
                drawerLayout.closeDrawer(GravityCompat.START);
                pageTitle.setText(R.string.questions);


                questionFragment();
            }
        });

        Button savingsIdeasButton = findViewById(R.id.savingsIdeas_btn);
        savingsIdeasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedFragment = "savingsIdeas";
                pageTitle.setText(R.string.savingsIdeas);
                drawerLayout.closeDrawer(GravityCompat.START);

                savingsIdeasFragment();



            }
        });

        Button myAccountButton = findViewById(R.id.drawer_my_account_button);
        myAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedFragment = "myAccount";
                drawerLayout.closeDrawer(GravityCompat.START);
                myAccountFragment();

            }
        });


        Button logOutButton = findViewById(R.id.drawer_log_out_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                logOut();
            }
        });



    }


    // Zdobywanie listy news do home page
    private void homePage_getNewsList() {
        newsService = new NewsService(androidDatabase);

        androidDatabase.getQueryExecutor().execute(() -> {
            final Optional<List<UserEntity>> userEntityList = Optional.ofNullable(androidDatabase.usersEntityDAO().getAll());

            if (userEntityList == null || userEntityList.get().size() == 0) {
                Intent intent = new Intent(this, StartActivity.class);
                startActivity(intent);
                finish();
            } else {
                String token = userEntityList.get().get(0).getToken();


                ApiUtils.getApiService().getNewsForLoggedUser("Bearer " + token) // na początku tokena dodajemy "Bearer " dla serwera
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableObserver<Response<List<News>>>() {
                            @Override
                            public void onNext(@NonNull Response<List<News>> response) {
                                //Kod wykonuje sie jesli serwer jest wlaczony i token jest prawidlowy
                                if (ApiUtils.getResponseStatusCode(response) == 200) {
                                    // zapis news do bazy danych
                                    newsService.saveNews(response.body());
                                    // włączanie fragmentu z newsami
                                    newsFragment(response.body());
                                }
                                // Kod wykonuje się jeśli wygasł token
                                else if (ApiUtils.getResponseStatusCode(response) == 500) {
                                    androidDatabase.getQueryExecutor().execute(() -> {
                                        //usuwanie przestarzałego tokena wraz z użytkownikiem
                                        androidDatabase.usersEntityDAO().deleteAll();
                                        //przejscie do aktywnosci start
                                        Intent intent = new Intent(getApplication(), StartActivity.class);
                                        startActivity(intent);
                                        finish();
                                    });
                                }
                            }
                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("API_CALL", e.getMessage(), e);
                                newsFragment(newsService.getNewsList());
                            }
                            @Override
                            public void onComplete() {
                            }
                        });
            }
        });
    }

    // Wlaczanie fragmentu z newsami
    private void newsFragment(List<News> newsList) {
        if (newsList == null) {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_fragment, new NewsListFragment(newsList, getApplicationContext()))
                .commit();
    }

    private void questionFragment() {
        androidDatabase.getQueryExecutor().execute(() -> {
            final Optional<List<UserEntity>> userEntityList = Optional.ofNullable(androidDatabase.usersEntityDAO().getAll());

            runOnUiThread(() -> {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_fragment, new QuestionFragmentForLoggedUser(getApplicationContext(), userEntityList.get().get(0)))
                        .commit();
            });

        });

    }

    public void savingsIdeasFragment() {
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

    private void myAccountFragment() {
        androidDatabase.getQueryExecutor().execute(() -> {
            final Optional<List<UserEntity>> userEntityList = Optional.ofNullable(androidDatabase.usersEntityDAO().getAll());

            runOnUiThread(() -> {
                System.out.println("ilosc uzytkownikow w systemie " + userEntityList.get().size());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_fragment, new MyAccountFragment(getApplicationContext(), userEntityList.get().get(0)))
                        .commit();
            });

        });

    }



    // Wlaczanie fragmentu z pomyslami
    private void runSavingsIdeasFragment(List<SavingsIdea> savingsIdeaList) {
        if (savingsIdeaList == null) {
            return;
        }
        androidDatabase.getQueryExecutor().execute(() -> {
            final Optional<List<UserEntity>> userEntityList = Optional.ofNullable(androidDatabase.usersEntityDAO().getAll());

            runOnUiThread(() -> {
                if (userEntityList.get().size() > 0){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_fragment, new SavingsIdeasListFragmentForLoggedUser(savingsIdeaList, getApplicationContext(), userEntityList.get().get(0)))
                            .commit();
                }


            });
        });


    }

    private void runStartActivityIfTokenIsUnavailable() {
        androidDatabase.getQueryExecutor().execute(() -> {
            final Optional<List<UserEntity>> userEntityList = Optional.ofNullable(androidDatabase.usersEntityDAO().getAll());

            runOnUiThread(() -> {
                for (UserEntity userEntity : userEntityList.get()) {
                    userEntityListToCheck.add(userEntity);
                }
                if (userEntityListToCheck.size() == 0 || userEntityListToCheck == null) {
                    Intent intent = new Intent(this, StartActivity.class);
                    startActivity(intent);
                }
            });
        });
    }

    private void setUserNameInToolbar() {
        androidDatabase.getQueryExecutor().execute(() -> {
            final Optional<List<UserEntity>> userEntityList = Optional.ofNullable(androidDatabase.usersEntityDAO().getAll());

            runOnUiThread(() -> {
                if (userEntityList.get().size() > 0)
                    userIDToolbar.setText(userEntityList.get().get(0).getName());
            });
        });

    }

    private void logOut(){
        androidDatabase.getQueryExecutor().execute(() -> {
            //usuwanie przestarzałego tokena wraz z użytkownikiem
            androidDatabase.usersEntityDAO().deleteAll();
            //przejscie do aktywnosci start
            Intent intent = new Intent(getApplication(), StartActivity.class);
            startActivity(intent);
            finish();
        });
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
//                case "forum":
//                    break;
//                case "chat":
//                    break;
                case "myAccount":
                    mSelectedFragment = "homePage";
                    myAccountFragment();
                    break;
                case "logOut":
                    break;

            }
        }else{
            finish();
        }



    }









}