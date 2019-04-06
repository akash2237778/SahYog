package com.example.sahyog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {
    EditText nameText;
    EditText userNameText;
    EditText passwordText;
    EditText reEnterPassword;
    EditText phoneNoText;
    String name;
    String userName;
    String password ;
    String reEnterPass;
    String phoneNo;
    ParseUser user;


    public void SignUp (String usrnme , String password){
        user = new ParseUser();

        user.setUsername(usrnme);
    user.setPassword(password);

    user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
            if( e==null ){
               Log.i("SignUp ","Successful");
            }else{
                Log.i("SignUp ","unSuccessful :" + e.getMessage());
            }
        }
    });

}


public void onClickSignUp (View view){
    name = String.valueOf(nameText.getText());
    userName = String.valueOf(userNameText.getText());
    password = String.valueOf(passwordText.getText());
    reEnterPass = String.valueOf(reEnterPassword.getText());
    phoneNo =String.valueOf(phoneNoText.getText());

    if(name == null || userName == null || password == null || reEnterPassword==null /*|| phoneNo == "null" */){
        Toast.makeText(MainActivity.this, "Please fill all the Fields !", Toast.LENGTH_SHORT).show();

    }else {
        if (password.equals(reEnterPass)) {
         SignUp(userName,password);
        } else {
            Toast.makeText(MainActivity.this, "Passwords not same !", Toast.LENGTH_SHORT).show();

        }

    }
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = (EditText)findViewById(R.id.nameText);
        userNameText = (EditText)findViewById(R.id.userNameText);
        passwordText = (EditText)findViewById(R.id.passwordText);
        reEnterPassword = (EditText)findViewById(R.id.reEnterPassword);
        phoneNoText = (EditText)findViewById(R.id.phoneNoText);


    }
}
