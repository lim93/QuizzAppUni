package com.whs.quizzappuni.Utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.whs.quizzappuni.Model.Definition;
import com.whs.quizzappuni.R;

import java.util.List;


/**
 * Created by krispin on 13.11.15.
 */
public class DefinitionAdapter extends ArrayAdapter<Definition> {

    public DefinitionAdapter(Context context, int resource, List<Definition> definitions) {
        super(context, resource, definitions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(android.R.layout.simple_list_item_2, null);
        }

        Definition definition = getItem(position);

        if (definition != null) {
            TextView title = (TextView) view.findViewById(android.R.id.text1);
            TextView descriptionShort = (TextView) view.findViewById(android.R.id.text2);

            if (title != null) {
                title.setText(definition.getTerm());
            }


            if (descriptionShort != null) {
                descriptionShort.setText(definition.getDefinitionText());
                descriptionShort.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
                descriptionShort.setMaxLines(2);
                descriptionShort.setEllipsize(TextUtils.TruncateAt.END);
            }

        }

        return view;
    }
}
