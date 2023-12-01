package com.example.du_an_1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.du_an_1.DAO.BanAnDAO;
import com.example.du_an_1.R;
import com.google.android.material.textfield.TextInputLayout;


public class EditTableActivity extends AppCompatActivity {

    TextInputLayout TXTL_edittable_tenban;
    Button BTN_edittable_SuaBan;
    BanAnDAO banAnDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittable_layout);

        //thuộc tính view
        TXTL_edittable_tenban = (TextInputLayout)findViewById(R.id.txtl_edittable_tenban);
        BTN_edittable_SuaBan = (Button)findViewById(R.id.btn_edittable_SuaBan);

        //khởi tạo dao mở kết nối csdl
        banAnDAO = new BanAnDAO(this);
        int maban = getIntent().getIntExtra("maban",0); //lấy maban từ bàn đc chọn

        BTN_edittable_SuaBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateban()){
                    return;
                }
                String tenban = TXTL_edittable_tenban.getEditText().getText().toString();
                if(tenban != null || tenban.equals("")){
                    boolean ktra = banAnDAO.CapNhatTenBan(maban,tenban);
                    Intent intent = new Intent();
                    intent.putExtra("ketquasua",ktra);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
    //validate dữ liệu
    private boolean validateban(){
        String val = TXTL_edittable_tenban.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_edittable_tenban.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_edittable_tenban.setError(null);
            TXTL_edittable_tenban.setErrorEnabled(false);
            return true;
        }
    }
}