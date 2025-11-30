package com.example.kidsmath;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "kidsmath.db";
    public static final int DATABASE_VERSION = 2;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Tabla Usuario
        db.execSQL("CREATE TABLE user (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "age INTEGER," +
                "avatar TEXT," +
                "points_total INTEGER DEFAULT 0" +
                ");");

        // Tabla Puntajes por juego
        db.execSQL("CREATE TABLE scores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "game_type TEXT," +
                "points INTEGER," +
                "date TEXT" +
                ");");

        // Tabla Recompensas
        db.execSQL("CREATE TABLE rewards (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "description TEXT," +
                "image TEXT," +
                "obtained INTEGER DEFAULT 0" +
                ");");

        // Usuario inicial
        db.execSQL("INSERT INTO user (name, age, avatar, points_total) " +
                "VALUES ('Invitado', 0, 'avatar1', 0)");

        // Recompensas iniciales
        db.execSQL("INSERT INTO rewards (name, description, image) VALUES" +
                "('Estrella Dorada', 'Al obtener 50 puntos', 'star_gold')," +
                "('Medalla Súper Niño', 'Por 10 respuestas correctas', 'medal_child')," +
                "('Genio Matemático', 'Al llegar a 100 puntos', 'genius')");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS scores");
        db.execSQL("DROP TABLE IF EXISTS rewards");
        onCreate(db);
    }


    public void updateUser(String name, int age, String avatar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();

        v.put("name", name);
        v.put("age", age);
        v.put("avatar", avatar);

        db.update("user", v, "id = 1", null);
    }

    public Cursor getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM user WHERE id = 1", null);
    }


    public void addScore(String game, int points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();

        v.put("game_type", game);
        v.put("points", points);
        v.put("date", String.valueOf(System.currentTimeMillis()));

        db.insert("scores", null, v);

        // Actualizar puntos totales
        db.execSQL("UPDATE user SET points_total = points_total + " + points + " WHERE id = 1");
    }

    public int getTotalPoints() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT points_total FROM user WHERE id = 1", null);

        if (c.moveToFirst()) {
            return c.getInt(0);
        }
        return 0;
    }

    public int getScoreByGame(String game) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT SUM(points) FROM scores WHERE game_type = ?",
                new String[]{ game }
        );

        if (c.moveToFirst()) {
            return c.getInt(0);
        }
        return 0;
    }


    public Cursor getRewards() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM rewards", null);
    }

    public void unlockReward(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE rewards SET obtained = 1 WHERE id = " + id);
    }
}

