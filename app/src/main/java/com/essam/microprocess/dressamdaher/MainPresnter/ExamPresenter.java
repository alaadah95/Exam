package com.essam.microprocess.dressamdaher.MainPresnter;

import android.database.sqlite.SQLiteDatabase;

import com.essam.microprocess.dressamdaher.Contracts.ExamContract;
import com.essam.microprocess.dressamdaher.MainModle.ExamModel;

/**
 * Created by microprocess on 2018-10-14.
 */

public class ExamPresenter implements ExamContract.presenter {
    ExamContract.view view;
    ExamContract.model model;
    public ExamPresenter(ExamContract.view view){
        this.view = view;
        model = new ExamModel(this);
    }


    @Override
    public void getQuestion(SQLiteDatabase db, String sqlTableName) {
        model.getQuestion(db,sqlTableName);
    }

    @Override
    public void quetionIs(String string, String string1, String string2, String string3, String string4, String string5, String string6) {
        view.quetionIs(string,string1,string2,string3,string4,string5,string6);
    }

    @Override
    public void insertAnswerInSql(SQLiteDatabase db, String sqlTableName, String ID_Qestion, String selectAnswer) {
        model.insertAnswerInSql(db,sqlTableName,ID_Qestion,selectAnswer);
    }

    @Override
    public void AnswerInserted() {
        view.AnswerInserted();
    }

    @Override
    public void Problem(String s) {
        view.Problem(s);
    }
}
