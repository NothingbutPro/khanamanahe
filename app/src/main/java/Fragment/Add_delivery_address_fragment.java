package Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.ics.cifatofoody.AppController;
import com.ics.cifatofoody.MainActivity;
import com.ics.cifatofoody.R;
import util.ConnectivityReceiver;
import util.CustomVolleyJsonRequest;
import util.Session_management;


public class Add_delivery_address_fragment extends Fragment implements View.OnClickListener {

    private static String TAG = Add_delivery_address_fragment.class.getSimpleName();

    private EditText et_phone, et_name, et_pin, et_house;
    private Button btn_update;
    private TextView tv_phone, tv_name, tv_pin, tv_house, tv_socity, btn_socity;
    private String getsocity = "1";
    private EditText et_berth_no, et_train_no, et_pnr_no, coach_no;
    private Session_management sessionManagement;

    private boolean isEdit = false;

    private String getlocation_id;
    private String getphone;
    private String getname;
    private String getpin;
    private String gethouse;
    private ArrayList<Delivery_address_model> delivery_address_modelList = new ArrayList<>();


    public Add_delivery_address_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_delivery_address, container, false);

        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.add_delivery_address));

        sessionManagement = new Session_management(getActivity());

       /* et_phone = (EditText) view.findViewById(R.id.et_add_adres_phone);
        et_name = (EditText) view.findViewById(R.id.et_add_adres_name);

        et_pin = (EditText) view.findViewById(R.id.et_add_adres_pin);
        et_house = (EditText) view.findViewById(R.id.et_add_adres_home);*/

        tv_phone = (TextView) view.findViewById(R.id.tv_add_adres_phone);
        tv_name = (TextView) view.findViewById(R.id.tv_add_adres_name);
        tv_pin = (TextView) view.findViewById(R.id.tv_add_adres_pin);
        tv_house = (TextView) view.findViewById(R.id.tv_add_adres_home);
        et_train_no = (EditText) view.findViewById(R.id.et_train_no);
        et_pnr_no = (EditText) view.findViewById(R.id.et_pnr_no);
        coach_no = (EditText) view.findViewById(R.id.coach_no);
        et_berth_no = (EditText) view.findViewById(R.id.et_berth_no);
        //    tv_socity = (TextView) view.findViewById(R.id.tv_add_adres_socity);
        btn_update = (Button) view.findViewById(R.id.btn_add_adres_edit);
        //   btn_socity = (TextView) view.findViewById(R.id.btn_add_adres_socity);

        String getsocity_name = sessionManagement.getUserDetails().get(BaseURL.KEY_SOCITY_NAME);
        String getsocity_id = sessionManagement.getUserDetails().get(BaseURL.KEY_SOCITY_ID);

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

                    et_train_no.setText(get_name);
                    et_pnr_no.setText(get_phone);
                    coach_no.setText(get_pine);
                    et_berth_no.setText(get_house);

//                    sessionManagement.updateSocity(get_socity_name, get_socity_id);
                }

            } else {

            }

        }

        if (!TextUtils.isEmpty(getsocity_name)) {

            btn_socity.setText(getsocity_name);
            sessionManagement.updateSocity(getsocity_name, getsocity_id);
        }

        btn_update.setOnClickListener(this);
        // btn_socity.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_add_adres_edit) {
            attemptEditProfile();
        } /*else if (id == R.id.btn_add_adres_socity) {

         *//*String getpincode = et_pin.getText().toString();

            if (!TextUtils.isEmpty(getpincode)) {*//*

                Bundle args = new Bundle();
                Fragment fm = new Socity_fragment();
                //args.putString("pincode", getpincode);
                fm.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                        .addToBackStack(null).commit();
            *//*} else {
                Toast.makeText(getActivity(), getResources().getString(R.string.please_enter_pincode), Toast.LENGTH_SHORT).show();
            }*//*

        }*/
    }

    private void attemptEditProfile() {

        tv_phone.setText(getResources().getString(R.string.receiver_mobile_number));
        tv_pin.setText(getResources().getString(R.string.tv_reg_pincode));
        tv_name.setText(getResources().getString(R.string.receiver_name_req));
        tv_house.setText(getResources().getString(R.string.tv_reg_house));
//        tv_socity.setText(getResources().getString(R.string.tv_reg_socity));

        tv_name.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_phone.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_pin.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_house.setTextColor(getResources().getColor(R.color.colorPrimary));
        //    tv_socity.setTextColor(getResources().getColor(R.color.dark_gray));

        getphone = et_train_no.getText().toString();
        getname = et_pnr_no.getText().toString();
        getpin = coach_no.getText().toString();
        gethouse = et_berth_no.getText().toString();
        getsocity = sessionManagement.getUserDetails().get(BaseURL.KEY_SOCITY_ID);

        boolean cancel = false;
        View focusView = null;

       /* if (TextUtils.isEmpty(getphone)) {
            tv_phone.setTextColor(getResources().getColor(R.color.red));
            focusView = et_phone;
            cancel = true;
        }*/

       /* else if (!isPhoneValid(getphone)) {
            tv_phone.setText(getResources().getString(R.string.phone_too_short));
            tv_phone.setTextColor(getResources().getColor(R.color.red));
            focusView = et_phone;
            cancel = true;
        }*/

        if (TextUtils.isEmpty(getname)) {
            tv_name.setTextColor(getResources().getColor(R.color.red));
            focusView = et_name;
            cancel = true;
        }

       /* if (TextUtils.isEmpty(getpin)) {
            tv_pin.setTextColor(getResources().getColor(R.color.colorPrimary));
            focusView = et_pin;
            cancel = true;
        }*/

        if (TextUtils.isEmpty(gethouse)) {
            tv_house.setTextColor(getResources().getColor(R.color.red));
            focusView = et_house;
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
                        makeEditAddressRequest(getlocation_id, getpin, gethouse, getname, getphone);
                    } else {
//                        makeAddAddressRequest(user_id, getpin, getsocity, gethouse, getname, getphone);
                        makeAddAddressRequest(user_id, getphone, getname, getpin, gethouse);
                    }
                }
            }
        }
    }

    private boolean isPhoneValid(String phoneno) {
        //TODO: Replace this with your own logic
        return phoneno.length() > 9;
    }

    /**
     * Method to make json object request where json response starts wtih
     */

    private void makeAddAddressRequest(String user_id, String phone, String name,
                                       String pin, String house) {

        // Tag used to cancel the request
        String tag_json_obj = "json_add_address_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("type", "train");
        params.put("user_id", user_id);
        params.put("train_no", phone);
        params.put("berth_no", house);
        params.put("pnr_no", name);
        params.put("coach_no", pin);


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

    /**
     * Method to make json object request where json response starts wtih
     */

    private void makeEditAddressRequest(final String user_id, final String pincode, String socity_id,
                                        final String house_no, final String receiver_name) {

        // Tag used to cancel the request
        String tag_json_obj = "json_edit_address_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", user_id);
        params.put("train_no", pincode);
        params.put("berth_no", socity_id);
        params.put("pnr_no", house_no);
        params.put("coach_no", receiver_name);

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
                        getlocation_id = getArguments().getString("user_id");
                        String get_name = getArguments().getString("train_no");
                        String get_phone = getArguments().getString("berth_no");
                        String get_pine = getArguments().getString("pnr_no");
                        String get_house = getArguments().getString("coach_no");
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
