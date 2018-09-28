package smart.social.worker.assignment;

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

public class AssignmentActivity extends AppCompatActivity {
    DbReport dbReport;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    public static final String vrpid = "vrpidKey";
    String tittleString;
    String vrpString;

    private ProgressDialog pDialog;
    String sheetnoString;
    String semesterStrng;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment);
        dbReport = new DbReport(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
            sheetnoString = sharedpreferences.getString("sheetno", "").trim();
            semesterStrng = sharedpreferences.getString("semester", "").trim();
        }

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


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

        getSupportActionBar().setTitle("Assignment");
        TextView submit = (TextView) findViewById(R.id.submit);
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
                        annexure3.getText().toString()
                );
                createData(new Gson().toJson(assignment));
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
                AppConfig.URL_GET_ASSIGNMENT, new Response.Listener<String>() {
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
                        Assignment groupProject = new Gson().fromJson(jObj.getString("data"), Assignment.class);
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

    private void createData(final String mData) {
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
