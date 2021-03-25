package com.example.projekt_android.constans;

import android.service.autofill.Validator;
import android.text.TextUtils;

public class Validation {
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


}
