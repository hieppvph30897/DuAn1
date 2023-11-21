package com.example.du_an_1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.du_an_1.Model.NhanVien;
import com.example.du_an_1.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    SQLiteDatabase database;
    public NhanVienDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemNhanVien(NhanVien nhanVien){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_NHANVIEN_HOTENNV, nhanVien.getHOTENNV());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_TENDN, nhanVien.getTENDN());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_MATKHAU, nhanVien.getMATKHAU());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_EMAIL, nhanVien.getEMAIL());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_SDT, nhanVien.getSDT());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_GIOITINH, nhanVien.getGIOITINH());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_NGAYSINH, nhanVien.getNGAYSINH());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_MAQUYEN, nhanVien.getMAQUYEN());

        long ktra = database.insert(CreateDatabase.TBL_NHANVIEN,null,contentValues);
        return ktra;
    }

    public long SuaNhanVien(NhanVien nhanVien, int manv){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_NHANVIEN_HOTENNV, nhanVien.getHOTENNV());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_TENDN, nhanVien.getTENDN());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_MATKHAU, nhanVien.getMATKHAU());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_EMAIL, nhanVien.getEMAIL());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_SDT, nhanVien.getSDT());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_GIOITINH, nhanVien.getGIOITINH());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_NGAYSINH, nhanVien.getNGAYSINH());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_MAQUYEN, nhanVien.getMAQUYEN());

        long ktra = database.update(CreateDatabase.TBL_NHANVIEN,contentValues,
                CreateDatabase.TBL_NHANVIEN_MANV+" = "+manv,null);
        return ktra;
    }

    public int KiemTraDN(String tenDN, String matKhau){
        String query = "SELECT * FROM " +CreateDatabase.TBL_NHANVIEN+ " WHERE "
                +CreateDatabase.TBL_NHANVIEN_TENDN +" = '"+ tenDN+"' AND "+CreateDatabase.TBL_NHANVIEN_MATKHAU +" = '" +matKhau +"'";
        int manv = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            manv = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MANV)) ;
            cursor.moveToNext();
        }
        return manv;
    }

    public boolean KtraTonTaiNV(){
        String query = "SELECT * FROM "+CreateDatabase.TBL_NHANVIEN;
        Cursor cursor =database.rawQuery(query,null);
        if(cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<NhanVien> LayDSNV(){
        List<NhanVien> nhanVienDTOS = new ArrayList<NhanVien>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_NHANVIEN;

        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            NhanVien nhanVien = new NhanVien();
            nhanVien.setHOTENNV(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_HOTENNV)));
            nhanVien.setEMAIL(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_EMAIL)));
            nhanVien.setGIOITINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_GIOITINH)));
            nhanVien.setNGAYSINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_NGAYSINH)));
            nhanVien.setSDT(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_SDT)));
            nhanVien.setTENDN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_TENDN)));
            nhanVien.setMATKHAU(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MATKHAU)));
            nhanVien.setMANV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MANV)));
            nhanVien.setMAQUYEN(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MAQUYEN)));

            nhanVienDTOS.add(nhanVien);
            cursor.moveToNext();
        }
        return nhanVienDTOS;
    }

    public boolean XoaNV(int manv){
        long ktra = database.delete(CreateDatabase.TBL_NHANVIEN,CreateDatabase.TBL_NHANVIEN_MANV+ " = " +manv
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public NhanVien LayNVTheoMa(int manv){
        NhanVien nhanVien = new NhanVien();
        String query = "SELECT * FROM "+CreateDatabase.TBL_NHANVIEN+" WHERE "+CreateDatabase.TBL_NHANVIEN_MANV+" = "+manv;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            nhanVien.setHOTENNV(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_HOTENNV)));
            nhanVien.setEMAIL(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_EMAIL)));
            nhanVien.setGIOITINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_GIOITINH)));
            nhanVien.setNGAYSINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_NGAYSINH)));
            nhanVien.setSDT(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_SDT)));
            nhanVien.setTENDN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_TENDN)));
            nhanVien.setMATKHAU(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MATKHAU)));
            nhanVien.setMANV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MANV)));
            nhanVien.setMAQUYEN(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MAQUYEN)));

            cursor.moveToNext();
        }
        return nhanVien;
    }

    public int LayQuyenNV(int manv){
        int maquyen = 0;
        String query = "SELECT * FROM "+CreateDatabase.TBL_NHANVIEN+" WHERE "+CreateDatabase.TBL_NHANVIEN_MANV+" = "+manv;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            maquyen = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MAQUYEN));

            cursor.moveToNext();
        }
        return maquyen;
    }
}
