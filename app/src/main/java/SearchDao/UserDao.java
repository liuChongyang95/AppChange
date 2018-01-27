package SearchDao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;


import java.util.TooManyListenersException;

import JavaBean.User;
import Util.Staticfinal_Value;
import Database.DBHelper;

/**
 * Created by Administrator on 2018/1/7.
 * <p>
 * 登录的一下方法暂时在这里面吧
 */


public class UserDao {

    private DBHelper UserdbHelper;
    private Context context;
    private String userInfo;


    private Staticfinal_Value sfv;

    public UserDao(Context context) {
        sfv = new Staticfinal_Value();
        UserdbHelper = new DBHelper(context, "DApp.db", null, sfv.staticVersion());
    }

    public boolean login(String username, String password) {
        SQLiteDatabase UsersDb = UserdbHelper.getReadableDatabase();
        String sql = "select * from Login where Username=? and  password=?";
        Cursor cursor = UsersDb.rawQuery(sql, new String[]{username, password});
        if (cursor.moveToFirst() == true) {
            cursor.close();
            return true;
        } else
            return false;
    }

    public String failedCause(String username, String password) {
        SQLiteDatabase UsersDb = UserdbHelper.getReadableDatabase();
        String sql_1 = "select * from Login where Username=?";
        String failed_cause;
        Cursor cursor = UsersDb.rawQuery(sql_1, new String[]{username});
        if (cursor.moveToFirst() == true) {
            cursor.close();
            String sql_2 = "select * from Login where password=? and Username =?";
            Cursor cursor1 = UsersDb.rawQuery(sql_2, new String[]{username, password});
            if (cursor1.moveToFirst() == false) {
                cursor1.close();
                failed_cause = "密码错误";
            } else
                failed_cause = null;
        } else
            failed_cause = "没有该用户";

        UsersDb.close();
        return failed_cause;
    }

    public String getUserName(String userId) {
        userInfo = null;
        SQLiteDatabase UsersDb = UserdbHelper.getReadableDatabase();
        String sql_1 = "select User_Nickname from User where User_id =?";
        Cursor cursor = UsersDb.rawQuery(sql_1, new String[]{userId});
        if (cursor.moveToFirst() == true) {
            userInfo = cursor.getString(cursor.getColumnIndex("User_Nickname"));
        }
        UsersDb.close();
        cursor.close();
        return userInfo;
    }

    public String getUserId(String username) {
        userInfo = null;
        SQLiteDatabase UsersDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_id from Login where Username= ?";
        Cursor cursor = UsersDb.rawQuery(sql, new String[]{username});
        if (cursor.moveToFirst() == true)
            userInfo = cursor.getString(cursor.getColumnIndex("User_id"));
        cursor.close();
        UsersDb.close();
        return userInfo;
    }

    public Drawable getUser_Photo(String userId) {
        Drawable photo = null;
        SQLiteDatabase UsersDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_Photo from User where User_id=?";
        Cursor cursor = UsersDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst() == true) {
            byte[] bytes = cursor.getBlob(cursor.getColumnIndex("User_Photo"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
            photo = bitmapDrawable;
        }
        cursor.close();
        UsersDb.close();
        return photo;
    }

    public String getIntensity(String careerName, String shape) {
        userInfo = null;
        SQLiteDatabase UsersDb = UserdbHelper.getReadableDatabase();
        String sql = "select Intensity from Career where Career=? and Shape=?";
        Cursor cursor = UsersDb.rawQuery(sql, new String[]{careerName, shape});
        if (cursor.moveToFirst() == true) {
            userInfo = cursor.getString(cursor.getColumnIndex("Intensity"));
        }
        cursor.close();
        UsersDb.close();
        return userInfo;
    }

    public String getNickname(String userId) {
        userInfo = null;
        SQLiteDatabase UsersDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_Nickname from User where User_id= ?";
        Cursor cursor = UsersDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst() == true)
            userInfo = cursor.getString(cursor.getColumnIndex("User_Nickname"));
        cursor.close();
        UsersDb.close();
        return userInfo;

    }

    public String getSex(String userId) {
        userInfo = null;
        SQLiteDatabase UserDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_Sex from User where User_id=?";
        Cursor cursor = UserDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst() == true)
            userInfo = cursor.getString(cursor.getColumnIndex("User_Sex"));
        cursor.close();
        UserDb.close();
        return userInfo;
    }

    public String getBirth(String userId) {
        userInfo = null;
        SQLiteDatabase UserDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_Birth from User where User_id=?";
        Cursor cursor = UserDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst() == true)
            userInfo = cursor.getString(cursor.getColumnIndex("User_Birth"));
        cursor.close();
        UserDb.close();
        return userInfo;
    }

    public String getTall(String userId) {
        userInfo = null;
        SQLiteDatabase UserDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_Tall from User where User_id=?";
        Cursor cursor = UserDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst() == true)
            userInfo = cursor.getString(cursor.getColumnIndex("User_Tall"));
        cursor.close();
        UserDb.close();
        return userInfo;
    }

    public String getWeight(String userId) {
        userInfo = null;
        SQLiteDatabase UserDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_Real_weight from User where User_id=?";
        Cursor cursor = UserDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst())
            userInfo = cursor.getString(cursor.getColumnIndex("User_Real_weight"));
        cursor.close();
        UserDb.close();
        return userInfo;
    }

    public String getExpect_weight(String userId) {
        userInfo = null;
        SQLiteDatabase UserDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_Expect_weight from User where User_id=?";
        Cursor cursor = UserDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst())
            userInfo = cursor.getString(cursor.getColumnIndex("User_Expect_weight"));
        cursor.close();
        UserDb.close();
        return userInfo;
    }

    public String getCareer(String userId) {
        userInfo = null;
        SQLiteDatabase UserDb = UserdbHelper.getReadableDatabase();
        String sql = "select Career from User where User_id=?";
        Cursor cursor = UserDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst())
            userInfo = cursor.getString(cursor.getColumnIndex("Career"));
        cursor.close();
        UserDb.close();
        return userInfo;
    }


    public String getShape(String userId) {
        userInfo = null;
        SQLiteDatabase UserDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_Shape from User where User_id=?";
        Cursor cursor = UserDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst())
            userInfo = cursor.getString(cursor.getColumnIndex("User_Shape"));
        cursor.close();
        UserDb.close();
        return userInfo;
    }

    public void changNickname(String userId, String userNickname) {
        userInfo = null;
        SQLiteDatabase UserDb = UserdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("User_Nickname", userNickname);
        UserDb.update("User", values, "User_id = ?", new String[]{userId});
    }
}
