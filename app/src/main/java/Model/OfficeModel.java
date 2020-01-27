package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfficeModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("location_offers")
    @Expose
    private String locationOffers;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private String status;


    public OfficeModel(String id, String locationOffers, String type, String status) {
        this.id = id;
        this.locationOffers = locationOffers;
        this.type = type;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocationOffers() {
        return locationOffers;
    }

    public void setLocationOffers(String locationOffers) {
        this.locationOffers = locationOffers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
