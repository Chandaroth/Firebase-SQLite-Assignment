package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.database.DBHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegister;
    TextView mLogin_btn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName   =findViewById(R.id.fullName);
        mEmail      =findViewById(R.id.email);
        mPassword   =findViewById(R.id.password);
        mPhone      =findViewById(R.id.phone);
        mRegister   =findViewById(R.id.btnRegister);
        mLogin_btn  =findViewById(R.id.txtLogin);

        fAuth       =FirebaseAuth.getInstance();
        progressBar =findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                final String password=mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required");
                    return;
                }

                if(password.length()<6){
                    mPassword.setError("Password Must Be >=6 Characters");
                    return;
                }
                dbHandler=new DBHandler(Register.this);
                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"User Created To Firebase",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                            String name = mFullName.getText().toString();
                            String email = mEmail.getText().toString();
                            String phone = mPhone.getText().toString();
                            String password = mPassword.getText().toString();

                            boolean status = dbHandler.addUser(name, email, phone,password);

                            if (status) {
                                Toast.makeText(Register.this, "SQLite Insert Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Register.this, "SQLite Insert Failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Register.this,"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });

        mLogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });




    }
}
