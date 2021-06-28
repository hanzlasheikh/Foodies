package com.hassan.sshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminDashBoardActivity extends AppCompatActivity {

    ImageView UploadpImage,CheckOrImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);

        UploadpImage=findViewById(R.id.up_load_icon);
        CheckOrImage=findViewById(R.id.check_or_image);


        UploadpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashBoardActivity.this,UploadProductsActivity.class);
                startActivity(intent);
            }
        });

        CheckOrImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashBoardActivity.this,CheckOrderActivity.class);
                startActivity(intent);
            }
        });

    }
}
