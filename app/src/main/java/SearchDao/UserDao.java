package SearchDao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.TooManyListenersException;

import Database.DBHelper;

/**
 * Created by Administrator on 2018/1/7.
 */

public class UserDao {

    private DBHelper UserdbHelper;
    private Context context;

    public UserDao(Context context) {
        UserdbHelper = new DBHelper(context, "DApp.db", null, 3);
    }

    public boolean login(String username, String password) {
        SQLiteDatabase UsersDb = UserdbHelper.getReadableDatabase();
        String sql = "select * from User where name=? and  password=?";
        Cursor cursor = UsersDb.rawQuery(sql, new String[]{username, password});
        if (cursor.moveToFirst() == true) {
            cursor.close();
            return true;
        } else
            return false;
    }

    public String failedCause(String username, String password) {
        SQLiteDatabase UsersDb = UserdbHelper.getReadableDatabase();
        String sql_1 = "select * from User where name=?";
        String failed_cause;
        Cursor cursor = UsersDb.rawQuery(sql_1, new String[]{username});
        if (cursor.moveToFirst() == true) {
            cursor.close();
            String sql_2 = "select * from User where password=? and name =?";
            Cursor cursor1 = UsersDb.rawQuery(sql_2, new String[]{username, password});
            if (cursor1.moveToFirst() == false) {
                cursor1.close();
                failed_cause = "密码错误";
            } else
                failed_cause = null;
        } else
            failed_cause = "没有该用户";

        return failed_cause;
    }

}
