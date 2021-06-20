package com.riaz.sshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private Button SignUp, SignUpLater;
    private TextInputEditText Email, Password,Repassword;

    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SignUpLater = findViewById(R.id.signuplater);
        SignUp      =findViewById(R.id.signup);

        Email     =findViewById(R.id.email);
        Password     =findViewById(R.id.password);
        Repassword     =findViewById(R.id.repassword);

        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateNewAccount();
            }
        });





        SignUpLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });




    }





    private void CreateNewAccount() {

        String password=Password.getText().toString();
        String email= Email.getText().toString();
        String confrmpassword=Repassword.getText().toString();




        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "please write your email..", Toast.LENGTH_SHORT).show();
        }
        else  if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "please write your password..", Toast.LENGTH_SHORT).show();
        }


        else  if (TextUtils.isEmpty(confrmpassword)){
            Toast.makeText(this, "please confrm your password..", Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(confrmpassword)){
            Toast.makeText(this, "Your Password do not match", Toast.LENGTH_SHORT).show();
        }


        else{
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait,While we are creating your new account...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);



            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                SendUserToDashboard();

                                Toast.makeText(SignupActivity.this, "you are authenticated succefuly....", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else{
                                String message= task.getException().getMessage();
                                Toast.makeText(SignupActivity.this, "Error Occured" + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });

        }
    }

    private void SendUserToDashboard() {

        Intent intent= new Intent(SignupActivity.this, MainActivity.class);
        startActivity(intent);
         finish();
    }
}
