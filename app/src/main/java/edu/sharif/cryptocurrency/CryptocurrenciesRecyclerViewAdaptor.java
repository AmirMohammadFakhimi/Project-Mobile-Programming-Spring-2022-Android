package edu.sharif.cryptocurrency;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CryptocurrenciesRecyclerViewAdaptor extends RecyclerView.Adapter<CryptocurrenciesRecyclerViewAdaptor.ViewHolder> {
    private final List<Cryptocurrency> cryptocurrencies;
    private final LayoutInflater inflater;
    private ItemClickListener clickListener;
    private final Context context;
    private final CryptocurrencyViewModel viewModel;

    public CryptocurrenciesRecyclerViewAdaptor(Context context, List<Cryptocurrency> cryptocurrencies,
                                               CryptocurrencyViewModel viewModel) {
        this.inflater = LayoutInflater.from(context);
        this.cryptocurrencies = cryptocurrencies;
        this.context = context;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cryptocurrencies_recycler_view_row, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cryptocurrency cryptocurrency = cryptocurrencies.get(position);

//        holder.icon.setImageDrawable();

        holder.name.setText(cryptocurrency.getName());
        try {
            holder.percentage.setText(String.valueOf(cryptocurrency.getPrice()));
        } catch (Exception e) {
            holder.percentage.setText("Error");
        }

//        Log.d("hiii", cryptocurrency.getName() + " " + cryptocurrency.getFavorite());
        if (cryptocurrency.getFavorite())
            holder.starButton.setImageDrawable(context.getDrawable(android.R.drawable.btn_star_big_on));
        else
            holder.starButton.setImageDrawable(context.getDrawable(android.R.drawable.btn_star_big_off));

        holder.starButton.setOnClickListener(v -> {
            Drawable star_off = ResourcesCompat.getDrawable(
                    v.getResources(), android.R.drawable.btn_star_big_off, null);
            Drawable star_on = ResourcesCompat.getDrawable(
                    v.getResources(), android.R.drawable.btn_star_big_on, null);

            if (holder.starButton.getDrawable().getConstantState().equals(star_off.getConstantState())) {
//                Log.d("hiii", ":)");
                holder.starButton.setImageDrawable(star_on);
                cryptocurrency.setFavorite(true);
            } else {
                holder.starButton.setImageDrawable(star_off);
                cryptocurrency.setFavorite(false);
            }

            viewModel.updateFavorite(cryptocurrency.getSymbol(), cryptocurrency.getFavorite());
        });
    }

    @Override
    public int getItemCount() {
        return cryptocurrencies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView icon;
        TextView name;
        TextView percentage;
        ImageButton starButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            percentage = itemView.findViewById(R.id.price);
            starButton = itemView.findViewById(R.id.starButton);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public Cryptocurrency getItem(int id) {
        return cryptocurrencies.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clear() {
        cryptocurrencies.clear();
        notifyDataSetChanged();
    }
}
