//package SearchDao;
//
///**
// * Created by Administrator on 2017/12/31.
// */
//package com.demo.dao;
//
//        import java.util.ArrayList;
//        import java.util.List;
//
//        import com.demo.bean.OutAccount;
//        import com.demo.db.DBhelper;
//
//        import android.content.Context;
//        import android.database.Cursor;
//        import android.database.sqlite.SQLiteDatabase;
//
//public class outAccountDao {
//    private Context context;
//
//    public outAccountDao(Context context) {
//        this.context = context;
//    };
//
//    public void insertOutAccount(OutAccount account) {
//        DBhelper dbHelper = new DBhelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        String sql = "insert into tb_outaccount values(null,?,?,?,?,?)";
//        db.execSQL(sql, new Object[] { account.getJine(), account.getTime(),
//                account.getLeibie(), account.getAddr(), account.getMemo() });
//    }
//
//    public List<OutAccount> selectAllOutAccount() {
//        List<OutAccount> res = new ArrayList<OutAccount>();
//
//        DBhelper dbHelper = new DBhelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        String sql = "select * from tb_outaccount";
//        Cursor rawQuery = db.rawQuery(sql, null);
//
//        while (rawQuery.moveToNext()) {
//            int id = rawQuery.getInt(0);
//            float jine = rawQuery.getFloat(1);
//            String time = rawQuery.getString(2);
//            String leibie = rawQuery.getString(3);
//            String addr = rawQuery.getString(4);
//            String memo = rawQuery.getString(5);
//            OutAccount outAccount = new OutAccount(id, jine, time, leibie,
//                    addr, memo);
//            res.add(outAccount);
//        }
//        return res;
//    }
//
//    // 从数据库中取类别
//    public ArrayList<String> selectLeibie() {
//        DBhelper dbHelper = new DBhelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // String sql2="select count(distinct leibie)from tb_outaccount";//类别数量
//
//        String sql1 = "select distinct leibie from tb_outaccount";//
//        Cursor rawQuery = db.rawQuery(sql1, null);
//        ArrayList<String> list = new ArrayList<String>();
//        while (rawQuery.moveToNext()) {
//            String str = rawQuery.getString(0);
//            System.out.println(str);
//            list.add(str);
//        }
//        return list;
//    }
//
//    // /***************************///
//    // 从数据库中取金额
//    public ArrayList<Float> yinshiJine() {
//        DBhelper dbHelper = new DBhelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // String sql2="select count(distinct leibie)from tb_outaccount";//类别数量
//        // select * from table where (品牌=‘条件' 条件 is null)
//
//        String sql1 = "select jine from tb_outaccount where(leibie='饮食')";//
//        Cursor rawQuery1 = db.rawQuery(sql1, null);
//        ArrayList<Float> yxjine = new ArrayList<Float>();
//        while (rawQuery1.moveToNext()) {
//            Float str2 = rawQuery1.getFloat(0);
//            System.out.println(str2);
//            yxjine.add(str2);
//        }
//        return yxjine;
//    }
//
//    public ArrayList<Float> fushiJine() {
//        DBhelper dbHelper = new DBhelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String sql1 = "select jine from tb_outaccount where(leibie='服饰')";//
//        Cursor rawQuery1 = db.rawQuery(sql1, null);
//        ArrayList<Float> fsjine = new ArrayList<Float>();
//        while (rawQuery1.moveToNext()) {
//            Float str2 = rawQuery1.getFloat(0);
//            fsjine.add(str2);
//        }
//        return fsjine;
//    }
//
//    public ArrayList<Float> lvyouJine() {
//        DBhelper dbHelper = new DBhelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String sql1 = "select jine from tb_outaccount where(leibie='旅游')";//
//        Cursor rawQuery1 = db.rawQuery(sql1, null);
//        ArrayList<Float> lyjine = new ArrayList<Float>();
//        while (rawQuery1.moveToNext()) {
//            Float str2 = rawQuery1.getFloat(0);
//            lyjine.add(str2);
//        }
//        return lyjine;
//    }
//
//    public ArrayList<Float> yuleJine() {
//        DBhelper dbHelper = new DBhelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String sql1 = "select jine from tb_outaccount where(leibie='娱乐')";//
//        Cursor rawQuery1 = db.rawQuery(sql1, null);
//        ArrayList<Float> yljine = new ArrayList<Float>();
//        while (rawQuery1.moveToNext()) {
//            Float str2 = rawQuery1.getFloat(0);
//            yljine.add(str2);
//        }
//        return yljine;
//    }
//
//    public ArrayList<Float> qitaJine() {
//        DBhelper dbHelper = new DBhelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String sql1 = "select jine from tb_outaccount where(leibie='其他')";//
//        Cursor rawQuery1 = db.rawQuery(sql1, null);
//        ArrayList<Float> qtjine = new ArrayList<Float>();
//        while (rawQuery1.moveToNext()) {
//            Float str2 = rawQuery1.getFloat(0);
//            qtjine.add(str2);
//        }
//        return qtjine;
//    }
//
//    public List<OutAccount> selectReferOutAccount(String str) {
//
//        List<OutAccount> ies = new ArrayList<OutAccount>();
//        DBhelper dbHelper = new DBhelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String sql = "select * from tb_outaccount where time Like '%" + str
//                + "%'";
//        Cursor rawQuery = db.rawQuery(sql, null);
//
//        while (rawQuery.moveToNext()) {
//            int id = rawQuery.getInt(0);
//            float jine = rawQuery.getFloat(1);
//            String time = rawQuery.getString(2);
//            String leibie = rawQuery.getString(3);
//            String addr = rawQuery.getString(4);
//            String memo = rawQuery.getString(5);
//            OutAccount OutAccount = new OutAccount(id, jine, time, leibie,
//                    addr, memo);
//            ies.add(OutAccount);
//        }
//        db.close();
//        return ies;
//    }
//    // ********************/////
//
//    // String sql2="select count(distinct leibie)from tb_outaccount";//类别数量
//    // Cursor rawQuery2=db.rawQuery(sql2, null);
//    // while(rawQuery2.moveToFirst()){
//
//    // }
//    // String[] leibieselect= db.sql;
//
//}
