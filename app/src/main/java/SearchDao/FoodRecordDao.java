package SearchDao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Database.DBHelper;
import JavaBean.Food;
import JavaBean.UserFood;
import Util.Staticfinal_Value;

public class FoodRecordDao {
    private Staticfinal_Value sfv;
    private DBHelper dbHelper;
    private SQLiteDatabase sqlDB;
    private String recordDate;
    private String recordContent;
    private String recordIntake;
    private String recordClass;
    private String recordFoodId;
    private FoodDao foodDao;
    private String foodName;
    private int item_id;


    public FoodRecordDao(Context context) {
        sfv = new Staticfinal_Value();
        dbHelper = new DBHelper(context, "DApp.db", null, sfv.staticVersion());
        foodDao = new FoodDao(context);
    }

    public List<UserFood> getFoodrecord(String userID) {
        List<UserFood> foodRecord = new ArrayList<>();
        sqlDB = dbHelper.getWritableDatabase();
        String sql = "select * from UserFood where User_id = ? order by _id desc";
        Cursor cursor = sqlDB.rawQuery(sql, new String[]{userID});
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                recordDate = cursor.getString(cursor.getColumnIndex("Food_date"));
                recordFoodId = cursor.getString(cursor.getColumnIndex("Food_id"));
                foodName = foodDao.find_Name(recordFoodId);
                recordIntake = cursor.getString(cursor.getColumnIndex("Food_intake"));
                recordClass = cursor.getString(cursor.getColumnIndex("Food_class"));
                item_id = cursor.getInt(cursor.getColumnIndex("_id"));
                foodRecord.add(new UserFood(recordDate, recordClass, foodName, recordIntake, item_id));
            }
            cursor.close();
        }
        dbHelper.close();
        sqlDB.close();
        return foodRecord;
    }
}
