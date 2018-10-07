package com.essam.microprocess.dressamdaher.JsonModel;

import java.util.List;

/**
 * Created by microprocess on 2018-10-07.
 */

public class AddExam_pojo {
    int hour,minute , second;

    String examID,oneQestionDegree, numberofQestion, final_degree;

    List<Questions_Form> questions;
    String examName, currentDateandTime;

    public AddExam_pojo() {
    }

    public AddExam_pojo(int hour, int minute, int second, String examID, String oneQestionDegree, String numberofQestion, String final_degree, List<Questions_Form> questions, String examName, String currentDateandTime) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.examID = examID;
        this.oneQestionDegree = oneQestionDegree;
        this.numberofQestion = numberofQestion;
        this.final_degree = final_degree;
        this.questions = questions;
        this.examName = examName;
        this.currentDateandTime = currentDateandTime;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public String getExamID() {
        return examID;
    }

    public String getOneQestionDegree() {
        return oneQestionDegree;
    }

    public String getNumberofQestion() {
        return numberofQestion;
    }

    public String getFinal_degree() {
        return final_degree;
    }

    public List<Questions_Form> getQuestions() {
        return questions;
    }

    public String getExamName() {
        return examName;
    }

    public String getCurrentDateandTime() {
        return currentDateandTime;
    }
}
