package com.company.lab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.company.lab.calculation.CalculationHelper;
import com.company.lab.calculation.Formula8Model;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class Formula8Fragment extends CalculationFragment {

    @BindView(R.id.formula8_n)
    TextView nText;
    @BindView(R.id.formula8_lambda)
    TextView lambdaText;
    @BindView(R.id.formula8_recycler)
    RecyclerView resultRecycler;

    private List<Double> items;

    private Formula8Adapter adapter;

    @Override
    public void calculate(List<Double> items) {
        this.items = items;

        if (resultRecycler != null) {
            initUI();
        }
    }

    private void initUI() {
        if (items != null) {
            CalculationHelper.Formula8Calculation formula8 = CalculationHelper.getCalculationFor8Formula();
            List<Formula8Model> result = formula8.calculate(items, CalculationHelper.EXP);

            nText.setText(String.format(Locale.getDefault(), "%d", items.size()));
            lambdaText.setText(String.format(Locale.getDefault(), "%.03f", CalculationHelper.EXP));

            adapter = new Formula8Adapter(items, result);
        }

        resultRecycler.setAdapter(adapter);
        resultRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_formula8;
    }
}
