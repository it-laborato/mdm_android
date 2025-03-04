package org.laborato.mdmlab.launcher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Next version should be 10 and versions must be increased by 10
    // to enable custom database changes
    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "mdmlab.launcher.sqlite";

    private static DatabaseHelper sInstance;

    private DatabaseHelper( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper instance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(LogTable.getCreateTableSql());
            db.execSQL(LogConfigTable.getCreateTableSql());
            db.execSQL(InfoHistoryTable.getCreateTableSql());
            db.execSQL(RemoteFileTable.getCreateTableSql());
            db.execSQL(LocationTable.getCreateTableSql());
            db.execSQL(DownloadTable.getCreateTableSql());
            db.setTransactionSuccessful();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.beginTransaction();
        try {
            if (oldVersion < 2 && newVersion >= 2) {
                db.execSQL(InfoHistoryTable.getCreateTableSql());
            }
            if (oldVersion < 3 && newVersion >= 3) {
                db.execSQL(RemoteFileTable.getCreateTableSql());
            }
            if (oldVersion < 4 && newVersion >= 4) {
                db.execSQL(InfoHistoryTable.getAlterTableAddMemoryTotalSql());
                db.execSQL(InfoHistoryTable.getAlterTableAddMemoryAvailableSql());
            }
            if (oldVersion < 5 && newVersion >= 5) {
                db.execSQL(LocationTable.getCreateTableSql());
            }
            if (oldVersion < 10 && newVersion >= 10) {
                db.execSQL(DownloadTable.getCreateTableSql());
            }
            db.setTransactionSuccessful();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
    }
}
