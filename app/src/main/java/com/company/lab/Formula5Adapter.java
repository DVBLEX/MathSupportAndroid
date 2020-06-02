package com.company.lab;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.lab.calculation.Formula5Model;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

class Formula5Adapter extends RecyclerView.Adapter<Formula5Adapter.BaseViewHolder> {

    private static final int TYPE_CELL = 1;
    private static final int TYPE_RESULT = 2;

    private List<Double> inputItems;
    private List<Formula5Model> items;

    public Formula5Adapter(List<Double> inputItems, List<Formula5Model> items) {
        this.inputItems = inputItems;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == items.size()) {
            return TYPE_RESULT;
        }

        return TYPE_CELL;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CELL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_formula5, parent, false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_RESULT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_formula5_result, parent, false);
            return new ResultViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.init(items.get(position));
        } else if (holder instanceof ResultViewHolder) {
            ResultViewHolder viewHolder = (ResultViewHolder) holder;
            viewHolder.init(inputItems, items.get(items.size() - 1));
        }
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.item_formula5_iteration)
        TextView iterationText;
        @BindView(R.id.item_formula5_cells_container)
        ViewGroup cellsContainer;
        @BindViews({R.id.item_formula5_cell_container1, R.id.item_formula5_cell_container2, R.id.item_formula5_cell_container3,
                R.id.item_formula5_cell_container4, R.id.item_formula5_cell_container5, R.id.item_formula5_cell_container6,
                R.id.item_formula5_cell_container7, R.id.item_formula5_cell_container8})
        List<ViewGroup> cellContainerList;
        @BindViews({R.id.item_formula5_label1, R.id.item_formula5_label2, R.id.item_formula5_label3,
                R.id.item_formula5_label4, R.id.item_formula5_label5, R.id.item_formula5_label6,
                R.id.item_formula5_label7, R.id.item_formula5_label8})
        List<TextView> labelTexts;
        @BindViews({R.id.item_formula5_numerator1, R.id.item_formula5_numerator2, R.id.item_formula5_numerator3,
                R.id.item_formula5_numerator4, R.id.item_formula5_numerator5, R.id.item_formula5_numerator6,
                R.id.item_formula5_numerator7, R.id.item_formula5_numerator8})
        List<TextView> numeratorTexts;
        @BindViews({R.id.item_formula5_denominator1, R.id.item_formula5_denominator2, R.id.item_formula5_denominator3,
                R.id.item_formula5_denominator4, R.id.item_formula5_denominator5, R.id.item_formula5_denominator6,
                R.id.item_formula5_denominator7, R.id.item_formula5_denominator8})
        List<TextView> denominatorTexts;
        @BindView(R.id.item_formula5_xcl)
        TextView xclText;
        @BindView(R.id.item_formula5_diff)
        TextView diffText;
        @BindView(R.id.item_formula5_result)
        TextView resultText;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void init(Formula5Model formula5Model) {

            if (formula5Model.iteration == 0) {
                cellsContainer.setVisibility(View.GONE);
                diffText.setVisibility(View.GONE);
                resultText.setVisibility(View.GONE);
            } else {
                cellsContainer.setVisibility(View.VISIBLE);
                diffText.setVisibility(View.VISIBLE);
                resultText.setVisibility(View.VISIBLE);

                int itemsCount = inputItems.size();

                for (int i = 0; i < cellContainerList.size(); i++) {
                    ViewGroup cellContainer = cellContainerList.get(i);
                    TextView labelText = labelTexts.get(i);
                    TextView numeratorText = numeratorTexts.get(i);
                    TextView denominatorText = denominatorTexts.get(i);

                    if (i < itemsCount) {
                        cellContainer.setVisibility(View.VISIBLE);
                        labelText.setText(String.format(Locale.getDefault(), "%d", i + 1));
                        numeratorText.setText(String.format(Locale.getDefault(), "%.03f",
                                formula5Model.numeratorList.get(i)));
                        denominatorText.setText(String.format(Locale.getDefault(), "%.03f",
                                formula5Model.denominatorList.get(i)));
                    } else if (i == itemsCount) {
                        cellContainer.setVisibility(View.VISIBLE);
                        labelText.setText("Сума");
                        numeratorText.setText(String.format(Locale.getDefault(), "%.03f",
                                formula5Model.numeratorSum));
                        denominatorText.setText(String.format(Locale.getDefault(), "%.03f",
                                formula5Model.denominatorSum));
                    } else {
                        cellContainer.setVisibility(View.INVISIBLE);
                    }
                }
            }

            iterationText.setText(String.format(Locale.getDefault(), "Ітерація %d", formula5Model.iteration));
            xclText.setText(String.format(Locale.getDefault(), "Xc[%d] = %.03f", formula5Model.iteration, formula5Model.result));
            diffText.setText(String.format(Locale.getDefault(), "Xc[%d] - Xc[%d] = %.03f",
                    formula5Model.iteration, formula5Model.iteration - 1, formula5Model.different));

            if (formula5Model.iteration == items.size() - 1) {
                resultText.setText("Умову виконано");
            } else {
                resultText.setText("Умову не виконано");
            }
        }
    }

    public class ResultViewHolder extends BaseViewHolder {

        @BindViews({R.id.item_formula5_cell_container1, R.id.item_formula5_cell_container2, R.id.item_formula5_cell_container3,
                R.id.item_formula5_cell_container4, R.id.item_formula5_cell_container5, R.id.item_formula5_cell_container6,
                R.id.item_formula5_cell_container7, R.id.item_formula5_cell_container8})
        List<ViewGroup> cellContainerList;
        @BindViews({R.id.item_formula5_label1, R.id.item_formula5_label2, R.id.item_formula5_label3,
                R.id.item_formula5_label4, R.id.item_formula5_label5, R.id.item_formula5_label6,
                R.id.item_formula5_label7, R.id.item_formula5_label8})
        List<TextView> labelTexts;
        @BindViews({R.id.item_formula5_diff1, R.id.item_formula5_diff2, R.id.item_formula5_diff3,
                R.id.item_formula5_diff4, R.id.item_formula5_diff5, R.id.item_formula5_diff6,
                R.id.item_formula5_diff7, R.id.item_formula5_diff8})
        List<TextView> diffTexts;
        @BindView(R.id.item_formula5_result)
        TextView resultText;

        public ResultViewHolder(View itemView) {
            super(itemView);
        }

        public void init(List<Double> inputItems, Formula5Model formula5Model) {

            double minDif = 0;
            int resultIndex = -1;

            for (int i = 0; i < cellContainerList.size(); i++) {
                ViewGroup cellContainer = cellContainerList.get(i);
                TextView labelText = labelTexts.get(i);
                TextView diffText = diffTexts.get(i);

                if (i < inputItems.size()) {
                    double diff = Math.abs(inputItems.get(i) - formula5Model.result);

                    if (diff < minDif || resultIndex < 0) {
                        minDif = diff;
                        resultIndex = i;
                    }

                    cellContainer.setVisibility(View.VISIBLE);
                    labelText.setText(String.format(Locale.getDefault(), "%d", i + 1));
                    diffText.setText(String.format(Locale.getDefault(), "%.03f",
                            diff));
                } else {
                    cellContainer.setVisibility(View.INVISIBLE);
                }
            }

            resultText.setText(String.format(Locale.getDefault(), "Найточніша оцінка експерту %d = %.03f",
                    resultIndex + 1, inputItems.get(resultIndex)));
        }
    }
}
