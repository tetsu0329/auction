package com.example.kuroro.auction;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    FirebaseAuth auth;
    EditText email, password, cpassword;
    Button register, clear;
    ProgressBar progressBar;
    CheckBox checkBox;
    TextView txtView;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editText3);
        password =  findViewById(R.id.editText5);
        cpassword =  findViewById(R.id.editText7);
        register =  findViewById(R.id.button4);
        clear =  findViewById(R.id.button5);
        progressBar = findViewById(R.id.progressBar4);
        checkBox = findViewById(R.id.checkBox2);

        progressBar.setVisibility(View.GONE);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String email1 = email.getText().toString();
                String password1 = password.getText().toString();
                String cpassword1 = cpassword.getText().toString();

                if(TextUtils.isEmpty(email1)){
                    Toast.makeText(Register.this, "Enter Email Address", Toast.LENGTH_SHORT)
                            .show();
                    email.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password1)){
                    Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_SHORT)
                            .show();
                    password.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(cpassword1)) {
                    Toast.makeText(Register.this, "Please Confirm your Password", Toast.LENGTH_SHORT)
                            .show();
                    cpassword.requestFocus();
                    return;
                }
                if (password1.equals(cpassword1)){
                    if(checkBox.isChecked()){
                        progressBar.setVisibility(View.VISIBLE);
                        register.setVisibility(View.GONE);
                        clear.setVisibility(View.GONE);
                        auth.createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Snackbar.make(view, "Registration Complete", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    progressBar.setVisibility(View.GONE);
                                    Intent intent = new Intent(Register.this, Setupprofile.class);
                                    startActivity(intent);
                                }
                                if(!task.isSuccessful()){

                                    Snackbar.make(view, "Try Different Email / Password", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    progressBar.setVisibility(View.GONE);
                                    register.setVisibility(View.VISIBLE);
                                    clear.setVisibility(View.VISIBLE);
                                }

                            }
                        });
                    }
                    else{
                        Snackbar.make(view, "Please Accept the Terms and Condition", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                }
                if (!password1.equals(cpassword1)){
                    Snackbar.make(view, "Password Doesn't Match", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    cpassword.requestFocus();
                }

            }
        });

    }
}
