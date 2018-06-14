package SearchDao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.math.BigDecimal;
import java.text.NumberFormat;

import Database.DBHelper;
import Util.Staticfinal_Value;

public class UserIntakeDao {
    private Staticfinal_Value sfv;
    private DBHelper dbHelper;
    private NumberFormat numberFormat;

    public UserIntakeDao(Context context) {
        sfv = new Staticfinal_Value();
        dbHelper = new DBHelper(context, "DApp.db", null, sfv.staticVersion());
    }

    //    用于食物试算等
    public String[] getFromUserIntake(String userId, String UIClass, String UIdate) {
        String[] UIenergy = new String[21];
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select * from UserIntake where User_id = ? and UI_class= ? and UI_date= ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{userId, UIClass, UIdate});
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                UIenergy[0] = cursor.getString(cursor.getColumnIndex("UI_energy"));//能量
                UIenergy[1] = cursor.getString(cursor.getColumnIndex("UI_protein"));//蛋白质
                UIenergy[2] = cursor.getString(cursor.getColumnIndex("UI_fat"));//脂肪
                UIenergy[3] = cursor.getString(cursor.getColumnIndex("UI_DF"));//膳食纤维
                UIenergy[4] = cursor.getString(cursor.getColumnIndex("UI_CH"));//碳水化物
                UIenergy[5] = cursor.getString(cursor.getColumnIndex("UI_water"));//水分
                UIenergy[6] = cursor.getString(cursor.getColumnIndex("UI_VA"));//维生素A
                UIenergy[7] = cursor.getString(cursor.getColumnIndex("UI_VB1"));//维生素B1
                UIenergy[8] = cursor.getString(cursor.getColumnIndex("UI_VB2"));//维生素B2
                UIenergy[9] = cursor.getString(cursor.getColumnIndex("UI_VB3"));//维生素B3
                UIenergy[10] = cursor.getString(cursor.getColumnIndex("UI_VE"));//维生素E
                UIenergy[11] = cursor.getString(cursor.getColumnIndex("UI_VC"));//维生素C
                UIenergy[12] = cursor.getString(cursor.getColumnIndex("UI_Fe"));//铁
                UIenergy[13] = cursor.getString(cursor.getColumnIndex("UI_Ga"));//钙
                UIenergy[14] = cursor.getString(cursor.getColumnIndex("UI_Na"));//钠
                UIenergy[15] = cursor.getString(cursor.getColumnIndex("UI_CLS"));//胆固醇
                UIenergy[16] = cursor.getString(cursor.getColumnIndex("UI_K"));//钾
                UIenergy[17] = cursor.getString(cursor.getColumnIndex("UI_Mg"));//镁
                UIenergy[18] = cursor.getString(cursor.getColumnIndex("UI_Zn"));//锌
                UIenergy[19] = cursor.getString(cursor.getColumnIndex("UI_P"));//磷
                UIenergy[20] = cursor.getString(cursor.getColumnIndex("UI_purine"));//嘌呤
            }
            cursor.close();
        }
        dbHelper.close();
        sqLiteDatabase.close();
        return UIenergy;
    }

    //    用于饮食报告
    public float[] fromUserIntake(String userId, String UIdate) {
        float[] UIenergy = new float[21];
        BigDecimal bigDecimal;
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select UI_date as date ,SUM(UI_energy) as energy,SUM(UI_protein) as protein ,SUM(UI_fat) as fat,SUM(UI_DF) as DF,SUM(UI_CH) as CH ," +
                "SUM(UI_water) as water,SUM(UI_VA) as VA,SUM(UI_VB1) as VB1,SUM(UI_VB2) as VB2,SUM(UI_VB3) as VB3,SUM(UI_VE) as VE,SUM(UI_VC) as VC," +
                "sum(UI_Fe) as Fe,sum(UI_Ga) as Ga,SUM(UI_Na) as Na,SUM(UI_CLS) as CLS,sum(UI_K) as K,sum(UI_Mg) as Mg,sum(UI_Zn) as Zn,sum(UI_P) as P,sum(UI_purine) as purine " +
                "from UserIntake where User_id = ? and UI_date= ? group by date";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{userId, UIdate});
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                UIenergy[0] = cursor.getFloat(cursor.getColumnIndex("energy"));//能量
                UIenergy[1] = cursor.getFloat(cursor.getColumnIndex("protein"));//蛋白质
                UIenergy[2] = cursor.getFloat(cursor.getColumnIndex("fat"));//脂肪
                UIenergy[3] = cursor.getFloat(cursor.getColumnIndex("DF"));//膳食纤维
                UIenergy[4] = cursor.getFloat(cursor.getColumnIndex("CH"));//碳水化物
                UIenergy[5] = cursor.getFloat(cursor.getColumnIndex("water"));//水分
                UIenergy[6] = cursor.getFloat(cursor.getColumnIndex("VA"));//维生素A
                UIenergy[7] = cursor.getFloat(cursor.getColumnIndex("VB1"));//维生素B1
                UIenergy[8] = cursor.getFloat(cursor.getColumnIndex("VB2"));//维生素B2
                UIenergy[9] = cursor.getFloat(cursor.getColumnIndex("VB3"));//维生素B3
                UIenergy[10] = cursor.getFloat(cursor.getColumnIndex("VE"));//维生素E
                UIenergy[11] = cursor.getFloat(cursor.getColumnIndex("VC"));//维生素C
                UIenergy[12] = cursor.getFloat(cursor.getColumnIndex("Fe"));//铁
                UIenergy[13] = cursor.getFloat(cursor.getColumnIndex("Ga"));//钙
                UIenergy[14] = cursor.getFloat(cursor.getColumnIndex("Na"));//钠
                UIenergy[15] = cursor.getFloat(cursor.getColumnIndex("CLS"));//胆固醇
                UIenergy[16] = cursor.getFloat(cursor.getColumnIndex("K"));//钾
                UIenergy[17] = cursor.getFloat(cursor.getColumnIndex("Mg"));//镁
                UIenergy[18] = cursor.getFloat(cursor.getColumnIndex("Zn"));//锌
                UIenergy[19] = cursor.getFloat(cursor.getColumnIndex("P"));//磷
                UIenergy[20] = cursor.getFloat(cursor.getColumnIndex("purine"));//嘌呤
            }
            cursor.close();
//            四舍五入
//            for (int i=0;i<UIenergy.length;i++){
//                bigDecimal=new BigDecimal(UIenergy[i]);
//                float halfUp=bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
//                UIenergy[i]=halfUp;
//                Log.d("half", String.valueOf(UIenergy[i]));
//            }
        } else {
            //                检查值，赋值为0
            for (int i = 0; i < UIenergy.length; i++) {
                UIenergy[i] = 0f;
            }
        }
        dbHelper.close();
        sqLiteDatabase.close();
        return UIenergy;
    }

    public String getTodayenergy(String userId, String UIdate) {
        float energy = 0;
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select sum(UI_energy) AS totalenergy from UserIntake where User_id=? and UI_date=?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{userId, UIdate});
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                energy = cursor.getFloat(cursor.getColumnIndex("totalenergy"));
            }
            cursor.close();
        }
        dbHelper.close();
        sqLiteDatabase.close();
        return String.valueOf(energy);
    }
}
