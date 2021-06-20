package com.riaz.sshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContinueOrderActivity extends AppCompatActivity {

    Button RemoveCartBtn,ContinueShBtn;
    private DatabaseReference ClickCartRef;

    String p_Key,userKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_order);

        RemoveCartBtn=findViewById(R.id.delete_from_cart);
        ContinueShBtn=findViewById(R.id.continue_shopping);


        p_Key=getIntent().getExtras().get("StdKey").toString();
        userKey=getIntent().getExtras().get("UserIDKey").toString();


        ClickCartRef = FirebaseDatabase.getInstance().getReference().child("AddTOCart").child(userKey).child(p_Key);


        ContinueShBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ContinueOrderActivity.this,OrderAddressActivity.class);
                intent.putExtra("StdKey",p_Key);
                intent.putExtra("UserIDKey",userKey);
                startActivity(intent);

            }
        });

        // Remove from cart item listner

        RemoveCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder1 = new AlertDialog.Builder(ContinueOrderActivity.this);
                builder1.setMessage("Are you sure you want to Remove this Item From cart");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                DeleteCurrentItem();
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

    private void DeleteCurrentItem() {
        ClickCartRef.removeValue();
        finish();
        Toast.makeText(this, "Removed item from cart", Toast.LENGTH_SHORT).show();


    }
}
