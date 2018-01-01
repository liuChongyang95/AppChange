package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.BaseColumns;
import android.widget.Toast;

import com.example.dapp.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2017/12/29.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static class PicColumns implements BaseColumns {
        public static final String picture = "picture";
    }

    public static final String CREATE_FRUIT = "create table Fruit (id integer primary key autoincrement, name text," +
            "nutrition text,"
            + PicColumns.picture + " blob not null)";
    private Context mContext;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_FRUIT);
        initDataBase(sqLiteDatabase, mContext);
        Toast.makeText(mContext, "Create successed", Toast.LENGTH_SHORT).show();
    }

    private void initDataBase(SQLiteDatabase sqLiteDatabase, Context mContext) {
        Drawable apple = mContext.getResources().getDrawable(R.drawable.apple);
        Drawable pear = mContext.getResources().getDrawable(R.drawable.pear);
        Drawable orange = mContext.getResources().getDrawable(R.drawable.orange);
        ContentValues values = new ContentValues();
        for (int i = 0; i <= 5; i++) {
            values.put("name", "苹果");
            values.put(PicColumns.picture, getPicture(apple));
            values.put("nutrition", "54千卡/100克");
            sqLiteDatabase.insert("Fruit", null, values);
            values.clear();
            values.put("name", "梨");
            values.put(PicColumns.picture, getPicture(pear));
            values.put("nutrition", "50千卡/100克");
            sqLiteDatabase.insert("Fruit", null, values);
            values.clear();
            values.put("name", "橘子");
            values.put("nutrition", "44千卡/100克");
            values.put(PicColumns.picture, getPicture(orange));
            sqLiteDatabase.insert("Fruit", null, values);
            values.clear();
            values.put("name", "Apple");
            values.put("nutrition", "54千卡/100克");
            values.put(PicColumns.picture, getPicture(apple));
            sqLiteDatabase.insert("Fruit", null, values);
            values.clear();
            values.put("name", "pear");
            values.put("nutrition", "44千卡/100克");
            values.put(PicColumns.picture, getPicture(pear));
            sqLiteDatabase.insert("Fruit", null, values);
            values.clear();
        }
    }

    private byte[] getPicture(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        return os.toByteArray();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = " DROP TABLE IF EXISTS Fruit";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
