package edu.sharif.cryptocurrency.ui.exchange_rate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExchangeRateViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ExchangeRateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}