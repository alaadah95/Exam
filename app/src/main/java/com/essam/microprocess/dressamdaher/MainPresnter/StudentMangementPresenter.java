package com.essam.microprocess.dressamdaher.MainPresnter;

import android.graphics.ColorSpace;
import android.util.Log;
import android.widget.Toast;

import com.essam.microprocess.dressamdaher.Contracts.StudentManagementContract;
import com.essam.microprocess.dressamdaher.Enums.DataBase_Refrences;
import com.essam.microprocess.dressamdaher.Fragment.StudentManagement;
import com.essam.microprocess.dressamdaher.JsonModel.FullRegisterForm;
import com.essam.microprocess.dressamdaher.MainModle.StudentManagementModel;
import com.essam.microprocess.dressamdaher.Views.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by microprocess on 2018-10-01.
 */

public class StudentMangementPresenter implements StudentManagementContract.presenter {

    private StudentManagementContract.view view;
    private StudentManagementContract.model model;

    public StudentMangementPresenter(StudentManagement studentManagement) {
        this.view = studentManagement;
        model = new StudentManagementModel(this);
    }

    @Override
    public void callStudentData() {
        model.getstudentData();
    }

    @Override
    public void SendListToView(List<FullRegisterForm> Result) {

        view.RecyclerConfig(Result);

    }

    @Override
    public void problem(String problem) {
        view.problem(problem);
    }
}
