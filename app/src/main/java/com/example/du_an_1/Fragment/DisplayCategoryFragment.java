package com.example.du_an_1.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.du_an_1.Activities.AddCategoryActivity;
import com.example.du_an_1.Activities.HomeActivity;
import com.example.du_an_1.Adapter.AdapterDisplayCategory;
import com.example.du_an_1.DAO.LoaiMonDAO;
import com.example.du_an_1.Model.LoaiMon;
import com.example.du_an_1.R;


import java.util.List;

public class DisplayCategoryFragment extends Fragment {

    GridView gvCategory;
    List<LoaiMon> loaiMonList;
    LoaiMonDAO loaiMonDAO;
    AdapterDisplayCategory adapter;
    FragmentManager fragmentManager;
    int maban;
    int maquyen = 0;
    SharedPreferences sharedPreferences;

    ActivityResultLauncher<Intent> resultLauncherCategory = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean ktra = intent.getBooleanExtra("ktra",false);
                        String chucnang = intent.getStringExtra("chucnang");
                        if(chucnang.equals("themloai"))
                        {
                            if(ktra){
                                HienThiDSLoai();
                                Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(ktra){
                                HienThiDSLoai();
                                Toast.makeText(getActivity(),"Sủa thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"sửa thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.displaycategory_layout,container,false);
        setHasOptionsMenu(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Quản lý thực đơn");

        gvCategory = (GridView)view.findViewById(R.id.gvCategory);

        fragmentManager = getActivity().getSupportFragmentManager();

        loaiMonDAO = new LoaiMonDAO(getActivity());
        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);
        HienThiDSLoai();

        Bundle bDataCategory = getArguments();
        if(bDataCategory != null){
            maban = bDataCategory.getInt("maban");
        }

        gvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int maloai = loaiMonList.get(position).getMaLoai();
                String tenloai = loaiMonList.get(position).getTenLoai();
                DisplayMenuFragment displayMenuFragment = new DisplayMenuFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("maloai",maloai);
                bundle.putString("tenloai",tenloai);
                bundle.putInt("maban",maban);
                displayMenuFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.contentView,displayMenuFragment).addToBackStack("hienthiloai");
                transaction.commit();
            }
        });

        registerForContextMenu(gvCategory);

        return view;
    }

    //hiển thị contextmenu
    @Override
    public void onCreateContextMenu(ContextMenu menu,View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //xử lí context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maloai = loaiMonList.get(vitri).getMaLoai();

        switch (id){
            case R.id.itEdit:
                if (maquyen == 1) {
                    Intent iEdit = new Intent(getActivity(), AddCategoryActivity.class);
                    iEdit.putExtra("maloai",maloai);
                    resultLauncherCategory.launch(iEdit);
                }else {
                    Toast.makeText(getActivity(),"Bạn không có quyền để dùng chức năng sửa!",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.itDelete:
                if (maquyen == 1) {
                    boolean ktra = loaiMonDAO.XoaLoaiMon(maloai);
                    if(ktra){
                        HienThiDSLoai();
                        Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_sucessful)
                                ,Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_failed)
                                ,Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(),"Bạn không có quyền để dùng chức năng xoá!",Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return true;
    }

    //khởi tạo nút thêm loại
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddCategory = menu.add(1,R.id.itAddCategory,1,R.string.addCategory);
        itAddCategory.setIcon(R.drawable.ic_baseline_add_24);
        itAddCategory.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    //xử lý nút thêm loại
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddCategory:
                if (maquyen == 1) {
                    Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
                    resultLauncherCategory.launch(intent);
                }else {
                    Toast.makeText(getActivity(),"Phải lên chức quản lý thì mới được sử dụng hoặc xem chức năng!",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //hiển thị dữ liệu trên gridview
    private void HienThiDSLoai(){
        loaiMonList = loaiMonDAO.LayDSLoaiMon();
        adapter = new AdapterDisplayCategory(getActivity(),R.layout.custom_layout_displaycategory, loaiMonList);
        gvCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
