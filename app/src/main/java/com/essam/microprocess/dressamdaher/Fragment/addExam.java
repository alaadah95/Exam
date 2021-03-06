package com.essam.microprocess.dressamdaher.Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.essam.microprocess.dressamdaher.Adapter.QuestionBankAdapter;
import com.essam.microprocess.dressamdaher.Adapter.RecyclerItemTouchHelper;
import com.essam.microprocess.dressamdaher.Adapter.addExamTouchHelper;
import com.essam.microprocess.dressamdaher.Adapter.addExam_Rec_Adapter;
import com.essam.microprocess.dressamdaher.Contracts.addExamContract;
import com.essam.microprocess.dressamdaher.Dialog.AnimatedDialog;
import com.essam.microprocess.dressamdaher.Enums.DataBase_Refrences;
import com.essam.microprocess.dressamdaher.JsonModel.Questions_Form;
import com.essam.microprocess.dressamdaher.JsonModel.Zone;
import com.essam.microprocess.dressamdaher.MainPresnter.addExamPresenter;
import com.essam.microprocess.dressamdaher.R;
import com.essam.microprocess.dressamdaher.Views.ControlPanel;
import com.essam.microprocess.dressamdaher.Views.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class addExam extends Fragment implements addExamContract.view  , addExamTouchHelper.RecyclerItemTouchHelperListener {

    @BindView(R.id.chosen_Qestions_Rec)
    RecyclerView recyclerView;

    @BindView(R.id.questionssize)
    TextView Questions_size;


    static EditText et_second;


    static EditText et_minute;


    static EditText et_hour;

    @BindView(R.id.et_degree)
    EditText et_degree;

    @BindView(R.id.et_random_number_question)
    EditText et_random_number_question;

    @BindView(R.id.Final_Degree)
    TextView txFinal_Degree;

    @BindView(R.id.ExamName)
    EditText ExamName ;

    @BindView(R.id.deleteAll)
    Button Btn_DeleteList;

    @BindView(R.id.Btn_addExam)
    Button Btn_addExam;


    String final_degree;
    int hour, minute, second;
    addExamContract.presenter presenter;
    addExam_Rec_Adapter adapter;
    private List<Questions_Form> Questions;
    AnimatedDialog dialog ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ControlPanel.Title.setText(R.string.addExam);
        ControlPanel.SetNavChecked(2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_exam, container, false);
        et_hour   = v.findViewById(R.id.et_hour);
        et_minute = v.findViewById(R.id.et_minute);
        et_second = v.findViewById(R.id.et_second);
        Questions = new ArrayList<>();
        ButterKnife.bind(this, v);
        //display dialog .
        dialog = new AnimatedDialog(getActivity());
        dialog.ShowDialog();

        et_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment dFragment = new TimePickerShow();
                // Show the date picker dialog fragment
                assert getFragmentManager() != null;
                dFragment.show(getFragmentManager(), "Time Picker");
            }
        });

        et_minute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dFragment = new TimePickerShow();
                // Show the date picker dialog fragment
                assert getFragmentManager() != null;
                dFragment.show(getFragmentManager(), "Time Picker");
            }
        });

        et_degree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et_random_number_question.getText().toString().isEmpty()
                        || charSequence.length() == 0
                        || et_degree.getText().toString().equals("0")) {

                    // Do not Make any Thing .
                    txFinal_Degree.setText("0");

                } else {

                    int x = Integer.parseInt(et_random_number_question.getText().toString());
                    int y = Integer.parseInt(String.valueOf(charSequence));
                    final_degree = String.valueOf(x * y);
                    txFinal_Degree.setText(final_degree);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_random_number_question.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et_degree.getText().toString().isEmpty()
                        || charSequence.length() == 0
                        || et_random_number_question.getText().toString().equals("0")) {

                    // Do not Make any Thing .
                    txFinal_Degree.setText("0");

                } else {

                    int x = Integer.parseInt(et_degree.getText().toString());
                    int y = Integer.parseInt(String.valueOf(charSequence));
                    final_degree = String.valueOf(x * y);
                    txFinal_Degree.setText(final_degree);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        Btn_DeleteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.ClearList();


            }
        });


        Btn_addExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Check_Hours_And_Minuts();
                if (hour > 0 || minute > 0) {
                    second = 0 ;
                    if (CheckReminderData()== 1) {

                        if(Questions.size() > 0 ) {


                            if(!ExamName.getText().toString().isEmpty()) {

                                dialog.ShowDialog();
                                //  getting time from server  ..... //
                                Map<String , String> map = new HashMap<>();
                                map.put("key", DataBase_Refrences.TimeApiKey.getRef());
                                map.put("format",DataBase_Refrences.Format.getRef());

                                addExamPresenter addExamPresenter = new addExamPresenter(addExam.this);
                                addExamPresenter.tellModelToGetDate(map);
//
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                                String currentDateandTime = sdf.format(new Date());
//
////                                presenter.storeExaminDatabase(hour,minute,second,et_degree.getText().toString()
////                                        ,et_random_number_question.getText().toString()
////                                        ,final_degree,Questions,ExamName.getText().toString(),currentDateandTime);



                            }
                            else {

                                Toast.makeText(getActivity(), "يرجي كتابة اسم الاختبار", Toast.LENGTH_SHORT).show();

                            }

                        }
                        else
                        {
                            Toast.makeText(getActivity(), "لا يوجد اسئلة مختارة ", Toast.LENGTH_SHORT).show();

                        }



                    }

                }


            }


        });

        //config your recycler view  .
        presenter = new addExamPresenter(this);
        presenter.CallgetQestionsToRecycleView();

        return v;
    }


    @Override
    public void realtimehere(Zone zone) {


       String date  = getDate(zone.getTimestamp());

        //  هنا الوقت موجود من هنا بقا حنقدر نخزن الوقت في الداتا بيز
        presenter.storeExaminDatabase(hour,minute,second,et_degree.getText().toString()
                ,et_random_number_question.getText().toString()
                ,final_degree,Questions,ExamName.getText().toString(),date,Questions_size.getText().toString(),zone.getTimestamp());


    }

    @Override
    public void cantgetRealTime(String E) {

    }

    @Override
    public void ConfigRecyclerview(List<Questions_Form> Questions) {
        dialog.Close_Dialog();
        this.Questions = Questions ;
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new addExamTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new addExam_Rec_Adapter(Questions, this);
        recyclerView.setAdapter(adapter);
        Update_Questions_size(Questions.size());

    }

    @Override
    public void Problem(String Result) {
        dialog.Close_Dialog();

        com.essam.microprocess.dressamdaher.Dialog.AlertDialog alertDialog =
                new com.essam.microprocess.dressamdaher.Dialog.AlertDialog(getActivity(),Result+"");
        alertDialog.show();
    }

    @Override
    public void Update_Questions_size(int lengh) {

        Questions_size.setText(lengh + "");

    }

    @Override
    public void refreshAdapter() {

        adapter.notifyDataSetChanged();

    }

    @Override
    public void Successful_Storing() {
        dialog.Close_Dialog();
        com.essam.microprocess.dressamdaher.Dialog.AlertDialog alertDialog =
                new com.essam.microprocess.dressamdaher.Dialog.AlertDialog(getActivity(),"تم اضافة الاختبار بنجاح");
        alertDialog.show();
        et_hour.setText("00");
        et_minute.setText("00");
        et_second.setText("00");
        et_random_number_question.setText("0");
        et_degree.setText("0");
        presenter.ClearList();
        txFinal_Degree.setText("0");
        ExamName.setText("");
        dialog.Close_Dialog();


    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        adapter.removeItem(viewHolder.getAdapterPosition());

    }


    public static class TimePickerShow extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.YEAR);
            int minit = calendar.get(Calendar.MONTH);

            TimePickerDialog dpd = new TimePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT, this, hour, minit, true);

            // Create a TextView programmatically.
            TextView tv = new TextView(getActivity());

            // Create a TextView programmatically
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, // Width of TextView
                    RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
            tv.setLayoutParams(lp);
            tv.setPadding(10, 10, 10, 10);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv.setText("This is a custom title.");
            tv.setTextColor(Color.parseColor("#ff0000"));
            tv.setBackgroundColor(Color.parseColor("#FFD2DAA7"));

            dpd.setTitle("قم باختيار وقت الاختبار"); // Uncomment this line to activate it

            return dpd;
        }


        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {

            et_hour.setText(i + "");
            et_minute.setText(i1 + "");

        }


    }

    void Check_Hours_And_Minuts() {

        hour = 0;
        minute = 0;
        // لو الاتنين مليانين
        if (!et_hour.getText().toString().isEmpty() && !et_minute.getText().toString().isEmpty()) {


            int hours = Integer.parseInt(et_hour.getText().toString());
            int minutes = Integer.parseInt(et_minute.getText().toString());

            // لو االدقائق والساعات بيساوي صفر
            if (hours <= 0 && minutes <= 0) {


                Toast.makeText(getActivity(), "يجب ملئ الساعات او الدقائق بقيم اكبر من الصفر", Toast.LENGTH_SHORT).show();
            } else {
                hour = Integer.parseInt(et_hour.getText().toString());
                minute = Integer.parseInt(et_minute.getText().toString());


            }


        }
        // لو الساعات فاضي والدقايق فاضي
        else if (et_hour.getText().toString().isEmpty() && et_minute.getText().toString().isEmpty()) {


            Toast.makeText(getActivity(), "يجب ملئ الساعات او الدقائق بقيم اكبر من الصفر", Toast.LENGTH_SHORT).show();


        }
        // لو الساعات فاضي والدقائق مليان
        else if ((et_hour.getText().toString().isEmpty() && !et_minute.getText().toString().isEmpty())) {

            int minutes = Integer.parseInt(et_minute.getText().toString());

            if (minutes <= 0) {

                Toast.makeText(getActivity(), "يجب ملئ الدقائق بقيم اكبر من الصفر", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "يجب ملئ الدقائق بقيم اكبر من الصفر", Toast.LENGTH_SHORT).show();
            } else {

                hour = 0;
                minute = Integer.parseInt(et_minute.getText().toString());

            }

        }

        // لو الساعات مليان والدقائق فاضي
        else if (!et_hour.getText().toString().isEmpty() && et_minute.getText().toString().isEmpty()) {

            int hours = Integer.parseInt(et_hour.getText().toString());

            if (hours <= 0) {

                Toast.makeText(getActivity(), "يجب ملئ الساعات بقيم اكبر من الصفر", Toast.LENGTH_SHORT).show();
            } else {

                minute = 0;
                hour = Integer.parseInt(et_hour.getText().toString());


            }

        }
    }

    public int CheckReminderData() {
        int result = 0 ;
        if (!et_degree.getText().toString().isEmpty()) {

            int degree = Integer.parseInt(et_degree.getText().toString());
            if (degree <= 0) {
                Toast.makeText(getActivity(), "توزيع الدرجات يجب ان يكون اكبر من الصفر", Toast.LENGTH_SHORT).show();
                result = 0;
            } else {

                if (!et_random_number_question.getText().toString().isEmpty()) {

                    int random = Integer.parseInt(et_random_number_question.getText().toString());

                    if (random <= 0) {
                        Toast.makeText(getActivity(), "يرجى تحديد عدد اسئلة الامتحان", Toast.LENGTH_SHORT).show();
                        result = 0;
                    } else {

                        int finaldegree = Integer.parseInt(Questions_size.getText().toString());
                        if (random > finaldegree) {
                            Toast.makeText(getActivity(), "الاسئلة العشوائية يجب ان تكون اقل من او تساوي الاسئلة المختارة", Toast.LENGTH_SHORT).show();
                            result = 0;
                        } else {
                            result = 1;
                        }

                    }
                }
                else {

                    result = 0;
                    Toast.makeText(getActivity(), "يرجي ملئ خانة الأسئلة", Toast.LENGTH_SHORT).show();

                }

            }

        } else {
            result = 0;
            Toast.makeText(getActivity(), "يرجي ملئ خانة الدرجات", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    private String getDate(long time_stamp_server) {

//        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
//        return formatter.format(time_stamp_server);
          Calendar cal = Calendar.getInstance(Locale.ENGLISH);
          cal.setTimeInMillis(time_stamp_server * 1000L);
          return DateFormat.format("dd-MM-yyyy", cal).toString();

    }

}
