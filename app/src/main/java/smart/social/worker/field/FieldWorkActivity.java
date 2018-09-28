package smart.social.worker.field;

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
import smart.social.worker.allstudent.MainActivityAllStudent;
import smart.social.worker.app.AppConfig;
import smart.social.worker.app.AppController;
import smart.social.worker.db.DbReport;
import smart.social.worker.map.MapListActivityImpl;

/**
 * Created by Gopal on 08-11-2017.
 */

public class FieldWorkActivity extends AppCompatActivity {
    DbReport dbReport;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    public static final String vrpid = "vrpidKey";
    String tittleString;
    String vrpString;
    String sheetnoString;
    String semesterStrng;


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

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_work);
        dbReport = new DbReport(this);


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
            sheetnoString = sharedpreferences.getString("sheetno", "").trim();
            semesterStrng = sharedpreferences.getString("semester", "").trim();
        }


        getSupportActionBar().setTitle("Field Work");
        TextView submit = (TextView) findViewById(R.id.submit);



        FloatingActionButton studentGroup = (FloatingActionButton) findViewById(R.id.studentgroup);
        studentGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent io = new Intent(FieldWorkActivity.this, MainActivityAllStudent.class);
                startActivity(io);
            }
        });

        FloatingActionButton studentRoute = (FloatingActionButton) findViewById(R.id.studentRoute);
        studentRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent io = new Intent(FieldWorkActivity.this, MapListActivityImpl.class);
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


        arrival = (EditText) findViewById(R.id.arrival);
        arrival.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    AppConfig.setDateEditText(arrival, FieldWorkActivity.this);
                }
            }
        });

        departure = (EditText) findViewById(R.id.departure);
        departure.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    AppConfig.setDateEditText(departure, FieldWorkActivity.this);
                }
            }
        });

        dateOfSubmission = (EditText) findViewById(R.id.departure);
        dateOfSubmission.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    AppConfig.setDateEditText(dateOfSubmission, FieldWorkActivity.this);
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
                        facultyRemarks.getText().toString()
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
                localHashMap.put("sheetno",sheetnoString);
                localHashMap.put("semester", semesterStrng);
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
                AppConfig.URL_CREATE_FIELD_WORK, new Response.Listener<String>() {
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
                localHashMap.put("sheetno",sheetnoString);
                localHashMap.put("semester",semesterStrng);
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
