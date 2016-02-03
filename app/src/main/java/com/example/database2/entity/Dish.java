package com.example.database2.entity;

import com.google.gson.annotations.SerializedName;
/**
 * Created by root on 11.11.15.
 */
public class Dish {
    @SerializedName("_id")
    public String _id;
    @SerializedName("dish_name")
    public String dish_name;
    @SerializedName("recipe")
    public String recipe;
    @SerializedName("type")
    public String type;

    public Dish(String _id, String dish_name, String recipe, String type)
    {
        this._id=_id;
        this.dish_name=dish_name;
        this.recipe=recipe;
        this.type = type;
    }
}
