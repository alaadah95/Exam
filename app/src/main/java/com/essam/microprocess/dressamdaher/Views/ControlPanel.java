package com.essam.microprocess.dressamdaher.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.essam.microprocess.dressamdaher.Contracts.ControlPanelContract;
import com.essam.microprocess.dressamdaher.Dialog.AlertDialog;
import com.essam.microprocess.dressamdaher.Dialog.AnimatedDialog;
import com.essam.microprocess.dressamdaher.Fragment.AddQ_frag;
import com.essam.microprocess.dressamdaher.Fragment.Question_Bank_Frag;
import com.essam.microprocess.dressamdaher.Fragment.StudentManagement;
import com.essam.microprocess.dressamdaher.Fragment.addExam;
import com.essam.microprocess.dressamdaher.MainPresnter.ControlpanelPresnter;
import com.essam.microprocess.dressamdaher.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ControlPanel extends AppCompatActivity
                          implements ControlPanelContract.ControlUI
                                   , View.OnClickListener
                                   , NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private ImageView open_nav;
    private DrawerLayout drawer;
    private NavigationView navigation;
    private TextView Title ;
    private FirebaseAuth auth;
    ControlpanelPresnter controlpanelPresnter;
    AnimatedDialog animatedDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawaer);


        controlpanelPresnter = new ControlpanelPresnter(this);
        controlpanelPresnter.updateUitoViews();

        //Check if User Banned افحص المستخدم اذا كان محظور
        controlpanelPresnter.CheckifUserBanned(auth.getCurrentUser().getUid());

    }

    @Override
    public void initializeViews() {
        toolbar    = findViewById(R.id.toolbar);
        open_nav   = findViewById(R.id.open_nav);
        drawer     = findViewById(R.id.drawer);
        navigation = findViewById(R.id.navigation);
        Title      = toolbar.findViewById(R.id.toolbar_title);
        auth       = FirebaseAuth.getInstance();
        animatedDialog = new AnimatedDialog(this);
        setSupportActionBar(toolbar);
        open_nav.setOnClickListener(this);
        navigation.setNavigationItemSelectedListener(this);


    }

    @Override
    public void emailandpasstrueexit() {

        animatedDialog.Close_Dialog();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void emailandpassnottrue(String E) {

        //  هنا بقا لسه حنشةف المشكله بتاعه لو الباسورد اتمسح من الداتا بيز ....

    }

    @Override
    public void whenClickFAB_showFrag() {
        Title.setText(getResources().getString(R.string.addQ));
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.Exam_Frame,new AddQ_frag())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void CheckifUserBannedResult(String Result) {

        //اذا كان هذا الطالب محظور

        if(Result.equals("Successful")) {

            AlertDialog alertDialog = new AlertDialog(ControlPanel.this
                    , getString(R.string.YouareBanned));
            alertDialog.setCancelable(false);
            alertDialog.show();
            alertDialog.btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(ControlPanel.this , MainActivity.class);
                    startActivity(intent);

                    finish();



                    //لو عاوز المحظور ميعرش يعمل اي اكونت تاني علي البرنامج يبقه الغي السطر الجي
                    auth.signOut();
                }
            });

        }


    }

    @Override
    public void onClick(View view) {

        if (view == open_nav){
            //  لو عزت تغير الاتجاه من هنا بتاع فتح ال nav
            if (drawer.isDrawerOpen(Gravity.START)){

                drawer.closeDrawer(Gravity.START);

            }else {
                drawer.openDrawer(Gravity.START);
            }

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){


            case R.id.emams:

                AlertDialog aleart_ok = new AlertDialog(this,getString(R.string.message));
                aleart_ok.setCancelable(false);
                aleart_ok.show();
                Toast.makeText(this, "hhhhhhhhh", Toast.LENGTH_SHORT).show();
                //  هنا اعمل الي انت عاوزه دي قائمه الاختبارات

                break;

            case R.id.studentManger:

                //  اداره الطلاب
                getSupportFragmentManager().popBackStack();
                Title.setText(R.string.mangeStudent);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.Exam_Frame,new StudentManagement())
                        .addToBackStack(null)
                        .commit();

                
                break;

            case R.id.exit:

                AlertDialog aleartDialog = new AlertDialog(this,getString(R.string.title),getString(R.string.message));
                aleartDialog.show();
                aleartDialog.btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                         حنتشيك علي الي في الداتا ولو صح حنطلع بره
                            animatedDialog.ShowDialog();
                            auth.signOut();
                            startActivity(new Intent(ControlPanel.this,MainActivity.class));
                            animatedDialog.Close_Dialog();
                            finish();

                    }
                });





                break;
            case R.id.questions:

                Title.setText(R.string.questions);
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.Exam_Frame,new Question_Bank_Frag())
                        .addToBackStack(null)
                        .commit();


                break;

            case R.id.addExam :

                Title.setText(R.string.addExam);
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.Exam_Frame,new addExam())
                        .addToBackStack(null)
                        .commit();
                break;
        }
//        getSupportFragmentManager().popBackStack();   //finish
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.START)){
            drawer.closeDrawer(Gravity.START);
        }else {
           super.onBackPressed();
        }

    }
}
