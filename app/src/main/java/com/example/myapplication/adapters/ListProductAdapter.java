package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ClickItemInterface;
import com.example.myapplication.R;
import com.example.myapplication.models.Products;

import java.util.ArrayList;


public class ListProductAdapter extends  RecyclerView.Adapter<ListProductAdapter.ProductViewHolder>{
    private LayoutInflater layoutInflater;

    private ArrayList<Products> listProduct;

    private ConstraintLayout constraintLayout;

    private int selectedItem = -1;

    private ClickItemInterface clickItemInterface;

    public ListProductAdapter(Context context, ArrayList<Products> listProduct, ClickItemInterface clickItemInterface) {
        layoutInflater = layoutInflater.from(context);
        this.listProduct = listProduct;
        this.clickItemInterface = clickItemInterface;
    }

    @NonNull
    @Override
    public ListProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_product,parent,false);
        return new ProductViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        Products mCurrents = listProduct.get(position);

        holder.txtName.setText(mCurrents.getName());
        holder.txtPrice.setText(String.valueOf(mCurrents.getGia())+" $");
        holder.imgView.setImageResource(mCurrents.getHinh());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clickItemInterface.onCLickItemTable(listProduct.get(position));
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView txtName, txtPrice;
        ImageView imgView;

        ConstraintLayout constraintLayout;

        ListProductAdapter listProductAdapter;

        public ProductViewHolder(@NonNull View itemView, ListProductAdapter listProductAdapter) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            imgView = itemView.findViewById(R.id.img);
            constraintLayout = itemView.findViewById(R.id.ct);

            this.listProductAdapter = listProductAdapter;
        }
    }
}
