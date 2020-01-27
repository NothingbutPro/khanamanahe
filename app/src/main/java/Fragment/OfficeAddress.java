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

import Adapter.OfficeAdapter;
import Model.OfficeModel;
import com.ics.cifatofoody.AppController;
import com.ics.cifatofoody.R;

public class OfficeAddress extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rv_Office;
    private OfficeAdapter officeAdapter;
    private LinearLayoutManager layoutManager;

    /*public static OfficeAddress newInstance(String param1, String param2) {
        OfficeAddress fragment = new OfficeAddress();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_office_address, container, false);
        rv_Office = (RecyclerView) view.findViewById(R.id.rv_Office);
        layoutManager = new LinearLayoutManager(getActivity());
        rv_Office.setLayoutManager(layoutManager);
        GetOfficeAddress("https://cifato.com/cifato_store/index.php/Api/location");
        return view;
    }

    private void GetOfficeAddress(String url) {

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

                        List<OfficeModel> officeModelList = new ArrayList<>();

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<OfficeModel>>() {
                        }.getType();

                        officeModelList = gson.fromJson(response.getString("data"), listType);
                        officeAdapter = new OfficeAdapter(getActivity(), officeModelList);
                        rv_Office.setAdapter(officeAdapter);
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

}
