package au.edu.unsw.infs3634_lab.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("data")
    @Expose
    private List<Crypto> data;
    @SerializedName("info")
    @Expose
    private Info info;

    public List<Crypto> getData() {
        return data;
    }

    public void setData(List<Crypto> data) {
        this.data = data;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}