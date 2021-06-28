package com.hassan.sshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class EditProductsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {



    private Toolbar mToolbar;
    private ProgressDialog loadingBar;
    private Button AddCart;
    private ImageView ImageViewProductDetail;
    private  TextView ProductDetailName,ProductDetailDescription,PDetailPrice,ProductCode;

    private DatabaseReference ClickProductRef;
    private FirebaseAuth mAuth;

    private DatabaseReference AddToCartRef;

    private String p_Key,p_refkey,postRandomName,current_user_id,saveCurrentDate, saveCurrentTime,currentUserEmail;

    String pro_name,pro_description,pro_price,pro_image,pro_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);



        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser= mAuth.getCurrentUser();
        if(currentUser!=null){
            current_user_id= mAuth.getCurrentUser().getUid();
        }


//        currentUserEmail =mAuth.getCurrentUser().getEmail();

        loadingBar = new ProgressDialog(this);


        AddToCartRef = FirebaseDatabase.getInstance().getReference().child("AddTOCart");

        p_Key=getIntent().getExtras().get("StdKey").toString();
        p_refkey=getIntent().getExtras().get("keyvalue").toString();

        ClickProductRef = FirebaseDatabase.getInstance().getReference().child("Products").child(p_refkey).child(p_Key);

        ImageViewProductDetail=findViewById(R.id.product_detail_image);

        ProductDetailDescription=findViewById(R.id.p_detail_description);
        ProductDetailName=findViewById(R.id.p_detail_name);
        PDetailPrice=findViewById(R.id.p_detail_price);
        AddCart=findViewById(R.id.add_to_cart);
        ProductCode=findViewById(R.id.p_detail_code);


        mToolbar =  findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.productdetail);


        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Size, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);





        ClickProductRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {


                    pro_name = dataSnapshot.child("productname").getValue().toString();
                    pro_description = dataSnapshot.child("productdescription").getValue().toString();
                    pro_price = dataSnapshot.child("productprice").getValue().toString();

                    pro_image = dataSnapshot.child("productimage").getValue().toString();
                    pro_code = dataSnapshot.child("productcode").getValue().toString();



                    ProductDetailName.setText(pro_name);
                    ProductDetailDescription.setText(pro_description);
                    PDetailPrice.setText("Rs: "+pro_price);
                    ProductCode.setText("I-code:"+pro_code);
                    Picasso.get().load(pro_image).into(ImageViewProductDetail);

                }
                AddCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (current_user_id != null) {
                            AddToCart();
                        }else {
                            Toast.makeText(EditProductsActivity.this, "plz first sign in ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditProductsActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        }) ;






        Calendar calFordate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currenTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currenTime.format(calFordate.getTime());

        postRandomName = saveCurrentDate+saveCurrentTime;


    }

    private void AddToCart() {
        HashMap postsMap = new HashMap();
        postsMap.put("productname",pro_name);
        postsMap.put("productdescription",pro_description);
        postsMap.put("productprice",pro_price);
        postsMap.put("productimage",pro_image);
        postsMap.put("productcode",pro_code);


        AddToCartRef.child(current_user_id).child(current_user_id + postRandomName).updateChildren(postsMap)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful()){

                            Toast.makeText(EditProductsActivity.this, "New Item Added successfully to cart..", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditProductsActivity.this,MainActivity.class);
                            startActivity(intent);
                            loadingBar.dismiss();

                        }
                        else {
                            Toast.makeText(EditProductsActivity.this, "Error Ocurred ", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });





    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
