package com.dh.agus.digitalhousemusic.Model.POJO;

/**
 * Created by agus on 12/1/17.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artists {

    @SerializedName("data")
    @Expose
    private List<Artist> data = null;

    public List<Artist> getData() {
        return data;
    }

    public void setData(List<Artist> data) {
        this.data = data;
    }

}
