package com.example.kidsmath;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {

    private DBHelper dbHelper;

    public DatabaseManager(Context context) {
        dbHelper = new DBHelper(context);
    }

    public Cursor getUser() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM user WHERE id = 1", null);
    }

    public void updateUser(String name, int age, String avatar) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", age);
        values.put("avatar", avatar);

        db.update("user", values, "id = 1", null);
    }


    // Agregar puntaje por juego
    public void addScore(String game, int points) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("game_type", game);
        values.put("points", points);
        values.put("date", String.valueOf(System.currentTimeMillis()));

        db.insert("scores", null, values);

        // Sumar a puntos totales del usuario
        db.execSQL("UPDATE user SET points_total = points_total + " + points + " WHERE id = 1");
    }

    // Puntaje total global
    public int getTotalPoints() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT points_total FROM user WHERE id = 1",
                null
        );

        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }

        return 0;
    }

    // Puntaje por tipo de juego
    public int getScoreByGame(String game) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT SUM(points) FROM scores WHERE game_type = ?",
                new String[]{ game }
        );

        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }

        return 0;
    }

    public Cursor getRewards() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM rewards", null);
    }

    public void unlockReward(int rewardId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("obtained", 1);

        db.update("rewards", values, "id = ?", new String[]{String.valueOf(rewardId)});
    }
}
