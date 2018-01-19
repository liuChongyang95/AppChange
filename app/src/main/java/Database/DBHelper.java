package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
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

    public static class PicColumns_user implements BaseColumns {
        public static final String picture = "User_Photo";
    }

    //           水果模拟
    private static final String CREATE_FRUIT = "create table Fruit (id integer primary key autoincrement, name text," +
            "nutrition text," + PicColumns.picture + " blob not null)";
    //           患者信息
    // name昵称
    private static final String CREATE_USER = "create table User (User_id integer ,User_Nickname " +
            "varchar(20) not null ," + "  User_Birth date not null, User_Sex char(6) not null, User_Tall varchar(6) not null," +
            "User_Real_weight varchar(6) not null, User_Expect_weight varchar(6),Record_time TimeStamp DEFAULT(datetime('now', 'localtime')),Career varchar(16)," +
            PicColumns_user.picture + " blob not null ,User_Shape char(6),User_Age integer not null, " +
            "User_Intensity varchar(10) not null,constraint User_PK primary key (User_id,Record_time) ) ";
    //           用户登录
    // name用户名
    private static final String CREATE_LOGIN = "create table Login(Username varchar(20) primary key ,password varchar(30), User_id integer ,foreign key (User_id) references User(User_id) on update cascade)";
    private Context mContext;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_FRUIT);
        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL(CREATE_LOGIN);
        initDataBase(sqLiteDatabase, mContext);
    }

    private void initDataBase(SQLiteDatabase sqLiteDatabase, Context mContext) {
        Drawable apple = mContext.getResources().getDrawable(R.drawable.apple);
        Drawable pear = mContext.getResources().getDrawable(R.drawable.pear);
        Drawable orange = mContext.getResources().getDrawable(R.drawable.orange);
        ContentValues values = new ContentValues();
        for (int i = 0; i <= 2; i++) {
            values.put("name", "苹果");
            values.put("picture", getPicture(apple));
            values.put("nutrition", "54千卡/100克");
            sqLiteDatabase.insert("Fruit", null, values);
            values.clear();
            values.put("name", "梨");
            values.put("picture", getPicture(pear));
            values.put("nutrition", "50千卡/100克");
            sqLiteDatabase.insert("Fruit", null, values);
            values.clear();
            values.put("name", "橘子");
            values.put("nutrition", "44千卡/100克");
            values.put("picture", getPicture(orange));
            sqLiteDatabase.insert("Fruit", null, values);
            values.clear();
            values.put("name", "Apple");
            values.put("nutrition", "54千卡/100克");
            values.put("picture", getPicture(apple));
            sqLiteDatabase.insert("Fruit", null, values);
            values.clear();
            values.put("name", "pear");
            values.put("nutrition", "44千卡/100克");
            values.put("picture", getPicture(pear));
            sqLiteDatabase.insert("Fruit", null, values);
            values.clear();
        }
    }

    public byte[] getPicture(Drawable drawable) {
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
        String sql_Fruit = " DROP TABLE IF EXISTS Fruit";
        String sql_User = " DROP TABLE IF EXISTS User";
        String sql_Login = "DROP TABLE IF EXISTS Login";
        sqLiteDatabase.execSQL(sql_Fruit);
        sqLiteDatabase.execSQL(sql_User);
        sqLiteDatabase.execSQL(sql_Login);
        onCreate(sqLiteDatabase);
    }
}
