package smart.social.worker.research;

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
        TextView submit = (TextView) findViewById(R.id.submit);
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
                        annexure3.getText().toString()

                );


                createData(new Gson().toJson(research));
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
                AppConfig.URL_CREATE_RESEARCH, new Response.Listener<String>() {
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
