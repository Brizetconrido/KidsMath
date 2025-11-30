package com.example.kidsmath;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ShapeGameActivity extends AppCompatActivity {

    String[] shapes = {"CÍRCULO", "CUADRADO", "TRIÁNGULO"};
    String currentShape;

    TextView txtInstruction, txtResult;
    Button btnTakePhoto;

    ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_game);

        txtInstruction = findViewById(R.id.txtShapeInstruction);
        txtResult = findViewById(R.id.txtShapeResult);
        btnTakePhoto = findViewById(R.id.btnTakeShapePhoto);

        // Elegir forma aleatoria
        currentShape = shapes[new Random().nextInt(shapes.length)];
        txtInstruction.setText("Encuentra un " + currentShape);

        // Cámara
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getData() != null && result.getData().getExtras() != null) {
                        Bitmap bmp = (Bitmap) result.getData().getExtras().get("data");
                        detectShape(bmp);
                    }
                }
        );

        btnTakePhoto.setOnClickListener(v -> {
            Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(cam);
        });
    }


    /***
     * Algoritmo simple
     * - Círculo → muchos bordes curvos
     * - Cuadrado → 4 esquinas claras
     * - Triángulo → 3 esquinas
     */
    private void detectShape(Bitmap bmp) {
        if (bmp == null) {
            txtResult.setText("Error al leer la foto");
            return;
        }

        int width = bmp.getWidth();
        int height = bmp.getHeight();

        int corners = 0;

        // Detectar "cambios fuertes de color" (bordes)
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {

                int c = bmp.getPixel(x, y);
                int left = bmp.getPixel(x - 1, y);
                int right = bmp.getPixel(x + 1, y);

                int diff1 = Math.abs(Color.red(c) - Color.red(left));
                int diff2 = Math.abs(Color.red(c) - Color.red(right));

                if (diff1 + diff2 > 120) {
                    corners++;
                }
            }
        }

        String detected;

        if (corners < 250) detected = "CÍRCULO";       // suave
        else if (corners < 600) detected = "TRIÁNGULO"; // algunas esquinas
        else detected = "CUADRADO";                    // bordes fuertes

        if (detected.equals(currentShape)) {
            txtResult.setText("🎉 ¡Correcto! Encontraste un " + currentShape);
        } else {
            txtResult.setText("❌ Detecté un " + detected + ", no un " + currentShape);
        }
    }
}
