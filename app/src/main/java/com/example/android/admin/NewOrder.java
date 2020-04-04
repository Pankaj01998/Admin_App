package com.example.android.admin;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
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
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.CallbackHandler;

public class NewOrder extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mNewOrderDatabaseReference;

    private ValueEventListener mChildEventListener;
    private ChildEventListener mChildChangeListener;

    private ListView mNewOrdersListView;

    private NewOrdersAdapter mNewOrdersAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.neworder);

        mNewOrdersListView = (ListView) findViewById(R.id.list_track_orders);


        final List<EntireOrderDetails> newOrders = new ArrayList<>();
        mNewOrdersAdapter = new NewOrdersAdapter(this, R.layout.list_item_new, newOrders);

        mNewOrdersListView.setAdapter(mNewOrdersAdapter);

        mNewOrdersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(NewOrder.this, "List item clicked " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(NewOrder.this, CheckingOut.class);


                EntireOrderDetails order = newOrders.get(position);
                i.putExtra("phone", order.getPhone());
                i.putExtra("time", order.getmTime());

                startActivity(i);
            }
        });

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mNewOrderDatabaseReference = mFirebaseDatabase.getReference().child("New_Orders");

        attachDatabaseReadListener();

    }


    private void attachDatabaseReadListener() {
        mChildEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mNewOrdersAdapter.clear();
                Log.e("Track Orders  1 ", "Entered");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.e("Track Orders  2 ", "Entered");

                    EntireOrderDetails order_details = snapshot.getValue(EntireOrderDetails.class);

//                    Log.e("Track Orders  3 ", order_details.getName());

                    mNewOrdersAdapter.add(order_details);


                    Log.e("Track Orders  4 ", "Entered");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mChildChangeListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Order_Details order = dataSnapshot.getValue(Order_Details.class);
                if (order.getmConfirmationStatus() == 0) {

                    String msg = "New Orders Arrived";


                    NotificationManager notificationManager = (NotificationManager) NewOrder.this.getSystemService(Context.NOTIFICATION_SERVICE);

                    int notificationId = 1;
                    String channelId = "channel-01";
                    String channelName = "Channel Name";
                    int importance = NotificationManager.IMPORTANCE_HIGH;

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel mChannel = new NotificationChannel(
                                channelId, channelName, importance);
                        notificationManager.createNotificationChannel(mChannel);
                    }

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(NewOrder.this, channelId)
                            .setSmallIcon(R.drawable.g1)
                            .setContentTitle("Confirmation Notification")
                            .setContentText(msg);


                    Intent notificationIntent = new Intent(NewOrder.this, MainActivity.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(NewOrder.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    mBuilder.setContentIntent(contentIntent);

                    notificationManager.notify(notificationId, mBuilder.build());

                    Log.e("Track Orders 33 1 ", "Entered");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mChildEventListener != null){
            mNewOrderDatabaseReference.addValueEventListener(mChildEventListener);
            mNewOrderDatabaseReference.addChildEventListener(mChildChangeListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mChildEventListener != null){
            mNewOrderDatabaseReference.removeEventListener(mChildEventListener);
            mNewOrderDatabaseReference.removeEventListener(mChildChangeListener);
        }
    }
}
