package com.hassan.sshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class ShowAlIitemsActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private RecyclerView ProductsList;
    private DatabaseReference ProductsRef;

    SearchView mSearch;
    String keyvalue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_al_iitems);

        mToolbar =  findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle(R.string.clothing);

        Intent intent = getIntent();
        keyvalue = intent.getStringExtra("key");


        mSearch=findViewById(R.id.search_view);

        ProductsList=findViewById(R.id.all_products_list);
        ProductsList.setHasFixedSize(true);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(keyvalue);


//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
//        ProductsList.setLayoutManager(linearLayoutManager);


        RecyclerView recyclerView = findViewById(R.id.all_products_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);


        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // String check="pqrs";
//        if(newText.isEmpty()) {
//            String first = newText.substring(0, newText.length()/newText.length()).toUpperCase();
//            String second = newText.substring(1).toLowerCase();
//            String ffinal = first + second;
//            Toast.makeText(WantedStdActivity.this, ffinal, Toast.LENGTH_SHORT).show();
//
//            Toast.makeText(WantedStdActivity.this, first, Toast.LENGTH_SHORT).show();
//
//            SearchStd(ffinal);
//        }

                if(!newText.isEmpty()) {
                    String s1 = newText.substring(0, 1).toUpperCase() + newText.substring(1);

                    SearchStd(s1);
                }
                else {// first make class
                    DisplayAllStucentsList();
                }

                return true;

            }
        });

        DisplayAllStucentsList();
    }

    private void SearchStd(String s1) {

        Query searchPeopleandFriendsQuery = ProductsRef.orderByChild("productname")
                .startAt(s1).endAt(s1 +"\uf8ff");



        FirebaseRecyclerAdapter<Products,ProductsViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Products, ProductsViewHolder>
                        (
                                Products.class,
                                R.layout.allitemslayout,
                                ProductsViewHolder.class,
                                searchPeopleandFriendsQuery
                        )
                {
                    @Override
                    protected void populateViewHolder(ProductsViewHolder viewHolder, Products model, int position)
                    {
                        final String StdKey = getRef(position).getKey();



                        viewHolder.setProductname(model.getProductname());
                        viewHolder.setProductdescription(model.getProductdescription());
                        viewHolder.setProductprice(model.getProductprice());
                        viewHolder.setProductimage(model.getProductimage());
//                        if(keyvalue ==null) {
//                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    Intent clickPostIntent = new Intent(WantedStdActivity.this, EditStudentActivity.class);
//                                    clickPostIntent.putExtra("StdKey", StdKey);
//                                    startActivity(clickPostIntent);
//                                }
//                            });
//                        }
                    }
                };

        ProductsList.setAdapter(firebaseRecyclerAdapter);







    }

    private void DisplayAllStucentsList() {


        FirebaseRecyclerAdapter<Products,ProductsViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Products, ProductsViewHolder>
                        (
                                Products.class,
                                R.layout.allitemslayout,
                                ProductsViewHolder.class,
                                ProductsRef
                        )
                {
                    @Override
                    protected void populateViewHolder(ProductsViewHolder viewHolder, Products model, int position)
                    {
                        final String StdKey = getRef(position).getKey();

                        viewHolder.setProductname(model.getProductname());
                        viewHolder.setProductdescription(model.getProductdescription());
                        viewHolder.setProductprice(model.getProductprice());
                        viewHolder.setProductimage(model.getProductimage());

                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                        Intent clickPostIntent = new Intent(ShowAlIitemsActivity.this, EditProductsActivity.class);
                                        clickPostIntent.putExtra("StdKey", StdKey);
                                        clickPostIntent.putExtra("keyvalue", keyvalue);
                                        startActivity(clickPostIntent);

                                }
                            });

                    }
                };

        ProductsList.setAdapter(firebaseRecyclerAdapter);



    }


    public static class ProductsViewHolder extends RecyclerView.ViewHolder{


        View mView;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }


        public void setProductimage(String productimage) {
            ImageView image = mView.findViewById(R.id.pro_image);
            Picasso.get().load(productimage).into(image);
        }

        public void setProductprice(String productprice) {
            TextView price=mView.findViewById(R.id.pro_price);
            price.setText("$"+productprice);
        }

        public void setProductdescription(String productdescription) {
            TextView description=mView.findViewById(R.id.pro_des);
            description.setText(productdescription);
        }
        public void setStddept(String stddept) {
//            TextView name=mView.findViewById(R.id.pro_name);
//            name.setText(stddept);
        }
        public void setProductname(String productname) {
            TextView name=mView.findViewById(R.id.pro_name);
            name.setText(productname);
        }


    }



}
