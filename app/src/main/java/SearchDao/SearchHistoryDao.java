package SearchDao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Database.DBHelper;
import JavaBean.History;
import Util.Staticfinal_Value;

public class SearchHistoryDao {
    private Staticfinal_Value sfv;
    private DBHelper dbHelper;
    private SQLiteDatabase historyDB;
    private String foodName;
    private History history;

    public SearchHistoryDao(Context context) {
        sfv = new Staticfinal_Value();
        dbHelper = new DBHelper(context, "DApp.db", null, sfv.staticVersion());
    }

    public List<History> getHistory(String userId) {
        List<History> historyList = new ArrayList<>();
        historyDB = dbHelper.getReadableDatabase();
        String sql = "select tempName from tempSH where User_id = ?  order by datetime(tempTime) desc";
        Cursor cursor = historyDB.rawQuery(sql, new String[]{userId});
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                foodName = cursor.getString(cursor.getColumnIndex("tempName"));
                history = new History(foodName);
                historyList.add(history);
            }
        }
        cursor.close();
        dbHelper.close();
        historyDB.close();
        return historyList;
    }
}
