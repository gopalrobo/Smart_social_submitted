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

public class ResearchActivity extends AppCompatActivity {
    DbReport dbReport;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    public static final String vrpid = "vrpidKey";
    String tittleString;
    String vrpString;


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
    private ProgressDialog pDialog;

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
        dbReport = new DbReport(this);


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
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

        getSupportActionBar().setTitle("Research");
        marks = (EditText) findViewById(R.id.marks);
        marks.setEnabled(false);
        staff = (MaterialBetterSpinner) findViewById(R.id.staff);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, STAFF);
        staff = (MaterialBetterSpinner) findViewById(R.id.staff);
        staff.setAdapter(adapter);

        submit = (TextView) findViewById(R.id.submit);
        draft = (TextView) findViewById(R.id.draft);


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
                        (submit.getText().toString().toLowerCase().equals("submit"))
                                ? FormStatus.submit.toString() : FormStatus.resubmitted.toString()

                );


                createData(research);
            }
        });

        draft.setOnClickListener(new View.OnClickListener() {
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
                        FormStatus.draft.toString()
                );


                createData(research);
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
                AppConfig.URL_GET_RESEARCH, new Response.Listener<String>() {
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
                            marks.setEnabled(false);
                            staff.setEnabled(false);
                            draft.setVisibility(View.GONE);
                            submit.setVisibility(View.GONE);
                        }


                        itemId = jObj.getString("id");
                        Research groupProject = new Gson().fromJson(jObj.getString("data"), Research.class);

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
                    if(success==1){
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


                        adapter = new ArrayAdapter<String>(ResearchActivity.this,
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
