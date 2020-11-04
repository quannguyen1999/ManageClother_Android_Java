package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.adapters.ListOrderAdapter;
import com.example.myapplication.adapters.ListProductAdapter;
import com.example.myapplication.models.Products;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PayActivity extends AppCompatActivity{
    //adapter
    private ListOrderAdapter listOrderAdapter;

    //list
    private ArrayList<Products> listProduct;

    //meta data
    private RecyclerView rclView;

    private TextView txtTotal;

    private Button btnThanhToan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        metaData();

        getData();

        responseListener();

        getSupportActionBar().hide();
    }

    private void responseListener() {
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(PayActivity.this);
                alert.setTitle("success");
                alert.setMessage("Thanh toán thành công...");
                alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(PayActivity.this
                                , MainActivity.class);
                        Bundle bundle = new Bundle();
                        intent.putExtra("emptyData", bundle);
                        startActivity(intent);
                    }
                });
                alert.show();

            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent.hasExtra("pay")) {
            final Bundle bundle = intent.getBundleExtra("pay");
            listProduct = (ArrayList<Products>) bundle.getSerializable("listOrder");
            initData(listProduct);
        }
    }


    private void initData(ArrayList<Products> listProduct) {
        for (Products products : listProduct) {
            System.out.println(products);
        }
        listOrderAdapter = new ListOrderAdapter(this, listProduct);

        rclView.setAdapter(listOrderAdapter);

        rclView.setLayoutManager(new LinearLayoutManager(this));

        int total = 0;

        for (Products products : listProduct) {
            total += (products.getGia());
        }

        txtTotal.setText(String.valueOf(total) + " $");
    }

    private void metaData() {

        btnThanhToan = findViewById(R.id.btnThanhToan);

        txtTotal = findViewById(R.id.txtTotal);

        rclView = findViewById(R.id.rclView);
    }
}