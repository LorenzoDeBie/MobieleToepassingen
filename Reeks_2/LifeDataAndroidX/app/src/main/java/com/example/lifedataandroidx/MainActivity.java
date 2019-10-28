package com.example.lifedataandroidx;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import androidx.lifecycle.ViewModelProviders;
import com.example.lifedataandroidx.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this, new MainViewModelFactory(this)).get(MainViewModel.class);
        //setContentView(R.layout.activity_main);
        ActivityMainBinding b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        b.setLifecycleOwner(this);
        b.setActivity(this);
    }

    public MainViewModel getViewModel() {
        return viewModel;
    }
}
