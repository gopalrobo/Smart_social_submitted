package smart.msocial.worker.assignment;

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

public class AssignmentActivityStaff extends AppCompatActivity {
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

    EditText assignmentNo;
    EditText dateOfAssignment;
    EditText acknowledgement;
    EditText submissionDate;
    EditText title;
    EditText abstractData;
    EditText introduction;
    EditText sectoralOverview;
    EditText context;
    EditText findings;
    EditText suggestions;
    EditText conclusion;
    EditText references;
    EditText annexure1;
    EditText annexure2;
    EditText annexure3;
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
        setContentView(R.layout.assignment);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        dbReport = new DbReport(this);
        getSupportActionBar().setTitle("Assignment Visit");
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


        assignmentNo = (EditText) findViewById(R.id.assignmentNo);
        dateOfAssignment = (EditText) findViewById(R.id.dateOfAssignment);
        acknowledgement = (EditText) findViewById(R.id.acknowledgement);
        submissionDate = (EditText) findViewById(R.id.submissionDate);
        title = (EditText) findViewById(R.id.title);
        abstractData = (EditText) findViewById(R.id.abstractData);
        introduction = (EditText) findViewById(R.id.introduction);
        sectoralOverview = (EditText) findViewById(R.id.sectoralOverview);
        context = (EditText) findViewById(R.id.context);
        findings = (EditText) findViewById(R.id.findings);
        suggestions = (EditText) findViewById(R.id.suggestions);
        conclusion = (EditText) findViewById(R.id.conclusion);
        references = (EditText) findViewById(R.id.references);
        annexure1 = (EditText) findViewById(R.id.annexure1);
        annexure2 = (EditText) findViewById(R.id.annexure2);
        annexure3 = (EditText) findViewById(R.id.annexure3);
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

                Assignment assignment = new Assignment(

                        assignmentNo.getText().toString(),
                        dateOfAssignment.getText().toString(),
                        acknowledgement.getText().toString(),
                        submissionDate.getText().toString(),
                        title.getText().toString(),
                        abstractData.getText().toString(),
                        introduction.getText().toString(),
                        sectoralOverview.getText().toString(),
                        context.getText().toString(),
                        findings.getText().toString(),
                        suggestions.getText().toString(),
                        conclusion.getText().toString(),
                        references.getText().toString(),
                        annexure1.getText().toString(),
                        annexure2.getText().toString(),
                        annexure3.getText().toString(),
                        staff.getText().toString(),
                        marks.getText().toString(),
                        FormStatus.approved.toString()
                );

                createData(assignment);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Assignment assignment = new Assignment(

                        assignmentNo.getText().toString(),
                        dateOfAssignment.getText().toString(),
                        acknowledgement.getText().toString(),
                        submissionDate.getText().toString(),
                        title.getText().toString(),
                        abstractData.getText().toString(),
                        introduction.getText().toString(),
                        sectoralOverview.getText().toString(),
                        context.getText().toString(),
                        findings.getText().toString(),
                        suggestions.getText().toString(),
                        conclusion.getText().toString(),
                        references.getText().toString(),
                        annexure1.getText().toString(),
                        annexure2.getText().toString(),
                        annexure3.getText().toString(),
                        staff.getText().toString(),
                        marks.getText().toString(),
                        FormStatus.rejected.toString()
                );

                createData(assignment);
            }
        });

        try {
            getData();
        } catch (Exception e) {
            Log.e("xxxxxxxxxx", e.toString());
            Toast.makeText(AssignmentActivityStaff.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private void getData() {

        Assignment groupProject = new Gson().fromJson(getIntent().getStringExtra("data"),
                Assignment.class);

        String status = groupProject.getStatus();
        getSupportActionBar().setSubtitle(status);
        assignmentNo.setEnabled(false);
        dateOfAssignment.setEnabled(false);
        acknowledgement.setEnabled(false);
        submissionDate.setEnabled(false);
        title.setEnabled(false);
        abstractData.setEnabled(false);
        introduction.setEnabled(false);
        sectoralOverview.setEnabled(false);
        context.setEnabled(false);
        findings.setEnabled(false);
        suggestions.setEnabled(false);
        conclusion.setEnabled(false);
        references.setEnabled(false);
        annexure1.setEnabled(false);
        annexure2.setEnabled(false);
        annexure3.setEnabled(false);
        staff.setEnabled(false);
        if (status.equals(FormStatus.approved.toString()) ||
                status.equals(FormStatus.rejected.toString())) {
            marks.setEnabled(false);
            draft.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
        }
        assignmentNo.setText(groupProject.assignmentNo);
        dateOfAssignment.setText(groupProject.dateOfAssignment);
        acknowledgement.setText(groupProject.acknowledgement);
        submissionDate.setText(groupProject.submissionDate);
        title.setText(groupProject.title);
        abstractData.setText(groupProject.abstractData);
        introduction.setText(groupProject.introduction);
        sectoralOverview.setText(groupProject.sectoralOverview);
        context.setText(groupProject.context);
        findings.setText(groupProject.findings);
        suggestions.setText(groupProject.suggestions);
        conclusion.setText(groupProject.conclusion);
        references.setText(groupProject.references);
        annexure1.setText(groupProject.annexure1);
        annexure2.setText(groupProject.annexure2);
        annexure3.setText(groupProject.annexure3);
        marks.setText(groupProject.marks);
        staff.setText(groupProject.staff);
    }

    private void createData(final Assignment mData) {
        String tag_string_req = "req_register";
        pDialog.setMessage("Posting ...");
        showDialog();
        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CREATE_ASSIGNMENT, new Response.Listener<String>() {
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
                localHashMap.put("userid", sharedpreferences.getString("editUser", ""));
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
