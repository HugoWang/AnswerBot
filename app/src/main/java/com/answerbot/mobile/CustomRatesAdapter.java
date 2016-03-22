package com.answerbot.mobile;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomRatesAdapter extends ArrayAdapter<Rate> {
    private Context context;
    List<Rate> rates = new ArrayList<>();

    static class ViewHolder{
        private SeekBar seekBar;
        TextView tvBody;
        TextView tvDetail;
    }

    public CustomRatesAdapter(Context context, ArrayList<Rate> rates) {
        super(context, com.answerbot.mobile.R.layout.item_rate, rates);
        this.context = context;
        this.rates.addAll(rates);
    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(com.answerbot.mobile.R.layout.item_rate, null);
            holder = new ViewHolder();
            holder.seekBar = (SeekBar) convertView.findViewById(com.answerbot.mobile.R.id.seekBar);
            holder.tvBody = (TextView) convertView.findViewById(com.answerbot.mobile.R.id.tvBody);
            holder.tvDetail = (TextView) convertView.findViewById(com.answerbot.mobile.R.id.tvDetails);
            convertView.setTag(holder);

        }
        else {

            holder = (ViewHolder) convertView.getTag();

        }



        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Rate r = rates.get(position);
                r.setSeek_value(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"RobotoCondensed-Regular.ttf");

        holder.seekBar.setProgress(rates.get(position).seek_value);
        holder.seekBar.setMax(100);
        holder.tvBody.setTypeface(tf);
        holder.tvDetail.setTypeface(tf);
        holder.tvBody.setText(rates.get(position).rate_body);
        holder.tvDetail.setText(rates.get(position).rate_details);

        return convertView;
    }
}