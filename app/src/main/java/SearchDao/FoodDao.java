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
                food.setEnergy(c.getString(c.getColumnIndex("Food_dic_energy")));
                food.setName(c.getString(c.getColumnIndex("Food_dic_name")));
                food.setId(c.getString(c.getColumnIndex("Food_dic_id")));
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_energy"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_protein"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_DF"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_CH"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_fat"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_water"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_CLS"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_vA"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_vB1"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_vB2"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_vB3"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_vC"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_vE"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_Fe"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_Ga"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_Na"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_P"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_K"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_Zn"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_Mg"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_GI"));
                else info = "—";
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
                if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄")&& !info.equals("─"))
                    info = c.getString(c.getColumnIndex("Food_dic_GI_per"));
                else info = "—";
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
