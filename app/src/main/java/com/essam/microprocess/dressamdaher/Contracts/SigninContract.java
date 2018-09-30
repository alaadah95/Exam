package com.essam.microprocess.dressamdaher.Contracts;

import android.widget.EditText;

/**
 * Created by microprocess on 2018-09-30.
 */

public interface SigninContract {

    interface model {
        String CheckisEmpty(EditText et_email, EditText et_password);
        void logIn(String email, String password);
    }
    interface presenter{

        void passtocheck(EditText et_email, EditText et_password);
        void passlogIn(String email, String password);
        void updatelogInResult(String Result);
    }
    interface view{
        void checkResult (String Result);
        void logInResult (String Result);
    }
}
