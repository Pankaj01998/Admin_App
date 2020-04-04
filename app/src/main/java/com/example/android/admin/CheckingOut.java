package com.example.android.admin;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckingOut extends AppCompatActivity {

    private Spinner mDaySpinner;
    private String mDay;

    private Button mRejectOrder;
    private Button mConfirmOrder;
    private Button mDeliverOrder;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mNewOrderDatabaseReference;

    private String mPhone;
    private String mTime;

    private ValueEventListener mListener;
    private ValueEventListener mConfirmListener;
    private ValueEventListener mDeliveryListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkingout);


        mDaySpinner = (Spinner) findViewById(R.id.spinner_day);
        setupSpinner();



        Bundle bundle = getIntent().getExtras();
        mPhone = bundle.getString("phone");
        mTime = bundle.getString("time");


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mNewOrderDatabaseReference = mFirebaseDatabase.getReference();

        mRejectOrder = (Button) findViewById(R.id.button_reject_order);

        rejectOrderbuttonClickListener();

        mConfirmOrder = (Button) findViewById(R.id.button_confirm_order);

        confirmOrderbuttonClickListener();

        mDeliverOrder = (Button) findViewById(R.id.button_delivered_order);

        deliverOrderbuttonClickListener();



    }

    private void deliverOrderbuttonClickListener() {
        mDeliverOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(CheckingOut.this);
                builder1.setMessage("Do you want to set this order as delivered ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        mDeliveryListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.e("Track Orders  1 ", "Entered");
                                Date c = Calendar.getInstance().getTime();

                                @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                String formattedDate = df.format(c);


                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    Log.e("Track Orders  2 ", snapshot.getKey());

                                    if (snapshot.getKey().equals("All_Orders")){
                                        for (DataSnapshot order : snapshot.getChildren()){
                                            EntireOrderDetails order_datails = order.getValue(EntireOrderDetails.class);

                                            if (order_datails.getmTime().equals(mTime)){
                                                Log.e("Track Orders  3 ", "Entered");
                                                mNewOrderDatabaseReference.child("All_Orders").child(order.getKey()).child("mDeliveryStatus").setValue(1);
                                                mNewOrderDatabaseReference.child("All_Orders").child(order.getKey()).child("mExpectedDeliveryDay").setValue(formattedDate);
                                            }
                                        }
                                    }

                                    if (snapshot.getKey().equals("New_Orders")){
                                        for (DataSnapshot order : snapshot.getChildren()){
                                            EntireOrderDetails order_datails = order.getValue(EntireOrderDetails.class);

                                            if (order_datails.getmTime().equals(mTime)){
                                                Log.e("Track Orders  3 ", "Entered");
                                                mNewOrderDatabaseReference.child("New_Orders").child(order.getKey()).getRef().removeValue();
                                            }
                                        }
                                    }

                                    if (snapshot.getKey().equals("Order_Details")){
                                        for (DataSnapshot order : snapshot.child(mPhone).getChildren()){
                                            EntireOrderDetails order_datails = order.getValue(EntireOrderDetails.class);

                                            if (order_datails.getmTime().equals(mTime)){
                                                Log.e("Track Orders  3 ", "Entered");
                                                mNewOrderDatabaseReference.child("Order_Details").child(mPhone).child(order.getKey()).child("mDeliveryStatus").setValue(1);
                                                mNewOrderDatabaseReference.child("Order_Details").child(mPhone).child(order.getKey()).child("mExpectedDeliveryDay").setValue(formattedDate);
                                                finish();
                                            }
                                        }
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        };

                        mNewOrderDatabaseReference.addValueEventListener(mDeliveryListener);
                    }
                });

                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert1 = builder1.create();
                alert1.show();
            }
        });

    }

    private void confirmOrderbuttonClickListener() {



        mConfirmOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mConfirmListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.e("Track Orders  1 ", "Entered");
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Log.e("Track Orders  2 ", snapshot.getKey());

                            if (snapshot.getKey().equals("All_Orders")){
                                for (DataSnapshot order : snapshot.getChildren()){
                                    EntireOrderDetails order_datails = order.getValue(EntireOrderDetails.class);

                                    if (order_datails.getmTime().equals(mTime)){
                                        Log.e("Track Orders  3 ", "Entered");
                                        mNewOrderDatabaseReference.child("All_Orders").child(order.getKey()).child("mConfirmationStatus").setValue(1);
                                        mNewOrderDatabaseReference.child("All_Orders").child(order.getKey()).child("mExpectedDeliveryDay").setValue(mDay);
                                    }
                                }
                            }

                            if (snapshot.getKey().equals("New_Orders")){
                                for (DataSnapshot order : snapshot.getChildren()){
                                    EntireOrderDetails order_datails = order.getValue(EntireOrderDetails.class);

                                    if (order_datails.getmTime().equals(mTime)){
                                        Log.e("Track Orders  3 ", "Entered");
                                        mNewOrderDatabaseReference.child("New_Orders").child(order.getKey()).child("mConfirmationStatus").setValue(1);
                                        mNewOrderDatabaseReference.child("New_Orders").child(order.getKey()).child("mExpectedDeliveryDay").setValue(mDay);
                                    }
                                }
                            }

                            if (snapshot.getKey().equals("Order_Details")){
                                for (DataSnapshot order : snapshot.child(mPhone).getChildren()){
                                    EntireOrderDetails order_datails = order.getValue(EntireOrderDetails.class);

                                    if (order_datails.getmTime().equals(mTime)){
                                        Log.e("Track Orders  3 ", "Entered");
                                        mNewOrderDatabaseReference.child("Order_Details").child(mPhone).child(order.getKey()).child("mConfirmationStatus").setValue(1);
                                        mNewOrderDatabaseReference.child("Order_Details").child(mPhone).child(order.getKey()).child("mExpectedDeliveryDay").setValue(mDay);
                                        finish();
                                    }
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };

                mNewOrderDatabaseReference.addValueEventListener(mConfirmListener);

            }
        });
    }

    private void rejectOrderbuttonClickListener() {
        mRejectOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.e("Track Orders  1 ", "Entered");
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Log.e("Track Orders  2 ", snapshot.getKey());

                            if (snapshot.getKey().equals("All_Orders")){
                                for (DataSnapshot order : snapshot.getChildren()){
                                    EntireOrderDetails order_datails = order.getValue(EntireOrderDetails.class);

                                    if (order_datails.getmTime().equals(mTime)){
                                        Log.e("Track Orders  3 ", "Entered");
                                        mNewOrderDatabaseReference.child("All_Orders").child(order.getKey()).child("mConfirmationStatus").setValue(-1);
                                    }
                                }
                            }

                            if (snapshot.getKey().equals("New_Orders")){
                                for (DataSnapshot order : snapshot.getChildren()){
                                    EntireOrderDetails order_datails = order.getValue(EntireOrderDetails.class);

                                    if (order_datails.getmTime().equals(mTime)){
                                        Log.e("Track Orders  3 ", "Entered");
                                        mNewOrderDatabaseReference.child("New_Orders").child(order.getKey()).getRef().removeValue();
                                    }
                                }
                            }

                            if (snapshot.getKey().equals("Order_Details")){
                                for (DataSnapshot order : snapshot.child(mPhone).getChildren()){
                                    EntireOrderDetails order_datails = order.getValue(EntireOrderDetails.class);

                                    if (order_datails.getmTime().equals(mTime)){
                                        Log.e("Track Orders  3 ", "Entered");
                                        mNewOrderDatabaseReference.child("Order_Details").child(mPhone).child(order.getKey()).child("mConfirmationStatus").setValue(-1);
                                        finish();
                                    }
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };

                mNewOrderDatabaseReference.addValueEventListener(mListener);


            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConfirmListener != null){
            mNewOrderDatabaseReference.removeEventListener(mConfirmListener);
        }

        if (mListener != null){
            mNewOrderDatabaseReference.removeEventListener(mListener);
        }
        if (mDeliveryListener != null){
            mNewOrderDatabaseReference.removeEventListener(mDeliveryListener);
        }
    }


    private void setupSpinner() {
        ArrayAdapter daySpinnerAdapter = ArrayAdapter.createFromResource(CheckingOut.this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);
        // Specify dropdown layout style - simple list view with 1 item per line
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mDaySpinner.setAdapter(daySpinnerAdapter);

        // Set the integer mSelected to the constant values
        mDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Info MA", "  4   @@@@@@@@@@@@@@@@@");
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.d1))) {
                        mDay = "Monday";
                        Toast.makeText(CheckingOut.this, "Day selected " + mDay, Toast.LENGTH_SHORT).show();

                    } else if (selection.equals(getString(R.string.d2))) {
                        mDay = "Tuesday";
                        Toast.makeText(CheckingOut.this, "Day selected " + mDay, Toast.LENGTH_SHORT).show();

                    } else {
                        mDay = "Friday";
                        Toast.makeText(CheckingOut.this, "Day selected " + mDay, Toast.LENGTH_SHORT).show();

                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDay = "Sunday"; // Unknown
            }
        });
    }
}
