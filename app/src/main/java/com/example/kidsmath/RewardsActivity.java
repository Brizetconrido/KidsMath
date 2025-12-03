package com.example.kidsmath;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RewardsActivity extends AppCompatActivity {

    TextView txtRewardMessage, txtRewardDescription, txtTotalPoints;
    LinearLayout layoutRewardMessage;
    Button btnBackReward, btnResetReward;

    DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        db = new DatabaseManager(this);

        // Conexión con XML
        txtRewardMessage = findViewById(R.id.txtRewardMessage);
        txtRewardDescription = findViewById(R.id.txtRewardDescription);
        txtTotalPoints = findViewById(R.id.txtTotalPoints);
        layoutRewardMessage = findViewById(R.id.layoutRewardMessage);

        btnBackReward = findViewById(R.id.btnBackReward);
        btnResetReward = findViewById(R.id.btnResetReward);

        showRewardMessage();

        btnBackReward.setOnClickListener(v -> finish());

        // 🔹 BOTÓN: borrar recompensa sin borrar puntos reales
        btnResetReward.setOnClickListener(v -> {

            // 1) Resetear solo recompensa
            db.resetRewardStatus();

            // 2) Mostrar 0 visualmente (pero no borrar BD)
            txtTotalPoints.setText("Puntos Totales: 0");

            // 3) Ocultar el mensaje de recompensa
            layoutRewardMessage.setVisibility(View.GONE);

            txtRewardMessage.setText(
                    "¡Sigue jugando! Necesitas 30 puntos para ganar una recompensa.\n\n" +
                            "Pídele a tu mamá que revise esta pantalla cuando llegues a los 30."
            );

            txtRewardDescription.setText("");
        });
    }

    private void showRewardMessage() {

        int totalPoints = db.getTotalPoints();
        txtTotalPoints.setText("Puntos Totales: " + totalPoints);

        if (totalPoints < 30) {

            layoutRewardMessage.setVisibility(View.GONE);

            txtRewardMessage.setText(
                    "¡Sigue jugando! Necesitas 30 puntos para ganar una recompensa.\n\n" +
                            "Pídele a tu mamá que revise esta pantalla cuando llegues a los 30."
            );

        } else {

            layoutRewardMessage.setVisibility(View.VISIBLE);

            txtRewardMessage.setText("🎉 ¡FELICIDADES! 🎉");
            txtRewardDescription.setText(
                    "Has alcanzado " + totalPoints + " puntos.\n\n" +
                            "⭐ HOY TU PREMIO ES: ¡Que te compren un dulce! ⭐\n\n" +
                            "Muéstrale esto a tu mamá.\n" +
                            "¡Lo estás haciendo excelente!"
            );
        }
    }
}

