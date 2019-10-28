package be.ugent.oomt.quizapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import be.ugent.oomt.quizapp.model.QuizMaster;
import be.ugent.oomt.quizapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int HINT_REQUEST = 1;
    private QuizMaster quizMaster = QuizMaster.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        quizMaster.feedback.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (!quizMaster.feedback.get().trim().isEmpty())
                    Toast.makeText(MainActivity.this, quizMaster.feedback.get(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.setQuizMaster(quizMaster);
        binding.setHandler(this);
    }

    public void onHintClick(View view) {
        Intent i = HintActivity.createIntent(this);
        startActivityForResult(i, HINT_REQUEST);
    }

    public void onOkClick() {
        if (quizMaster.validateAnswer()) {
            quizMaster.nextQuestion();
            quizMaster.feedback.set(getString(R.string.valid_answer));
        } else {
            quizMaster.feedback.set(getString(R.string.invalid_answer));
        }
        quizMaster.userAnswer.set("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == HINT_REQUEST) {
            String feedback = "";
            if (resultCode == RESULT_OK) { // hint shown
                if (data.getBooleanExtra(HintActivity.TEXT_HINT_VIEWED, false))
                    feedback += "\n" + getString(R.string.text_hint_shown);
                if (data.getBooleanExtra(HintActivity.IMAGE_HINT_VIEWED, false))
                    feedback += "\n" + getString(R.string.image_hint_shown);

            } else { // hint not shown
                feedback = getString(R.string.no_hints_shown);
            }
            quizMaster.feedback.set(feedback.trim());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        Log.d(MainActivity.class.getName(), "requestCode: " + requestCode + ", resultCode: " + resultCode);
    }
}
