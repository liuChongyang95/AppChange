package SearchDao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.dapp.MainActivity;
import com.example.dapp.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.FruitAdapter;
import Database.DBHelper;
import Model.Fruit;

/**
 * Created by Administrator on 2017/12/31.
 */

public class FruitDao {
    private SQLiteDatabase fruitsDb;
    private Context context;
    private String fruitName;
    private String fruitNutrition;
    private Fruit fruit;
    private DBHelper fruitDBHelper;


    public FruitDao(Context context) {
        this.context = context;
    }

    //加载列表
    public List<Fruit> getFruitList() {
        List<Fruit> fruitList = new ArrayList<>();
        fruitDBHelper = new DBHelper(context, "Fruit.db", null, 1);
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
                fruitName = cursor.getString(cursor.getColumnIndex("name"));
                fruitNutrition = cursor.getString(cursor.getColumnIndex("nutrition"));
                fruit = new Fruit(fruitName, drawable, fruitNutrition);
                fruitList.add(fruit);
            }
        }
        return fruitList;
    }

    //Search
    public List<Fruit> searchFruit(String searchFruitText) {
        String sql_searchFruit = "select * from Fruit where name Like '%" + searchFruitText + "%'";
        List<Fruit> searchList = new ArrayList<>();
        fruitDBHelper = new DBHelper(context, "Fruit.db", null, 1);
        fruitsDb = fruitDBHelper.getReadableDatabase();
        Cursor cursor_search = fruitsDb.rawQuery(sql_searchFruit, null);
        while (cursor_search.moveToNext()) {
            byte[] b = cursor_search.getBlob(cursor_search.getColumnIndexOrThrow(DBHelper.PicColumns.picture));
            //将获取的数据转换成drawable
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, null);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
            Drawable drawable = bitmapDrawable;
            fruitName = cursor_search.getString(cursor_search.getColumnIndex("name"));
            fruitNutrition = cursor_search.getString(cursor_search.getColumnIndex("nutrition"));
            fruit = new Fruit(fruitName, drawable, fruitNutrition);
            searchList.add(fruit);
        }
        fruitsDb.close();
        return searchList;
    }
}
