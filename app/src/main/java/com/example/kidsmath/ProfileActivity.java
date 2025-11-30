package com.example.kidsmath;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.example.kidsmath.api.ApiClient;

public class ProfileActivity extends AppCompatActivity {

    EditText edtNombre, edtEdad;
    TextView txtPuntos;
    Button btnSaveProfile, btnChangePhoto;
    ImageView imgProfile;

    private static final int CAMERA_PERMISSION_CODE = 100;

    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                    imgProfile.setImageBitmap(photo);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        edtNombre = findViewById(R.id.edtNombre);
        edtEdad = findViewById(R.id.edtEdad);
        txtPuntos = findViewById(R.id.txtPuntos);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnChangePhoto = findViewById(R.id.btnChangePhoto);
        imgProfile = findViewById(R.id.imgProfile);

        ApiClient api = new ApiClient(this);


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

            int puntos = 100;   // puedes cambiarlo cuando hagas que los juegos sumen puntos
            String juego = "perfil";

            api.sendScore(nombre, puntos, juego, new ApiClient.ApiResponse() {
                @Override
                public void onSuccess(String response) {
                    Toast.makeText(ProfileActivity.this,
                            "Perfil enviado correctamente a la API",
                            Toast.LENGTH_SHORT).show();

                    txtPuntos.setText("Puntos Totales: " + puntos);
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(ProfileActivity.this,
                            "ERROR API: " + error,
                            Toast.LENGTH_SHORT).show();
                }
            });

        });
    }


    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_CODE);
    }

    // Resultado del permiso
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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

