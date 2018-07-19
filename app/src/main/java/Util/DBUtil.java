package Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.GProject.DiabetesApp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
/*
* 用于读取res-raw文件夹中的食物字典表。存储到手机内存database中。大概是这样。。懒得回忆了。
* */
public class DBUtil {
    private static SQLiteDatabase database;
    private static final String DATABASE_FILENAME = "food.db";
    private static final String PACKAGE_NAME = "com.GProject.DiabetesApp";
    private static final String DATABASE_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME+"/"+"databases";
    private Context mContext;

    public DBUtil(Context context) {
        mContext = context;
    }

    public SQLiteDatabase openDatabase() {
        try {
            String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
            File dir = new File(DATABASE_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }
            if (!(new File(databaseFilename)).exists()) {
                InputStream is = mContext.getResources().openRawResource(R.raw.food);
                FileOutputStream fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[8192];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            database = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
            return database;
        } catch (Exception e) {
            e.printStackTrace();
        }
        database.close();
        return null;
    }

}
