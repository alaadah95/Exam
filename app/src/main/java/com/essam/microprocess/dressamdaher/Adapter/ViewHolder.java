package com.essam.microprocess.dressamdaher.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essam.microprocess.dressamdaher.Dialog.AlertDialog;
import com.essam.microprocess.dressamdaher.Dialog.AnimatedDialog;
import com.essam.microprocess.dressamdaher.Dialog.CustomTypeFaceSpan;
import com.essam.microprocess.dressamdaher.Enums.DataBase_Refrences;
import com.essam.microprocess.dressamdaher.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by microprocess on 2018-10-08.
 */

public class ViewHolder extends RecyclerView.ViewHolder  {
  Button BtnStartExam;
  TextView ExamName;
  TextView Date;
  CardView Cardview;
  Context context;
  LinearLayout linear;
  String ExamID;
  AnimatedDialog dialog;
  FirebaseAuth auth ;

  public ViewHolder(View itemView) {
    super(itemView);
    ExamName = itemView.findViewById(R.id.ExamName);
    Date     = itemView.findViewById(R.id.date);
    Cardview = itemView.findViewById(R.id.card);
    linear = itemView.findViewById(R.id.linear);
    context = itemView.getContext();
    dialog = new AnimatedDialog(context);
    auth = FirebaseAuth.getInstance();
    BtnStartExam = itemView.findViewById(R.id.StartExam);

    linear.setEnabled(false);
    CheckifAdmin();
    linear.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {


        PopupMenu popup = new PopupMenu(context, Cardview);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.deleteexam, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.Delete_exam){

              //Deleting
              final AlertDialog alertDialog = new AlertDialog(context,"تحذير","هل انت متأكد من حذف هذا الاختبار ؟ ");
              alertDialog.show();
              alertDialog.btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  dialog.ShowDialog();
                  Delete_Exam();
                  alertDialog.dismiss();
                }
              });


            }

            return true;
          }
        });


        Menu menu = popup.getMenu();
        for (int i = 0; i < menu.size(); i++) {
          MenuItem mi = menu.getItem(i);
          applyFontToMenuItem(mi);

        }



        popup.show();
      }
    });


  }

  private void CheckifAdmin() {

    if(auth.getCurrentUser() != null) {

      DatabaseReference reference = FirebaseDatabase.getInstance().getReference(DataBase_Refrences.ADMIN.getRef()).child(auth.getCurrentUser().getUid());
      reference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
          if (dataSnapshot.exists()) {

            linear.setEnabled(true);

          }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {


        }
      });
    }
  }

  private void applyFontToMenuItem(MenuItem mi) {

    Typeface font = Typeface.createFromAsset(context.getAssets(),"atherfont.ttf");
    SpannableString mNewTitle = new SpannableString(mi.getTitle());
    mNewTitle.setSpan(new CustomTypeFaceSpan("", font, Color.WHITE), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    mi.setTitle(mNewTitle);

  }

  private void Delete_Exam(){

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference(DataBase_Refrences.EXAMS.getRef()).child(ExamID);
    reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
      @Override
      public void onSuccess(Void aVoid) {

        dialog.Close_Dialog();
        AlertDialog alertDialog = new AlertDialog(context,"تم حذف الاختبار بنجاح");
        alertDialog.show();


      }
    }).addOnFailureListener(new OnFailureListener() {

      @Override
      public void onFailure(@NonNull Exception e) {

        dialog.Close_Dialog();
        AlertDialog alertDialog = new AlertDialog(context,"حدثت مشكلة" +e.toString() );
        alertDialog.show();

      }
    });



  }

}