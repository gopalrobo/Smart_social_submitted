package smart.msocial.worker.observation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import smart.msocial.worker.R;
import smart.msocial.worker.app.AppConfig;
import smart.msocial.worker.app.AppController;
import smart.msocial.worker.app.FormStatus;
import smart.msocial.worker.db.DbReport;

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
    String sheetnoString;
    String semesterStrng;

    EditText agencyName;
    EditText establishmentYear;
    EditText organigram;
    EditText workingAreas;
    EditText targetGroup;
    EditText currentProjectsActivities;
    EditText roleSocialWorker;
    EditText observations;
    EditText learnings;
    MaterialBetterSpinner staff;
    EditText marks;
    String itemId = null;


    private ProgressDialog pDialog;

    private static String[] STAFF = new String[]{
            "Loading"
    };
    ArrayAdapter<String> adapter;
    TextView submit;
    TextView draft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.observation_visit);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        dbReport = new DbReport(this);
        getSupportActionBar().setTitle("Observation Visit");
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
            sheetnoString = sharedpreferences.getString("sheetno", "").trim();
            semesterStrng = sharedpreferences.getString("semester", "").trim();
            getSupportActionBar().setTitle(sheetnoString);
        }



        submit = (TextView) findViewById(R.id.submit);
        draft = (TextView) findViewById(R.id.draft);

        agencyName = (EditText) findViewById(R.id.agencyName);
        establishmentYear = (EditText) findViewById(R.id.establishmentYear);
        organigram = (EditText) findViewById(R.id.organigram);
        workingAreas = (EditText) findViewById(R.id.workingAreas);
        targetGroup = (EditText) findViewById(R.id.targetGroup);
        currentProjectsActivities = (EditText) findViewById(R.id.currentProjectsActivities);
        roleSocialWorker = (EditText) findViewById(R.id.roleSocialWorker);
        observations = (EditText) findViewById(R.id.observations);
        learnings = (EditText) findViewById(R.id.learnings);
        marks = (EditText) findViewById(R.id.marks);
        marks.setEnabled(false);
        staff = (MaterialBetterSpinner) findViewById(R.id.staff);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, STAFF);
        staff = (MaterialBetterSpinner) findViewById(R.id.staff);
        staff.setAdapter(adapter);

        draft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObservationVisit groupProject = new ObservationVisit(
                        agencyName.getText().toString(),
                        establishmentYear.getText().toString(),
                        organigram.getText().toString(),
                        workingAreas.getText().toString(),
                        targetGroup.getText().toString(),
                        currentProjectsActivities.getText().toString(),
                        roleSocialWorker.getText().toString(),
                        observations.getText().toString(),
                        learnings.getText().toString(),
                        staff.getText().toString(),
                        marks.getText().toString(),
                        FormStatus.draft.toString()
                );

                createData(groupProject);
            }
        });
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
                        learnings.getText().toString(),
                        staff.getText().toString(),
                        marks.getText().toString(),
                        (submit.getText().toString().toLowerCase().equals("submit"))
                                ? FormStatus.submit.toString() : FormStatus.resubmitted.toString()
                );

                createData(groupProject);
            }
        });


        getAllStaff();
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
                        String status = jObj.getString("status");
                        getSupportActionBar().setSubtitle(status);
                        if (status.equals(FormStatus.rejected.toString())) {
                            submit.setText("Re submit");
                        }
                        if (status.equals(FormStatus.submit.toString()) ||
                                status.equals(FormStatus.approved.toString()) ||
                                status.equals(FormStatus.resubmitted.toString())) {
                            agencyName.setEnabled(false);
                            establishmentYear.setEnabled(false);
                            organigram.setEnabled(false);
                            workingAreas.setEnabled(false);
                            targetGroup.setEnabled(false);
                            currentProjectsActivities.setEnabled(false);
                            roleSocialWorker.setEnabled(false);
                            observations.setEnabled(false);
                            learnings.setEnabled(false);
                            marks.setEnabled(false);
                            staff.setEnabled(false);
                            draft.setVisibility(View.GONE);
                            submit.setVisibility(View.GONE);
                        }

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
                        marks.setText(groupProject.marks);
                        staff.setText(groupProject.staff);
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
                localHashMap.put("sheetno", sheetnoString);
                localHashMap.put("semester", semesterStrng);
                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void createData(final ObservationVisit mData) {
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
                    if (success == 1) {
                        finish();
                    }
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
                localHashMap.put("sheetno", sheetnoString);
                localHashMap.put("semester", semesterStrng);
                localHashMap.put("data", new Gson().toJson(mData));
                localHashMap.put("staff", mData.staff);
                localHashMap.put("status", mData.status);
                localHashMap.put("mark", mData.marks);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pDialog!=null&&pDialog.isShowing())
            pDialog.dismiss();
    }

    private void getAllStaff() {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        String url;
        url = AppConfig.URL_GET_ALL_STAFF;
        pDialog.setMessage("Updating ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    if (success == 1) {
                        JSONArray subjectArray = jObj.getJSONArray("staff");
                        STAFF = new String[subjectArray.length()];
                        for (int i = 0; i < subjectArray.length(); i++) {
                            JSONObject subjectObject = subjectArray.getJSONObject(i);
                            STAFF[i] = subjectObject.getString("name");
                        }


                        adapter = new ArrayAdapter<String>(ObservationVisitActivity.this,
                                android.R.layout.simple_dropdown_item_1line, STAFF);
                        staff.setAdapter(adapter);

                        getData();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


}
