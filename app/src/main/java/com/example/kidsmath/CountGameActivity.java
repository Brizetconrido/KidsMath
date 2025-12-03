package com.example.kidsmath;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class CountGameActivity extends AppCompatActivity {

    LinearLayout layoutImages;
    EditText edtAnswerCount;
    TextView txtResultCount;
    Button btnCheckCount, btnNextCount, btnVolver;

    int contadorReal;

    DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_game);

        db = new DatabaseManager(this);

        layoutImages = findViewById(R.id.layoutImages);
        edtAnswerCount = findViewById(R.id.edtAnswerCount);
        txtResultCount = findViewById(R.id.txtResultCount);

        btnCheckCount = findViewById(R.id.btnCheckCount);
        btnNextCount = findViewById(R.id.btnNextCount); // ← NUEVO
        btnVolver = findViewById(R.id.btnVolver);

        generarObjetos();

        //  BOTÓN COMPROBAR
        btnCheckCount.setOnClickListener(v -> {

            String respuesta = edtAnswerCount.getText().toString().trim();

            if (respuesta.isEmpty()) {
                Toast.makeText(this, "Ingresa un número", Toast.LENGTH_SHORT).show();
                return;
            }

            int respuestaNum = Integer.parseInt(respuesta);

            if (respuestaNum == contadorReal) {

                txtResultCount.setText("¡Correcto +1 punto!");
                txtResultCount.setTextColor(0xFF2E7D32);

                db.addScore("contar", 1);

                new Handler().postDelayed(() -> {
                    generarObjetos();
                    txtResultCount.setText("");
                    edtAnswerCount.setText("");
                }, 900);

            } else {
                txtResultCount.setText("Incorrecto");
                txtResultCount.setTextColor(0xFFC62828);
            }
        });

        //  BOTÓN SIGUIENTE (sin sumar puntos)
        btnNextCount.setOnClickListener(v -> {
            generarObjetos();
            txtResultCount.setText("");
            edtAnswerCount.setText("");
            Toast.makeText(this, "Nuevo ejercicio ➤", Toast.LENGTH_SHORT).show();
        });

        // ➤BOTÓN VOLVER
        btnVolver.setOnClickListener(v -> {
            Intent i = new Intent(CountGameActivity.this, GameSelectionActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void generarObjetos() {

        layoutImages.removeAllViews(); // limpiar

        Random random = new Random();
        contadorReal = random.nextInt(8) + 3; // 3–10 objetos

        for (int i = 0; i < contadorReal; i++) {
            ImageView img = new ImageView(this);
            img.setImageResource(android.R.drawable.btn_star_big_on);
            img.setPadding(10, 10, 10, 10);
            img.setLayoutParams(new LinearLayout.LayoutParams(120, 120));
            layoutImages.addView(img);
        }
    }
}

