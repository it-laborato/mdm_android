package com.hmdm.launcher.db;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.hmdm.launcher.json.RemoteLogConfig;
import com.hmdm.launcher.json.RemoteLogItem;

import java.util.List;

public class LogConfigTable {
    private static final String CREATE_TABLE =
            "CREATE TABLE log_rules (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "packageId TEXT, " +
                    "level INTEGER, " +
                    "filter TEXT " +
                    ")";
    private static final String DELETE_ALL =
            "DELETE FROM log_rules";
    private static final String INSERT_RULE =
            "INSERT OR IGNORE INTO log_rules(packageId, level, filter) VALUES (?, ?, ?)";
    private static final String FIND_MATCHING =
            "SELECT * FROM log_rules WHERE packageId = ? AND level >= ? AND (filter IS NULL OR filter = '' OR ? LIKE ('%' || filter || '%')) LIMIT 1";

    public static String getCreateTableSql() {
        return CREATE_TABLE;
    }

    public static void replaceAll(SQLiteDatabase db, List<RemoteLogConfig> items) {
        db.beginTransaction();
        try {
            db.execSQL(DELETE_ALL);
            for (RemoteLogConfig item : items) {
                db.execSQL(INSERT_RULE, new String[]{
                        item.getPackageId(),
                        Integer.toString(item.getLogLevel()),
                        item.getFilter()
                });
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public static boolean match(SQLiteDatabase db, RemoteLogItem item) {
        Cursor cursor = db.rawQuery(FIND_MATCHING, new String[] {
                item.getPackageId(),
                Integer.toString(item.getLogLevel()),
                item.getMessage()
        });
        boolean ret = cursor.moveToFirst();
        cursor.close();
        return ret;
    }
}
