package com.example.whereto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class QuestionsAdapter extends BaseAdapter {
    private ArrayList<Question> questions;
    private Context context;

    public QuestionsAdapter(ArrayList<Question> questions, Context context) {
        this.questions = questions;
        this.context = context;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_rv, parent, false);
        }

        ((TextView) v.findViewById(R.id.tvName)).setText(questions.get(position).getAttractionName());
        ((TextView) v.findViewById(R.id.tvLocation)).setText(questions.get(position).getAttractionLocation());
        ((TextView) v.findViewById(R.id.tvDescription)).setText(questions.get(position).getAttractionDescription());

        // TODO: add image?

        return v;
    }
}
