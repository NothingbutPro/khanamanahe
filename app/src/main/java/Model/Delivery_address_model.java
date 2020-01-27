package Model;

import android.os.Parcel;
import android.os.Parcelable;


public class Delivery_address_model implements Parcelable {

    String location_id;
    String user_id;
    String socity_id;
    String house_no;
    String receiver_name;
    String receiver_mobile;
    String socity_name;
    String pincode;
    String area;
    String newAddress;
    String id;
    String train_no;
    String berth_no;
    String coach_no;
    String pnr_no;
    String addressType;
    String delivery_charge;
    boolean ischeckd;

    public Delivery_address_model(String getlocation_id, String getpin, String gethouse, String getname, String getphone) {
        location_id = getlocation_id;
        pincode = getpin;
        house_no = gethouse;
        receiver_name = getname;
        receiver_mobile = getphone;
    }

    public Delivery_address_model(String id, String user_id, String train_no, String berth_no, String coach_no, String pnr_no,String area,String newAddrs,String addressType) {
        this.id = id;
        this.user_id = user_id;
        this.train_no = train_no;
        this.berth_no = berth_no;
        this.coach_no = coach_no;
        this.pnr_no = pnr_no;
        this.area=area;
        this.newAddress=newAddrs;
        this.addressType=addressType;
    }


    protected Delivery_address_model(Parcel in) {
        location_id = in.readString();
        user_id = in.readString();
        socity_id = in.readString();
        house_no = in.readString();
        receiver_name = in.readString();
        receiver_mobile = in.readString();
        socity_name = in.readString();
        pincode = in.readString();
        delivery_charge = in.readString();
        area = in.readString();
        newAddress = in.readString();
        addressType = in.readString();
        /*id = in.readString();
        train_no = in.readString();
        berth_no = in.readString();
        coach_no = in.readString();
        pnr_no = in.readString();*/
        ischeckd = in.readByte() != 0;
    }

    public static final Creator<Delivery_address_model> CREATOR = new Creator<Delivery_address_model>() {
        @Override
        public Delivery_address_model createFromParcel(Parcel in) {
            return new Delivery_address_model(in);
        }

        @Override
        public Delivery_address_model[] newArray(int size) {
            return new Delivery_address_model[size];
        }
    };


    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
    public String getLocation_id() {
        return location_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getSocity_id() {
        return socity_id;
    }

    public String getHouse_no() {
        return house_no;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public String getSocity_name() {
        return socity_name;
    }

    public String getPincode() {
        return pincode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }

    public boolean getIscheckd() {
        return ischeckd;
    }

    public void setIscheckd(boolean ischeckd) {
        this.ischeckd = ischeckd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(location_id);
        parcel.writeString(user_id);
        parcel.writeString(socity_id);
        parcel.writeString(house_no);
        parcel.writeString(receiver_name);
        parcel.writeString(receiver_mobile);
        parcel.writeString(socity_name);
        parcel.writeString(pincode);
        parcel.writeString(delivery_charge);
        parcel.writeString(area);
        parcel.writeString(newAddress);
      /*  parcel.writeString(id);
        parcel.writeString(train_no);
        parcel.writeString(berth_no);
        parcel.writeString(coach_no);
        parcel.writeString(pnr_no);*/
        parcel.writeByte((byte) (ischeckd ? 1 : 0));
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrain_no() {
        return train_no;
    }

    public void setTrain_no(String train_no) {
        this.train_no = train_no;
    }

    public String getBerth_no() {
        return berth_no;
    }

    public void setBerth_no(String berth_no) {
        this.berth_no = berth_no;
    }

    public String getCoach_no() {
        return coach_no;
    }

    public void setCoach_no(String coach_no) {
        this.coach_no = coach_no;
    }

    public String getPnr_no() {
        return pnr_no;
    }

    public void setPnr_no(String pnr_no) {
        this.pnr_no = pnr_no;
    }
}
