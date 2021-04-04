package com.example.projekt_android.menuViews.logIn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projekt_android.MainActivity;
import com.example.projekt_android.R;
import com.example.projekt_android.api.ApiUtils;
import com.example.projekt_android.constans.Validation;
import com.example.projekt_android.database.database.AndroidDatabase;
import com.example.projekt_android.database.service.UserService;
import com.example.projekt_android.mapper.UserMapper;
import com.example.projekt_android.model.User;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LogInFragment extends Fragment {
    private Context context;
    private UserService userService;

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button logInButton;


    String password;
    String email;

    public LogInFragment() {
        // Required empty public constructor
    }


    public LogInFragment(Context context) {
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

        View rootView = inflater.inflate(R.layout.fragment_log_in, container, false);

        emailEditText = rootView.findViewById(R.id.emailForLogIn);
        passwordEditText = rootView.findViewById(R.id.passwordEditText);

        logInButton = rootView.findViewById(R.id.logInButton);

//        setGoneErrorTextViews();

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("LOG IN BUTTON KLIKNIETY");

                password =passwordEditText.getText().toString();
                email = emailEditText.getText().toString();
                userService = new UserService(AndroidDatabase.getAndroidDatabase(getContext()), getContext(), getActivity());

                if (validationContentQuestionForm()) {
//                    setGoneErrorTextViews();

                    ApiUtils.getApiService().logIn(email, password)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DisposableObserver<Response<User>>() {
                                @Override
                                public void onNext(@NonNull Response<User> response) {
                                    if (ApiUtils.getResponseStatusCode(response) == 200) {
                                        // zapis tokena do bazy danych
                                        System.out.println(response.body().getEmail());
                                        System.out.println(response.body().getToken());
                                        System.out.println(response.body().getId());
                                        if (response.body()!=null || !response.body().equals(""))
                                        userService.saveUser(UserMapper.mapToEntity(response.body()));


                                        Intent intent = new Intent(getActivity() , MainActivity.class);
                                        startActivity(intent);
                                        //Close Notification Drawer because after clicked it doesn't close
                                        Intent closeIntent = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                                        context.sendBroadcast(closeIntent);

                                        getActivity().finish();
//                                        newsService.saveNews(response.body());
                                        // włączanie fragmentu z newsami
//                                        runNewsFragment(response.body());
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


                } else {

                    CharSequence text = getResources().getText(R.string.error_send_question);
                    Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        if (context != null) {
        } else {
            return null;
        }
        return rootView;
    }

    private boolean validationContentQuestionForm() {
        CharSequence charSequence = email;
        if (password != null && email != null && Validation.isValidEmail(charSequence)) {
            return true;
        }
        return false;

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