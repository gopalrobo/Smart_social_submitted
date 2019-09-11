package smart.msocial.worker.group;

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

/**
 * Created by Gopal on 08-11-2017.
 */

public class GroupProjectActivityStaff extends AppCompatActivity {
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
        setContentView(R.layout.group_project);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        dbReport = new DbReport(this);
        getSupportActionBar().setTitle("Group Project Visit");
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
            sheetnoString = sharedpreferences.getString("sheetno", "").trim();
            semesterStrng = sharedpreferences.getString("semester", "").trim();
            getSupportActionBar().setTitle(sheetnoString);
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

                GroupProject groupProject = new GroupProject(
                        issuesIdentified.getText().toString(),
                        literatureReview.getText().toString(),
                        learingsFindings.getText().toString(),
                        agencyIdentified.getText().toString(),
                        arrival.getText().toString(),
                        purposeOfVisit.getText().toString(),
                        programSchedule.getText().toString(),
                        programReport.getText().toString(),
                        insigtsFromVisits.getText().toString(),
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

                GroupProject groupProject = new GroupProject(
                        issuesIdentified.getText().toString(),
                        literatureReview.getText().toString(),
                        learingsFindings.getText().toString(),
                        agencyIdentified.getText().toString(),
                        arrival.getText().toString(),
                        purposeOfVisit.getText().toString(),
                        programSchedule.getText().toString(),
                        programReport.getText().toString(),
                        insigtsFromVisits.getText().toString(),
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
            Toast.makeText(GroupProjectActivityStaff.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private void getData() {

        GroupProject groupProject = new Gson().fromJson(getIntent().getStringExtra("data"),
                GroupProject.class);

        String status = groupProject.getStatus();
        getSupportActionBar().setSubtitle(status);
        issuesIdentified.setEnabled(false);
        literatureReview.setEnabled(false);
        learingsFindings.setEnabled(false);
        agencyIdentified.setEnabled(false);
        arrival.setEnabled(false);
        purposeOfVisit.setEnabled(false);
        programSchedule.setEnabled(false);
        programReport.setEnabled(false);
        insigtsFromVisits.setEnabled(false);

        staff.setEnabled(false);
        if (status.equals(FormStatus.approved.toString()) ||
                status.equals(FormStatus.rejected.toString())) {
            marks.setEnabled(false);
            draft.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
        }
        issuesIdentified.setText(groupProject.issuesIdentified);
        literatureReview.setText(groupProject.literatureReview);
        learingsFindings.setText(groupProject.learingsFindings);
        agencyIdentified.setText(groupProject.agencyIdentified);
        arrival.setText(groupProject.arrival);
        purposeOfVisit.setText(groupProject.purposeOfVisit);
        programSchedule.setText(groupProject.programSchedule);
        programReport.setText(groupProject.programReport);
        insigtsFromVisits.setText(groupProject.insigtsFromVisits);
        marks.setText(groupProject.marks);
        staff.setText(groupProject.staff);
    }

    private void createData(final GroupProject mData) {
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
