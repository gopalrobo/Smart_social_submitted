package smart.msocial.worker.communityPlace;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import smart.msocial.worker.R;
import smart.msocial.worker.app.AppConfig;
import smart.msocial.worker.app.AppController;
import smart.msocial.worker.app.FormStatus;
import smart.msocial.worker.db.DbReport;
import smart.msocial.worker.field.FiledWork;

/**
 * Created by Gopal on 08-11-2017.
 */

public class CommunityPlacementActivityStaff extends AppCompatActivity {
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

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        dbReport = new DbReport(this);
        getSupportActionBar().setTitle("Community Placement");
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
            sheetnoString = sharedpreferences.getString("sheetno", "").trim();
            semesterStrng = sharedpreferences.getString("semester", "").trim();
            getSupportActionBar().setTitle(sharedpreferences.getString("stdName", "")
                    +" "+sheetnoString);
        }


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

        staff = (MaterialBetterSpinner) findViewById(R.id.staff);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, STAFF);
        staff = (MaterialBetterSpinner) findViewById(R.id.staff);
        staff.setAdapter(adapter);

        submit = (TextView) findViewById(R.id.submit);
        draft = (TextView) findViewById(R.id.draft);



        draft.setText("Approve");
        submit.setText("Reject");

        draft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                        FormStatus.approved.toString()
                );

                createData(groupProject);
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
                        FormStatus.rejected.toString()
                );

                createData(groupProject);
            }
        });

        try {
            getData();
        } catch (Exception e) {
            Log.e("xxxxxxxxxx", e.toString());
            Toast.makeText(CommunityPlacementActivityStaff.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private void getData() {

        FiledWork groupProject = new Gson().fromJson(getIntent().getStringExtra("data"),
                FiledWork.class);

        String status = groupProject.getStatus();
        getSupportActionBar().setSubtitle(status);
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
        facultyRemarks.setEnabled(true);
        staff.setEnabled(false);
        if (status.equals(FormStatus.approved.toString()) ||
                status.equals(FormStatus.rejected.toString())) {
            facultyRemarks.setEnabled(false);
            marks.setEnabled(false);
            draft.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
        }
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


}
