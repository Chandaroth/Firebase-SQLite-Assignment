package com.example.myapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.fragment.FragmentAbout;
import com.example.myapplication.fragment.FragmentSecond;
import com.example.myapplication.fragment.FragmentMain;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DrawerActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener
        {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    //TextView txtAccountEmail;

    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //Load Default Fragment
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment,new FragmentMain());
        fragmentTransaction.commit();

        // Get the transferred data from source login.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null){
            String data = extras.getString("message"); // retrieve the data using keyName

            View header = navigationView.getHeaderView(0);
            TextView tv = (TextView) header.findViewById(R.id.txtEmailAccount);
            tv.setText("Account: "+data);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //Auto Close Drawer When Selected
        drawerLayout.closeDrawer(GravityCompat.START);
        //Working After Selected
        switch (menuItem.getItemId()) {
            case R.id.home:
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new FragmentMain());
                fragmentTransaction.commit();
                return true;
            case R.id.add_data:
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new FragmentSecond());
                fragmentTransaction.commit();
                return true;
            case R.id.menu_logout:
                AlertDialog.Builder dialog = new AlertDialog.Builder(DrawerActivity.this);
                dialog.setTitle("Information");
                dialog.setMessage("Do you want to logout?");
                dialog.setCancelable(true);

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                dialog.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int
                                    id) {
                                // Continue with some operation
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getApplicationContext(),Login.class));
                                finish();
                            }
                        });
                // A null listener allows the button to dismiss the dialog and take no further action.

                dialog.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = dialog.create();
                alert.show();
                return true;
            case R.id.menu_resetpassword:
                ResetPassword();
                return true;

            case R.id.menu_about:
                //startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new FragmentAbout());
                fragmentTransaction.commit();
                return true;
        }
        return true;
    }

    public void ResetPassword(){
        fAuth=FirebaseAuth.getInstance();
        final EditText resetMail=new EditText(DrawerActivity.this);
        final AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(DrawerActivity.this);
        passwordResetDialog.setTitle("Reset Password?");
        passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
        passwordResetDialog.setView(resetMail);
        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mail= resetMail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DrawerActivity.this,"Reset Link Sent To Your Email.",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DrawerActivity.this,"Error ! Reset Link is Not Sent" + e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        passwordResetDialog.create().show();
    }

            @Override
            public void onBackPressed() {
                Toast.makeText(DrawerActivity.this,"There is no back action",Toast.LENGTH_SHORT).show();
                return;
            }
        }


