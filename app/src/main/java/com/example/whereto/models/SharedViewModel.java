package com.example.whereto.models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    public MutableLiveData<UserPreferences> item = new MutableLiveData<>();

    public UserPreferences getItem() {
        return item.getValue();
    }

    public void setItem(UserPreferences item) {
        this.item.setValue(item);
    }

}
