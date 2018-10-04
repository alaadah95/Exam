package com.essam.microprocess.dressamdaher.MainPresnter;

import com.essam.microprocess.dressamdaher.Contracts.QuestionsBankContract;
import com.essam.microprocess.dressamdaher.JsonModel.Questions_Form;
import com.essam.microprocess.dressamdaher.MainModle.Question_BankModel;

import java.util.List;

/**
 * Created by microprocess on 2018-10-05.
 */

public class Question_BankPresenter implements QuestionsBankContract.presenter {
    QuestionsBankContract.view view;
    QuestionsBankContract.model model;
    public Question_BankPresenter(QuestionsBankContract.view  view){
        this.view = view ;

        model = new Question_BankModel(this);
    }

    @Override
    public void callQuestionData() {
        model.getQuestionData();
    }

    @Override
    public void SendListToView(List<Questions_Form> Result) {
        view.RecyclerConfig(Result);
    }

    @Override
    public void problem(String problem) {
        view.problem(problem);
    }
}
