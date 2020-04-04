package com.example.android.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private ChildEventListener mHomeChildEventListener;

    private Button mNewOrderButton;
    private Button mAllOrderButton;


    private FirebaseAuth mLoginFirebaseAuth;
    private FirebaseAuth.AuthStateListener  mLoginAuthStateListener;
    public static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNewOrderClickListener();

        setAllOrderClickListener();

        mLoginFirebaseAuth = FirebaseAuth.getInstance();



    }

    private void setAllOrderClickListener() {
        mAllOrderButton = (Button) findViewById(R.id.button_all_order);
        mAllOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AllOrders.class);
                startActivity(i);
            }
        });
    }

    private void setNewOrderClickListener() {
        mNewOrderButton = (Button) findViewById(R.id.button_new_order);
        mNewOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewOrder.class);
                startActivity(i);
            }
        });
    }


}
