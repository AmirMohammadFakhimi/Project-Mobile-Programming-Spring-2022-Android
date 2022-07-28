package edu.sharif.cryptocurrency.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.sharif.cryptocurrency.CryptocurrenciesRecyclerViewAdaptor;
import edu.sharif.cryptocurrency.Cryptocurrency;
import edu.sharif.cryptocurrency.CryptocurrencyViewModel;
import edu.sharif.cryptocurrency.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements CryptocurrenciesRecyclerViewAdaptor.ItemClickListener {

    private FragmentHomeBinding binding;
    private CryptocurrencyViewModel viewModel;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageButton refreshButton;
    private CryptocurrenciesRecyclerViewAdaptor adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = binding.progressBar;
        textView = binding.textView;
        refreshButton = binding.refreshButton;
        refreshButton.setOnClickListener(view1 -> getData());

        viewModel = ViewModelProviders.of(this).get(CryptocurrencyViewModel.class);
    }

    public void getData() {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.getCryptocurrencies(this.getView())
                .observe(getViewLifecycleOwner(), this::doOnObserve);
    }

    @SuppressLint("SetTextI18n")
    private void doOnObserve(List<Cryptocurrency> cryptocurrencies) {
        binding.progressBar.setVisibility(View.INVISIBLE);

        RecyclerView cryptocurrenciesRecyclerView = binding.cryptocurrenciesList;
        cryptocurrenciesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CryptocurrenciesRecyclerViewAdaptor(getContext(), cryptocurrencies, viewModel);
        cryptocurrenciesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}