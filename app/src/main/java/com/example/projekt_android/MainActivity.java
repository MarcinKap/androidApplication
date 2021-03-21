package com.example.projekt_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.projekt_android.MenuViews.News.NewsListFragment;
import com.example.projekt_android.Model.News;
import com.example.projekt_android.api.ApiUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String mSelectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("OnCreate w MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (savedInstanceState == null){

            mSelectedFragment = "homePage";

            getNewsListAndSaveToRecyclerView();


        }





//        initControls();
//        initListener();
    }



    // Wlaczanie recyxler view odpalający newsy
    private void saveNewsToNewsFragment(List<News> newsList){

        if (newsList == null) {
            System.out.println("lista jest null");
            return;
        }


        /// ZACZYNAMY

        // Wpisywanie fragmentu do kontenera
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_fragment, new NewsListFragment(newsList))
                .commit();



        // to wpisać do fragmentu chyba`    `
//        RecyclerView recyclerView = findViewById(R.id.newsRecyclerView);
//        if (recyclerView == null){
//            System.out.println("nie znaleziono recyclerview");
//            return;
//        }


        //przypisac fragment home do content_main

        // stworzyć obiekt fragment NewsListFragment - w środku powinien być napisany kod który dopiesuje listę do id/newsRecyclerView

//        Fragment fragment = new CurrentListMsgFragment(MqttForegroundService.getActiveMessageList());

//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container_fragment, fragment)
//                .commit();



//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//
//        NewsAdapter newsAdapter = new NewsAdapter(newsList);
//        recyclerView.setAdapter(newsAdapter);

    }


    // Zdobywanie listy news do home page
    private void getNewsListAndSaveToRecyclerView(){
        ApiUtils.getApiService().getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Response<List<News>>>() {
                    @Override
                    public void onNext(@NonNull Response<List<News>> response) {
                        if (ApiUtils.getResponseStatusCode(response) == 200){
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
    







//    private EditText editLogin;
//    private EditText editPassword;
//    private Button btnLogin;

    private void initControls(){


//        this.editLogin = findViewById(R.id.editLogin);
//        this.editPassword = findViewById(R.id.editPassword);
//        this.btnLogin = findViewById(R.id.btnLogin);

    }



    private void initListener(){





//        if(TextUtils.isEmpty(this.editLogin.getText())){
//            Toast.makeText(this, getString(R.string.loginSuccess), Toast.LENGTH_SHORT).show();
//        }


//        this.btnLogin.setOnClickListener(view -> {
//
//
//
//        });



    }




}