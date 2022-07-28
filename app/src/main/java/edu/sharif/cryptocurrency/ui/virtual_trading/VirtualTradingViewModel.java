package edu.sharif.cryptocurrency.ui.virtual_trading;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VirtualTradingViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public VirtualTradingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}