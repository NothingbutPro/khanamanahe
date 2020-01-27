package Model;

import org.json.JSONException;
import org.json.JSONObject;

public class DataModel {
    String product_id, qty, unit_value, unit, price;

    public DataModel(String product_id, String qty, String unit_value, String unit, String price) {
        this.product_id = product_id;
        this.qty = qty;
        this.unit_value = unit_value;
        this.unit = unit;
        this.price = price;
    }
   /* JSONObject obj = new JSONObject();
    {
        try {
            obj.put("product_id",product_id);
            obj.put("qty",qty);
            obj.put("unit_value",unit_value);
            obj.put("unit",unit);
            obj.put("price",price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("product_id",product_id);
            obj.put("qty",qty);
            obj.put("unit_value",unit_value);
            obj.put("unit",unit);
            obj.put("price",price);
        } catch (JSONException e) {
//            trace("DefaultListItem.toString JSONException: " + e.getMessage());
        }
        return obj;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnit_value() {
        return unit_value;
    }

    public void setUnit_value(String unit_value) {
        this.unit_value = unit_value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
