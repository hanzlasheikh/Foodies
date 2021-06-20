package com.riaz.sshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CheckOrderActivity extends AppCompatActivity {



    private RecyclerView OrdersList;
    private DatabaseReference OrdersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order);


        OrdersList = findViewById(R.id.check_orders);
        OrdersList.setHasFixedSize(true);

        OrdersRef = FirebaseDatabase.getInstance().getReference().child("Orders");


        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        OrdersList.setLayoutManager(linearLayoutManager);

        DisplayAllOrdersList();

    }

    private void DisplayAllOrdersList() {




        FirebaseRecyclerAdapter<Orders, CheckOrderActivity.OrdersViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Orders, CheckOrderActivity.OrdersViewHolder>
                        (
                                Orders.class,
                                R.layout.orders_layouts,
                                CheckOrderActivity.OrdersViewHolder.class,
                                OrdersRef
                        )
                {
                    @Override
                    protected void populateViewHolder(CheckOrderActivity.OrdersViewHolder viewHolder, Orders model, int position)
                    {
                        final String StdKey = getRef(position).getKey();

                        viewHolder.setName(model.getName());
                        viewHolder.setNumber(model.getNumber());
                        viewHolder.setAddress(model.getAddress());
                        viewHolder.setOpcode(model.getOpcode());


                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//                            OrdersRef.child(StdKey).removeValue();

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(CheckOrderActivity.this);
                                builder1.setMessage("Are you sure you want to Remove this Item From Orders");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                OrdersRef.child(StdKey).removeValue();
                                            }
                                        });

                                builder1.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();






                            }
                        });

                    }
                };

        OrdersList.setAdapter(firebaseRecyclerAdapter);






    }



    public static class OrdersViewHolder extends RecyclerView.ViewHolder{


        View mView;
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setOpcode(String opcode){
            TextView code=mView.findViewById(R.id.order_item_code);
            code.setText(opcode);
        }
        public void setNumber(String number) {
            TextView orderUsernumber=mView.findViewById(R.id.number_user);
            orderUsernumber.setText(number);
        }

        public void setName(String name) {
            TextView orderUsername=mView.findViewById(R.id.all_order_itemname);
            orderUsername.setText(name);
        }

        public void setAddress(String address) {
            TextView orderUseraddress=mView.findViewById(R.id.address_user);
            orderUseraddress.setText(address);
        }




    }
}
