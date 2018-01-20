package SearchDao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import Database.DBHelper;
import JavaBean.Fruit;
import Util.Staticfinal_Value;

/**
 * Created by Administrator on 2017/12/31.
 */

public class FruitDao {
    private SQLiteDatabase fruitsDb;
    private Context context;
    private String fruitName;
    private Fruit fruit;
    private DBHelper fruitDBHelper;
    private Staticfinal_Value sfv;


    public FruitDao(Context context) {
        sfv = new Staticfinal_Value();
        fruitDBHelper = new DBHelper(context, "DApp.db", null, sfv.staticVersion());
    }

    //加载列表
    public List<Fruit> getFruitList() {
        List<Fruit> fruitList = new ArrayList<>();
        fruitsDb = fruitDBHelper.getReadableDatabase();
        String sql = "select * from Fruit";
        Cursor cursor = fruitsDb.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                byte[] b = cursor.getBlob(cursor.getColumnIndexOrThrow(DBHelper.PicColumns.picture));
                //将获取的数据转换成drawable
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, null);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                Drawable drawable = bitmapDrawable;
                fruitName = cursor.getString(cursor.getColumnIndex("Ri_Food_name"));
//                fruitNutrition = cursor.getString(cursor.getColumnIndex("nutrition"));
                fruit = new Fruit(fruitName, drawable, null, null);
                fruitList.add(fruit);
            }
        }
        return fruitList;
    }

    //Search
    public List<Fruit> searchFruit(String searchFruitText) {
        String sql_searchFruit = "select * from Fruit where Ri_Food_name Like '%" + searchFruitText + "%'";
        List<Fruit> searchList = new ArrayList<>();
        fruitsDb = fruitDBHelper.getReadableDatabase();
        Cursor cursor_search = fruitsDb.rawQuery(sql_searchFruit, null);
        if (cursor_search != null && cursor_search.getCount() != 0) {
            while (cursor_search.moveToNext()) {
                byte[] b = cursor_search.getBlob(cursor_search.getColumnIndexOrThrow(DBHelper.PicColumns.picture));
                //将获取的数据转换成drawable
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, null);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                Drawable drawable = bitmapDrawable;
                //第一层 模糊搜索食物名称
                fruitName = cursor_search.getString(cursor_search.getColumnIndex("Ri_Food_name"));
//                fruitNutrition = cursor_search.getString(cursor_search.getColumnIndex("nutrition"));
                fruit = new Fruit(fruitName, drawable, null, null);
                searchList.add(fruit);
                cursor_search.close();
//                String sql_searchFood_2="select * from Fruit where Ri_Food_id=?";
//                Cursor cursor=fruitsDb.rawQuery(sql_searchFood_2,new String[]{})
            }
        }
        fruitsDb.close();
        return searchList;
    }
}
