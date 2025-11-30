package com.example.kidsmath;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GameSelectionActivity extends AppCompatActivity {

    Button btnSumas, btnRestas, btnMultiplicacion, btnDivision, btnContar, btnNumeroFaltante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);

        btnSumas = findViewById(R.id.btnSumas);
        btnRestas = findViewById(R.id.btnRestas);
        btnMultiplicacion = findViewById(R.id.btnMultiplicacion);
        btnDivision = findViewById(R.id.btnDivision);
        btnContar = findViewById(R.id.btnContar);
        btnNumeroFaltante = findViewById(R.id.btnNumeroFaltante);
        btnColorGame = findViewById(R.id.btnColorGame);
        btnShapeGame = findViewById(R.id.btnShapeGame);

        btnSumas.setOnClickListener(v ->
                startActivity(new Intent(this, SumGameActivity.class))
        );

        btnRestas.setOnClickListener(v ->
                startActivity(new Intent(this, SubGameActivity.class))
        );

        btnMultiplicacion.setOnClickListener(v ->
                startActivity(new Intent(this, MultiGameActivity.class))
        );

        btnDivision.setOnClickListener(v ->
                startActivity(new Intent(this, DivGameActivity.class))
        );

        btnContar.setOnClickListener(v ->
                startActivity(new Intent(this, CountGameActivity.class))
        );

        btnNumeroFaltante.setOnClickListener(v ->
                startActivity(new Intent(this, MissingNumberActivity.class))
        );

        btnColorGame.setOnClickListener(v ->
                startActivity(new Intent(this, ColorGameActivity.class))
        );

        btnShapeGame.setOnClickListener(v ->
                startActivity(new Intent(this, ShapeGameActivity.class))
        );

    }
}


