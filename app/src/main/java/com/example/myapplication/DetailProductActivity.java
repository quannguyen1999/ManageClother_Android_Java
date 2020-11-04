package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.adapters.ListProductAdapter;
import com.example.myapplication.models.Products;

import java.util.ArrayList;

public class DetailProductActivity extends AppCompatActivity {
    //meta data
    ImageView imgView;

    TextView txtName, txtPrice;

    Button btnAdd, btnMinus, btnThanhToan;

    TextView txtNumber;

    //remember array in order
    ArrayList<Products> listOrder;

    //pass data detail to products
    Products products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        metaData();

        getData();

        responseListener();

        getSupportActionBar().hide();
    }

    private void responseListener() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = Integer.parseInt(txtNumber.getText().toString());
                number++;
                txtNumber.setText(String.valueOf(number));
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = Integer.parseInt(txtNumber.getText().toString());
                if (number >= 1) {
                    number--;
                    txtNumber.setText(String.valueOf(number));
                }

            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(DetailProductActivity.this)
                        .setTitle("Delete?")
                        .setMessage("Are you sure ? ")
                        .setPositiveButton("Tiếp tục mua", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (listOrder == null) {
                                    listOrder = new ArrayList<>();
                                }
                                listOrder.add(products);
                                Intent intent1 = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("listOrder", listOrder);
                                intent1.putExtra("add", bundle);
                                setResult(RESULT_OK, intent1);
                                finish();
                            }
                        })
                        .setNegativeButton("Thanh toán luôn", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (listOrder == null) {
                                    listOrder = new ArrayList<>();
                                }
                                listOrder.add(products);
                                Intent intent1 = new Intent(DetailProductActivity.this, PayActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("listOrder", listOrder);
                                intent1.putExtra("pay", bundle);
                                startActivity(intent1);
                            }
                        })
                        .show();
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent.hasExtra("view")) {
            final Bundle bundle = intent.getBundleExtra("view");
            products = (Products) bundle.getSerializable("product");
            listOrder = (ArrayList<Products>) bundle.getSerializable("listOrder");
            txtName.setText(products.getName());
            txtPrice.setText(String.valueOf(products.getGia()) + " $");
            imgView.setImageResource(products.getHinh());
        }
    }

    private void metaData() {
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        imgView = findViewById(R.id.img);
        txtNumber = findViewById(R.id.txtNumber);

        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnAdd = findViewById(R.id.btnAdd);
        btnMinus = findViewById(R.id.btnMinus);
    }
}