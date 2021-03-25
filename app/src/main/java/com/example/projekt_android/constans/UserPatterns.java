package com.example.projekt_android.constans;

public class UserPatterns {
    //todo poprawić emailpattern na podstawie testu
    public static final String emailPattern = "^[a-zA-Z0-9]+[\\._a-zA-Z0-9]*@[a-zA-Z0-9]+{2,}\\.[a-zA-Z]{2,}[\\.a-zA-Z0-9]*$";
    public static final String passwordPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\!\\@\\#\\$\\*])(?!.*\\s).{6,30}$";
}
