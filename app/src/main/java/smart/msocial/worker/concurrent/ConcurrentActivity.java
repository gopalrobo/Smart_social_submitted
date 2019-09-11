package smart.msocial.worker.concurrent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import smart.msocial.worker.allstudent.MainActivityAllStudent;
import smart.msocial.worker.app.AppConfig;
import smart.msocial.worker.app.AppController;
import smart.msocial.worker.app.FormStatus;
import smart.msocial.worker.db.DbReport;
import smart.msocial.worker.field.FiledWork;
import smart.msocial.worker.map.MapListActivityImpl;

/**
 * Created by Gopal on 08-11-2017.
 */

public class ConcurrentActivity extends AppCompatActivity {
    DbReport dbReport;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    public static final String vrpid = "vrpidKey";
    String tittleString;
    String vrpString;
    String sheetnoString;
    String semesterStrng;
    private ProgressDialog pDialog;


    EditText organizationName;
    EditText arrival;
    EditText departure;
    EditText totalHours;
    EditText dateOfSubmission;
    EditText planForTheDay;
    EditText technicalDetails;
    EditText futurePlan;
    EditText conclusion;
    EditText supervisorRemarks;
    EditText facultyRemarks;
    String itemId = null;

    MaterialBetterSpinner staff;
    EditText marks;
    private static String[] STAFF = new String[]{
            "Loading"
    };
    ArrayAdapter<String> adapter;
    TextView submit;
    TextView draft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_work);
        dbReport = new DbReport(this);


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        getSupportActionBar().setTitle("Concurrent Field Work sheet");

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
            sheetnoString = sharedpreferences.getString("sheetno", "").trim();
            semesterStrng = sharedpreferences.getString("semester", "").trim();
            getSupportActionBar().setTitle(sheetnoString);
        }

        FloatingActionButton studentGroup = (FloatingActionButton) findViewById(R.id.studentgroup);
        studentGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent io = new Intent(ConcurrentActivity.this, MainActivityAllStudent.class);
                startActivity(io);
            }
        });

        FloatingActionButton studentRoute = (FloatingActionButton) findViewById(R.id.studentRoute);
        studentRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent io = new Intent(ConcurrentActivity.this, MapListActivityImpl.class);
                startActivity(io);
            }
        });


        organizationName = (EditText) findViewById(R.id.organizationName);
        arrival = (EditText) findViewById(R.id.arrival);
        departure = (EditText) findViewById(R.id.departure);
        totalHours = (EditText) findViewById(R.id.totalHours);
        dateOfSubmission = (EditText) findViewById(R.id.dateOfSubmission);
        planForTheDay = (EditText) findViewById(R.id.planForTheDay);
        technicalDetails = (EditText) findViewById(R.id.technicalDetails);
        futurePlan = (EditText) findViewById(R.id.futurePlan);
        conclusion = (EditText) findViewById(R.id.conclusion);
        supervisorRemarks = (EditText) findViewById(R.id.supervisorRemarks);
        facultyRemarks = (EditText) findViewById(R.id.facultyRemarks);
        marks = (EditText) findViewById(R.id.marks);
        marks.setEnabled(false);
        staff = (MaterialBetterSpinner) findViewById(R.id.staff);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, STAFF);
        staff = (MaterialBetterSpinner) findViewById(R.id.staff);
        staff.setAdapter(adapter);

        submit = (TextView) findViewById(R.id.submit);
        draft = (TextView) findViewById(R.id.draft);

        arrival = (EditText) findViewById(R.id.arrival);
        arrival.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    AppConfig.setDateEditText(arrival, ConcurrentActivity.this);
                }
            }
        });

        departure = (EditText) findViewById(R.id.departure);
        departure.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    AppConfig.setDateEditText(departure, ConcurrentActivity.this);
                }
            }
        });

        dateOfSubmission = (EditText) findViewById(R.id.departure);
        dateOfSubmission.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    AppConfig.setDateEditText(dateOfSubmission, ConcurrentActivity.this);
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FiledWork groupProject = new FiledWork(
                        organizationName.getText().toString(),
                        arrival.getText().toString(),
                        departure.getText().toString(),
                        totalHours.getText().toString(),
                        dateOfSubmission.getText().toString(),
                        planForTheDay.getText().toString(),
                        technicalDetails.getText().toString(),
                        futurePlan.getText().toString(),
                        conclusion.getText().toString(),
                        supervisorRemarks.getText().toString(),
                        facultyRemarks.getText().toString(),
                        staff.getText().toString(),
                        marks.getText().toString(),
                        (submit.getText().toString().toLowerCase().equals("submit"))
                                ? FormStatus.submit.toString() : FormStatus.resubmitted.toString()

                );
                createData(groupProject);
            }
        });

        draft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FiledWork groupProject = new FiledWork(
                        organizationName.getText().toString(),
                        arrival.getText().toString(),
                        departure.getText().toString(),
                        totalHours.getText().toString(),
                        dateOfSubmission.getText().toString(),
                        planForTheDay.getText().toString(),
                        technicalDetails.getText().toString(),
                        futurePlan.getText().toString(),
                        conclusion.getText().toString(),
                        supervisorRemarks.getText().toString(),
                        facultyRemarks.getText().toString(),
                        staff.getText().toString(),
                        marks.getText().toString(),
                        FormStatus.draft.toString()

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
                AppConfig.URL_GET_FIELD_WORK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Register Response: ", response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    String msg = jObj.getString("message");
                    if (success == 1) {

                        String status = jObj.getString("status");
                        getSupportActionBar().setSubtitle(status);
                        if (status.equals(FormStatus.rejected.toString())) {
                            submit.setText("Re submit");
                        }
                        if (status.equals(FormStatus.submit.toString()) ||
                                status.equals(FormStatus.approved.toString()) ||
                                status.equals(FormStatus.resubmitted.toString())) {
                            organizationName.setEnabled(false);
                            arrival.setEnabled(false);
                            departure.setEnabled(false);
                            totalHours.setEnabled(false);
                            dateOfSubmission.setEnabled(false);
                            planForTheDay.setEnabled(false);
                            technicalDetails.setEnabled(false);
                            futurePlan.setEnabled(false);
                            conclusion.setEnabled(false);
                            supervisorRemarks.setEnabled(false);
                            facultyRemarks.setEnabled(false);
                            marks.setEnabled(false);
                           // staff.setEnabled(false);
                         //   draft.setVisibility(View.GONE);
                            submit.setVisibility(View.GONE);
                        }


                        itemId = jObj.getString("id");
                        FiledWork groupProject = new Gson().fromJson(jObj.getString("data"), FiledWork.class);
                        organizationName.setText(groupProject.organizationName);
                        arrival.setText(groupProject.arrival);
                        departure.setText(groupProject.departure);
                        totalHours.setText(groupProject.totalHours);
                        dateOfSubmission.setText(groupProject.dateOfSubmission);
                        planForTheDay.setText(groupProject.planForTheDay);
                        technicalDetails.setText(groupProject.technicalDetails);
                        futurePlan.setText(groupProject.futurePlan);
                        conclusion.setText(groupProject.conclusion);
                        supervisorRemarks.setText(groupProject.supervisorRemarks);
                        facultyRemarks.setText(groupProject.facultyRemarks);
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

    private void createData(final FiledWork mData) {
        String tag_string_req = "req_register";
        pDialog.setMessage("Posting ...");
        showDialog();
        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CREATE_FIELD_WORK, new Response.Listener<String>() {
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


                        adapter = new ArrayAdapter<String>(ConcurrentActivity.this,
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
