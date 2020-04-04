package com.example.android.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllOrders extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mNewOrderDatabaseReference;

    private ValueEventListener mChildEventListener;

    private ListView mAllOrdersListView;

    private NewOrdersAdapter mAllOrdersAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allorders);

        mAllOrdersListView = (ListView) findViewById(R.id.list_track_all_orders);


        final List<EntireOrderDetails> allOrders = new ArrayList<>();
        mAllOrdersAdapter = new NewOrdersAdapter(this, R.layout.list_item_new, allOrders);

        mAllOrdersListView.setAdapter(mAllOrdersAdapter);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mNewOrderDatabaseReference = mFirebaseDatabase.getReference().child("All_Orders");

        attachDatabaseReadListener();

    }


    private void attachDatabaseReadListener() {
        mChildEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAllOrdersAdapter.clear();
                Log.e("Track Orders  1 ", "Entered");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.e("Track Orders  2 ", "Entered");

                    EntireOrderDetails order_details = snapshot.getValue(EntireOrderDetails.class);

//                    Log.e("Track Orders  3 ", order_details.getName());

                    mAllOrdersAdapter.add(order_details);


                    Log.e("Track Orders  4 ", "Entered");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mNewOrderDatabaseReference.addValueEventListener(mChildEventListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mChildEventListener != null){
            mNewOrderDatabaseReference.addValueEventListener(mChildEventListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mChildEventListener != null){
            mNewOrderDatabaseReference.removeEventListener(mChildEventListener);
        }
    }
}