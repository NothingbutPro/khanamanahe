package com.ics.cifatofoody;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Iterator;

import com.ics.cifatofoody.R;
import util.Session_management;

public class ContinueActivity extends AppCompatActivity {

    EditText et_username, et_mobile_no, et_train_no, et_berth_no, et_pnr_no, et_email, et_password;
    Button btnContinue;
    String getphone, getname;
    Session_management sessionManagement;
    private String gettrain;
    private String getberth;
    private String getpnr;
    private String getemail;
    private String getpassword;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_continue);

        et_username = (EditText) findViewById(R.id.et_username);
        et_mobile_no = (EditText) findViewById(R.id.et_mobile_no);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
       /* et_pnr_no = (EditText) findViewById(R.id.et_pnr_no);
        et_berth_no = (EditText) findViewById(R.id.et_berth_no);
        et_train_no = (EditText) findViewById(R.id.et_train_no);*/
        btnContinue = (Button) findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getphone = et_mobile_no.getText().toString();
                getemail = et_email.getText().toString();
                getpassword = et_password.getText().toString();
                getname = et_username.getText().toString();
                /*getpnr = et_pnr_no.getText().toString();
                getberth = et_berth_no.getText().toString();
                gettrain = et_train_no.getText().toString();*/

                new SendJsonDataToServer().execute();

            }
        });
    }

    //----------------------------------------------------


    class SendJsonDataToServer extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(ContinueActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            String urlParameters = "";
            try {

                urlParameters = "user_phone=" + URLEncoder.encode(getphone, "UTF-8") +
                        "&user_fullname=" + URLEncoder.encode(getname, "UTF-8") +
                        "&user_email=" + URLEncoder.encode(getemail, "UTF-8") +
                        "&user_password=" + URLEncoder.encode(getpassword, "UTF-8");

                Log.e("urlParameters", urlParameters);

            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
            String result = Utilities.postParamsAndfindJSON("http://cifato.com/cifato_store/index.php/Api/signup", urlParameters);
            //  Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
            return result;


        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("SendJsonDataToServer>>>", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    String responce = jsonObject.getString("responce");
                    message = jsonObject.getString("message");
                    JSONObject dataObj = jsonObject.getJSONObject("data");
                    String user_phone = dataObj.getString("user_phone");
                    String user_fullname = dataObj.getString("user_fullname");
                    String user_email = dataObj.getString("user_email");
                   /* String pnr_no = dataObj.getString("pnr_no");
                    String berth_no = dataObj.getString("berth_no");
                    String train_no = dataObj.getString("train_no");*/
                    String user_id = dataObj.getString("user_id");


                    Log.e(">>>>", jsonObject.toString() + " " + responce + " " + message);

                    if (responce.equalsIgnoreCase("true")) {

                        et_mobile_no.setText("");
                        et_email.setText("");
                        et_password.setText("");
                        et_username.setText("");
                       /* et_pnr_no.setText("");
                        et_berth_no.setText("");*/
                        // et_train_no.setText("");

                        Toast.makeText(ContinueActivity.this, message, Toast.LENGTH_SHORT).show();

                        Session_management.setUsername(ContinueActivity.this, user_fullname);
                        Session_management.setMobileNo(ContinueActivity.this, user_phone);
                      /*  Session_management.setTrainNo(ContinueActivity.this, train_no);
                        Session_management.setBerthNo(ContinueActivity.this, berth_no);
                        Session_management.setPnrNo(ContinueActivity.this, pnr_no);*/

                        sessionManagement = new Session_management(ContinueActivity.this);
                        sessionManagement.createGuestLogin(user_id, user_fullname, user_email, user_phone);

                       /* Bundle args = new Bundle();
                        Delivery_fragment fm = new Delivery_fragment();
                        fm.setArguments(args);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                                .addToBackStack(null).commit();*/

                        Intent i = new Intent(ContinueActivity.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(ContinueActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(ContinueActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }

    }

    //----------------------------------------------------------

 /*   class SendJsonDataToServer extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(ContinueActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

                String urlParameters = "";
                try {

                    *//*urlParameters = "&user_name=" + URLEncoder.encode(getname, "UTF-8") +
                            "&user_mobile=" + URLEncoder.encode(getphone, "UTF-8") +
                            "&berth_no=" + URLEncoder.encode(getberth, "UTF-8") +
                            "&pnr_no=" + URLEncoder.encode(getpnr, "UTF-8") +
                            "&user_email=" + URLEncoder.encode(getemail, "UTF-8") +
                            "&train_no=" + URLEncoder.encode(gettrain, "UTF-8") ;*//*

                    urlParameters = "user_phone=" + URLEncoder.encode(getphone, "UTF-8") +
                            "&user_fullname=" + URLEncoder.encode(getname, "UTF-8") +
                            "&user_email=" + URLEncoder.encode(getemail, "UTF-8") +
                            "&pnr_no=" + URLEncoder.encode(getpnr, "UTF-8") +
                            "&berth_no=" + URLEncoder.encode(getberth, "UTF-8") +
                            "&train_no=" + URLEncoder.encode(gettrain, "UTF-8");


                    Log.e("urlParameters", urlParameters);


                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
                String result = Utilities.postParamsAndfindJSON("http://cifato.com/cifato_store/index.php/Api/signup", urlParameters);
                return result;


        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();

                // JSONObject jsonObject = null;
                Log.e("SendJsonDataToServer>>>", result.toString());
                try {


                    // result=getJSONUrl(URL);  //<< get json string from server
                    JSONObject jsonObject = new JSONObject(result);
                    //jsonObject = new JSONObject(result);
                    String response = jsonObject.getString("responce");
                    String message = jsonObject.getString("message");
                    JSONObject data = jsonObject.getJSONObject("data");

                    String user_phone = data.getString("user_phone");
                    String user_fullname = data.getString("user_fullname");
                    String user_id = data.getString("user_id");
                    String user_email = data.getString("user_email");
                    String pnr_no = data.getString("pnr_no");
                    String berth_no = data.getString("berth_no");
                    String train_no = data.getString("train_no");


                    Log.e(">>>>", jsonObject.toString() + " " + response + " " + message);

                    if (response.equalsIgnoreCase("true")) {

                    *//*    Session_management.setUsername(ContinueActivity.this, user_fullname);
                        Session_management.setMobileNo(ContinueActivity.this, user_phone);

                        sessionManagement = new Session_management(ContinueActivity.this);
                        sessionManagement.createGuestLogin(user_id,user_fullname,user_phone);*//*

                        Intent i = new Intent(ContinueActivity.this, MainActivity.class);
                        startActivity(i);

                       *//* if(sessionManagement.isLoggedIn()) {

                            Fragment fm = new Delivery_fragment();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                                    .addToBackStack(null).commit();

                        }*//*


                        et_username.setText("");
                        et_mobile_no.setText("");
                        et_train_no.setText("");
                        et_berth_no.setText("");
                        et_pnr_no.setText("");
                        et_email.setText("");


                       *//* Toast.makeText(ContinueActivity.this, message, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ContinueActivity.this, LoginActivity.class);
                        startActivity(intent);*//*

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }


    }*/


}
