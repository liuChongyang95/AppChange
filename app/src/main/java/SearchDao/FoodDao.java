package SearchDao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Database.DBHelper;
import JavaBean.Food;
import Util.DBUtil;

public class FoodDao {
    private DBUtil dbUtil;
    private String info;

    public FoodDao(Context context) {
        dbUtil = new DBUtil(context);
    }

    public List<Food> findAllSeason(String foodName) {
        List<Food> foods = new ArrayList<>();
        SQLiteDatabase myDateBase = dbUtil.openDatabase();
        String sql = "select * from Food_Dic where Food_dic_name Like '%" + foodName + "%'";
        try {
            Cursor c = myDateBase.rawQuery(sql, null);
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
        myDateBase.close();
        return foods;
    }

    public String find_energy(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_energy from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_energy"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_protein(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_protein from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_protein"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_DF(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_DF from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_DF"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_CH(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_CH from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_CH"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_fat(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_fat from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_fat"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_water(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_water from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_water"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_CLS(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_CLS from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_CLS"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_vA(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_vA from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_vA"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_vB1(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_vB1 from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_vB1"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_vB2(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_vB2 from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_vB2"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_vB3(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_vB3 from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_vB3"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_vC(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_vC from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_vC"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_vE(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_vE from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_vE"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_Fe(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_Fe from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_Fe"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_Ga(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_Ga from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_Ga"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_Na(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_Na from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_Na"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_P(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_P from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_P"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_K(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_K from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_K"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_Zn(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_Zn from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_Zn"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_Mg(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_Mg from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_Mg"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_GI(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_GI from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_GI"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;

    }

    public String find_GI_per(String foodName) {
        info = null;
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_GI_per from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c.moveToFirst()) {
                info = c.getString(c.getColumnIndex("Food_dic_GI_per"));
                c.moveToNext();
            }
            if (!c.isClosed()) {
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;

    }
}
