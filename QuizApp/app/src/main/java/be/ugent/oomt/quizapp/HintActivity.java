package be.ugent.oomt.quizapp;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import be.ugent.oomt.quizapp.databinding.ActivityHintBinding;
import be.ugent.oomt.quizapp.model.QuizMaster;

public class HintActivity extends AppCompatActivity {

    public static final int NO_HINT = 123;
    public static final int IMAGE_HINT = 984;
    public static final int TEXT_HINT = 148;

    public static final String TEXT_HINT_VIEWED = "be.ugent.oomt.quizapp.txtHintViewed";
    public static final String IMAGE_HINT_VIEWED = "be.ugent.oomt.quizapp.imageHintViewed";

    public ObservableInt hintVisibility = new ObservableInt();


    public static Intent createIntent(Context ctx) {
        Intent i = new Intent(ctx, HintActivity.class);
        // Add required extras
        // none (shared singleton model)
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHintBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_hint);

        hintVisibility.set(NO_HINT);

        binding.setQuizMaster(QuizMaster.getInstance());
        binding.setHandler(this);
    }

    public void onShowTextHint() {
        hintVisibility.set(TEXT_HINT);
        Intent i = getIntent();
        i.putExtra(TEXT_HINT_VIEWED, true);
        setResult(RESULT_OK, i);
    }

    public void onShowImageHint() {
        hintVisibility.set(IMAGE_HINT);
        Intent i = getIntent();
        i.putExtra(IMAGE_HINT_VIEWED, true);
        setResult(RESULT_OK, i);
    }

    /*
    This method needs to be overwritten to call the finish() method. This way our MainActivity
    receives a onActivityResult call. Without this code the UP functionality works but does not
    call the required result method.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
