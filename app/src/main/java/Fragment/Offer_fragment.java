package Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Adapter.OrderAdapter;
import Model.My_order_model;
import Model.Offer_Model;
import com.ics.cifatofoody.AppController;
import com.ics.cifatofoody.R;


public class Offer_fragment extends Fragment {

    private static String TAG = Offer_fragment.class.getSimpleName();
    private RecyclerView rv_myorder;
    private List<My_order_model> my_order_modelList = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private LinearLayoutManager layoutManager;

    public Offer_fragment() {
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
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);
        rv_myorder = (RecyclerView) view.findViewById(R.id.rv_myorder);
        layoutManager = new LinearLayoutManager(getActivity());
        rv_myorder.setLayoutManager(layoutManager);
        GetOffers("https://cifato.com/cifato_store/index.php/Api/offers");
        /*((MainActivity) getActivity()).setTitle(getResources().getString(R.string.my_order));

        // handle the touch event if true
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // check user can press back button or not
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    Fragment fm = new Home_fragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                            .addToBackStack(null).commit();
                    return true;
                }
                return false;
            }
        });*/


        return view;
    }

    private void GetOffers(String url) {

        // Tag used to cancel the request
        String tag_json_obj = "json_info_req";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    // Parsing json array response
                    // loop through each json object

                    boolean status = response.getBoolean("responce");
                    if (status) {

                        List<Offer_Model> modelArrayList = new ArrayList<>();

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Offer_Model>>() {
                        }.getType();
                        try {
                            modelArrayList = gson.fromJson(response.getString("data"), listType);
                            orderAdapter = new OrderAdapter(getActivity(), modelArrayList);
                            rv_myorder.setAdapter(orderAdapter);
                        }catch (Exception e)
                        {
                            Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_SHORT).show();
                        }
                       /* String id = support_info_modelList.get(0).getId();
                        String location_offers = support_info_modelList.get(0).getLocationOffers();
                        String type = support_info_modelList.get(0).getType();
                        String statu = support_info_modelList.get(0).getStatus();*/

//                        txt.setText(Html.fromHtml(desc));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
    /*private void makeGetOrderRequest(String userid) {

        // Tag used to cancel the request
        String tag_json_obj = "json_socity_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", userid);

        CustomVolleyJsonArrayRequest jsonObjReq = new CustomVolleyJsonArrayRequest(Request.Method.POST,
                BaseURL.GET_ORDER, params, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                Gson gson = new Gson();
                Type listType = new TypeToken<List<My_order_model>>() {
                }.getType();

                my_order_modelList = gson.fromJson(response.toString(), listType);

                My_order_adapter adapter = new My_order_adapter(my_order_modelList);
                rv_myorder.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                if (my_order_modelList.isEmpty()) {
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

    }*/


}
