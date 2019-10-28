package com.example.appquiz;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import com.example.appquiz.databinding.ActivityMainBinding;
import com.example.appquiz.model.*;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_HINT_USED = 0;
    private static final String TEXT_HINT_USED = "com.example.appquiz.text_hint_used";
    private static final String VISUAL_HINT_USED = "com.example.appquiz.visual_hint_used";
    private static final String TAG = "MainActivity";
    private QuizMaster quizMaster = QuizMaster.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        final QuizMaster quizMaster = QuizMaster.getInstance();
        activityMainBinding.setQuizMaster(quizMaster);
        activityMainBinding.setMainActivity(this);
        quizMaster.feedback.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(!quizMaster.feedback.get().trim().isEmpty())
                    Toast.makeText(MainActivity.this, quizMaster.feedback.get(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btnHintClickHandler(View view) {
        Intent intent = HintActivity.createIntent(getApplicationContext());
        startActivityForResult(intent, REQUEST_CODE_HINT_USED);
    }

    public void btnOkClickHandler() {
        if(quizMaster.validateAnswer()) {
            quizMaster.feedback.set(getString(R.string.answerOk));
            quizMaster.nextQuestion();
            quizMaster.userAnswer.set("");
        }
        else {
            quizMaster.feedback.set(getString(R.string.answerNotOk));
        }
    }

    public static Intent createHintReply(Context ctx, boolean textHintUsed, boolean visualHintUsed) {
        Intent intent = new Intent(ctx, MainActivity.class);
        intent.putExtra(TEXT_HINT_USED, textHintUsed);
        intent.putExtra(VISUAL_HINT_USED, visualHintUsed);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode != REQUEST_CODE_HINT_USED) return;
        if(resultCode == RESULT_OK || resultCode == RESULT_CANCELED) {
            assert data != null;
            boolean text = data.getBooleanExtra(TEXT_HINT_USED, false);
            boolean visual = data.getBooleanExtra(VISUAL_HINT_USED, false);
            String toastText = "";
            if(text && visual) toastText = "You have viewed text and visual hint";
            else if(text) toastText = "You have viewed text hint";
            else if(visual) toastText = "You have viewed visual hint";
            else toastText = "You have viewed no hint";
            Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
        }
    }
}
