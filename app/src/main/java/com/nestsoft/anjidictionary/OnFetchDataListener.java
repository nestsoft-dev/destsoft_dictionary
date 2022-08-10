package com.nestsoft.anjidictionary;

import com.nestsoft.anjidictionary.Models.APIResponse;

public interface OnFetchDataListener {
    void onFetchData(APIResponse apiResponse,String message);
    void onError(String message);
}
