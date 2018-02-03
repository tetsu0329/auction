package com.example.kuroro.auction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference mDatabaseRef;
    EditText editText, editText2;
    Button login, register;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("userinfo");

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);

        login = findViewById(R.id.button2);
        register = findViewById(R.id.button3);

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
        if(null != auth.getCurrentUser()){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            login();

        }
        if(null == auth.getCurrentUser()){
            login();
        }

    }
    public void login (){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                auth.signOut();
                editText.setEnabled(false);
                editText2.setEnabled(false);
                final String email1 = editText.getText().toString();
                String password1 = editText2.getText().toString();

                progressBar.setVisibility(View.VISIBLE);

                login.setVisibility(View.GONE);
                register.setVisibility(View.GONE);

                auth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(!task.isSuccessful()){
                            Snackbar.make(view, "Incorrect Username or Password", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            login.setVisibility(View.VISIBLE);
                            register.setVisibility(View.VISIBLE);

                            editText.setEnabled(true);
                            editText2.setEnabled(true);
                        }
                        else{
                            if(auth.getCurrentUser().isEmailVerified()){
                                final String id = auth.getCurrentUser().getUid();
                                mDatabaseRef = FirebaseDatabase.getInstance().getReference();
                                Query search = mDatabaseRef.child("userinfo").orderByChild("userID").equalTo(id);
                                search.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){

                                            for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                                                UserList userAccount = snapshot.getValue(UserList.class);
                                                String stat = userAccount.getUserStatus();

                                                if(stat.equals("Pending")){
                                                    login.setVisibility(View.VISIBLE);
                                                    register.setVisibility(View.VISIBLE);

                                                    editText.setEnabled(true);
                                                    editText2.setEnabled(true);

                                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                                    startActivity(intent);
                                                }
                                                else{
                                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        }
                                        else{
                                            Intent intent = new Intent(Login.this, ProfileSet.class);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                            else{
                                auth.signOut();
                                Snackbar.make(view, "Please Verify First your Email", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                login.setVisibility(View.VISIBLE);
                                register.setVisibility(View.VISIBLE);

                                editText.setEnabled(true);
                                editText2.setEnabled(true);
                            }
                        }
                    }
                });
            }
        });
    }
}
