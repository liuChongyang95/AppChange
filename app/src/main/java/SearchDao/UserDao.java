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


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
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
        if (cursor.moveToFirst()) {
            cursor.close();
            UserdbHelper.close();
            UsersDb.close();
            return true;
        } else {
            UserdbHelper.close();
            UsersDb.close();
            return false;
        }
    }

    public String failedCause(String username, String password) {
        SQLiteDatabase UsersDb = UserdbHelper.getReadableDatabase();
        String sql_1 = "select * from Login where Username=?";
        String failed_cause;
        Cursor cursor = UsersDb.rawQuery(sql_1, new String[]{username});
        if (cursor.moveToFirst()) {
            cursor.close();
            String sql_2 = "select * from Login where password=? and Username =?";
            Cursor cursor1 = UsersDb.rawQuery(sql_2, new String[]{username, password});
            if (!cursor1.moveToFirst()) {
                cursor1.close();
                failed_cause = "密码错误";
            } else
                failed_cause = null;
        } else
            failed_cause = "没有该用户";
        UserdbHelper.close();
        UsersDb.close();
        return failed_cause;
    }

    public String getUserName(String userId) {
        SQLiteDatabase UsersDb = UserdbHelper.getReadableDatabase();
        String sql_1 = "select User_Nickname from User where User_id =?";
        Cursor cursor = UsersDb.rawQuery(sql_1, new String[]{userId});
        if (cursor.moveToFirst()) {
            userInfo = cursor.getString(cursor.getColumnIndex("User_Nickname"));
        }
        UserdbHelper.close();
        UsersDb.close();
        cursor.close();
        return userInfo;
    }

    public String getUserId(String username) {
        userInfo = null;
        SQLiteDatabase UsersDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_id from Login where Username= ?";
        Cursor cursor = UsersDb.rawQuery(sql, new String[]{username});
        if (cursor.moveToFirst())
            userInfo = cursor.getString(cursor.getColumnIndex("User_id"));
        UserdbHelper.close();
        cursor.close();
        UsersDb.close();
        return userInfo;
    }

    public Drawable getUser_Photo(String userId) {
        Drawable photo = null;
        SQLiteDatabase UsersDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_Photo from User where User_id=?";
        Cursor cursor = UsersDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst()) {
            byte[] bytes = cursor.getBlob(cursor.getColumnIndex("User_Photo"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
            photo = bitmapDrawable;
        }
        UserdbHelper.close();
        cursor.close();
        UsersDb.close();
        return photo;
    }

    public String getIntensity(String careerName) {
        SQLiteDatabase UsersDb = UserdbHelper.getReadableDatabase();
        String sql = "select Intensity from Career where Career=?";
        Cursor cursor = UsersDb.rawQuery(sql, new String[]{careerName});
        if (cursor.moveToFirst()) {
            userInfo = cursor.getString(cursor.getColumnIndex("Intensity"));
        }
        UserdbHelper.close();
        cursor.close();
        UsersDb.close();
        return userInfo;
    }

    public String getNickname(String userId) {
        SQLiteDatabase UsersDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_Nickname from User where User_id= ?";
        Cursor cursor = UsersDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst())
            userInfo = cursor.getString(cursor.getColumnIndex("User_Nickname"));
        UserdbHelper.close();
        cursor.close();
        UsersDb.close();
        return userInfo;

    }

    public String getSex(String userId) {
        SQLiteDatabase UserDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_Sex from User where User_id=?";
        Cursor cursor = UserDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst())
            userInfo = cursor.getString(cursor.getColumnIndex("User_Sex"));
        UserdbHelper.close();
        cursor.close();
        UserDb.close();
        return userInfo;
    }

    public String getBirth(String userId) {
        SQLiteDatabase UserDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_Birth from User where User_id=?";
        Cursor cursor = UserDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst())
            userInfo = cursor.getString(cursor.getColumnIndex("User_Birth"));
        UserdbHelper.close();
        cursor.close();
        UserDb.close();
        return userInfo;
    }

    public String getTall(String userId) {
        SQLiteDatabase UserDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_Tall from User where User_id=?";
        Cursor cursor = UserDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst())
            userInfo = cursor.getString(cursor.getColumnIndex("User_Tall"));
        UserdbHelper.close();
        cursor.close();
        UserDb.close();
        return userInfo;
    }

    public String getWeight(String userId) {
        SQLiteDatabase UserDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_Real_weight from User where User_id=?";
        Cursor cursor = UserDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst())
            userInfo = cursor.getString(cursor.getColumnIndex("User_Real_weight"));
        UserdbHelper.close();
        cursor.close();
        UserDb.close();
        return userInfo;
    }

    public String getExpect_weight(String userId) {
        SQLiteDatabase UserDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_Expect_weight from User where User_id=?";
        Cursor cursor = UserDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst())
            userInfo = cursor.getString(cursor.getColumnIndex("User_Expect_weight"));
        cursor.close();
        UserdbHelper.close();
        UserDb.close();
        return userInfo;
    }

    public String getCareer(String userId) {
        SQLiteDatabase UserDb = UserdbHelper.getReadableDatabase();
        String sql = "select Career from User where User_id=?";
        Cursor cursor = UserDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst())
            userInfo = cursor.getString(cursor.getColumnIndex("Career"));
        cursor.close();
        UserdbHelper.close();
        UserDb.close();
        return userInfo;
    }


    public String getShape(String userId) {
        SQLiteDatabase UserDb = UserdbHelper.getReadableDatabase();
        String sql = "select User_Shape from User where User_id=?";
        Cursor cursor = UserDb.rawQuery(sql, new String[]{userId});
        if (cursor.moveToFirst())
            userInfo = cursor.getString(cursor.getColumnIndex("User_Shape"));
        cursor.close();
        UserDb.close();
        UserdbHelper.close();
        return userInfo;
    }

    public void changNickname(String userId, String userNickname) {
        SQLiteDatabase UserDb = UserdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("User_Nickname", userNickname);
        UserDb.update("User", values, "User_id = ?", new String[]{userId});
        UserDb.close();
        UserdbHelper.close();
    }

    public void changeUser_Photo(String userId, Drawable userPhoto) throws IOException {
        SQLiteDatabase UserDb = UserdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("User_Photo", getPicture(userPhoto));
        UserDb.update("User", values, "User_id=?", new String[]{userId});
        UserDb.close();
        UserdbHelper.close();
    }

    public void changeSex(String userId, String userSex) {
        SQLiteDatabase UserDb = UserdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("User_Sex", userSex);
        UserDb.update("User", values, "User_id = ?", new String[]{userId});
        UserDb.close();
        UserdbHelper.close();
    }

    public void changeBirth(String userId, java.sql.Date userBirth) {
        SQLiteDatabase UserDb = UserdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("User_Birth", String.valueOf(userBirth));
        UserDb.update("User", values, "User_id = ?", new String[]{userId});
        UserDb.close();
        UserdbHelper.close();
    }

    public void changeTall(String userId, String userTall) {
        SQLiteDatabase UserDb = UserdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("User_Tall", userTall);
        UserDb.update("User", values, "User_id = ?", new String[]{userId});
        UserDb.close();
        UserdbHelper.close();
    }

    public void changeWeight(String userId, String userWeight) {
        SQLiteDatabase UserDb = UserdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("User_Real_weight", userWeight);
        UserDb.update("User", values, "User_id = ?", new String[]{userId});
        UserDb.close();
        UserdbHelper.close();
    }

    public void changeExpectWeight(String userId, Float userEWeight) {
        SQLiteDatabase UserDb = UserdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("User_Expect_weight", String.valueOf(userEWeight));
        UserDb.update("User", values, "User_id = ?", new String[]{userId});
        UserDb.close();
        UserdbHelper.close();
    }

    public void changeShape(String userId, String userShape) {
        SQLiteDatabase UserDb = UserdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("User_Shape", userShape);
        UserDb.update("User", values, "User_id = ?", new String[]{userId});
        UserDb.close();
        UserdbHelper.close();
    }

    public void changeCareer(String userId, String userCareer) {
        SQLiteDatabase UserDb = UserdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Career", userCareer);
        UserDb.update("User", values, "User_id = ?", new String[]{userId});
        UserDb.close();
        UserdbHelper.close();
    }

    public void changeIntensity(String userId, String userIntensity) {
        SQLiteDatabase UserDb = UserdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("User_Intensity", userIntensity);
        UserDb.update("User", values, "User_id = ?", new String[]{userId});
        UserDb.close();
        UserdbHelper.close();
    }

    public void changePassword(String userId, String userPassword) {
        SQLiteDatabase UserDb = UserdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", userPassword);
        UserDb.update("Login", values, "User_id = ?", new String[]{userId});
        UserDb.close();
        UserdbHelper.close();
    }

    private byte[] getPicture(Drawable drawable) throws IOException {
        if (drawable == null) {
            return null;
        }
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        byte[] ba = os.toByteArray();
        os.close();
        return ba;
    }
}
