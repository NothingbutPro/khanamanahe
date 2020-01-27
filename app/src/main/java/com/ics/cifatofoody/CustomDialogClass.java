package com.ics.cifatofoody;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Config.BaseURL;
import Model.Delivery_address_model;
import com.ics.cifatofoody.R;
import util.ConnectivityReceiver;
import util.CustomVolleyJsonRequest;
import util.Session_management;

public class CustomDialogClass extends Fragment implements
        android.view.View.OnClickListener {

    public Activity context;
    Toolbar toolbar;
    private static String TAG = CustomDialogClass.class.getSimpleName();

    private EditText et_add_adres_name, et_add_adres_phone, et_add_adres_pin, et_add_adres_home;
    private Button btn_add_adres_edit;
    private String getName, getPhone, getPincode, getHomeAddrs;
    private boolean isEdit = false;
    private ArrayList<Delivery_address_model> delivery_address_modelList = new ArrayList<>();
    private Session_management sessionManagement;
    private String getlocation_id;
    public CustomDialogClass(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_custom_dialog_class, container, false);

        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.add_delivery_address));

        sessionManagement = new Session_management(getActivity());

        et_add_adres_name = (EditText)view.findViewById(R.id.et_add_adres_name);
        et_add_adres_phone = (EditText) view.findViewById(R.id.et_add_adres_phone);
        et_add_adres_pin = (EditText)view.findViewById(R.id.et_add_adres_pin);
        et_add_adres_home = (EditText)view.findViewById(R.id.et_add_adres_home);
        btn_add_adres_edit = (Button)view.findViewById(R.id.btn_add_adres_edit);

        Bundle args = getArguments();

        if (args != null) {

            delivery_address_modelList = getArguments().getParcelableArrayList("ListModel");
            Log.e("vxvm", delivery_address_modelList.size() + "");
            if (delivery_address_modelList.size() > 0) {
                String get_name = null, get_phone = null, get_pine = null, get_house = null;
                for (Delivery_address_model delivery_address_model : delivery_address_modelList) {
                    getlocation_id = delivery_address_model.getUser_id();
                    get_name = delivery_address_model.getReceiver_name();
                    get_phone = delivery_address_model.getReceiver_mobile();
                    get_pine = delivery_address_model.getPincode();
                    get_house = delivery_address_model.getHouse_no();
                }
                if (TextUtils.isEmpty(get_name) && get_name == null) {
                    isEdit = false;
                } else {
                    isEdit = true;

                    //  Toast.makeText(getActivity(), "edit", Toast.LENGTH_SHORT).show();

                    et_add_adres_name.setText(get_name);
                    et_add_adres_phone.setText(get_phone);
                    et_add_adres_pin.setText(get_pine);
                    et_add_adres_home.setText(get_house);

//                    sessionManagement.updateSocity(get_socity_name, get_socity_id);
                }

            } else {

            }

        }

       /* if (!TextUtils.isEmpty(getsocity_name)) {

            btn_socity.setText(getsocity_name);
            sessionManagement.updateSocity(getsocity_name, getsocity_id);
        }
*/
        btn_add_adres_edit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btn_add_adres_edit) {
            attemptEditProfile();
        }

       /* switch (v.getId()) {
            case R.id.btn_add_adres_edit:

                getName = et_add_adres_name.getText().toString();
                getPhone = et_add_adres_phone.getText().toString();
                getPincode = et_add_adres_pin.getText().toString();
                getHomeAddrs = et_add_adres_home.getText().toString();

                break;
        }*/
    }

    private void attemptEditProfile() {

     /*   tv_phone.setText(getResources().getString(R.string.receiver_mobile_number));
        tv_pin.setText(getResources().getString(R.string.tv_reg_pincode));
        tv_name.setText(getResources().getString(R.string.receiver_name_req));
        tv_house.setText(getResources().getString(R.string.tv_reg_house));
//        tv_socity.setText(getResources().getString(R.string.tv_reg_socity));

        tv_name.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_phone.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_pin.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_house.setTextColor(getResources().getColor(R.color.colorPrimary));
        //    tv_socity.setTextColor(getResources().getColor(R.color.dark_gray));*/

        getPhone = et_add_adres_phone.getText().toString();
        getName = et_add_adres_name.getText().toString();
        getPincode = et_add_adres_pin.getText().toString();
        getHomeAddrs = et_add_adres_home.getText().toString();
      //  getsocity = sessionManagement.getUserDetails().get(BaseURL.KEY_SOCITY_ID);

        boolean cancel = false;
        View focusView = null;

        /*if (TextUtils.isEmpty(getPhone)) {
           *//* tv_phone.setTextColor(getResources().getColor(R.color.red));
            focusView = et_phone;*//*
            cancel = true;
        }*//* else if (!isPhoneValid(getphone)) {
            tv_phone.setText(getResources().getString(R.string.phone_too_short));
            tv_phone.setTextColor(getResources().getColor(R.color.red));
            focusView = et_phone;
            cancel = true;
        }*/

       /* if (TextUtils.isEmpty(getName)) {
            *//*tv_name.setTextColor(getResources().getColor(R.color.red));
            focusView = et_name;*//*
            cancel = true;
        }*/

       /* if (TextUtils.isEmpty(getpin)) {
            tv_pin.setTextColor(getResources().getColor(R.color.colorPrimary));
            focusView = et_pin;
            cancel = true;
        }*/

        if (TextUtils.isEmpty(getHomeAddrs)) {
           /* tv_house.setTextColor(getResources().getColor(R.color.red));
            focusView = et_house;*/
            cancel = true;
        }

       /* if (TextUtils.isEmpty(getsocity) && getsocity == null) {
            tv_socity.setTextColor(getResources().getColor(R.color.red));
            focusView = btn_socity;
            cancel = true;
        }*/

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null)
                focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            if (ConnectivityReceiver.isConnected()) {

                String user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

                // check internet connection
                if (ConnectivityReceiver.isConnected()) {
                    if (isEdit) {
                        makeEditAddressRequest(getlocation_id, getPincode, getHomeAddrs);
                    } else {
//                        makeAddAddressRequest(user_id, getpin, getsocity, gethouse, getname, getphone);
                        makeAddAddressRequest(user_id, getPincode, getHomeAddrs);
                    }
                }
            }
        }
    }
    private void makeAddAddressRequest(String user_id, String area, String address) {

        // Tag used to cancel the request
        String tag_json_obj = "json_add_address_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("type", "Home");
        params.put("user_id", user_id);
        params.put("area", area);
        params.put("address", address);


        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.ADD_ADDRESS, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    Boolean status = response.getBoolean("responce");
                    if (status) {

                        /*getArguments().putString("location_id", et_name.getText().toString().trim());
                        getArguments().putString("pincode", et_pin.getText().toString().trim());
                        getArguments().putString("house_no", et_house.getText().toString().trim());
                        getArguments().putString("receiver_name", et_name.getText().toString().trim());
                        getArguments().putString("receiver_mobile", et_phone.getText().toString().trim());*/
                        ((MainActivity) getActivity()).onBackPressed();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private void makeEditAddressRequest(final String user_id, final String pincode, String addressHome) {

        // Tag used to cancel the request
        String tag_json_obj = "json_edit_address_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("type", "Home");
        params.put("user_id", user_id);
        params.put("area", pincode);
        params.put("address", addressHome);


        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.EDIT_ADDRESS, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    Boolean status = response.getBoolean("responce");
                    if (status) {

                        String msg = response.getString("data");
                        Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                      //  getlocation_id = getArguments().getString("user_id");
                        String get_name = getArguments().getString("area");
                        String get_phone = getArguments().getString("adress");

                       /* getArguments().putString("location_id", location_id);
                        getArguments().putString("pincode", pincode);
                        getArguments().putString("house_no", house_no);
                        getArguments().putString("receiver_name", receiver_name);
                        getArguments().putString("receiver_mobile", receiver_mobile);*/


                        ((MainActivity) getActivity()).onBackPressed();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });


        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }
}
