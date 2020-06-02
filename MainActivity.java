package com.company.lab;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.NumberPicker;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_expert_count_picker)
    NumberPicker expertCountPicker;
    @BindView(R.id.main_input_recycler)
    RecyclerView inputRecycler;
    @BindView(R.id.main_iteration_pager)
    ViewPager iterationPager;

    InputAdapter inputAdapter;
    IterationPagerAdapter iterationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initUI();
        initData();
    }

    private void initUI() {
        expertCountPicker.setMinValue(1);
        expertCountPicker.setMaxValue(7);
        expertCountPicker.setValue(5);
        expertCountPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (inputAdapter != null) {
                inputAdapter.changeItemsCount(newVal);
            }
        });

        inputAdapter = new InputAdapter();
        inputRecycler.setAdapter(inputAdapter);
        inputRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        iterationAdapter = new IterationPagerAdapter(getSupportFragmentManager());
        iterationPager.setAdapter(iterationAdapter);
    }

    private void initData() {
//        List<Double> expertsData = Arrays.asList(6.2, 6.3, 7.0, 6.3, 6.0);
//        List<Double> expertsData = Arrays.asList(  8.9,	9.8,	8.9,	8.3,	8.7,	9.8);
        /*
        7.7,	8.8,	9.5,	7.7,	8.9,	9.3
        8.1,	8.4,	9.7,	9.2,	9.0,	10.0
        9.8,	9.6,	9.1,	9.9,	8.8,	9.5
        8.9,	9.8,	8.9,	8.3,	8.7,	9.8
        9.3,	9.5,	9.8,	9.1,	9.2,	9.6
        9.1,	9.8,	9.4,	9.1,	8.8,	9.5
        9.0,	9.7,	8.2,	9.8,	8.8,	8.9

         */
//        List<Double> expertsData = Arrays.asList(8.0, 7.6, 6.9, 10.0, 7.1, 7.9, 9.4);

        List<Double> expertsData = Arrays.asList(5.0, 6.5, 4.3, 5.2, 6.0);

        expertCountPicker.setValue(expertsData.size());
        inputAdapter.setItems(expertsData);
    }


    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }

        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        view.clearFocus();
    }

    @OnClick(R.id.main_calculate_button)
    void onCalculateClick() {
        hideKeyboard();

        iterationAdapter.calculate(inputAdapter.getItems());
    }
}
