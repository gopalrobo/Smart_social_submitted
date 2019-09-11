package smart.msocial.worker.research;

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

public class ResearchActivityStaff extends AppCompatActivity {
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

    EditText title;
    EditText disclaimer;
    EditText acknowledgement;
    EditText contents;
    EditText preface;
    EditText executiveSummary;
    EditText introduction;
    EditText hypothesis;
    EditText researchMethodology;
    EditText researchFindings;
    EditText conclusion;
    EditText reference;
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
        setContentView(R.layout.research);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        dbReport = new DbReport(this);
        getSupportActionBar().setTitle("Research");
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
            sheetnoString = sharedpreferences.getString("sheetno", "").trim();
            semesterStrng = sharedpreferences.getString("semester", "").trim();
            getSupportActionBar().setTitle(sheetnoString);
        }


        title = (EditText) findViewById(R.id.title);
        disclaimer = (EditText) findViewById(R.id.disclaimer);
        acknowledgement = (EditText) findViewById(R.id.acknowledgement);
        contents = (EditText) findViewById(R.id.contents);
        preface = (EditText) findViewById(R.id.preface);
        executiveSummary = (EditText) findViewById(R.id.executiveSummary);
        introduction = (EditText) findViewById(R.id.introduction);
        hypothesis = (EditText) findViewById(R.id.hypothesis);
        researchMethodology = (EditText) findViewById(R.id.researchMethodology);
        researchFindings = (EditText) findViewById(R.id.researchFindings);
        conclusion = (EditText) findViewById(R.id.conclusion);
        reference = (EditText) findViewById(R.id.reference);
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

                Research research = new Research(
                        title.getText().toString(),
                        disclaimer.getText().toString(),
                        acknowledgement.getText().toString(),
                        contents.getText().toString(),
                        preface.getText().toString(),
                        executiveSummary.getText().toString(),
                        introduction.getText().toString(),
                        hypothesis.getText().toString(),
                        researchMethodology.getText().toString(),
                        researchFindings.getText().toString(),
                        conclusion.getText().toString(),
                        reference.getText().toString(),
                        annexure1.getText().toString(),
                        annexure2.getText().toString(),
                        annexure3.getText().toString(),
                        staff.getText().toString(),
                        marks.getText().toString(),
                        FormStatus.approved.toString()
                );

                createData(research);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Research research = new Research(
                        title.getText().toString(),
                        disclaimer.getText().toString(),
                        acknowledgement.getText().toString(),
                        contents.getText().toString(),
                        preface.getText().toString(),
                        executiveSummary.getText().toString(),
                        introduction.getText().toString(),
                        hypothesis.getText().toString(),
                        researchMethodology.getText().toString(),
                        researchFindings.getText().toString(),
                        conclusion.getText().toString(),
                        reference.getText().toString(),
                        annexure1.getText().toString(),
                        annexure2.getText().toString(),
                        annexure3.getText().toString(),
                        staff.getText().toString(),
                        marks.getText().toString(),
                        FormStatus.rejected.toString()
                );

                createData(research);
            }
        });

        try {
            getData();
        } catch (Exception e) {
            Log.e("xxxxxxxxxx", e.toString());
            Toast.makeText(ResearchActivityStaff.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private void getData() {

        Research groupProject = new Gson().fromJson(getIntent().getStringExtra("data"),
                Research.class);

        String status = groupProject.getStatus();
        getSupportActionBar().setSubtitle(status);
        title.setEnabled(false);
        disclaimer.setEnabled(false);
        acknowledgement.setEnabled(false);
        contents.setEnabled(false);
        preface.setEnabled(false);
        executiveSummary.setEnabled(false);
        introduction.setEnabled(false);
        hypothesis.setEnabled(false);
        researchMethodology.setEnabled(false);
        researchFindings.setEnabled(false);
        conclusion.setEnabled(false);
        reference.setEnabled(false);
        annexure1.setEnabled(false);
        annexure2.setEnabled(false);
        annexure3.setEnabled(false);
        staff.setEnabled(false);

        staff.setEnabled(false);
        if (status.equals(FormStatus.approved.toString()) ||
                status.equals(FormStatus.rejected.toString())) {
            marks.setEnabled(false);
            draft.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
        }
        title.setText(groupProject.title);
        disclaimer.setText(groupProject.disclaimer);
        acknowledgement.setText(groupProject.acknowledgement);
        contents.setText(groupProject.contents);
        preface.setText(groupProject.preface);
        executiveSummary.setText(groupProject.executiveSummary);
        introduction.setText(groupProject.introduction);
        hypothesis.setText(groupProject.hypothesis);
        researchMethodology.setText(groupProject.researchMethodology);
        researchFindings.setText(groupProject.researchFindings);
        conclusion.setText(groupProject.conclusion);
        reference.setText(groupProject.reference);
        annexure1.setText(groupProject.annexure1);
        annexure2.setText(groupProject.annexure2);
        annexure3.setText(groupProject.annexure3);
        marks.setText(groupProject.marks);
        staff.setText(groupProject.staff);
    }

    private void createData(final Research mData) {
        String tag_string_req = "req_register";
        pDialog.setMessage("Posting ...");
        showDialog();
        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CREATE_RESEARCH, new Response.Listener<String>() {
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
