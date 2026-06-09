package com.example.batikstore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.batikstore.R;
import com.example.batikstore.model.Product;
import com.example.batikstore.util.PriceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public interface OnProductListener {
        void onItemClick(Product product);
        void onAddToCart(Product product);
        void onToggleFavorite(Product product);
    }

    private final List<Product> items = new ArrayList<>();
    private final OnProductListener listener;

    public ProductAdapter(OnProductListener listener) {
        this.listener = listener;
    }

    public void setItems(List<Product> newItems) {
        items.clear();
        if (newItems != null) items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        Product p = items.get(position);
        h.title.setText(p.getTitle());
        h.category.setText(p.getCategory() != null ? p.getCategory().toUpperCase(Locale.getDefault()) : "");
        h.price.setText(PriceUtil.formatRupiah(p.getPrice()));
        if (p.getRating() != null) {
            h.rating.setText(String.format(Locale.US, "★ %.1f", p.getRating().getRate()));
        } else {
            h.rating.setText("★ -");
        }
        Glide.with(h.itemView.getContext()).load(p.getImage())
                .centerCrop().placeholder(R.color.cream).error(R.drawable.ic_store).into(h.image);

        updateHeart(h, p.isFavorite());

        h.itemView.setOnClickListener(v -> { if (listener != null) listener.onItemClick(p); });
        h.btnAdd.setOnClickListener(v -> { if (listener != null) listener.onAddToCart(p); });
        h.btnFav.setOnClickListener(v -> {
            int pos = h.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;
            Product item = items.get(pos);
            item.setFavorite(!item.isFavorite());
            updateHeart(h, item.isFavorite());
            if (listener != null) listener.onToggleFavorite(item);
        });
    }

    private void updateHeart(ViewHolder h, boolean fav) {
        if (fav) {
            h.btnFav.setImageResource(R.drawable.ic_favorite);
            h.btnFav.setColorFilter(h.itemView.getContext().getColor(R.color.sale_red));
        } else {
            h.btnFav.setImageResource(R.drawable.ic_favorite_border);
            h.btnFav.setColorFilter(h.itemView.getContext().getColor(R.color.batik_brown_light));
        }
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, btnFav, btnAdd;
        TextView title, category, price, rating;

        ViewHolder(@NonNull View v) {
            super(v);
            image = v.findViewById(R.id.img_product);
            btnFav = v.findViewById(R.id.btn_favorite_card);
            btnAdd = v.findViewById(R.id.btn_add_cart);
            title = v.findViewById(R.id.tv_title);
            category = v.findViewById(R.id.tv_category);
            price = v.findViewById(R.id.tv_price);
            rating = v.findViewById(R.id.tv_rating);
        }
    }
}