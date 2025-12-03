package com.example.kidsmath;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    TextView txtSuma, txtResta, txtMulti, txtDiv, txtTotal, txtCount, txtMissing;
    Button btnBackScore;
    DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        txtSuma = findViewById(R.id.txtScoreSum);
        txtResta = findViewById(R.id.txtScoreSub);
        txtMulti = findViewById(R.id.txtScoreMulti);
        txtDiv = findViewById(R.id.txtScoreDiv);
        txtTotal = findViewById(R.id.txtTotalScore);
        txtCount = findViewById(R.id.txtScoreCount);
        txtMissing = findViewById(R.id.txtScoreMissing);
        btnBackScore = findViewById(R.id.btnBackScore);

        db = new DatabaseManager(this);

        loadScores();

        //  BOTÓN VOLVER
        btnBackScore.setOnClickListener(v -> {
            Intent i = new Intent(ScoreActivity.this, MainMenuActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void loadScores() {

        int sSum = db.getScoreByGame("sumas");
        int sRes = db.getScoreByGame("restas");
        int sMul = db.getScoreByGame("multiplicaciones");
        int sDiv = db.getScoreByGame("divisiones");
        int sCount = db.getScoreByGame("contar");
        int sMissing = db.getScoreByGame("numero_faltante");

        int total = db.getTotalPoints();

        txtSuma.setText("Puntaje: " + sSum);
        txtResta.setText("Puntaje: " + sRes);
        txtMulti.setText("Puntaje: " + sMul);
        txtDiv.setText("Puntaje: " + sDiv);
        txtCount.setText("Puntaje: " + sCount);
        txtMissing.setText("Puntaje: " + sMissing);
        txtTotal.setText("Puntos Totales: " + total);
    }
}
