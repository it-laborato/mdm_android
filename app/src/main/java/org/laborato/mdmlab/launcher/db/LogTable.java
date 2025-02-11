package org.laborato.mdmlab.launcher.db;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.laborato.mdmlab.launcher.json.RemoteLogItem;

import java.util.LinkedList;
import java.util.List;

public class LogTable {
    private static final String CREATE_TABLE =
            "CREATE TABLE logs (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "ts INTEGER, " +
                    "level INTEGER, " +
                    "packageId TEXT, " +
                    "message TEXT" +
                    ")";
    private static final String SELECT_LAST_LOGS =
            "SELECT * FROM logs ORDER BY ts LIMIT ?";
    private static final String INSERT_LOG =
            "INSERT OR IGNORE INTO logs(ts, level, packageId, message) VALUES (?, ?, ?, ?)";
    private static final String DELETE_FROM_LOGS =
            "DELETE FROM logs WHERE _id=?";
    private static final String DELETE_OLD_LOGS =
            "DELETE FROM logs WHERE ts < ?";

    public static String getCreateTableSql() {
        return CREATE_TABLE;
    }

    public static void insert(SQLiteDatabase db, RemoteLogItem item) {
        try {
            db.execSQL(INSERT_LOG, new String[]{
                    Long.toString(item.getTimestamp()),
                    Integer.toString(item.getLogLevel()),
                    item.getPackageId(),
                    item.getMessage()
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteOldItems(SQLiteDatabase db) {
        long oldTs = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000L;
        try {
            db.execSQL(DELETE_FROM_LOGS, new String[]{
                    Long.toString(oldTs)
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(SQLiteDatabase db, List<RemoteLogItem> items) {
        db.beginTransaction();
        try {
            for (RemoteLogItem item : items) {
                db.execSQL(DELETE_FROM_LOGS, new String[]{
                        Long.toString(item.getId())
                });
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @SuppressLint("Range")
    public static List<RemoteLogItem> select(SQLiteDatabase db, int limit) {
        Cursor cursor = db.rawQuery( SELECT_LAST_LOGS, new String[] {
            Integer.toString(limit)
        });
        List<RemoteLogItem> result = new LinkedList<>();

        boolean isDataNotEmpty = cursor.moveToFirst();
        while (isDataNotEmpty) {
            RemoteLogItem item = new RemoteLogItem();
            item.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            item.setTimestamp(cursor.getLong(cursor.getColumnIndex("ts")));
            item.setLogLevel(cursor.getInt(cursor.getColumnIndex("level")));
            item.setPackageId(cursor.getString(cursor.getColumnIndex("packageId")));
            item.setMessage(cursor.getString(cursor.getColumnIndex("message")));
            result.add(item);

            isDataNotEmpty = cursor.moveToNext();
        }
        cursor.close();

        return result;
    }
}
