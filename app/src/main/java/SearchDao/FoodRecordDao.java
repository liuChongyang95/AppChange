package SearchDao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.NumberFormat;
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
    private String recordIntake;
    private int recordUnit;
    private String recordClass;
    private String recordFoodId;
    private FoodDao foodDao;
    private String foodName;
    private String itemEnergy;
    private int item_id;
    private String foodEnergy;
    private NumberFormat numberFormat;

    public FoodRecordDao(Context context) {
        sfv = new Staticfinal_Value();
        dbHelper = new DBHelper(context, "DApp.db", null, sfv.staticVersion());
        foodDao = new FoodDao(context);
    }

    //    生成食物历史记录表
    public List<UserFood> getFoodrecord(String userID) {
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        List<UserFood> foodRecord = new ArrayList<>();
        sqlDB = dbHelper.getReadableDatabase();
        String sql = "select * from UserFood where User_id = ? order by _id desc";
        Cursor cursor = sqlDB.rawQuery(sql, new String[]{userID});
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                recordDate = cursor.getString(cursor.getColumnIndex("Food_date"));
                recordFoodId = cursor.getString(cursor.getColumnIndex("Food_id"));
//                单品名称
                foodName = foodDao.find_Name(recordFoodId);
//                单品百g能量
                itemEnergy = foodDao.find_energy(foodName);
                recordIntake = cursor.getString(cursor.getColumnIndex("Food_intake"));
                recordUnit = cursor.getInt(cursor.getColumnIndex("Food_unit"));
                switch (recordUnit) {
                    case 0:
                        foodEnergy = numberFormat.format(Float.valueOf(
                                numberFormat.format(Float.valueOf(
                                        recordIntake) / 100)) * Float.valueOf(itemEnergy)).replace(",", "") + " 千卡";
                        break;
//                        小
                    case 1:
                        foodEnergy = numberFormat.format(Float.valueOf(
                                numberFormat.format(Float.valueOf(
                                        recordIntake) / 100 * 106.4)) * Float.valueOf(itemEnergy)).replace(",", "") + " 千卡";
                        break;
//                        中
                    case 2:
                        foodEnergy = numberFormat.format(Float.valueOf(
                                numberFormat.format(Float.valueOf(
                                        recordIntake) / 100 * 159.6)) * Float.valueOf(itemEnergy)).replace(",", "") + " 千卡";
                        break;
//                        大
                    case 3:
                        foodEnergy = numberFormat.format(Float.valueOf(
                                numberFormat.format(Float.valueOf(
                                        recordIntake) / 100 * 288.8)) * Float.valueOf(itemEnergy)).replace(",", "") + " 千卡";
                        break;
                }
                recordClass = cursor.getString(cursor.getColumnIndex("Food_class"));
                item_id = cursor.getInt(cursor.getColumnIndex("_id"));
                foodRecord.add(new UserFood(recordDate, recordClass, foodName, recordIntake, item_id, foodEnergy, recordFoodId, recordUnit));
            }
            cursor.close();
        }
        dbHelper.close();
        sqlDB.close();
        return foodRecord;
    }

    //    单位标记查询
    public int getRecordUnit(String item_id) {
        sqlDB = dbHelper.getReadableDatabase();
        String sql = "select Food_unit from UserFood where _id=?";
        Cursor cursor = sqlDB.rawQuery(sql, new String[]{item_id});
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                recordUnit = cursor.getInt(cursor.getColumnIndex("Food_unit"));
            }
            cursor.close();
        }
        dbHelper.close();
        sqlDB.close();
        return recordUnit;
    }
}

