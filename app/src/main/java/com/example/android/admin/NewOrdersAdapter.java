package com.example.android.admin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NewOrdersAdapter extends ArrayAdapter<EntireOrderDetails> {

    public NewOrdersAdapter(Context context, int resource, List<EntireOrderDetails> objects) {
        super(context, resource, objects);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_item_new, parent, false);
        }

        TextView dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.timeTextView);

        TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        TextView villageTextView = (TextView) convertView.findViewById(R.id.villageTextView);

        EntireOrderDetails order = getItem(position);

        dateTextView.setText(order.getmDate());
        timeTextView.setText(order.getmTime());

        nameTextView.setText(order.getName());

        if (order.getVillage() == 1){
            villageTextView.setText("Allipur");
        }
        else if (order.getVillage() == 2){
            villageTextView.setText("Shirud");
        }
        else if (order.getVillage() == 0){
            villageTextView.setText("Pawni");
        }

        TextView orderStatusTextView = (TextView) convertView.findViewById(R.id.orderStatusTextView);
        TextView expectedDeliveryTextView = (TextView) convertView.findViewById(R.id.expectedDeliveryTextView);
        TextView deliveryTextView = (TextView) convertView.findViewById(R.id.deliveryTextView);
        expectedDeliveryTextView.setVisibility(View.GONE);
        deliveryTextView.setVisibility(View.GONE);



        if (order.getmConfirmationStatus() == 1){
            orderStatusTextView.setText("Confirmed");
            deliveryTextView.setVisibility(View.VISIBLE);
            expectedDeliveryTextView.setVisibility(View.VISIBLE);
//            if (order.getVillage() == 0){
//                expectedDeliveryTextView.setText("Thursday");
//            }
//            if (order.getVillage() == 1){
//                expectedDeliveryTextView.setText("Friday");
//            }
            expectedDeliveryTextView.setText(order.getmExpectedDeliveryDay());
        }
        else if(order.getmConfirmationStatus() == -1){
            orderStatusTextView.setText("Rejected");
        }
        else {
            orderStatusTextView.setText("Ordered");
        }

        if (order.getmDeliveryStatus() == 1){
            orderStatusTextView.setText("Delivered");
            if (order.getmConfirmationStatus() == 1) {
                deliveryTextView.setVisibility(View.VISIBLE);
                deliveryTextView.setText("Delivered Date");
                expectedDeliveryTextView.setVisibility(View.VISIBLE);
                expectedDeliveryTextView.setText(order.getmExpectedDeliveryDay());
            }
            else if (order.getmConfirmationStatus() == -1){
                deliveryTextView.setVisibility(View.VISIBLE);
                deliveryTextView.setText("Rejected Date");
                expectedDeliveryTextView.setVisibility(View.VISIBLE);
                expectedDeliveryTextView.setText(order.getmExpectedDeliveryDay());
            }

        }

        return convertView;
    }

}


