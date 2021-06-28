package com.hassan.sshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminLoginActivity extends AppCompatActivity {

    private TextInputEditText Email, Password;
    private Button Login;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);


        mAuth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);

        Login = findViewById(R.id.login);





        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser currentUser= mAuth.getCurrentUser();
               if (currentUser != null) {

                   AllowingUserToLogin();
               }
               else {
                   Toast.makeText(AdminLoginActivity.this, "First login plz", Toast.LENGTH_SHORT).show();
               }

            }
        });

    }

    private void AllowingUserToLogin() {

        String email = Email.getText().toString();
        String password = Password.getText().toString();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please write your Email...", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else{
            if (email.equals("admin")&& password.equals("admin"))
            {
               Intent intent= new Intent(AdminLoginActivity.this,AdminDashBoardActivity.class);
               startActivity(intent);
               finish();

            } else {
                Toast.makeText(this, "Email,Password incorrect", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
