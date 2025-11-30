package com.example.kidsmath;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.Arrays;

public class RecorderActivity extends AppCompatActivity {

    Button btnRecord, btnPlay;
    MediaRecorder recorder;
    MediaPlayer player;

    boolean isRecording = false;
    String outputFile;

    LinearLayout listRecordingsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);

        btnRecord = findViewById(R.id.btnRecord);
        btnPlay = findViewById(R.id.btnPlay);
        listRecordingsLayout = findViewById(R.id.listRecordings);

        File folder = new File(getExternalFilesDir(null), "recordings");
        if (!folder.exists()) folder.mkdirs();

        outputFile = folder.getAbsolutePath() + "/grabacion_" + System.currentTimeMillis() + ".mp4";

        btnRecord.setOnClickListener(v -> {
            if (!hasPermission()) {
                requestPermission();
                return;
            }

            if (isRecording) stopRecording();
            else startRecording();
        });

        btnPlay.setOnClickListener(v -> playLastRecording());

        loadRecordings();
    }

    private void startRecording() {
        try {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            recorder.setOutputFile(outputFile);

            recorder.prepare();
            recorder.start();

            isRecording = true;
            btnRecord.setText("DETENER");

            Toast.makeText(this, "Grabando...", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Error al grabar", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        try {
            recorder.stop();
            recorder.release();
            recorder = null;

            isRecording = false;
            btnRecord.setText("GRABAR");

            Toast.makeText(this, "Guardado:\n" + outputFile, Toast.LENGTH_LONG).show();

            loadRecordings();

            // generar nuevo archivo para siguiente grabación
            outputFile = new File(getExternalFilesDir(null), "recordings/grabacion_" + System.currentTimeMillis() + ".mp4").getAbsolutePath();

        } catch (Exception e) {
            Toast.makeText(this, "Error al detener", Toast.LENGTH_SHORT).show();
        }
    }

    private void playLastRecording() {
        File folder = new File(getExternalFilesDir(null), "recordings");
        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            Toast.makeText(this, "No hay grabaciones", Toast.LENGTH_SHORT).show();
            return;
        }

        File last = files[files.length - 1];

        try {
            player = new MediaPlayer();
            player.setDataSource(last.getAbsolutePath());
            player.prepare();
            player.start();

            Toast.makeText(this, "Reproduciendo última grabación...", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Error al reproducir", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadRecordings() {
        listRecordingsLayout.removeAllViews();

        File folder = new File(getExternalFilesDir(null), "recordings");
        File[] files = folder.listFiles();

        if (files == null || files.length == 0) return;

        Arrays.sort(files);

        for (File f : files) {
            addRecordingItem(f);
        }
    }

    private void addRecordingItem(File file) {
        LinearLayout item = new LinearLayout(this);
        item.setOrientation(LinearLayout.HORIZONTAL);
        item.setPadding(10,10,10,10);

        TextView name = new TextView(this);
        name.setText(file.getName());
        name.setTextSize(18f);
        name.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        Button play = new Button(this);
        play.setText("▶");
        play.setOnClickListener(v -> playFile(file));

        Button delete = new Button(this);
        delete.setText("🗑");
        delete.setOnClickListener(v -> deleteFile(file));

        item.addView(name);
        item.addView(play);
        item.addView(delete);

        listRecordingsLayout.addView(item);
    }

    private void playFile(File file) {
        try {
            player = new MediaPlayer();
            player.setDataSource(file.getAbsolutePath());
            player.prepare();
            player.start();
            Toast.makeText(this, "Reproduciendo...", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al reproducir", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteFile(File file) {
        if (file.delete()) {
            Toast.makeText(this, "Archivo eliminado", Toast.LENGTH_SHORT).show();
            loadRecordings();
        } else {
            Toast.makeText(this, "No se pudo eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO}, 2000);
    }
}
