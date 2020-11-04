package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CLickPayActivity;
import com.example.myapplication.ClickItemInterface;
import com.example.myapplication.R;
import com.example.myapplication.models.Products;

import java.util.ArrayList;


public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.ProductViewHolder> {
    private LayoutInflater layoutInflater;

    private ArrayList<Products> listProduct;

    private ConstraintLayout constraintLayout;

    private int selectedItem = -1;

    CLickPayActivity cLickPayActivity;

    public ListOrderAdapter(Context context, ArrayList<Products> listProduct, CLickPayActivity cLickPayActivity) {
        layoutInflater = layoutInflater.from(context);
        this.listProduct = listProduct;
        this.cLickPayActivity = cLickPayActivity;
    }

    @NonNull
    @Override
    public ListOrderAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_order, parent, false);
        return new ProductViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        Products mCurrents = listProduct.get(position);

        holder.txtName.setText(mCurrents.getName());
        holder.txtPrice.setText(String.valueOf(mCurrents.getGia()) + " $");
        holder.img.setImageResource(mCurrents.getHinh());

        holder.constraintLayout.setBackgroundColor(Color.TRANSPARENT);
        holder.txtName.setTextColor(Color.BLACK);
        holder.txtPrice.setTextColor(Color.BLACK);
        holder.txtContent.setTextColor(Color.BLACK);

        if (selectedItem == position) {
            holder.constraintLayout.setBackgroundColor(Color.BLACK);
            holder.txtName.setTextColor(Color.WHITE);
            holder.txtPrice.setTextColor(Color.WHITE);
            holder.txtContent.setTextColor(Color.WHITE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int previousItem = selectedItem;
                selectedItem = position;
                ListOrderAdapter.this.notifyItemChanged(previousItem);
                ListOrderAdapter.this.notifyItemChanged(position);
            }
        });

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listProduct.remove(listProduct.get(position));

                cLickPayActivity.onCLickItemTable(listProduct);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice, txtContent;
        ImageView img;

        ConstraintLayout constraintLayout;

        ListOrderAdapter listProductAdapter;

        Button btnXoa;

        public ProductViewHolder(@NonNull View itemView, ListOrderAdapter listProductAdapter) {
            super(itemView);

            btnXoa = itemView.findViewById(R.id.btnXoa);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtContent = itemView.findViewById(R.id.txtContent);
            img = itemView.findViewById(R.id.img);
            constraintLayout = itemView.findViewById(R.id.ct);

            this.listProductAdapter = listProductAdapter;
        }
    }
}
