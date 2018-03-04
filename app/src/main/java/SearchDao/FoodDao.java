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
    private String[] nutri = {"Food_dic_energy", "Food_dic_protein", "Food_dic_fat", "Food_dic_DF", "Food_dic_CH",
            "Food_dic_water", "Food_dic_vA", "Food_dic_vB1", "Food_dic_vB2", "Food_dic_vB3",
            "Food_dic_vE", "Food_dic_vC", "Food_dic_Fe", "Food_dic_Ga", "Food_dic_Na", "Food_dic_CLS", "Food_dic_K", "Food_dic_Mg", "Food_dic_Zn",
            "Food_dic_P", "Food_dic_purine"};

    public FoodDao(Context context) {
        dbUtil = new DBUtil(context);
    }

    public List<Food> findAllSeason(String foodName) {
        List<Food> foods = new ArrayList<>();
        SQLiteDatabase myDateBase = dbUtil.openDatabase();
        String sql = "select * from Food_Dic where Food_dic_name Like '%" + foodName + "%'";
        try {
            Cursor c = myDateBase.rawQuery(sql, null);
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    Food food = new Food();
                    food.setEnergy(c.getString(c.getColumnIndex("Food_dic_energy")));
                    food.setName(c.getString(c.getColumnIndex("Food_dic_name")));
                    food.setId(c.getString(c.getColumnIndex("Food_dic_id")));
                    foods.add(food);
                    c.moveToNext();
                }
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        myDateBase.close();
        return foods;
    }

    public String find_energy(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_energy from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_energy"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_energy"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_protein(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_protein from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_protein"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_protein"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_DF(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_DF from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_DF"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_DF"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_CH(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_CH from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_CH"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_CH"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_fat(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_fat from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_fat"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_fat"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_water(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_water from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_water"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_water"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_CLS(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_CLS from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_CLS"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_CLS"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_vA(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_vA from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_vA"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_vA"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_vB1(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_vB1 from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_vB1"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_vB1"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_vB2(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_vB2 from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_vB2"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_vB2"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_vB3(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_vB3 from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_vB3"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_vB3"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_vC(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_vC from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_vC"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_vC"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_vE(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_vE from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_vE"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_vE"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_Fe(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_Fe from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_Fe"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_Fe"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_Ga(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_Ga from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_Ga"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_Ga"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_Na(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_Na from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_Na"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_Na"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_P(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_P from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_P"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_P"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_K(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_K from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_K"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_K"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_Zn(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_Zn from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_Zn"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_Zn"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_Mg(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_Mg from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_Mg"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_Mg"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_GI(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_GI from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_GI"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_GI"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;

    }

    public String find_GI_per(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_GI_per from Food_Dic where Food_dic_name = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_GI_per"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_GI_per"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_Name(String foodId) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_name from Food_Dic where Food_dic_id = ?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodId});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_name"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_name"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String find_purine(String foodName) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String sql = "select Food_dic_purine from Food_Dic where Food_dic_name=?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodName});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    info = c.getString(c.getColumnIndex("Food_dic_purine"));
                    if (info != null && !info.equals("…") && !info.equals("Tr") && info.length() > 0 && !info.equals("—") && !info.equals("┄") && !info.equals("─"))
                        info = c.getString(c.getColumnIndex("Food_dic_purine"));
                    else info = "—";
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return info;
    }

    public String[] findNutrition(String foodId) {
        SQLiteDatabase foodDB = dbUtil.openDatabase();
        String[] Nutri_info = new String[21];
        String sql = "select * from Food_Dic where Food_dic_id=?";
        try {
            Cursor c = foodDB.rawQuery(sql, new String[]{foodId});
            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    for (int i = 0; i <= 20; i++) {
                        Nutri_info[i] = c.getString(c.getColumnIndex(nutri[i]));
                    }
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodDB.close();
        return Nutri_info;
    }

}
