package com.riaz.sshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class UploadProductsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Toolbar mToolbar;
    private ProgressDialog loadingBar;

    private ImageView ProductImage;
    private EditText Productprice,ProductName,ProductCode;
    private EditText Productdescription;
    private EditText Productquantity;
    private Button   UploadBtn;

    private StorageReference ProductImageRef;
    private DatabaseReference ProductsRef;
    private FirebaseAuth mAuth;

    private Uri ImageUri;

    private  String p_price,p_description,p_quantity,dept,p_name,p_code;


    private String saveCurrentDate, saveCurrentTime, postRandomName,downloadUrl,current_user_id;

    final  static  int GalleryPick=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_products);


        ProductImage=findViewById(R.id.product_image);
        Productprice=findViewById(R.id.product_price);
        ProductName=findViewById(R.id.product_name);
        Productdescription=findViewById(R.id.product_description);
        Productquantity=findViewById(R.id.product_quantity);
        ProductCode=findViewById(R.id.product_code);

        UploadBtn=findViewById(R.id.product_upload);

        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        current_user_id= mAuth.getCurrentUser().getUid();


        ProductImageRef = FirebaseStorage.getInstance().getReference();
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");


        mToolbar =  findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.uploadproducts);


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.top_color));


        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);


        ProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        UploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductsInfo();
            }
        });
    }

    private void ValidateProductsInfo() {
         p_price =Productprice.getText().toString();
         p_description =Productdescription.getText().toString();
         p_quantity =Productquantity.getText().toString();
         p_name =ProductName.getText().toString();
         p_code =ProductCode.getText().toString();

        if (ImageUri == null){
            Toast.makeText(this, "Please Select  image...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(p_price)){
            Toast.makeText(this, "Please Enter ID...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(p_description)){
            Toast.makeText(this, "Please Enter name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(p_quantity)){
            Toast.makeText(this, "Please write description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(p_name)){
            Toast.makeText(this, "Please write name...", Toast.LENGTH_SHORT).show();
        }

        else {
            loadingBar.setTitle("Add Item");
            loadingBar.setMessage("Please wait,While we updating...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            StoringImageToFirebaseStorage();
        }
    }

    private void StoringImageToFirebaseStorage() {

        Calendar calFordate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currenTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currenTime.format(calFordate.getTime());

        postRandomName = saveCurrentDate+saveCurrentTime;

        StorageReference filePath = ProductImageRef.child("Products Image").child(ImageUri.getLastPathSegment() + postRandomName +  ".jpg");
        StorageTask<UploadTask.TaskSnapshot> taskSnapshotStorageTask = filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){

                    Toast.makeText(UploadProductsActivity.this, "Image uploaded succesfully to storage...", Toast.LENGTH_SHORT).show();
                    StorageReference filePath = ProductImageRef.child("Products Image").child(ImageUri.getLastPathSegment() +postRandomName + ".jpg");
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downlooadPicUrl = uri.toString();


                            SavingPostInformationToDatabase(downlooadPicUrl);



                        }
                    });



                }
                else {
                    String message= task.getException().getMessage();
                    Toast.makeText(UploadProductsActivity.this, "Error ocurred: " + message, Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });




    }

    private void SavingPostInformationToDatabase(String downlooadPicUrl) {


        Toast.makeText(this, "savingitemsinfo", Toast.LENGTH_SHORT).show();

        HashMap postsMap = new HashMap();
        postsMap.put("uid",current_user_id);
        postsMap.put("productimage",downlooadPicUrl);
        postsMap.put("productprice",p_price);
        postsMap.put("productdescription",p_description);
        postsMap.put("productquantity",p_quantity);
        postsMap.put("stddept",dept);
        postsMap.put("productname",p_name);
        postsMap.put("productcode",p_code);

        ProductsRef.child(dept).child(current_user_id + postRandomName).updateChildren(postsMap)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful()){

                            Toast.makeText(UploadProductsActivity.this, "New item Added successfully..", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            finish();

                        }
                        else {
                            Toast.makeText(UploadProductsActivity.this, "Error Ocurred ", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
    }


    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null){
            ImageUri = data.getData();
            ProductImage.setImageURI(ImageUri);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dept = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
