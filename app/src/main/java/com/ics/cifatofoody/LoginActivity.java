package com.ics.cifatofoody;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import com.ics.cifatofoody.R;
import util.ConnectivityReceiver;
import util.Session_management;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = LoginActivity.class.getSimpleName();

    private Button btn_continue, btn_register;
    private EditText et_password, et_email;
    private TextView tv_password, tv_email, btn_forgot;

    String Email, Password;
    String sever_url;
    Session_management sessionManagement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        et_password = (EditText) findViewById(R.id.et_login_pass);
        et_email = (EditText) findViewById(R.id.et_login_email);
        tv_password = (TextView) findViewById(R.id.tv_login_password);
        tv_email = (TextView) findViewById(R.id.tv_login_email);
        btn_continue = (Button) findViewById(R.id.btnContinue);
        btn_register = (Button) findViewById(R.id.btnRegister);
        btn_forgot = (TextView) findViewById(R.id.btnForgot);

        btn_continue.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_forgot.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnContinue) {
            Email = et_email.getText().toString().trim();
            Password = et_password.getText().toString().trim();

            attemptLogin();

            //   new SendJsonDataToServer().execute();

        } else if (id == R.id.btnRegister) {
            Intent startRegister = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(startRegister);
        } else if (id == R.id.btnForgot) {
            Intent startRegister = new Intent(LoginActivity.this, ForgotActivity.class);
            startActivity(startRegister);
        }
    }

    private void attemptLogin() {


        tv_password.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_email.setTextColor(getResources().getColor(R.color.colorPrimary));

//        String getpassword = et_password.getText().toString();
//        String getemail = et_email.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(Email)) {
            tv_email.setText(getResources().getString(R.string.tv_login_email));
            tv_email.setTextColor(getResources().getColor(R.color.red));
            focusView = et_email;
            cancel = true;
        }
        if (TextUtils.isEmpty(Password)) {
            tv_password.setText(getResources().getString(R.string.tv_login_password));
            tv_password.setTextColor(getResources().getColor(R.color.red));
            focusView = et_password;
            cancel = true;
        } else if (!isPasswordValid(Password)) {
            tv_password.setText(getResources().getString(R.string.password_too_short));
            tv_password.setTextColor(getResources().getColor(R.color.red));
            focusView = et_password;
            cancel = true;
        }

        /*if (TextUtils.isEmpty(Email)) {
            tv_email.setTextColor(getResources().getColor(R.color.red));
            focusView = et_email;
            cancel = true;
        } else if (!isEmailValid(Email)) {
            tv_email.setText(getResources().getString(R.string.invalide_email_address));
            tv_email.setTextColor(getResources().getColor(R.color.red));
            focusView = et_email;
            cancel = true;
        }*/

        if (cancel) {
            if (focusView != null)
                focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            if (ConnectivityReceiver.isConnected()) {

                new SendJsonDataToServer().execute();
                // makeLoginRequest(getemail, getpassword);
            }
        }

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Method to make json object request where json response starts wtih
     */


    //*************************************************************


    class SendJsonDataToServer extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(LoginActivity.this, R.style.AppCompatAlertDialogStyle);
            dialog.setMessage("Processing");
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://cifato.com/cifato_store/index.php/Api/login");


                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_email", Email);
                postDataParams.put("password", Password);

                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds*/);
                conn.setConnectTimeout(15000  /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        result.append(line);
                    }
                    r.close();
                    return result.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("SendJsonDataToServer>>>", result.toString());
                try {
                    // result=getJSONUrl(URL);  //<< get json string from server
                    jsonObject = new JSONObject(result);
                    String responce = jsonObject.getString("responce");
                    if (responce.equals("false")) {
                        String error = jsonObject.getString("error");
                        Log.e("error", error + "");
                        Toast.makeText(LoginActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                    } else if (responce.equals("true")) {
                        JSONObject message = jsonObject.getJSONObject("data");
                        String user_id = message.getString("user_id");
                        String user_fullname = message.getString("user_fullname");
                        String user_email = message.getString("user_email");
                        String user_phone = message.getString("user_phone");
                        String user_image = message.getString("user_image");

                        Log.e(">>>>", jsonObject.toString() + " " + responce + " " + "");
//                        et_email.setText("");
//                        et_password.setText("");
                        Session_management.setUsername(LoginActivity.this, user_fullname);
                        Session_management.setMobileNo(LoginActivity.this, user_phone);
                        sessionManagement = new Session_management(LoginActivity.this);
                        sessionManagement.createLoginSession(user_id, user_fullname, user_email, user_phone);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.putExtra("Login", "0");
                        startActivity(intent);
                        finish();
                        Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();

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


    }


}
