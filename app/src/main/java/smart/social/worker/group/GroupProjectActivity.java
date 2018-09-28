package smart.social.worker.group;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import smart.social.worker.app.AppController;
import smart.social.worker.R;
import smart.social.worker.app.AppConfig;
import smart.social.worker.db.DbReport;

/**
 * Created by Gopal on 08-11-2017.
 */

public class GroupProjectActivity extends AppCompatActivity {
    DbReport dbReport;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    public static final String vrpid = "vrpidKey";
    String tittleString;
    String vrpString;

    EditText issuesIdentified;
    EditText literatureReview;
    EditText learingsFindings;
    EditText agencyIdentified;
    EditText arrival;
    EditText purposeOfVisit;
    EditText programSchedule;
    EditText programReport;
    EditText insigtsFromVisits;
    String itemId = null;


    private ProgressDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_project);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        dbReport = new DbReport(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
        }


        getSupportActionBar().setTitle("Group Project");
        TextView submit = (TextView) findViewById(R.id.submit);
        try {
            String obs = dbReport.getData(vrpString, tittleString, getResources().getString(R.string.groupProject));
            if (obs != null) {
                JSONObject jsonObject = new JSONObject(obs);
            }
        } catch (Exception e) {
            Log.d("field Work ", e.toString());
        }

        issuesIdentified = (EditText) findViewById(R.id.issuesIdentified);
        literatureReview = (EditText) findViewById(R.id.literatureReview);
        learingsFindings = (EditText) findViewById(R.id.learingsFindings);
        agencyIdentified = (EditText) findViewById(R.id.agencyIdentified);
        arrival = (EditText) findViewById(R.id.arrival);
        purposeOfVisit = (EditText) findViewById(R.id.purposeOfVisit);
        programSchedule = (EditText) findViewById(R.id.programSchedule);
        programReport = (EditText) findViewById(R.id.programReport);
        insigtsFromVisits = (EditText) findViewById(R.id.insigtsFromVisits);


        arrival.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    AppConfig.setDateEditText(arrival, GroupProjectActivity.this);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GroupProject groupProject = new GroupProject(
                        issuesIdentified.getText().toString(),
                        literatureReview.getText().toString(),
                        learingsFindings.getText().toString(),
                        agencyIdentified.getText().toString(),
                        arrival.getText().toString(),
                        purposeOfVisit.getText().toString(),
                        programSchedule.getText().toString(),
                        programReport.getText().toString(),
                        insigtsFromVisits.getText().toString()
                );

                createData(new Gson().toJson(groupProject));
            }
        });


        getData();
    }


    private void getData() {
        String tag_string_req = "req_register";
        pDialog.setMessage("Fetching ...");
        showDialog();
        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_GROUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Register Response: ", response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    String msg = jObj.getString("message");
                    if (success == 1) {
                        itemId = jObj.getString("id");
                        GroupProject groupProject = new Gson().fromJson(jObj.getString("data"), GroupProject.class);
                        issuesIdentified.setText(groupProject.issuesIdentified);
                        literatureReview.setText(groupProject.literatureReview);
                        learingsFindings.setText(groupProject.learingsFindings);
                        agencyIdentified.setText(groupProject.agencyIdentified);
                        arrival.setText(groupProject.arrival);
                        purposeOfVisit.setText(groupProject.purposeOfVisit);
                        programSchedule.setText(groupProject.programSchedule);
                        programReport.setText(groupProject.programReport);
                        insigtsFromVisits.setText(groupProject.insigtsFromVisits);
                    }
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e("xxxxxxxxxxx", e.toString());
                    Toast.makeText(getApplicationContext(), "Some Network Error.Try after some time", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Registration Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Some Network Error.Try after some time", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap localHashMap = new HashMap();
                localHashMap.put("userid", sharedpreferences.getString(vrpid, ""));
                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void createData(final String mData) {
        String tag_string_req = "req_register";
        pDialog.setMessage("Posting ...");
        showDialog();
        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CREATE_GROUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Register Response: ", response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    String msg = jObj.getString("message");
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e("xxxxxxxxxxx", e.toString());
                    Toast.makeText(getApplicationContext(), "Some Network Error.Try after some time", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Registration Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Some Network Error.Try after some time", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap localHashMap = new HashMap();
                localHashMap.put("userid", sharedpreferences.getString(vrpid, ""));
                localHashMap.put("data", mData);
                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
