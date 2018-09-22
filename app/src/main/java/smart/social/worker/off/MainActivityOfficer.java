package smart.social.worker.off;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smart.social.worker.MainActivity;
import smart.social.worker.R;
import smart.social.worker.app.AppConfig;
import smart.social.worker.app.AppController;
import smart.social.worker.db.DbStudent;
import smart.social.worker.forms.MainActivityFragment;


public class MainActivityOfficer extends AppCompatActivity {
    private List<Officer> officerList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OfficerAdapter mAdapter;
    private ProgressDialog pDialog;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String update = "updateKey";
    public static final String farmerid = "farmeridKey";
    public static final String nameid = "nameidKey";
    public static final String seniorid = "farmeridKey";
    public static final String vrpid = "vrpidKey";


    private DbStudent dbFarmer;
    private static final String TAG = MainActivityOfficer.class.getSimpleName();


    Map<String, String> desigValues = new HashMap<>();
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_officer);

        desigValues.put("pp", "Principal");
        desigValues.put("vpp", "Vice Principal");
        desigValues.put("dn", "Dean");
        desigValues.put("hod", "Head of the Department");
        desigValues.put("pr", "Professor");
        desigValues.put("st", "Student");

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        dbFarmer = new DbStudent(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(sharedpreferences.getString(nameid, ""));
        setSupportActionBar(toolbar);

        this.pDialog = new ProgressDialog(this);
        this.pDialog.setCancelable(false);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new OfficerAdapter(officerList);

        recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep officer_list_row.xmlml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep officer_list_rowow.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Officer officer = officerList.get(position);
                toolbar.setTitle(officer.getName());
                if (officer.getSenior().equals("st")) {
                    LoginUser("Gopal", "1234");
                } else {
                    getAllFeeds(officer.getSenior());
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        if (!sharedpreferences.getString(seniorid, "").equals("vi")) {
            getAllFeeds(sharedpreferences.getString(seniorid, ""));
        } else {
            LoginUser("Jeeva", "1234");
        }
    }


    private void getAllFeeds(final String mSenior) {
        Log.e("xxxxxxxxxxxx", mSenior);
        pDialog.setMessage("Fetching...");
        showDialog();
        StringRequest local16 = new StringRequest(1, AppConfig.URL_ALL_SOFFICER, new Response.Listener<String>() {
            public void onResponse(String paramString) {
                Log.d("tag", "Register Response: " + paramString.toString());
                hideDialog();
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.getInt("success") == 1) {
                        officerList = new ArrayList<>();
                        JSONArray localJSONArray = localJSONObject1.getJSONArray("officer");
                        for (int i = 0; i < localJSONArray.length(); i++) {
                            JSONObject jsonObject = localJSONArray.getJSONObject(i);
                            Officer officer = new Officer(jsonObject.getString("name"), desigValues.get(jsonObject.getString("designation")), jsonObject.getString("senior"));
                            officerList.add(officer);
                        }
                        mAdapter.notifyData(officerList);
                        return;
                    }
                    String str = localJSONObject1.getString("message");
                    Toast.makeText(getApplicationContext(), str, 1).show();
                    return;
                } catch (JSONException localJSONException) {
                    localJSONException.printStackTrace();
                }
            }
        }
                , new Response.ErrorListener() {
            public void onErrorResponse(VolleyError paramVolleyError) {
                Log.e("tag", "Fetch Error: " + paramVolleyError.getMessage());
                Toast.makeText(getApplicationContext(), paramVolleyError.getMessage(), 1).show();
                hideDialog();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> localHashMap = new HashMap<String, String>();
                localHashMap.put("senior", mSenior);
                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(local16, "req_posting");
    }


    private void LoginUser(final String username,
                           final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Login ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    if (success == 1) {
                        dbFarmer.deleteAll();
                        JSONArray vrp = jObj.getJSONArray("vrp");
                        String mvrpid = vrp.getJSONObject(0).getString("vrpid");
                        String data = vrp.getJSONObject(0).getString("data");
                        String pass = vrp.getJSONObject(0).getString("password");

                        dbFarmer.addData(mvrpid, new Gson().toJson(data));
                        dbFarmer.updatePassByvrpid(mvrpid, pass);

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(vrpid, mvrpid);
                        editor.commit();
                        Intent io = new Intent(MainActivityOfficer.this, MainActivityFragment.class);
                        startActivity(io);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void hideDialog() {
        if (this.pDialog.isShowing())
            this.pDialog.dismiss();
    }

    private void showDialog() {
        if (!this.pDialog.isShowing())
            this.pDialog.show();
    }



}
