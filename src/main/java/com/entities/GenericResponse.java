package com.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenericResponse {

    @SerializedName("response")
    public List<ArticleResponse> listItem;
}
