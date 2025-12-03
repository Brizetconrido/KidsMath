package com.example.kidsmath;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ColorGameActivity extends AppCompatActivity {

    String[] colors = {"ROJO", "VERDE", "AZUL", "AMARILLO"};
    String currentColor;

    TextView txtInstruction, txtResult;
    Button btnTakePhoto, btnVolver;

    ActivityResultLauncher<Intent> cameraLauncher;

    DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_game);

        txtInstruction = findViewById(R.id.txtColorInstruction);
        txtResult = findViewById(R.id.txtColorResult);
        btnTakePhoto = findViewById(R.id.btnTakeColorPhoto);
        btnVolver = findViewById(R.id.btnVolver);

        db = new DatabaseManager(this);

        // Elegir color aleatorio
        currentColor = colors[new Random().nextInt(colors.length)];
        txtInstruction.setText("Busca algo " + currentColor + " y toma una foto");

        // Cámara
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getData() != null && result.getData().getExtras() != null) {
                        Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                        validateColor(photo);
                    }
                }
        );

        btnTakePhoto.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(intent);
        });

        // Botón volver
        btnVolver.setOnClickListener(v -> {
            Intent i = new Intent(ColorGameActivity.this, GameSelectionActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void validateColor(Bitmap bmp) {

        if (bmp == null) {
            txtResult.setText("Error al obtener foto");
            return;
        }

        int pixel = bmp.getPixel(bmp.getWidth() / 2, bmp.getHeight() / 2);

        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = pixel & 0xff;

        boolean ok = false;

        switch (currentColor) {
            case "ROJO":
                ok = r > g && r > b;
                break;

            case "VERDE":
                ok = g > r && g > b;
                break;

            case "AZUL":
                ok = b > r && b > g;
                break;

            case "AMARILLO":
                ok = r > 150 && g > 150;
                break;
        }

        if (ok) {
            txtResult.setText("¡Correcto! Encontraste " + currentColor + " +1 punto");
            db.addScore("color", 1);
        } else {
            txtResult.setText("❌ Ese no parece ser " + currentColor);
        }
    }
}

