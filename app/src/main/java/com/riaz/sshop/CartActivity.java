package com.riaz.sshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private RecyclerView CartList;
    private DatabaseReference CartRef;
    Context context;

    private FirebaseAuth mAuth;
    String userKeyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        mAuth = FirebaseAuth.getInstance();
        userKeyId = mAuth.getCurrentUser().getUid();

        mToolbar = findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle(R.string.cartitem);


        CartList = findViewById(R.id.all_cart_items);
        CartList.setHasFixedSize(true);

        CartRef = FirebaseDatabase.getInstance().getReference().child("AddTOCart").child(userKeyId);


        //    RecyclerView recyclerView = findViewById(R.id.all_cart_items);
//        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
//        recyclerView.setLayoutManager(layoutManager);


        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        CartList.setLayoutManager(linearLayoutManager);





        DisplayAllCartList();

    }

    private void DisplayAllCartList() {


        FirebaseRecyclerAdapter<Cart, CartViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>
                        (
                                Cart.class,
                                R.layout.cart_items_layout,
                                CartViewHolder.class,
                                CartRef
                        )
                {
                    @Override
                    protected void populateViewHolder(CartViewHolder viewHolder, Cart model, int position)
                    {
                        final String StdKey = getRef(position).getKey();

                        viewHolder.setProductname(model.getProductname());
                        viewHolder.setProductdescription(model.getProductdescription());
                        viewHolder.setProductprice(model.getProductprice());
                        viewHolder.setProductcode(model.getProductcode());
                        viewHolder.setProductimage(model.getProductimage());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent clickPostIntent = new Intent(CartActivity.this, ContinueOrderActivity.class);
                                clickPostIntent.putExtra("StdKey", StdKey);
                                clickPostIntent.putExtra("UserIDKey",userKeyId);

                                startActivity(clickPostIntent);
                            }
                        });

                    }
                };

        CartList.setAdapter(firebaseRecyclerAdapter);




    }


    public static class CartViewHolder extends RecyclerView.ViewHolder {


        View mView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;


        }

        public void setProductimage(String productimage) {
            ImageView image = mView.findViewById(R.id.cart_imageview);
            Picasso.get().load(productimage).into(image);
        }

        public void setProductprice(String productprice) {
            TextView price=mView.findViewById(R.id.cart_price);
            price.setText("$"+productprice);
        }

        public void setProductdescription(String productdescription) {
            TextView description=mView.findViewById(R.id.cart_description);
            description.setText(productdescription);
        }

        public void setProductname(String productname) {
            TextView name=mView.findViewById(R.id.all_cart_itemname);
            name.setText(productname);
        }

        public void setProductcode(String productcode) {
            TextView code=mView.findViewById(R.id.cart_code);
            code.setText(productcode);
        }




    }
}
