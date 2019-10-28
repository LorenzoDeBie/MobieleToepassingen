package com.example.appquiz;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import com.example.appquiz.databinding.ActivityHintBinding;
import com.example.appquiz.model.QuizMaster;

public class HintActivity extends AppCompatActivity {

    private static  final String CURRENT_QUESTION = "current_question";
    public static final int NO_HINT = 0;
    public static final int TEXT_HINT = 1;
    public static final int VISUAL_HINT = 2;

    private QuizMaster quizMaster = QuizMaster.getInstance();

    private boolean textViewed = false;
    private boolean visualViewed = false;
    public ObservableInt hint_visibility = new ObservableInt(NO_HINT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        ActivityHintBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_hint);
        binding.setQuizMaster(quizMaster);
        binding.setHintActivity(this);
        setResult(RESULT_CANCELED, MainActivity.createHintReply(getApplicationContext(), textViewed, visualViewed));
    }

    public static Intent createIntent(Context ctx) {
        return new Intent(ctx, HintActivity.class);
    }

    public void onBtnTextClicked(View view) {
        textViewed = true;
        setResult(RESULT_OK, MainActivity.createHintReply(getApplicationContext(), textViewed, visualViewed));
        //TODO: show text hint
        hint_visibility.set(TEXT_HINT);
    }

    public void onBtnVisualClicked(View view) {
        visualViewed = true;
        setResult(RESULT_OK, MainActivity.createHintReply(getApplicationContext(), textViewed, visualViewed));
        //TODO: show visual hint
        hint_visibility.set(VISUAL_HINT);
    }



}
