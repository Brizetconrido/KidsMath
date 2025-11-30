package com.example.kidsmath;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainMenuActivity extends AppCompatActivity {

    Button btnJugar, btnPuntajes, btnRecompensas, btnPerfil;
    Button btnCamara, btnMicrofono;

    // Launchers para cámara y micrófono
    ActivityResultLauncher<Intent> cameraLauncher;
    ActivityResultLauncher<Intent> micLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnJugar = findViewById(R.id.btnJugar);
        btnPuntajes = findViewById(R.id.btnPuntajes);
        btnRecompensas = findViewById(R.id.btnRecompensas);
        btnPerfil = findViewById(R.id.btnPerfil);

        btnCamara = findViewById(R.id.btnCamara);
        btnMicrofono = findViewById(R.id.btnMicrofono);

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> Toast.makeText(this, "Foto tomada", Toast.LENGTH_SHORT).show()
        );

        micLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> Toast.makeText(this, "Audio grabado", Toast.LENGTH_SHORT).show()
        );

        btnJugar.setOnClickListener(v ->
                startActivity(new Intent(this, GameSelectionActivity.class))
        );

        btnPuntajes.setOnClickListener(v ->
                startActivity(new Intent(this, ScoreActivity.class))
        );

        btnRecompensas.setOnClickListener(v ->
                startActivity(new Intent(this, RewardsActivity.class))
        );

        btnPerfil.setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class))
        );

        btnCamara.setOnClickListener(v -> {
            if (checkPermission(Manifest.permission.CAMERA)) {
                openCamera();
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 200);
            }
        });

        btnMicrofono.setOnClickListener(v -> {
            startActivity(new Intent(this, RecorderActivity.class));
        });

    }

    private boolean checkPermission(String perm) {
        return ContextCompat.checkSelfPermission(this, perm)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
    }

    private void openMicrophone() {
        Intent micIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        micLauncher.launch(micIntent);
    }
}
