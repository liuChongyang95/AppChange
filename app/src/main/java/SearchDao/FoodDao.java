package SearchDao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Database.DBHelper;
import JavaBean.Food;
import Util.DBUtil;
import Util.Staticfinal_Value;

public class FoodDao {
    private Staticfinal_Value sfv;
    private DBHelper foodDBHelper;
    private DBUtil dbUtil;

    public FoodDao(Context context) {
        sfv = new Staticfinal_Value();
        foodDBHelper = new DBHelper(context, "food.db", null, sfv.staticVersion());
        dbUtil=new DBUtil(context);
    }

    public List<Food> findAllSeason( String foodName) {
        List<Food> foods = new ArrayList<>();
        SQLiteDatabase myDateBase = dbUtil.openDatabase();
        String sql = "select * from Food_Dic where Food_dic_name=?";
        try {
            Cursor c = myDateBase.rawQuery(sql, new String[]{foodName});
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Food food = new Food();
                food.setId(c.getString(c.getColumnIndex("Food_dic_id")));
                food.setName(c.getString(c.getColumnIndex("Food_dic_name")));
                foods.add(food);
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        assert myDateBase != null;

        myDateBase.close();
        return foods;
    }
}
