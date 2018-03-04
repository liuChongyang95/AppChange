package SearchDao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public String[] getFromUserIntake(String userId, String UIClass, String UIdate) {
        String[] UIenergy = new String[21];
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select * from UserIntake where User_id = ? and UI_class= ? and UI_date= ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{userId, UIClass, UIdate});
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                UIenergy[0] = cursor.getString(cursor.getColumnIndex("UI_energy"));
                UIenergy[1] = cursor.getString(cursor.getColumnIndex("UI_protein"));
                UIenergy[2] = cursor.getString(cursor.getColumnIndex("UI_fat"));
                UIenergy[3] = cursor.getString(cursor.getColumnIndex("UI_DF"));
                UIenergy[4] = cursor.getString(cursor.getColumnIndex("UI_CH"));
                UIenergy[5] = cursor.getString(cursor.getColumnIndex("UI_water"));
                UIenergy[6] = cursor.getString(cursor.getColumnIndex("UI_VA"));
                UIenergy[7] = cursor.getString(cursor.getColumnIndex("UI_VB1"));
                UIenergy[8] = cursor.getString(cursor.getColumnIndex("UI_VB2"));
                UIenergy[9] = cursor.getString(cursor.getColumnIndex("UI_VB3"));
                UIenergy[10] = cursor.getString(cursor.getColumnIndex("UI_VE"));
                UIenergy[11] = cursor.getString(cursor.getColumnIndex("UI_VC"));
                UIenergy[12] = cursor.getString(cursor.getColumnIndex("UI_Fe"));
                UIenergy[13] = cursor.getString(cursor.getColumnIndex("UI_Ga"));
                UIenergy[14] = cursor.getString(cursor.getColumnIndex("UI_Na"));
                UIenergy[15] = cursor.getString(cursor.getColumnIndex("UI_CLS"));
                UIenergy[16] = cursor.getString(cursor.getColumnIndex("UI_K"));
                UIenergy[17] = cursor.getString(cursor.getColumnIndex("UI_Mg"));
                UIenergy[18] = cursor.getString(cursor.getColumnIndex("UI_Zn"));
                UIenergy[19] = cursor.getString(cursor.getColumnIndex("UI_P"));
                UIenergy[20] = cursor.getString(cursor.getColumnIndex("UI_purine"));
            }
            cursor.close();
        }
        dbHelper.close();
        sqLiteDatabase.close();
        return UIenergy;
    }

    public String getTodayenergy(String userId, String UIdate) {
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        String energy;
        float b = 0;
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select * from UserIntake where User_id=? and UI_date=?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{userId, UIdate});
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                float a = Float.parseFloat(cursor.getString(cursor.getColumnIndex("UI_energy")));
                b = b + a;
            }
            cursor.close();
        }
        energy = numberFormat.format(b);
        dbHelper.close();
        sqLiteDatabase.close();
        return energy;
    }
}
