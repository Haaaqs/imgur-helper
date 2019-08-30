package me.haq.imguruploader.util;

import com.google.gson.JsonObject;

public interface Callback {
    void onSuccess(JsonObject jsonObject);

    void onFail(Exception e);
}