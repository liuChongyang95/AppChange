package SearchDao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import Database.DBHelper;
import JavaBean.Fruit;
import Util.Staticfinal_Value;

/**
 * Created by Administrator on 2017/12/31.
 * <p>
 * Fruit 是Ri_Food
 */

public class FruitDao {
    private SQLiteDatabase fruitsDb;
    private Context context;
    private String fruitName;
    private String fruitId;
    private String fruitEp_id;
    private String[] fruitEp_id_split = null;
    private String sql_searchFruit;
    private String aSearchFruitText;
    private Fruit fruit;
    private DBHelper fruitDBHelper;
    private TreeSet treeSet;
    private Staticfinal_Value sfv;
    private final static String TAG = "FruitDao";


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
        cursor.close();
        fruitsDb.close();
        return fruitList;
    }

    //Search
    public List<Fruit> searchFruit(String searchFruitText_all) {
        Cursor cursor_search = null;
        Cursor cursor_ID = null;
        Cursor cursor_name_all = null;
        treeSet = new TreeSet();
        //第一层 模糊搜索食物名称
        List<String> searchList = new ArrayList<>();
        List<Fruit> resultList = new ArrayList<>();
        fruitsDb = fruitDBHelper.getReadableDatabase();

        //全词
        String sql = "select * from Fruit where Ri_Food_name Like '%" + searchFruitText_all + "%'";
        cursor_name_all = fruitsDb.rawQuery(sql, null);
        if (cursor_name_all != null && cursor_name_all.getCount() != 0) {
            while (cursor_name_all.moveToNext()) {
                fruitId = cursor_name_all.getString(cursor_name_all.getColumnIndex("Ri_Food_id"));
                searchList.add(fruitId);
            }
        }
        //切词
        for (int a = 0; a < searchFruitText_all.length(); a++) {
            aSearchFruitText = String.valueOf(searchFruitText_all.charAt(a));
            sql_searchFruit = "select * from Fruit where Ri_Food_name Like '%" + aSearchFruitText + "%'";
//            Log.d(TAG, sql_searchFruit);
            cursor_search = fruitsDb.rawQuery(sql_searchFruit, null);
            if (cursor_search != null && cursor_search.getCount() != 0) {
                while (cursor_search.moveToNext()) {
                    fruitId = cursor_search.getString(cursor_search.getColumnIndex("Ri_Food_id"));
                    searchList.add(fruitId);
                    fruitEp_id = cursor_search.getString(cursor_search.getColumnIndex("Ri_Food_ep_id"));
                    if (fruitEp_id != null) {
                        fruitEp_id_split = fruitEp_id.split("\\s+");
                        searchList.addAll(Arrays.asList(fruitEp_id_split));
                    }
                    treeSet.addAll(searchList);
                }
            }
        }
        searchList.clear();
        searchList.addAll(treeSet);
        cursor_search.close();
        //向第二层传递食物ID
        //第二层 查询同ID食物
        String sql_searchFood_2 = "select * from Fruit where Ri_Food_id=?";
        for (int a = 0; a < searchList.size(); a++) {
            cursor_ID = fruitsDb.rawQuery(sql_searchFood_2, new String[]{searchList.get(a)});
            if (cursor_ID != null && cursor_ID.getCount() != 0) {
                while (cursor_ID.moveToNext()) {
                    byte[] b = cursor_ID.getBlob(cursor_ID.getColumnIndexOrThrow(DBHelper.PicColumns.picture));
                    //将获取的数据转换成drawable
                    Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, null);
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                    Drawable drawable_2 = bitmapDrawable;
                    fruitName = cursor_ID.getString(cursor_ID.getColumnIndex("Ri_Food_name"));
//                        fruitId = cursor_search.getString(cursor_search.getColumnIndex("Ri_Food_id");
//                        fruitEp_id=cursor.getString(cursor);
                    fruit = new Fruit(fruitName, drawable_2, null, null);
                    resultList.add(fruit);
                }
            }
        }
        cursor_ID.close();
        fruitsDb.close();
        return resultList;
    }
}
