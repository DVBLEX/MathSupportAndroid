package com.company.lab;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

class InputAdapter extends RecyclerView.Adapter<InputAdapter.BaseViewHolder> {

    private static final int TYPE_INPUT = 1;
    private static final int TYPE_AVERAGE = 2;

    private List<ItemWrapper> items;

    public InputAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == items.size()) {
            return TYPE_AVERAGE;
        }

        return TYPE_INPUT;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_INPUT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_input, parent, false);
            return new InputViewHolder(view);
        } else if (viewType == TYPE_AVERAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_average, parent, false);
            return new AverageViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder instanceof InputViewHolder) {
            InputViewHolder viewHolder = (InputViewHolder) holder;
            viewHolder.init(position, items.get(position));
        } else if (holder instanceof AverageViewHolder) {
            AverageViewHolder viewHolder = (AverageViewHolder) holder;

            double average = 0;
            for (ItemWrapper item : items) {
                average += item.getValue();
            }

            viewHolder.init(average / items.size());
        }
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    public void setItems(List<Double> inputValues) {
        items = new ArrayList<>();
        for (Double item : inputValues) {
            items.add(new ItemWrapper(item));
        }

        notifyDataSetChanged();
    }

    public List<Double> getItems() {
        List<Double> list = new ArrayList<>();
        for (ItemWrapper wrapper : items) {
            list.add(wrapper.getValue());
        }

        return list;
    }

    public void changeItemsCount(int newValue) {
        if (newValue > items.size()) {
            for (int i = items.size(); i < newValue; i++) {
                items.add(new ItemWrapper(0));
                notifyItemInserted(i);
            }
            notifyItemChanged(items.size());
        } else if (newValue < items.size()) {
            for (int i = items.size() - 1; i >= newValue; i--) {
                items.remove(i);
                notifyItemRemoved(i);
            }
            notifyItemChanged(items.size());
        }
    }

    private static class ItemWrapper {
        private double value;

        public ItemWrapper(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }

    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        BaseViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    class InputViewHolder extends BaseViewHolder {

        @BindView(R.id.expert_input_position_text)
        TextView positionText;
        @BindView(R.id.expert_input_text)
        EditText inputText;

        public InputViewHolder(View itemView) {
            super(itemView);
        }

        void init(int position, ItemWrapper item) {
            positionText.setText(String.format(Locale.getDefault(), "%d", position + 1));
            inputText.setText(String.format(Locale.getDefault(), "%s", item.getValue()));

            inputText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String text = s.toString();
                    double value;

                    try {
                        value = Double.parseDouble(text);
                    } catch (Exception ex) {
                        value = 0;
                    }

                    item.setValue(value);

                    try {
                        notifyItemChanged(items.size());
                    } catch (Exception e) {

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    class AverageViewHolder extends BaseViewHolder {

        @BindView(R.id.expert_average_text)
        TextView averageText;

        AverageViewHolder(View itemView) {
            super(itemView);
        }

        void init(double average) {
            averageText.setText(String.format(Locale.getDefault(), "%.03f", average));
        }
    }
}
