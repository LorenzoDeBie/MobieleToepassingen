package com.example.lifedataandroidx;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private int currentQuoteIndex;
    private QuoteDAO dao;

    private MutableLiveData<String> quote = new MutableLiveData<>();
    private MutableLiveData<String> author = new MutableLiveData<>();
    private MutableLiveData<Integer> progress = new MutableLiveData<>();

    public MainViewModel(Context c) {
        super();
        currentQuoteIndex = 0;
        dao = new QuoteDAO(c);
        Log.i("quote", Integer.toString(dao.size()));
        quote.setValue(dao.get(currentQuoteIndex).getText());
        author.setValue(dao.get(currentQuoteIndex).getAuthor());

        new CountDownTimer(5000, 10) {

            public void onTick(long millisUntilFinished) {
                progress.setValue((int)(100*(5000-millisUntilFinished)/5000));
            }

            public void onFinish() {
                currentQuoteIndex = (currentQuoteIndex+1)%dao.size();
                quote.setValue(dao.get(currentQuoteIndex).getText());
                author.setValue(dao.get(currentQuoteIndex).getAuthor());
                progress.setValue(0);
                this.start();
            }
        }.start();
    }

    public LiveData<String> getQuote() {
        return quote;
    }

    public LiveData<String> getAuthor() {
        return author;
    }

    public LiveData<Integer> getProgress() {
        return progress;
    }
}
