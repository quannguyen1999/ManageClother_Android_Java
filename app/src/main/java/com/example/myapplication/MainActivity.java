package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.adapters.ListProductAdapter;
import com.example.myapplication.models.Products;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickItemInterface {
    //adapter
    private ListProductAdapter listProductAdapter;

    //array list
    private ArrayList<Products> listProduct;

    private ArrayList<Products> listOrderX;

    private ArrayList<Products> listFind;

    //meta data
    private RecyclerView rclView;

    private TextView txtCount;

    private ImageButton imgBtnThanhToan;

    private ImageButton btnSearch;

    private EditText txtSearch;

    private Button btnReset;

    //request code
    private static final int ADD = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //meta data
        metaData();

        //receive data when other activity send
        receiveData();

        //init data products
        initData();

        //set on listner button
        responseListener();

        //hide app bar
        getSupportActionBar().hide();

    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        System.out.println(listFind.size());
//        outState.putSerializable("listfind", listFind);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
////        ArrayList<Products> listFindD = (ArrayList<Products>) savedInstanceState.getSerializable("listfind");
////        System.out.println(listFindD.size());
////        txtSearch.setText("");
//    }

    private void responseListener() {
        imgBtnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listOrderX == null || listOrderX.size() <= 0) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Chưa chọn hàng ");
                    alert.setMessage("VUi lòng chọn :(");
                    alert.setNegativeButton("Ok", null);
                    alert.show();
                } else {
                    Intent intent1 = new Intent(MainActivity.this, PayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("listOrder", listOrderX);
                    intent1.putExtra("pay", bundle);
                    startActivity(intent1);
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = txtSearch.getText().toString();
                if (text.isEmpty()) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Chưa nhập search");
                    alert.setMessage("Vui lòng enter...");
                    alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            txtSearch.requestFocus();
                        }
                    });
                    alert.show();
                } else {
                    listFind = new ArrayList<>();
                    for (Products products : listProduct) {
                        if (products.getName().equalsIgnoreCase(text)) {
                            listFind.add(products);
                        }
                    }
                    ClickItemInterface clickItemInterface = new ClickItemInterface() {
                        @Override
                        public void onCLickItemTable(Products products) {
                            //send activity to DetailProductActiviry to handle
                            Intent intent = new Intent(MainActivity.this, DetailProductActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("product", products);
                            bundle.putSerializable("listOrder", listOrderX);
                            intent.putExtra("view", bundle);
                            startActivityForResult(intent, ADD);
                        }
                    };

                    listProductAdapter = new ListProductAdapter(MainActivity.this, listFind, clickItemInterface);

                    rclView.setAdapter(listProductAdapter);

                    rclView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

                    closeKeyBoard();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });
    }

    private void receiveData() {
        Intent intent = getIntent();
        if (intent.hasExtra("emptyData")) {
            listOrderX = new ArrayList<>();
        }
    }

    private void initData() {
        listProduct = new ArrayList<>();

        listProduct.add(new Products("P01", "mai", 100, 10, R.drawable.girl01));

        listProduct.add(new Products("P01", "phượng", 200, 10, R.drawable.girl01));

        listProduct.add(new Products("P01", "huyền", 300, 10, R.drawable.girl01));

        listProduct.add(new Products("P01", "tâm", 400, 10, R.drawable.girl01));

        listProduct.add(new Products("P01", "bitch", 500, 10, R.drawable.girl01));

        listProductAdapter = new ListProductAdapter(this, listProduct, this);

        rclView.setAdapter(listProductAdapter);

        rclView.setLayoutManager(new GridLayoutManager(this, 2));

//        rclView.setHasFixedSize(true);
    }

    private void metaData() {
        btnReset = findViewById(R.id.btnReset);
        txtCount = findViewById(R.id.txtCount);
        rclView = findViewById(R.id.rclView);
        imgBtnThanhToan = findViewById(R.id.imgBtnThanhToan);
        txtSearch = findViewById(R.id.txtSearch);
        btnSearch = findViewById(R.id.btnSearch);
    }

    @Override
    public void onCLickItemTable(Products products) {
        //send activity to DetailProductActiviry to handle
        Intent intent = new Intent(this, DetailProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", products);
        bundle.putSerializable("listOrder", listOrderX);
        intent.putExtra("view", bundle);
        startActivityForResult(intent, ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getBundleExtra("add");
                ArrayList<Products> listOrder = (ArrayList<Products>) bundle.getSerializable("listOrder");
                txtCount.setText(String.valueOf(listOrder.size()));
                listOrderX = listOrder;
            }
        }
    }

    private void closeKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}