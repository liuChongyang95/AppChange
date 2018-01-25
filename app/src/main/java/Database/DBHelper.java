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
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.dapp.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2017/12/29.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static class PicColumns implements BaseColumns {
        public static final String picture = "Ri_Food_photo";
    }

    public static class PicColumns_user implements BaseColumns {
        public static final String picture = "User_Photo";
    }

    //           水果模拟
    private static final String CREATE_FRUIT = "create table Fruit (Ri_Food_name varchar(20) primary key ,Ri_Food_id char(20) not null," +
            "Ri_Food_ep_id varchar(20)," + PicColumns.picture + " blob not null)";
    //           患者信息
    // name昵称
    private static final String CREATE_USER = "create table User (User_id integer ,User_Nickname " +
            "varchar(20) not null ," + "  User_Birth date not null, User_Sex char(6) not null, User_Tall varchar(6) not null," +
            "User_Real_weight varchar(6) not null, User_Expect_weight varchar(6),Record_time TimeStamp DEFAULT(datetime('now', 'localtime')),Career varchar(16)," +
            PicColumns_user.picture + " blob not null ,User_Shape char(6), " +
            "User_Intensity varchar(10) not null,constraint User_PK primary key (User_id,Record_time) ) ";
    //           用户登录
    // name用户名
    private static final String CREATE_LOGIN = "create table Login(Username varchar(20) primary key ,password varchar(30), User_id integer ,foreign key (User_id) references User(User_id) on update cascade)";
    //           职业设定
    private static final String CREATE_CAREER = "create table Career(Career varchar(16),Intensity char(14),Shape char(10),Career_energy_min integer,Career_energy_max integer ,constraint Career_PK primary key(Career,Shape))";


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
        sqLiteDatabase.execSQL(CREATE_CAREER);
        initDataBase_Food(sqLiteDatabase, mContext);
        initDataBase_Career(sqLiteDatabase, mContext);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql_Fruit = " DROP TABLE IF EXISTS Fruit";
        String sql_User = " DROP TABLE IF EXISTS User";
        String sql_Login = "DROP TABLE IF EXISTS Login";
        String sql_Career = "DROP TABLE IF EXISTS Career";
        sqLiteDatabase.execSQL(sql_Fruit);
        sqLiteDatabase.execSQL(sql_User);
        sqLiteDatabase.execSQL(sql_Login);
        sqLiteDatabase.execSQL(sql_Career);
        onCreate(sqLiteDatabase);
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

    private void initDataBase_Food(SQLiteDatabase sqLiteDatabase, Context mContext) {
        Drawable apple = mContext.getResources().getDrawable(R.drawable.apple);
        Drawable pear = mContext.getResources().getDrawable(R.drawable.pear);
        Drawable orange = mContext.getResources().getDrawable(R.drawable.orange);
        Drawable apple_1 = mContext.getResources().getDrawable(R.drawable.apple_1);
        Drawable apple_2 = mContext.getResources().getDrawable(R.drawable.apple_2);
        Drawable apple_3 = mContext.getResources().getDrawable(R.drawable.apple_3);
        ContentValues values = new ContentValues();
        values.put("Ri_Food_name", "青苹果");
        values.put("Ri_Food_id", "000010");
        values.put("Ri_Food_photo", getPicture(apple_1));
        values.put("Ri_Food_ep_id", "00005 00006");
        sqLiteDatabase.insert("Fruit", null, values);
        values.clear();
        values.put("Ri_Food_name", "橘子");
        values.put("Ri_Food_id", "00003");
        values.put("Ri_Food_photo", getPicture(orange));
        sqLiteDatabase.insert("Fruit", null, values);
        values.clear();
        values.put("Ri_Food_name", "apple");
        values.put("Ri_Food_id", "00001");
        values.put("Ri_Food_photo", getPicture(apple_1));
        values.put("Ri_Food_ep_id", "00005 00006");
        sqLiteDatabase.insert("Fruit", null, values);
        values.clear();
        values.put("Ri_Food_name", "果酱(apple)");
        values.put("Ri_Food_id", "00005");
        values.put("Ri_Food_photo", getPicture(apple_2));
        sqLiteDatabase.insert("Fruit", null, values);
        values.clear();
        values.put("Ri_Food_name", "梨");
        values.put("Ri_Food_photo", getPicture(pear));
        values.put("Ri_Food_id", "00002");
        values.put("Ri_Food_ep_id", "00006 00007");
        sqLiteDatabase.insert("Fruit", null, values);
        values.clear();
        values.put("Ri_Food_name", "苹果");
        values.put("Ri_Food_photo", getPicture(apple));
        values.put("Ri_Food_id", "00001");
        values.put("Ri_Food_ep_id", "00005 00006");
        sqLiteDatabase.insert("Fruit", null, values);
        values.clear();
        values.put("Ri_Food_name", "沙拉");
        values.put("Ri_Food_id", "00006");
        values.put("Ri_Food_photo", getPicture(apple_3));
        sqLiteDatabase.insert("Fruit", null, values);
        values.clear();
        values.put("Ri_Food_name", "果酱(pear)");
        values.put("Ri_Food_id", "00007");
        values.put("Ri_Food_photo", getPicture(apple_2));
        sqLiteDatabase.insert("Fruit", null, values);
        values.clear();
    }

    private void initDataBase_Career(SQLiteDatabase sqLiteDatabase, Context mContext) {
        ContentValues values = new ContentValues();
        values.put("Career", "办公室职员");
        values.put("Intensity", "轻体力劳动");
        values.put("Shape", "消瘦");
        values.put("Career_energy_min", "35");
        values.put("Career_energy_max", "35");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "教师");
        values.put("Intensity", "轻体力劳动");
        values.put("Shape", "消瘦");
        values.put("Career_energy_min", "35");
        values.put("Career_energy_max", "35");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "售货员");
        values.put("Intensity", "轻体力劳动");
        values.put("Shape", "消瘦");
        values.put("Career_energy_min", "35");
        values.put("Career_energy_max", "35");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "简单家务");
        values.put("Intensity", "轻体力劳动");
        values.put("Shape", "消瘦");
        values.put("Career_energy_min", "35");
        values.put("Career_energy_max", "35");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();

        values.put("Career", "办公室职员");
        values.put("Intensity", "轻体力劳动");
        values.put("Shape", "正常");
        values.put("Career_energy_min", "30");
        values.put("Career_energy_max", "30");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "教师");
        values.put("Intensity", "轻体力劳动");
        values.put("Shape", "正常");
        values.put("Career_energy_min", "30");
        values.put("Career_energy_max", "30");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "售货员");
        values.put("Intensity", "轻体力劳动");
        values.put("Shape", "正常");
        values.put("Career_energy_min", "30");
        values.put("Career_energy_max", "30");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "简单家务");
        values.put("Intensity", "轻体力劳动");
        values.put("Shape", "正常");
        values.put("Career_energy_min", "30");
        values.put("Career_energy_max", "30");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();

        values.put("Career", "办公室职员");
        values.put("Intensity", "轻体力劳动");
        values.put("Shape", "肥胖");
        values.put("Career_energy_min", "20");
        values.put("Career_energy_max", "25");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "教师");
        values.put("Intensity", "轻体力劳动");
        values.put("Shape", "肥胖");
        values.put("Career_energy_min", "20");
        values.put("Career_energy_max", "25");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "售货员");
        values.put("Intensity", "轻体力劳动");
        values.put("Shape", "肥胖");
        values.put("Career_energy_min", "20");
        values.put("Career_energy_max", "25");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "简单家务");
        values.put("Intensity", "轻体力劳动");
        values.put("Shape", "肥胖");
        values.put("Career_energy_min", "20");
        values.put("Career_energy_max", "25");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();


        values.put("Career", "学生");
        values.put("Intensity", "中体力劳动");
        values.put("Shape", "消瘦");
        values.put("Career_energy_min", "40");
        values.put("Career_energy_max", "40");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "司机");
        values.put("Intensity", "中体力劳动");
        values.put("Shape", "消瘦");
        values.put("Career_energy_min", "40");
        values.put("Career_energy_max", "40");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "外科医生");
        values.put("Intensity", "中体力劳动");
        values.put("Shape", "消瘦");
        values.put("Career_energy_min", "40");
        values.put("Career_energy_max", "40");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "体育教师");
        values.put("Intensity", "中体力劳动");
        values.put("Shape", "消瘦");
        values.put("Career_energy_min", "40");
        values.put("Career_energy_max", "40");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "一般农活");
        values.put("Intensity", "中体力劳动");
        values.put("Shape", "消瘦");
        values.put("Career_energy_min", "40");
        values.put("Career_energy_max", "40");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();

        values.put("Career", "学生");
        values.put("Intensity", "中体力劳动");
        values.put("Shape", "正常");
        values.put("Career_energy_min", "35");
        values.put("Career_energy_max", "35");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "司机");
        values.put("Intensity", "中体力劳动");
        values.put("Shape", "正常");
        values.put("Career_energy_min", "35");
        values.put("Career_energy_max", "35");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "外科医生");
        values.put("Intensity", "中体力劳动");
        values.put("Shape", "正常");
        values.put("Career_energy_min", "35");
        values.put("Career_energy_max", "35");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "体育教师");
        values.put("Intensity", "中体力劳动");
        values.put("Shape", "正常");
        values.put("Career_energy_min", "35");
        values.put("Career_energy_max", "35");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "一般农活");
        values.put("Intensity", "中体力劳动");
        values.put("Shape", "正常");
        values.put("Career_energy_min", "35");
        values.put("Career_energy_max", "35");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();

        values.put("Career", "学生");
        values.put("Intensity", "中体力劳动");
        values.put("Shape", "肥胖");
        values.put("Career_energy_min", "30");
        values.put("Career_energy_max", "30");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "司机");
        values.put("Intensity", "中体力劳动");
        values.put("Shape", "肥胖");
        values.put("Career_energy_min", "30");
        values.put("Career_energy_max", "30");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "外科医生");
        values.put("Intensity", "中体力劳动");
        values.put("Shape", "肥胖");
        values.put("Career_energy_min", "30");
        values.put("Career_energy_max", "30");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "体育教师");
        values.put("Intensity", "中体力劳动");
        values.put("Shape", "肥胖");
        values.put("Career_energy_min", "30");
        values.put("Career_energy_max", "30");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "一般农活");
        values.put("Intensity", "中体力劳动");
        values.put("Shape", "肥胖");
        values.put("Career_energy_min", "30");
        values.put("Career_energy_max", "30");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();


        values.put("Career", "建筑工");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "消瘦");
        values.put("Career_energy_min", "45");
        values.put("Career_energy_max", "45");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "搬运工");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "消瘦");
        values.put("Career_energy_min", "45");
        values.put("Career_energy_max", "45");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "冶炼工");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "消瘦");
        values.put("Career_energy_min", "45");
        values.put("Career_energy_max", "45");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "重的农活");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "消瘦");
        values.put("Career_energy_min", "45");
        values.put("Career_energy_max", "45");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "运动员");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "消瘦");
        values.put("Career_energy_min", "45");
        values.put("Career_energy_max", "45");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "舞蹈者");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "消瘦");
        values.put("Career_energy_min", "45");
        values.put("Career_energy_max", "45");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();

        values.put("Career", "建筑工");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "正常");
        values.put("Career_energy_min", "40");
        values.put("Career_energy_max", "40");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "搬运工");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "正常");
        values.put("Career_energy_min", "40");
        values.put("Career_energy_max", "40");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "冶炼工");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "正常");
        values.put("Career_energy_min", "40");
        values.put("Career_energy_max", "40");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "重的农活");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "正常");
        values.put("Career_energy_min", "40");
        values.put("Career_energy_max", "40");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "运动员");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "正常");
        values.put("Career_energy_min", "40");
        values.put("Career_energy_max", "40");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "舞蹈者");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "正常");
        values.put("Career_energy_min", "40");
        values.put("Career_energy_max", "40");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();

        values.put("Career", "建筑工");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "肥胖");
        values.put("Career_energy_min", "35");
        values.put("Career_energy_max", "35");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "搬运工");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "肥胖");
        values.put("Career_energy_min", "35");
        values.put("Career_energy_max", "35");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "冶炼工");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "肥胖");
        values.put("Career_energy_min", "35");
        values.put("Career_energy_max", "35");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "重的农活");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "肥胖");
        values.put("Career_energy_min", "35");
        values.put("Career_energy_max", "35");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "运动员");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "肥胖");
        values.put("Career_energy_min", "35");
        values.put("Career_energy_max", "35");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
        values.put("Career", "舞蹈者");
        values.put("Intensity", "重体力劳动");
        values.put("Shape", "肥胖");
        values.put("Career_energy_min", "35");
        values.put("Career_energy_max", "35");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();


        values.put("Career", "无职业");
        values.put("Intensity", "基本行动");
        values.put("Shape", "消瘦");
        values.put("Career_energy_min", "20");
        values.put("Career_energy_max", "25");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();

        values.put("Career", "无职业");
        values.put("Intensity", "基本行动");
        values.put("Shape", "正常");
        values.put("Career_energy_min", "15");
        values.put("Career_energy_max", "20");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();

        values.put("Career", "无职业");
        values.put("Intensity", "基本行动");
        values.put("Shape", "肥胖");
        values.put("Career_energy_min", "15");
        values.put("Career_energy_max", "15");
        sqLiteDatabase.insert("Career", null, values);
        values.clear();
    }
}
