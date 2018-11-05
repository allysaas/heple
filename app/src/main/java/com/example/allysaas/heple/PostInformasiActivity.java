package com.example.allysaas.heple;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.allysaas.heple.model.PostInformasiModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class PostInformasiActivity extends AppCompatActivity {

    EditText nama, alamat, lokasi, urlPeta, noHp, deskripsi;
    ImageView post, location, choosenPhoto;
    RadioButton menetap, berkeliling;
    boolean choosen;
    String status;
    private int PICK_IMAGE_REQUEST = 1;

    Uri filePathData;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_informasi);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        nama = findViewById(R.id.nama_penjual);
        alamat = findViewById(R.id.alamat);
        lokasi = findViewById(R.id.lokasi_berjualan);
        urlPeta = findViewById(R.id.peta_lokasi);
        noHp = findViewById(R.id.no_hp);
        deskripsi = findViewById(R.id.deskripsi);
        menetap = findViewById(R.id.radio_menetap);
        berkeliling = findViewById(R.id.radio_berkeliling);
        location = findViewById(R.id.piclocation);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post_informasi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if(item.getItemId() == R.id.post_menu_icon){
            postInformasi();
        }
        return super.onOptionsItemSelected(item);
    }

    public void openMap(View v) {
        Uri gmmIntentUri = Uri.parse("geo:0,0");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void chooseStatusBerjualan(View v) {
        choosen = ((RadioButton) v).isChecked();

        switch (v.getId()) {
            case R.id.radio_menetap:
                if (choosen) {
                    status = "Menetap";
                }
                break;
            case R.id.radio_berkeliling:
                if (choosen) {
                    status = "Berkeliling";
                }
        }
    }

    public void choosePhoto(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePathData = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathData);
                choosenPhoto = findViewById(R.id.photo);
                choosenPhoto.setImageBitmap(bitmap);
                choosenPhoto.getLayoutParams().height = 700;
                choosenPhoto.getLayoutParams().width = 600;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void postInformasi() {
        PostInformasiModel postInformasiModel = new PostInformasiModel(
                nama.getText().toString(), alamat.getText().toString(), lokasi.getText().toString(),
                urlPeta.getText().toString(), noHp.getText().toString(), status, deskripsi.getText().toString());
        uploadPicture();
    }

    public void uploadPicture() {
        if (filePathData != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePathData)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(PostInformasiActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(PostInformasiActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

}
