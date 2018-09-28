package smart.social.worker.observation;

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

import smart.social.worker.R;
import smart.social.worker.app.AppConfig;
import smart.social.worker.app.AppController;
import smart.social.worker.db.DbReport;

/**
 * Created by Gopal on 08-11-2017.
 */

public class ObservationVisitActivity extends AppCompatActivity {
    DbReport dbReport;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    public static final String vrpid = "vrpidKey";
    String tittleString;
    String vrpString;

    EditText agencyName;
    EditText establishmentYear;
    EditText organigram;
    EditText workingAreas;
    EditText targetGroup;
    EditText currentProjectsActivities;
    EditText roleSocialWorker;
    EditText observations;
    EditText learnings;
    String itemId = null;


    private ProgressDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.observation_visit);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        dbReport = new DbReport(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
        }


        getSupportActionBar().setTitle("Observation Visit");
        TextView submit = (TextView) findViewById(R.id.submit);
        try {
            String obs = dbReport.getData(vrpString, tittleString, getResources().getString(R.string.groupProject));
            if (obs != null) {
                JSONObject jsonObject = new JSONObject(obs);
            }
        } catch (Exception e) {
            Log.d("field Work ", e.toString());
        }

        agencyName = (EditText) findViewById(R.id.agencyName);
        establishmentYear = (EditText) findViewById(R.id.establishmentYear);
        organigram = (EditText) findViewById(R.id.organigram);
        workingAreas = (EditText) findViewById(R.id.workingAreas);
        targetGroup = (EditText) findViewById(R.id.targetGroup);
        currentProjectsActivities = (EditText) findViewById(R.id.currentProjectsActivities);
        roleSocialWorker = (EditText) findViewById(R.id.roleSocialWorker);
        observations = (EditText) findViewById(R.id.observations);
        learnings = (EditText) findViewById(R.id.learnings);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ObservationVisit groupProject = new ObservationVisit(
                        agencyName.getText().toString(),
                        establishmentYear.getText().toString(),
                        organigram.getText().toString(),
                        workingAreas.getText().toString(),
                        targetGroup.getText().toString(),
                        currentProjectsActivities.getText().toString(),
                        roleSocialWorker.getText().toString(),
                        observations.getText().toString(),
                        learnings.getText().toString()
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
                AppConfig.URL_GET_OBSERVISIT, new Response.Listener<String>() {
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
                        ObservationVisit groupProject = new Gson().fromJson(jObj.getString("data"), ObservationVisit.class);
                        agencyName.setText(groupProject.agencyName);
                        establishmentYear.setText(groupProject.establishmentYear);
                        organigram.setText(groupProject.organigram);
                        workingAreas.setText(groupProject.workingAreas);
                        targetGroup.setText(groupProject.targetGroup);
                        currentProjectsActivities.setText(groupProject.currentProjectsActivities);
                        roleSocialWorker.setText(groupProject.roleSocialWorker);
                        observations.setText(groupProject.observations);
                        learnings.setText(groupProject.learnings);
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
                AppConfig.URL_CREATE_OBSERVISIT, new Response.Listener<String>() {
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
