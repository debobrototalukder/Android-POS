package com.rainingkitkat.mobilepos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private Context context;
    private List<Product> productList;
    Utils utils = new Utils();
    //dbHandler dbHandler = new dbHandler(context);

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_layout, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Product product = productList.get(position);
        holder.productTitle.setText("Name : " + product.getName());
        holder.productPrice.setText(product.getPrice());
        holder.productQuantity.setText(product.getQuantity());

        holder.deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), productList.size());
                //dbHandler.deleteItemFromCart(utils.getUsername(context), holder.productTitle.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productTitle;
        TextView productPrice;
        EditText productQuantity;
        ImageButton deleteCard;

        public ViewHolder(View itemView) {
            super(itemView);

            productTitle = itemView.findViewById(R.id.textViewTitle);
            productPrice = itemView.findViewById(R.id.textViewPrice);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            deleteCard = itemView.findViewById(R.id.close);


            productQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b){
                        Toast.makeText(context, "Words", Toast.LENGTH_SHORT).show();
                    }
                    int quantity = Integer.parseInt(productQuantity.getText().toString());
                    utils.setQuantity(context, quantity);
                }
            });
        }

    }
}
