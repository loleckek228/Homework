package com.geekbrains.android.homework.rest.entities;

import com.google.gson.annotations.SerializedName;

public class SysRestModel {
    @SerializedName("id") public int id;
    @SerializedName("sunrise") public long sunrise;
    @SerializedName("sunset") public long sunset;
}