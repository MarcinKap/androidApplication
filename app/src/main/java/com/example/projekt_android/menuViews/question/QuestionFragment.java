package com.example.projekt_android.menuViews.question;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.projekt_android.model.Question;
import com.example.projekt_android.R;
import com.example.projekt_android.api.ApiUtils;
import com.example.projekt_android.constans.Validation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionFragment extends Fragment {
    private Question question;
    private Context context;

    private EditText questionSenderEmail;
    private EditText questionNameAndSurname;
    private EditText questionTextMessage;
    private EditText questionTopic;
    private Button questionButtonSendMessage;

    private TextView questionNameAndSurnameError;
    private TextView questionSenderEmailError;
    private TextView questionTextMessageError;
    private TextView questionTopicError;

    String nameAndSurname;
    String email;
    String topic;
    String message;

    public QuestionFragment() {
        // Required empty public constructor
    }


    public QuestionFragment(Context context) {
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

        View rootView = inflater.inflate(R.layout.fragment_question, container, false);

        questionTopic = rootView.findViewById(R.id.questionTopic);
        questionTextMessage = rootView.findViewById(R.id.questionTextMessage);
        questionNameAndSurname = rootView.findViewById(R.id.questionNameAndSurname);
        questionSenderEmail = rootView.findViewById(R.id.questionSenderEmail);

        questionNameAndSurnameError = rootView.findViewById(R.id.questionNameAndSurnameError);
        questionSenderEmailError = rootView.findViewById(R.id.questionSenderEmailError);
        questionTextMessageError = rootView.findViewById(R.id.questionTextMessageError);
        questionTopicError = rootView.findViewById(R.id.questionTopicError);

        setGoneErrorTextViews();


        questionButtonSendMessage = rootView.findViewById(R.id.questionButtonSendMessage);

        questionButtonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameAndSurname = questionNameAndSurname.getText().toString();
                email = questionSenderEmail.getText().toString();
                topic = questionTopic.getText().toString();
                message = questionTextMessage.getText().toString();


                if (validationContentQuestionForm()) {
                    setGoneErrorTextViews();

                    Question question = new Question(nameAndSurname, email, topic, message);

                    Call<Void> call = ApiUtils.getApiService().sendQuestion(nameAndSurname, email, topic, message);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
//                        lblresponse.setText(response.body().toString());
                            questionTextMessage.setText(null);
                            questionTopic.setText(null);

                            CharSequence text = getResources().getText(R.string.toast_question_sended);
                            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                            toast.show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
                            System.out.println("nie udało się");
                        }

                    });
                } else {

                    setVisibleErrorTextViewsIfErrorsHappened();


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
        if (nameAndSurname != null && email != null && topic != null && !topic.equals("") && message != null && !message.equals("") && Validation.isValidEmail(charSequence)) {
            return true;
        }
        return false;

    }

    private void setVisibleErrorTextViewsIfErrorsHappened() {
        CharSequence charSequence = email;
        if (nameAndSurname == null || nameAndSurname.equals("")) {
            questionNameAndSurnameError.setVisibility(View.VISIBLE);
            questionNameAndSurnameError.setText(R.string.fil_name);
            questionNameAndSurname.setBackgroundResource(R.drawable.input_field_error);
        }else{
            questionNameAndSurnameError.setVisibility(View.INVISIBLE);
            questionNameAndSurname.setBackgroundResource(R.drawable.input_field);
        }

        if (topic == null || topic.equals("")) {
            questionTopicError.setVisibility(View.VISIBLE);
            questionTopicError.setText(R.string.fill_topic);
            questionTopic.setBackgroundResource(R.drawable.input_field_error);
        }else {
            questionTopicError.setVisibility(View.INVISIBLE);
            questionTopic.setBackgroundResource(R.drawable.input_field);
        }
        if (message == null || message.equals("")) {
            questionTextMessageError.setVisibility(View.VISIBLE);
            questionTextMessageError.setText(R.string.fill_message);
            questionTextMessage.setBackgroundResource(R.drawable.input_field_error);
        }else{
            questionTextMessageError.setVisibility(View.INVISIBLE);
            questionTextMessage.setBackgroundResource(R.drawable.input_field);
        }

        if (email == null || email.equals("")) {
            questionSenderEmailError.setVisibility(View.VISIBLE);
            questionSenderEmailError.setText(R.string.fill_email);
            questionSenderEmail.setBackgroundResource(R.drawable.input_field_error);
        } else if (!Validation.isValidEmail(charSequence)) {
            questionSenderEmailError.setVisibility(View.VISIBLE);
            questionSenderEmailError.setText(R.string.wrong_email);
        } else{
            questionSenderEmailError.setVisibility(View.INVISIBLE);
            questionSenderEmail.setBackgroundResource(R.drawable.input_field);
        }
    }

    private void setGoneErrorTextViews() {
        questionNameAndSurnameError.setVisibility(View.INVISIBLE);
        questionSenderEmailError.setVisibility(View.INVISIBLE);
        questionTextMessageError.setVisibility(View.INVISIBLE);
        questionTopicError.setVisibility(View.INVISIBLE);

        questionNameAndSurname.setBackgroundResource(R.drawable.input_field);
        questionTopic.setBackgroundResource(R.drawable.input_field);
        questionSenderEmail.setBackgroundResource(R.drawable.input_field);
        questionTextMessage.setBackgroundResource(R.drawable.input_field);

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