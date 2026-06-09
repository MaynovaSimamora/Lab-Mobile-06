package com.example.batikstore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batikstore.DetailActivity;
import com.example.batikstore.R;
import com.example.batikstore.adapter.ProductAdapter;
import com.example.batikstore.db.AppDatabase;
import com.example.batikstore.db.CartDao;
import com.example.batikstore.model.CartItem;
import com.example.batikstore.model.Product;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteFragment extends Fragment {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private ProductAdapter adapter;
    private TextView tvEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_favorite);
        tvEmpty = view.findViewById(R.id.tv_empty);

        adapter = new ProductAdapter(new ProductAdapter.OnProductListener() {
            @Override public void onItemClick(Product product) { openDetail(product); }
            @Override public void onAddToCart(Product product) { addToCart(product); }
            @Override public void onToggleFavorite(Product product) { unfavorite(product); }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void openDetail(Product product) {
        Intent intent = new Intent(requireContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_PRODUCT, product);
        startActivity(intent);
    }

    private void addToCart(Product p) {
        executor.execute(() -> {
            CartDao dao = AppDatabase.getInstance(requireContext()).cartDao();
            CartItem existing = dao.getById(p.getId());
            if (existing != null) {
                dao.updateQty(p.getId(), existing.getQuantity() + 1);
            } else {
                CartItem item = new CartItem();
                item.setProductId(p.getId());
                item.setTitle(p.getTitle());
                item.setPrice(p.getPrice());
                item.setImage(p.getImage());
                item.setQuantity(1);
                dao.insert(item);
            }
            mainHandler.post(() ->
                    Toast.makeText(requireContext(), "Ditambahkan ke keranjang", Toast.LENGTH_SHORT).show());
        });
    }

    private void unfavorite(Product p) {
        executor.execute(() -> {
            AppDatabase.getInstance(requireContext()).productDao().setFavorite(p.getId(), p.isFavorite());
            mainHandler.post(this::loadFavorites);
        });
    }

    private void loadFavorites() {
        executor.execute(() -> {
            List<Product> favorites = AppDatabase.getInstance(requireContext()).productDao().getFavorites();
            mainHandler.post(() -> {
                adapter.setItems(favorites);
                tvEmpty.setVisibility(favorites.isEmpty() ? View.VISIBLE : View.GONE);
            });
        });
    }
}