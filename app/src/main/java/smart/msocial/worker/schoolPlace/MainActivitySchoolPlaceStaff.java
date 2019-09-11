package smart.msocial.worker.schoolPlace;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smart.msocial.worker.DividerItemDecoration;
import smart.msocial.worker.R;
import smart.msocial.worker.app.AppConfig;
import smart.msocial.worker.app.AppController;
import smart.msocial.worker.app.FormStatus;
import smart.msocial.worker.observation.BaseBean;

public class MainActivitySchoolPlaceStaff extends AppCompatActivity {

    private List<BaseBean> baseList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SchoolPlaceStaffAdapter mAdapter;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    private ProgressDialog pDialog;
    public static final String staffName = "staffNameKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new SchoolPlaceStaffAdapter(baseList, this);
        getSupportActionBar().setTitle("School Placement List");
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void getData() {
        String tag_string_req = "req_register";
        pDialog.setMessage("Fetching ...");
        showDialog();
        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_ALL_SP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Register Response: ", response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    String msg = jObj.getString("message");
                    if (success == 1) {
                        baseList=new ArrayList<>();
                        JSONArray dataArray = jObj.getJSONArray("ov");
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataObject = dataArray.getJSONObject(i);
                            if(!dataObject.getString("status").equals(FormStatus.draft.toString())) {
                                BaseBean baseBean = new BaseBean(
                                        dataObject.getString("semester"),
                                        dataObject.getString("sheet"),
                                        dataObject.getString("userid"),
                                        dataObject.getString("userid"),
                                        dataObject.getString("mark"),
                                        dataObject.getString("data"),
                                        dataObject.getString("status")
                                );
                                baseList.add(baseBean);
                            }
                        }
                        mAdapter.notifyData(baseList);
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
                localHashMap.put("staff", sharedpreferences.getString(staffName, ""));
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
