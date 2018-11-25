package com.rainingkitkat.mobilepos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    dbHandler db;
    Utils utils;
    MainActivity mainActivity;

    private String username;
    private String balance;
    private double totalAmountDouble = 0;

    private TextView balanceTV;
    private TextView totalAmount;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;

    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        utils = new Utils();
        db = new dbHandler(this);
        mainActivity = new MainActivity();

        balanceTV = findViewById(R.id.balance);
        totalAmount = findViewById(R.id.total_amount);
        recyclerView = findViewById(R.id.recycler_view);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        username = utils.getUsername(this);

        balance = db.getUserBalance(username);

        balanceTV.setText("Balance : " + balance + "Dhs");
        totalAmount.setText("0 Dhs");

        productList = new ArrayList<>();

        addToCart();
        totalAmount.setText(totalAmountDouble + "");

        //totalAmount.setText();

    }

    public void addToCart(){
        // Adds All The Products To The View
        for (int i = 0; i < db.getProductName().size(); i++){
            productList.add(new Product(db.getProductName().get(i), db.getProductPrice().get(i), db.getProductQuantity().get(i)));
            totalAmountDouble += Double.parseDouble(db.getProductPrice().get(i));
        }

        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);
    }

    public void backBtn(View view){
        finish();
    }

    public void confirmPurchase(View view){
        //ToDo : This is supposed to take you to the home page
        finish();
        db.deductBalance(totalAmountDouble, utils.getUsername(this));
        balance = db.getUserBalance(username);
        Log.d("Cart", "---------------->"+ balance);

        balanceTV.setText("Balance : " + balance + "Dhs");
    }

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
        public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.list_layout, null);
            ProductAdapter.ViewHolder viewHolder = new ProductAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ProductAdapter.ViewHolder holder, int position) {
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
                    String usernameTemp = utils.getUsername(mainActivity.context);
                    dbHandler.deleteItemFromCart(usernameTemp, holder.productTitle.getText().toString());
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
            ImageButton deleteCard, addBtn, subBtn;

            public ViewHolder(View itemView) {
                super(itemView);

                productTitle = itemView.findViewById(R.id.textViewTitle);
                productPrice = itemView.findViewById(R.id.textViewPrice);
                productQuantity = itemView.findViewById(R.id.product_quantity);
                deleteCard = itemView.findViewById(R.id.close);
                addBtn = itemView.findViewById(R.id.add_button);
                subBtn = itemView.findViewById(R.id.subtract_button);

                productQuantity.setEnabled(false);

                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        productQuantity.setText(Integer.parseInt(productQuantity.getText().toString()) + 1 + "");
                        totalAmountDouble = totalAmountDouble + Double.parseDouble(productPrice.getText().toString());
                        totalAmount.setText(totalAmountDouble + "");
                    }
                });

                subBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Integer.parseInt(productQuantity.getText().toString()) <= 1){
                            Toast.makeText(Cart.this, "Cannot Go Lower", Toast.LENGTH_SHORT).show();

                        } else {
                            productQuantity.setText(Integer.parseInt(productQuantity.getText().toString()) - 1 + "");
                            totalAmountDouble = totalAmountDouble - Double.parseDouble(productPrice.getText().toString());
                            totalAmount.setText(totalAmountDouble + "");
                        }
                    }
                });
            }

        }
    }

    //ToDo : Do This Based On User ID

}


