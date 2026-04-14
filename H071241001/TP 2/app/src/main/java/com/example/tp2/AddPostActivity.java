package com.example.tp2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class AddPostActivity extends AppCompatActivity {

    private Uri selectedImageUri;
    private ImageView ivPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        ivPreview = findViewById(R.id.iv_new_post_image);
        Button btnPick = findViewById(R.id.btn_pick_image);
        Button btnUpload = findViewById(R.id.btn_upload);
        EditText etCaption = findViewById(R.id.et_caption);
        ImageView btnBack = findViewById(R.id.btn_back_add);

        // Tombol Back
        btnBack.setOnClickListener(v -> finish());

        // Konfigurasi untuk membuka galeri
        ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();

                        // MENCEGAH CRASH: Minta izin permanen membaca gambar dari galeri
                        try {
                            getContentResolver().takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        ivPreview.setImageURI(selectedImageUri);
                    }
                }
        );

        // Tombol untuk memanggil galeri
        btnPick.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            galleryLauncher.launch(intent);
        });

        // Tombol Upload/Share
        btnUpload.setOnClickListener(v -> {
            String caption = etCaption.getText().toString();

            if (selectedImageUri == null) {
                Toast.makeText(this, "Pilih gambar dulu dari galeri!", Toast.LENGTH_SHORT).show();
            } else if (caption.trim().isEmpty()) {
                Toast.makeText(this, "Isi caption dulu!", Toast.LENGTH_SHORT).show();
            } else {
                String username = getIntent().getStringExtra("USERNAME");
                int profilePic = getIntent().getIntExtra("PROFILE_PIC", R.drawable.profile1);

                // Membuat postingan baru dengan Uri gambar dari galeri
                Post newPost = new Post(username, profilePic, selectedImageUri.toString(), caption);

                // Menyimpan ke wadah data utama
                DataSource.addPost(newPost);

                Toast.makeText(this, "Berhasil diposting!", Toast.LENGTH_SHORT).show();
                finish(); // Menutup halaman dan kembali ke Profil
            }
        });
    }
}