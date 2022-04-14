package com.humaira.lecturenote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class CustomNoteAdapter extends ArrayAdapter<Notes> {

    private final Context context;
    private final ArrayList<Notes> values;


    public CustomNoteAdapter(@NonNull Context context, @NonNull ArrayList<Notes> objects) {
        super(context, -1, objects);
        this.context = context;
        this.values = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.layout_note_list, parent, false);

        TextView eventCourse = rowView.findViewById(R.id.tvCourse);
        TextView eventTopic = rowView.findViewById(R.id.tvTopic);
        TextView eventDateTime = rowView.findViewById(R.id.tvDateTime);
        TextView eventDescription = rowView.findViewById(R.id.tvDescription);

        eventCourse.setText(values.get(position).course);
        eventTopic.setText(values.get(position).topic);
        eventDateTime.setText(values.get(position).date);
        eventDescription.setText(values.get(position).description);

        return rowView;
    }
}

