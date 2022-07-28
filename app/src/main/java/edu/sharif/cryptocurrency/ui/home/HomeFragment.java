package edu.sharif.cryptocurrency.ui.home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import edu.sharif.cryptocurrency.Cryptocurrency;
import edu.sharif.cryptocurrency.CryptocurrencyViewModel;
import edu.sharif.cryptocurrency.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private CryptocurrencyViewModel viewModel;
    private ProgressBar progressBar;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar= binding.progressBar;
        textView= binding.textView;

//        submitButton.setOnClickListener(view1 -> goForSubmit());
        viewModel = ViewModelProviders.of(this).get(CryptocurrencyViewModel.class);
    }

    public void getData() {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.getCryptocurrencies(this.getView())
                .observe(getViewLifecycleOwner(), this::doOnObserve);
    }

    @SuppressLint("SetTextI18n")
    private void doOnObserve(ArrayList<Cryptocurrency> cryptocurrencies) {
        for (Cryptocurrency cryptocurrency : cryptocurrencies) {
            textView.append(cryptocurrency.getName() + " " + cryptocurrency.getPrice() + "\n");
        }
//        if (city == null) {
//            binding.cityInfo.setText(R.string.no_city_selected);
//            if (adapter != null) adapter.clear();
//            return;
//        }
//
//        String cityName;
//        if (city.getName() == null)
//            cityName = "City name not found";
//        else
//            cityName = city.getName();
//
//        binding.cityInfo.setText(cityName + "\n" +
//                city.getLatitude() + "°, " + city.getLongitude() + "°\n" +
//                "Last Update: " + city.getLastUpdatedTime()
//                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
//
//        if (city.getWeatherForecasts() != null) {
//            binding.progressBar.setVisibility(View.INVISIBLE);
//
//            RecyclerView forecastRecyclerView = binding.forecastRecyclerView;
//            forecastRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//            weathers = city.getWeatherForecasts();
//            adapter = new ForecastRecyclerViewAdaptor(getContext(), weathers);
//            adapter.setClickListener(this);
//            forecastRecyclerView.setAdapter(adapter);
//        }


    }
}