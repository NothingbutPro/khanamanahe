package Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.My_order_detail_adapter;
import Config.BaseURL;
import Model.My_order_detail_model;
import com.ics.cifatofoody.AppController;
import com.ics.cifatofoody.MainActivity;
import com.ics.cifatofoody.R;
import util.ConnectivityReceiver;
import util.CustomVolleyJsonArrayRequest;
import util.CustomVolleyJsonRequest;
import util.Session_management;


public class My_order_detail_fragment extends Fragment {

    private static String TAG = My_order_detail_fragment.class.getSimpleName();

    private TextView tv_date, tv_time, tv_total, tv_delivery_charge;
    private Button btn_cancle, btn_order_confirm;
    private RecyclerView rv_detail_order;

    private String sale_id;

    private List<My_order_detail_model> my_order_detail_modelList = new ArrayList<>();

    public My_order_detail_fragment() {
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
        View view = inflater.inflate(R.layout.fragment_my_order_detail, container, false);

        tv_date = (TextView) view.findViewById(R.id.tv_order_Detail_date);
        tv_time = (TextView) view.findViewById(R.id.tv_order_Detail_time);
        tv_delivery_charge = (TextView) view.findViewById(R.id.tv_order_Detail_deli_charge);
        tv_total = (TextView) view.findViewById(R.id.tv_order_Detail_total);
        btn_cancle = (Button) view.findViewById(R.id.btn_order_detail_cancle);
        btn_order_confirm = (Button) view.findViewById(R.id.btn_order_confirm);
        rv_detail_order = (RecyclerView) view.findViewById(R.id.rv_order_detail);

        rv_detail_order.setLayoutManager(new LinearLayoutManager(getActivity()));

        sale_id = getArguments().getString("sale_id");
        String total_rs = getArguments().getString("total");
        String date = getArguments().getString("date");
        String time = getArguments().getString("time");
        final String status = getArguments().getString("status");
        String deli_charge = getArguments().getString("deli_charge");

        if (status.equals("0")) {
            btn_cancle.setVisibility(View.VISIBLE);
        } else {
            btn_cancle.setVisibility(View.GONE);
        }
        if (status.equals("1")) {
            btn_order_confirm.setVisibility(View.VISIBLE);
        } else {
            btn_order_confirm.setVisibility(View.GONE);
        }
        tv_total.setText(total_rs);
        tv_date.setText(getResources().getString(R.string.date) + date);
        tv_time.setText(getResources().getString(R.string.time) + time);
        tv_delivery_charge.setText(getResources().getString(R.string.delivery_charge) + deli_charge);

        // check internet connection
        if (ConnectivityReceiver.isConnected()) {
            makeGetOrderDetailRequest(sale_id);
        } else {
            ((MainActivity) getActivity()).onNetworkConnectionChanged(false);
        }

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals("0")) {
                    showDeleteDialog();
                } /*else if (status.equals("1")) {
                    showDeliverDialog();
                }*/
            }
        });

        btn_order_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals("1")) {
                    showDeliverDialog();
                }
            }
        });
        return view;
    }

    // alertdialog for deliver order
    @SuppressLint("NewApi")
    private void showDeliverDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.payment_done));
        builder.setPositiveButton("Payment Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(getActivity());
                alertDialog2.setMessage(getResources().getString(R.string.confirm_order));
                alertDialog2.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog2.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Session_management sessionManagement = new Session_management(getActivity());
                        String user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

                        // check internet connection
                        if (ConnectivityReceiver.isConnected()) {
                            makeGetOrderDeliverRequest(sale_id);
                        }

                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog2 = alertDialog2.create();
                dialog2.show();
                Button positiveButton = dialog2.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setBackgroundResource(R.drawable.xml_button);
                positiveButton.setHeight(12);
                positiveButton.setTextColor(Color.parseColor("#ffffff"));
                positiveButton.setAllCaps(false);

                Button negativeButton = dialog2.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setBackgroundResource(R.drawable.xml_button);
                negativeButton.setHeight(12);
                negativeButton.setTextColor(Color.parseColor("#ffffff"));
                negativeButton.setAllCaps(false);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setBackgroundResource(R.drawable.xml_button);
        positiveButton.setHeight(16);
        positiveButton.setTextColor(Color.parseColor("#ffffff"));
        positiveButton.setAllCaps(false);


    }

    // alertdialog for cancle order
    private void showDeleteDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage(getResources().getString(R.string.cancle_order_note));

        alertDialog.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Session_management sessionManagement = new Session_management(getActivity());
                String user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

                // check internet connection
                if (ConnectivityReceiver.isConnected()) {
                    makeDeleteOrderRequest(sale_id, user_id);
                }

                dialogInterface.dismiss();
            }
        });


        AlertDialog dialog2 = alertDialog.create();
        dialog2.show();
        Button positiveButton = dialog2.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setBackgroundResource(R.drawable.xml_button);
        positiveButton.setHeight(12);
        positiveButton.setTextColor(Color.parseColor("#ffffff"));
        positiveButton.setAllCaps(false);

        Button negativeButton = dialog2.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setBackgroundResource(R.drawable.xml_button);
        negativeButton.setHeight(12);
        negativeButton.setTextColor(Color.parseColor("#ffffff"));
        negativeButton.setAllCaps(false);
    }


    private void makeGetOrderDeliverRequest(String order_id) {

        // Tag used to cancel the request
        String tag_json_obj = "json_order_confirm_req";
        String URL = BaseURL.ORDER_CONFIRM + "?order_id=" + order_id;

       /* Map<String, String> params = new HashMap<String, String>();
        params.put("order_id", order_id);*/

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    Boolean status = response.getBoolean("responce");
                    if (status) {

                        String msg = response.getString("message");
                        Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();

                        ((MainActivity) getActivity()).onBackPressed();

                    } else {
                        String error = response.getString("error");
                        Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
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
     * Method to make json array request where json response starts wtih
     */
    private void makeGetOrderDetailRequest(String sale_id) {

        // Tag used to cancel the request
        String tag_json_obj = "json_order_detail_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("sale_id", sale_id);

        CustomVolleyJsonArrayRequest jsonObjReq = new CustomVolleyJsonArrayRequest(Request.Method.POST,
                BaseURL.ORDER_DETAIL, params, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                Gson gson = new Gson();
                Type listType = new TypeToken<List<My_order_detail_model>>() {
                }.getType();

                my_order_detail_modelList = gson.fromJson(response.toString(), listType);

                My_order_detail_adapter adapter = new My_order_detail_adapter(my_order_detail_modelList);
                rv_detail_order.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                if (my_order_detail_modelList.isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
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
    private void makeDeleteOrderRequest(String sale_id, String user_id) {

        // Tag used to cancel the request
        String tag_json_obj = "json_delete_order_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("sale_id", sale_id);
        params.put("user_id", user_id);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.DELETE_ORDER, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    Boolean status = response.getBoolean("responce");
                    if (status) {

                        String msg = response.getString("message");
                        Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();

                        ((MainActivity) getActivity()).onBackPressed();

                    } else {
                        String error = response.getString("error");
                        Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
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

}
