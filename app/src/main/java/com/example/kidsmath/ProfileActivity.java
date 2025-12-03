package com.example.kidsmath;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ProfileActivity extends AppCompatActivity {

    EditText edtNombre, edtEdad;
    TextView txtPuntos;
    Button btnSaveProfile, btnChangePhoto, btnVolver;
    ImageView imgProfile;

    private static final int CAMERA_PERMISSION_CODE = 100;

    DatabaseManager db;
    Bitmap currentPhoto = null;

    ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK &&
                                result.getData() != null &&
                                result.getData().getExtras() != null) {

                            Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                            currentPhoto = photo;
                            imgProfile.setImageBitmap(photo);
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new DatabaseManager(this);

        edtNombre = findViewById(R.id.edtNombre);
        edtEdad = findViewById(R.id.edtEdad);
        txtPuntos = findViewById(R.id.txtPuntos);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnChangePhoto = findViewById(R.id.btnChangePhoto);
        imgProfile = findViewById(R.id.imgProfile);
        btnVolver = findViewById(R.id.btnVolver); // ← NUEVO

        loadUserData();

        btnChangePhoto.setOnClickListener(v -> {
            if (checkCameraPermission()) {
                openCamera();
            } else {
                requestCameraPermission();
            }
        });

        btnSaveProfile.setOnClickListener(v -> {
            String nombre = edtNombre.getText().toString().trim();
            String edadTxt = edtEdad.getText().toString().trim();

            if (nombre.isEmpty()) {
                Toast.makeText(this, "Escribe tu nombre", Toast.LENGTH_SHORT).show();
                return;
            }

            if (edadTxt.isEmpty()) {
                Toast.makeText(this, "Escribe tu edad", Toast.LENGTH_SHORT).show();
                return;
            }

            int edad = Integer.parseInt(edadTxt);

            db.updateUser(nombre, edad, "avatar1");

            Toast.makeText(this, "Perfil guardado correctamente", Toast.LENGTH_SHORT).show();
        });

        // ← BOTÓN VOLVER AL MENÚ PRINCIPAL
        btnVolver.setOnClickListener(v -> {
            Intent i = new Intent(ProfileActivity.this, MainMenuActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void loadUserData() {
        Cursor c = db.getUser();

        if (c.moveToFirst()) {
            String name = c.getString(c.getColumnIndexOrThrow("name"));
            int age = c.getInt(c.getColumnIndexOrThrow("age"));
            int points = c.getInt(c.getColumnIndexOrThrow("points_total"));

            edtNombre.setText(name);
            edtEdad.setText(String.valueOf(age));
            txtPuntos.setText("Puntos Totales: " + points);
        }
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_CODE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
    }
}
