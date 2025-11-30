package com.example.kidsmath;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SumGameActivity extends AppCompatActivity {

    TextView txtOperacion;
    EditText edtRespuesta;
    Button btnComprobar, btnSiguiente;

    int a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_game);


        txtOperacion = findViewById(R.id.txtOperacion);
        edtRespuesta = findViewById(R.id.edtRespuesta);
        btnComprobar = findViewById(R.id.btnComprobar);
        btnSiguiente = findViewById(R.id.btnSiguiente);

        generateSum();

        // Botón Comprobar
        btnComprobar.setOnClickListener(v -> {
            String answer = edtRespuesta.getText().toString();

            if (answer.isEmpty()) {
                Toast.makeText(this, "Ingrese una respuesta", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Integer.parseInt(answer) == a + b) {
                Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón Siguiente
        btnSiguiente.setOnClickListener(v -> generateSum());
    }

    private void generateSum() {
        Random r = new Random();
        a = r.nextInt(10);
        b = r.nextInt(10);
        txtOperacion.setText(a + " + " + b + " = ?");
        edtRespuesta.setText("");
    }
}
