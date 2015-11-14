package com.whs.quizzappuni.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.whs.quizzappuni.Model.Round;
import com.whs.quizzappuni.R;

import java.util.List;


/**
 * Created by Marc on 13.11.15.
 */
public class ResultAdapter extends ArrayAdapter<Round> {

    public ResultAdapter(Context context, int resource, List<Round> rounds) {
        super(context, resource, rounds);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.result_row, null);
        }

        Round rounds = getItem(position);

        if (rounds != null) {
            TextView datetime = (TextView) view.findViewById(R.id.datetime);
            TextView points = (TextView) view.findViewById(R.id.result_points);
            TextView time = (TextView) view.findViewById(R.id.result_time);

            if (datetime != null) {
                datetime.setText("DUMMY");
            }


            if (points != null) {
                points.setText(toString().valueOf(rounds.getScore()) + " Punkte");
            }


            if (time != null) {
                time.setText(toString().valueOf(rounds.getDurationSeconds()) + " Sekunden");
            }

        }

        return view;
    }
}
