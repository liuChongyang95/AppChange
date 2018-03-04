package SearchDao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import Database.DBHelper;
import Util.Staticfinal_Value;

public class CareerDao {
    private DBHelper careerDBhelper;
    private Staticfinal_Value sfv;
    private SQLiteDatabase sqLiteDatabase;
    private int maxE;
    private int minE;
    public CareerDao(Context context) {
        sfv = new Staticfinal_Value();
        careerDBhelper = new DBHelper(context, "DApp.db", null, sfv.staticVersion());
    }

    public int getMin_energy(String shape, String intensity, String career) {
        sqLiteDatabase = careerDBhelper.getReadableDatabase();
        String sql = "select Career_energy_min from Career where Shape = ? and Intensity = ? and Career=?";

        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{shape, intensity, career});
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                minE = cursor.getInt(cursor.getColumnIndex("Career_energy_min"));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        careerDBhelper.close();
        sqLiteDatabase.close();
        return minE;

    }

    public int getMax_energy(String shape, String intensity, String career) {
        sqLiteDatabase = careerDBhelper.getReadableDatabase();
        String sql = "select Career_energy_max from Career where Shape = ? and Intensity = ? and Career=?";

        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{shape, intensity, career});
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                maxE = cursor.getInt(cursor.getColumnIndex("Career_energy_max"));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        careerDBhelper.close();
        sqLiteDatabase.close();
        return maxE;

    }

}
