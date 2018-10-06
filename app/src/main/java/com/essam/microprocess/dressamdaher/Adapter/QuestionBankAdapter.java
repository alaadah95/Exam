package com.essam.microprocess.dressamdaher.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essam.microprocess.dressamdaher.JsonModel.FullRegisterForm;
import com.essam.microprocess.dressamdaher.JsonModel.Questions_Form;
import com.essam.microprocess.dressamdaher.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by microprocess on 2018-10-05.
 */

public class QuestionBankAdapter extends RecyclerView.Adapter<QuestionBankAdapter.ViewHolder> {
    public static List<Questions_Form> qestions ;
    private List<Questions_Form>listnew;
    Context context;
    TextView Question;
    public QuestionBankAdapter(List <Questions_Form> qestions, Context context){

        this.qestions = qestions;
        this.context  = context;

    }

    @NonNull
    @Override
    public QuestionBankAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.qestion_rec_layout,parent,false);
        return new QuestionBankAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionBankAdapter.ViewHolder holder, int position) {
        this.Question.setText(qestions.get(position).getQuestion());

        //animation
        holder.Cardview.setScaleX(.9f);
        holder.Cardview.setScaleY(.9f);
        holder.Cardview.animate().scaleX(1f).scaleY(1f).setDuration(500);

        //animation 2
        holder.background.setScaleX(.9f);
        holder.background.setScaleY(.9f);
        holder.background.animate().scaleX(1f).scaleY(1f).setDuration(500);


    }

    @Override
    public int getItemCount() {

        if (qestions!=null){

            return qestions.size();
        }
       return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView Cardview;
        LinearLayout background;
        TextView tx;

        public ViewHolder(View itemView) {
            super(itemView);
            Question = itemView.findViewById(R.id.Question);
            Cardview = itemView.findViewById(R.id.Cardview);
            background = itemView.findViewById(R.id.Cardview2);
            tx = itemView.findViewById(R.id.tx);
        }
    }





    public Filter getFilter() {




        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Questions_Form> FilteredArrList = new ArrayList<>();
                if (listnew == null) {

                    listnew = new ArrayList<>(qestions); // saves the original data in mOriginalValues

                }

                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = listnew.size();
                    results.values = listnew;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < listnew.size(); i++) {
                        String data = listnew.get(i).getQuestion();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(listnew.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                qestions = (ArrayList<Questions_Form>) results.values;
//                Question.setTextColor(context.getResources().getColor(R.color.color2));
                // has the filtered values
                notifyDataSetChanged();

            }
        };
        return filter;
    }

}
