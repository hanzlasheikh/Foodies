package com.riaz.sshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;

import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

public class MainActivity extends AppCompatActivity {

    SliderLayout sliderLayout;
     NavigationView navigationView;
    DrawerLayout drawerLayout;
     ActionBarDrawerToggle actionBarDrawerToggle;
     private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private ImageView Restaurant1,Restaurant2,Restaurant3,CartImageview,Restaurant4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Restaurant1=findViewById(R.id.mens_shisrt);
        Restaurant2=findViewById(R.id.tourser);
        Restaurant3=findViewById(R.id.shoess);
        Restaurant4=findViewById(R.id.restaurant4);
        CartImageview=findViewById(R.id.cart_image);

        mAuth = FirebaseAuth.getInstance();

        navigationView = findViewById(R.id.navigation_view);


        mToolbar =  findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.sshop);




        drawerLayout = findViewById(R.id.drawable_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.navigation_view);
        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.top_color));


        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(5); //set scroll delay in seconds :
        setSliderViews();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                UserMenuSelector(menuItem);
                return false;
            }
        });


        CartImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

        Restaurant1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(MainActivity.this,ShowAlIitemsActivity.class);
              intent.putExtra("key","restaurants1");
              startActivity(intent);
          }
      });


        Restaurant2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ShowAlIitemsActivity.class);
                intent.putExtra("key","restaurants2");
                startActivity(intent);
            }
        });



        Restaurant3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ShowAlIitemsActivity.class);
                intent.putExtra("key","restaurants3");
                startActivity(intent);
            }
        });

        Restaurant4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ShowAlIitemsActivity.class);
                intent.putExtra("key","restaurants4");
                startActivity(intent);
            }
        });





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.nav_logout:
                mAuth.signOut();
                SendUserTologinActivity();
                break;
            case R.id.nav_admin:
                Intent intent=new Intent(MainActivity.this,AdminLoginActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void SendUserTologinActivity() {
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }


    private void setSliderViews() {

        for (int i = 0; i <= 2; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(this);

            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.foodrpic);
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.foodrpic);
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.foodrpic);
                    break;

            }

            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
//            sliderView.setDescription("The quick brown fox jumps over the lazy dog.\n" +
//                    "Jackdaws love my big sphinx of quartz. " + (i + 1));
//            final int finalI = i;

            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
//                    Toast.makeText(MainActivity.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();

                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }

    }
}
