package com.example.du_an_1.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_1.Activities.AddCategoryActivity;
import com.example.du_an_1.Activities.HomeActivity;
import com.example.du_an_1.Adapter.AdapterRecycleViewCategory;
import com.example.du_an_1.Adapter.AdapterRecycleViewStatistic;
import com.example.du_an_1.DAO.DonDatDAO;
import com.example.du_an_1.DAO.LoaiMonDAO;

import com.example.du_an_1.Model.DonDat;
import com.example.du_an_1.Model.LoaiMon;
import com.example.du_an_1.R;
import com.google.android.material.navigation.NavigationView;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DisplayHomeFragment extends Fragment implements View.OnClickListener {

    RecyclerView rcv_displayhome_LoaiMon, rcv_displayhome_DonTrongNgay;
    RelativeLayout layout_displayhome_ThongKe,layout_displayhome_XemBan, layout_displayhome_XemMenu, layout_displayhome_XemNV;
    TextView txt_displayhome_ViewAllCategory, txt_displayhome_ViewAllStatistic;
    LoaiMonDAO loaiMonDAO;
    DonDatDAO donDatDAO;
    List<LoaiMon> loaiMonDTOList;
    List<DonDat> donDatDTOS;
    AdapterRecycleViewCategory adapterRecycleViewCategory;
    AdapterRecycleViewStatistic adapterRecycleViewStatistic;
    int maquyen1 = 0;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displayhome_layout,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Trang chủ");
        setHasOptionsMenu(true);

        //region Lấy dối tượng view
        rcv_displayhome_LoaiMon = (RecyclerView)view.findViewById(R.id.rcv_displayhome_LoaiMon);
        rcv_displayhome_DonTrongNgay = (RecyclerView)view.findViewById(R.id.rcv_displayhome_DonTrongNgay);
        layout_displayhome_ThongKe = (RelativeLayout)view.findViewById(R.id.layout_displayhome_ThongKe);
        layout_displayhome_XemBan = (RelativeLayout)view.findViewById(R.id.layout_displayhome_XemBan);
        layout_displayhome_XemMenu = (RelativeLayout)view.findViewById(R.id.layout_displayhome_XemMenu);
        layout_displayhome_XemNV = (RelativeLayout)view.findViewById(R.id.layout_displayhome_XemNV);
        txt_displayhome_ViewAllCategory = (TextView) view.findViewById(R.id.txt_displayhome_ViewAllCategory);
        txt_displayhome_ViewAllStatistic = (TextView) view.findViewById(R.id.txt_displayhome_ViewAllStatistic);
        //endregion

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen1 = sharedPreferences.getInt("maquyen",0);


        //khởi tạo kết nối
        loaiMonDAO = new LoaiMonDAO(getActivity());
        donDatDAO = new DonDatDAO(getActivity());

        HienThiDSLoai();
        HienThiDonTrongNgay();

        layout_displayhome_ThongKe.setOnClickListener(this);
        layout_displayhome_XemBan.setOnClickListener(this);
        layout_displayhome_XemMenu.setOnClickListener(this);
        layout_displayhome_XemNV.setOnClickListener(this);
        txt_displayhome_ViewAllCategory.setOnClickListener(this);
        txt_displayhome_ViewAllStatistic.setOnClickListener(this);

        return view;
    }

    private void HienThiDSLoai(){
        rcv_displayhome_LoaiMon.setHasFixedSize(true);
        rcv_displayhome_LoaiMon.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        loaiMonDTOList = loaiMonDAO.LayDSLoaiMon();
        adapterRecycleViewCategory = new AdapterRecycleViewCategory(getActivity(),R.layout.custom_layout_displaycategory,loaiMonDTOList);
        rcv_displayhome_LoaiMon.setAdapter(adapterRecycleViewCategory);
        adapterRecycleViewCategory.notifyDataSetChanged();
    }

    private void HienThiDonTrongNgay(){
        rcv_displayhome_DonTrongNgay.setHasFixedSize(true);
        rcv_displayhome_DonTrongNgay.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngaydat= dateFormat.format(calendar.getTime());

        donDatDTOS = donDatDAO.LayDSDonDatNgay(ngaydat);
        adapterRecycleViewStatistic = new AdapterRecycleViewStatistic(getActivity(),R.layout.custom_layout_displaystatistic,donDatDTOS);
        rcv_displayhome_DonTrongNgay.setAdapter(adapterRecycleViewStatistic);
        adapterRecycleViewCategory.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        NavigationView navigationView = (NavigationView)getActivity().findViewById(R.id.navigation_view_trangchu);
        switch (id){
            case R.id.layout_displayhome_ThongKe:

            case R.id.txt_displayhome_ViewAllStatistic:
                if (maquyen1 == 1) {
                    FragmentTransaction tranDisplayStatistic = getActivity().getSupportFragmentManager().beginTransaction();
                    tranDisplayStatistic.replace(R.id.contentView,new DisplayStatisticFragment());
                    tranDisplayStatistic.addToBackStack(null);
                    tranDisplayStatistic.commit();
                    navigationView.setCheckedItem(R.id.nav_statistic);
                }else {
                    Toast.makeText(getActivity(), "Phải lên chức quản lý thì mới được sử dụng hoặc xem chức năng!", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.layout_displayhome_XemBan:

                    FragmentTransaction tranDisplayTable = getActivity().getSupportFragmentManager().beginTransaction();
                    tranDisplayTable.replace(R.id.contentView,new DisplayTableFragment());
                    tranDisplayTable.addToBackStack(null);
                    tranDisplayTable.commit();
                    navigationView.setCheckedItem(R.id.nav_table);


                break;

            case R.id.layout_displayhome_XemMenu:
                if (maquyen1 == 1) {
                    Intent iAddCategory = new Intent(getActivity(), AddCategoryActivity.class);
                    startActivity(iAddCategory);
                    navigationView.setCheckedItem(R.id.nav_category);
                } else {
                    Toast.makeText(getActivity(), "Bạn không có quyền truy cập!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.layout_displayhome_XemNV:
                if (maquyen1 == 1) {
                    FragmentTransaction tranDisplayStaff= getActivity().getSupportFragmentManager().beginTransaction();
                    tranDisplayStaff.replace(R.id.contentView,new DisplayStaffFragment());
                    tranDisplayStaff.addToBackStack(null);
                    tranDisplayStaff.commit();
                    navigationView.setCheckedItem(R.id.nav_staff);
                }else {
                    Toast.makeText(getActivity(), "Bạn không có quyền truy cập!", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.txt_displayhome_ViewAllCategory:
                if (maquyen1 == 1) {
                    FragmentTransaction tranDisplayCategory = getActivity().getSupportFragmentManager().beginTransaction();
                    tranDisplayCategory.replace(R.id.contentView,new DisplayCategoryFragment());
                    tranDisplayCategory.addToBackStack(null);
                    tranDisplayCategory.commit();
                    navigationView.setCheckedItem(R.id.nav_category);

                }else {
                    Toast.makeText(getActivity(), "Phải lên chức quản lý thì mới được sử dụng hoặc xem chức năng!", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}

