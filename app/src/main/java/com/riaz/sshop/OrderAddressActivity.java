package com.riaz.sshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class OrderAddressActivity extends AppCompatActivity {

    EditText CodeProduct,NameOrderUser,NumberOrderUser,AddressOrderUser;

    Button OrderBtn;

    private DatabaseReference OrderRef,ClickCartRef;

    private FirebaseAuth mAuth;

    String p_Key,userKey;

    private String saveCurrentDate, saveCurrentTime, postRandomName,current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_address);


        CodeProduct=findViewById(R.id.code_of_product);
        NameOrderUser=findViewById(R.id.name_order_user);
        NumberOrderUser=findViewById(R.id.phone_nu_order_user);
        AddressOrderUser=findViewById(R.id.address_order_user);

        OrderBtn=findViewById(R.id.order_placed);

        OrderRef = FirebaseDatabase.getInstance().getReference().child("Orders");



        p_Key=getIntent().getExtras().get("StdKey").toString();
        userKey=getIntent().getExtras().get("UserIDKey").toString();
        ClickCartRef = FirebaseDatabase.getInstance().getReference().child("AddTOCart").child(userKey).child(p_Key);

        mAuth = FirebaseAuth.getInstance();
        current_user_id= mAuth.getCurrentUser().getUid();


        OrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveOrder();

            }
        });

    }

    private void SaveOrder() {


        String code=CodeProduct.getText().toString();
        String name=NameOrderUser.getText().toString();
        String number=NumberOrderUser.getText().toString();
        String address=AddressOrderUser.getText().toString();


        Calendar calFordate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currenTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currenTime.format(calFordate.getTime());

        postRandomName = saveCurrentDate+saveCurrentTime;


        if (code == null){
            Toast.makeText(this, "Please enter product code...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please Enter name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(number)){
            Toast.makeText(this, "Please Enter number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(address)){
            Toast.makeText(this, "Please enter address...", Toast.LENGTH_SHORT).show();
        }

        else {


            HashMap postsMap = new HashMap();
            postsMap.put("opcode",code);
            postsMap.put("name",name);
            postsMap.put("number",number);
            postsMap.put("address",address);


            OrderRef.child(current_user_id + postRandomName).updateChildren(postsMap)
                    .addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if (task.isSuccessful()){

                                Toast.makeText(OrderAddressActivity.this, "order placed successfully..", Toast.LENGTH_SHORT).show();

                                ClickCartRef.removeValue();
                                Intent intent = new Intent(OrderAddressActivity.this,CartActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(OrderAddressActivity.this, "Error Ocurred ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



        }
    }
}
