package com.whs.quizzappuni.Utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by krispin on 24.10.15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.whs.quizzappuni/databases/";
    private final Context context;
    private String dbName;
    protected SQLiteDatabase db;

    /**
     * Konstruktor - Bekommt eine Referenz auf den Context um auf den assets-Ordner zugreifen zu können
     *
     * @param context
     * @param name    Name der Datenbank
     */
    protected DBHelper(Context context, String name) {

        super(context, name, null, 1);
        this.context = context;
        this.dbName = name;
    }

    /**
     * Leere DB erzeugen und mit DB aus dem Assets-Ordner überschreiben.
     */
    protected void forceCreateDataBase() {

        //Leere DB erzeugen
        this.getReadableDatabase();

        try {
            copyDataBase();
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Kopieren der db " + dbName + ": " + e.getMessage());
        }
    }

    /**
     * Falls DB noch nicht existiert: Leere DB erzeugen und mit DB aus dem Assets-Ordner überschreiben.
     */
    protected void createDataBase() {

        boolean dbExists = checkDataBase();

        if (dbExists) {
            //nichts zu tun
        } else {

            //Leere DB erzeugen
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new RuntimeException("Fehler beim Kopieren der db " + dbName + ": " + e.getMessage());
            }
        }

    }

    /**
     * Check ob es die DB schon gibt. Dazu wird versucht, die DB zu öffnen
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String fullPath = DB_PATH + dbName;
            checkDB = SQLiteDatabase.openDatabase(fullPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            //DB existiert nocht nicht
        }

        if (checkDB != null) {
            //falls es die DB schon gibt, wieder schliessen
            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Kopiert die quizzdb aus dem assets-Ordner und überschreibt damit die zuvor erzeugte leere DB
     */
    private void copyDataBase() throws IOException {

        //qizzdb als InputStream öffnen
        InputStream inputStream = context.getAssets().open(dbName);

        String fileName = DB_PATH + dbName;

        //leere db als OutputStream öffnen
        OutputStream output = new FileOutputStream(fileName);

        //überschreiben
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        //Streams schliessen
        output.flush();
        output.close();
        inputStream.close();

    }

    protected synchronized void openReadOnly() throws SQLException {

        if (db != null && db.isOpen()) {

            //Nothing to do
            return;

        } else {
            String fullPath = DB_PATH + dbName;
            db = SQLiteDatabase.openDatabase(fullPath, null, SQLiteDatabase.OPEN_READONLY);
        }

    }

    protected synchronized void openReadWrite() throws SQLException {

        if (null != db && db.isOpen() && !db.isReadOnly()) {

            //Nothing to do
            return;

        } else {
            String fullPath = DB_PATH + dbName;
            db = SQLiteDatabase.openDatabase(fullPath, null, SQLiteDatabase.OPEN_READWRITE);
        }


    }


    @Override
    public synchronized void close() {

        if (db != null && db.isOpen()) {
            db.close();
        }

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
