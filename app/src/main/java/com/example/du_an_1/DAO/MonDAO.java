package com.example.du_an_1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.du_an_1.Database.CreateDatabase;
import com.example.du_an_1.Model.MonAn;

import java.util.ArrayList;
import java.util.List;

public class MonDAO {
    SQLiteDatabase database;
    public MonDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemMon(MonAn monAn){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_MON_MALOAI, monAn.getMaLoai());
        contentValues.put(CreateDatabase.TBL_MON_TENMON, monAn.getTenMon());
        contentValues.put(CreateDatabase.TBL_MON_GIATIEN, monAn.getGiaTien());
        contentValues.put(CreateDatabase.TBL_MON_HINHANH, monAn.getHinhAnh());
        contentValues.put(CreateDatabase.TBL_MON_TINHTRANG,"true");

        long ktra = database.insert(CreateDatabase.TBL_MON,null,contentValues);

        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaMon(int mamon){
        long ktra = database.delete(CreateDatabase.TBL_MON,CreateDatabase.TBL_MON_MAMON+ " = " +mamon
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaMon(MonAn monAn, int mamon){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_MON_MALOAI, monAn.getMaLoai());
        contentValues.put(CreateDatabase.TBL_MON_TENMON, monAn.getTenMon());
        contentValues.put(CreateDatabase.TBL_MON_GIATIEN, monAn.getGiaTien());
        contentValues.put(CreateDatabase.TBL_MON_HINHANH, monAn.getHinhAnh());
        contentValues.put(CreateDatabase.TBL_MON_TINHTRANG, monAn.getTinhTrang());

        long ktra = database.update(CreateDatabase.TBL_MON,contentValues,
                CreateDatabase.TBL_MON_MAMON+" = "+mamon,null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }


    public List<MonAn> LayDSMonTheoLoai(int maloai){
        List<MonAn> monAnDTOList = new ArrayList<MonAn>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_MON+ " WHERE " +CreateDatabase.TBL_MON_MALOAI+ " = '" +maloai+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            MonAn monAn = new MonAn();
            monAn.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_MON_HINHANH)));
            monAn.setTenMon(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TENMON)));
            monAn.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_MALOAI)));
            monAn.setMaMon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_MAMON)));
            monAn.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_GIATIEN)));
            monAn.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TINHTRANG)));
            monAnDTOList.add(monAn);

            cursor.moveToNext();
        }
        return monAnDTOList;
    }

    public MonAn LayMonTheoMa(int mamon){
        MonAn monAnDTO = new MonAn();
        String query = "SELECT * FROM "+CreateDatabase.TBL_MON+" WHERE "+CreateDatabase.TBL_MON_MAMON+" = "+mamon;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            monAnDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_MON_HINHANH)));
            monAnDTO.setTenMon(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TENMON)));
            monAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_MALOAI)));
            monAnDTO.setMaMon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_MAMON)));
            monAnDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_GIATIEN)));
            monAnDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TINHTRANG)));

            cursor.moveToNext();
        }
        return monAnDTO;
    }

}
