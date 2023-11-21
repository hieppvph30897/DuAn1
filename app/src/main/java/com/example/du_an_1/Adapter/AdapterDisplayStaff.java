package com.example.du_an_1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.du_an_1.DAO.QuyenDAO;
import com.example.du_an_1.Model.NhanVien;
import com.example.du_an_1.R;


import java.util.List;

public class AdapterDisplayStaff extends BaseAdapter {

    Context context;
    int layout;
    List<NhanVien> nhanViens;
    ViewHolder viewHolder;
    QuyenDAO quyenDAO;

    public AdapterDisplayStaff(Context context, int layout, List<NhanVien> nhanViens){
        this.context = context;
        this.layout = layout;
        this.nhanViens = nhanViens;
        quyenDAO = new QuyenDAO(context);
    }

    @Override
    public int getCount() {
        return nhanViens.size();
    }

    @Override
    public Object getItem(int position) {
        return nhanViens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return nhanViens.get(position).getMANV();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.img_customstaff_HinhNV = (ImageView)view.findViewById(R.id.img_customstaff_HinhNV);
            viewHolder.txt_customstaff_TenNV = (TextView)view.findViewById(R.id.txt_customstaff_TenNV);
            viewHolder.txt_customstaff_TenQuyen = (TextView)view.findViewById(R.id.txt_customstaff_TenQuyen);
            viewHolder.txt_customstaff_SDT = (TextView)view.findViewById(R.id.txt_customstaff_SDT);
            viewHolder.txt_customstaff_Email = (TextView)view.findViewById(R.id.txt_customstaff_Email);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        NhanVien nhanVien = nhanViens.get(position);

        viewHolder.txt_customstaff_TenNV.setText(nhanVien.getHOTENNV());
        viewHolder.txt_customstaff_TenQuyen.setText(quyenDAO.LayTenQuyenTheoMa(nhanVien.getMAQUYEN()));
        viewHolder.txt_customstaff_SDT.setText(nhanVien.getSDT());
        viewHolder.txt_customstaff_Email.setText(nhanVien.getEMAIL());

        return view;
    }

    public class ViewHolder{
        ImageView img_customstaff_HinhNV;
        TextView txt_customstaff_TenNV, txt_customstaff_TenQuyen,txt_customstaff_SDT, txt_customstaff_Email;
    }
}
