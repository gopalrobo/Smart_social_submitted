package smart.msocial.worker.staff;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.Map;

import smart.msocial.worker.DividerItemDecoration;
import smart.msocial.worker.R;
import smart.msocial.worker.Student;
import smart.msocial.worker.app.AppConfig;
import smart.msocial.worker.app.AppController;

public class StaffRegistration extends AppCompatActivity implements SubjectClick, StuedntClick {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String nameId = "nameKey";

    private ProgressDialog pDialog;
    EditText name;
    EditText bachelorDegree;
    EditText masterDegree;
    EditText mphil;
    EditText phd;
    EditText phone;
    EditText education;
    EditText experience;
    EditText socialmedia;
    EditText password;
    EditText confirmpassword;

    TextView submittxt;

    String staffId = null;


    private Map<String, Subject> subjectMap = new HashMap<>();
    private ArrayList<Subject> cloudList = new ArrayList<>();
    private ArrayList<Subject> subjectArrayList = new ArrayList<>();
    private RecyclerView subjectList;
    private SubjectAdapter mSubjectAdapter;

    private Map<String, Student> studentMap = new HashMap<>();
    private ArrayList<Student> cloudStudent = new ArrayList<>();
    private ArrayList<Student> studentArrayList = new ArrayList<>();
    private RecyclerView studentList;
    private StudentAdapter mStudentAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_registration);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        name = (EditText) findViewById(R.id.name);
        bachelorDegree = (EditText) findViewById(R.id.bachelorDegree);
        masterDegree = (EditText) findViewById(R.id.masterDegree);
        mphil = (EditText) findViewById(R.id.mphil);
        phd = (EditText) findViewById(R.id.phd);
        phone = (EditText) findViewById(R.id.phone);
        education = (EditText) findViewById(R.id.education);
        experience = (EditText) findViewById(R.id.experience);
        socialmedia = (EditText) findViewById(R.id.socialmedia);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        submittxt = (TextView) findViewById(R.id.submittxt);


        studentList = (RecyclerView) findViewById(R.id.studentList);
        mStudentAdapter = new StudentAdapter(studentArrayList, this, this);
        studentList.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        studentList.setLayoutManager(mLayoutManager);
        studentList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        studentList.setItemAnimator(new DefaultItemAnimator());
        studentList.setAdapter(mStudentAdapter);
        ImageView addStudent = (ImageView) findViewById(R.id.addStudent);
        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectionStudent("Assign Student");
            }
        });

        subjectList = (RecyclerView) findViewById(R.id.subjectList);
        mSubjectAdapter = new SubjectAdapter(subjectArrayList, this, this);
        subjectList.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutsubject = new LinearLayoutManager(this);
        subjectList.setLayoutManager(mLayoutsubject);
        subjectList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        subjectList.setItemAnimator(new DefaultItemAnimator());
        subjectList.setAdapter(mSubjectAdapter);
        ImageView addSubject = (ImageView) findViewById(R.id.addSubject);
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectionSubject("Assign Subject");
            }
        });

        submittxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Staff staff = new Staff(
                        name.getText().toString(),
                        bachelorDegree.getText().toString(),
                        masterDegree.getText().toString(),
                        mphil.getText().toString(),
                        phd.getText().toString(),
                        phone.getText().toString(),
                        education.getText().toString(),
                        experience.getText().toString(),
                        socialmedia.getText().toString(),
                        password.getText().toString(),
                        confirmpassword.getText().toString(),
                        subjectArrayList,
                        studentArrayList
                );
                createData(new Gson().toJson(staff), password.getText().toString());
            }
        });
        getAllSubject();
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {
        subjectArrayList.remove(position);
        mSubjectAdapter.notifyData(subjectArrayList);
    }

    @Override
    public void onStItemClick(int position) {

    }

    @Override
    public void onStDeleteClick(int position) {
        studentArrayList.remove(position);
        mStudentAdapter.notifyData(studentArrayList);
    }

    private void getAllSubject() {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        String url;
        url = AppConfig.URL_GET_SUBJECTS;
        pDialog.setMessage("Updating ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    cloudList = new ArrayList<>();
                    subjectMap = new HashMap<>();
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    if (success == 1) {
                        JSONArray subjectArray = jObj.getJSONArray("subject");
                        for (int i = 0; i < subjectArray.length(); i++) {
                            JSONObject subjectObject = subjectArray.getJSONObject(i);
                            Subject subject = new Subject(subjectObject.getString("name")
                                    , subjectObject.getString("code"), "unassign");
                            cloudList.add(subject);
                            subjectMap.put(subject.name, subject);
                        }
                        getAllStudent();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                       "Something went wrong", Toast.LENGTH_LONG).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pDialog!=null&&pDialog.isShowing())
            pDialog.dismiss();
    }

    private void getAllStudent() {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        String url;
        url = AppConfig.URL_GET_STUDENTS;
        pDialog.setMessage("Updating ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    cloudStudent = new ArrayList<>();
                    studentMap = new HashMap<>();
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    if (success == 1) {
                        JSONArray subjectArray = jObj.getJSONArray("vrp");
                        for (int i = 0; i < subjectArray.length(); i++) {
                            JSONObject subjectObject = subjectArray.getJSONObject(i);
                            Student student = new Student();
                            student.setStudentname(subjectObject.getString("username"));
                            student.setId(subjectObject.getString("vrpid"));
                            cloudStudent.add(student);
                            studentMap.put(student.getStudentname(), student);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(getApplicationContext(),
                        "Something went wrong", Toast.LENGTH_LONG).show();

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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void multiSelectionStudent(String title) {
        // final CharSequence[] items = items;
        // arraylist to keep the selected items
        final ArrayList seletedItems = new ArrayList();
        boolean[] checkedItems = new boolean[cloudStudent.size()];
        final String[] items = new String[cloudStudent.size()];


        if (cloudStudent.size() > 0) {
            for (int j = 0; j < cloudStudent.size(); j++) {
                items[j] = cloudStudent.get(j).getStudentname();
                for (int i = 0; i < studentArrayList.size(); i++) {
                    if (studentArrayList.get(i).getStudentname().equals(cloudStudent.get(j).getStudentname())) {
                        checkedItems[j] = true;
                        seletedItems.add(studentArrayList.get(j).getStudentname());
                        break;
                    }
                }
            }
        }

        TextView myMsg = new TextView(StaffRegistration.this);
        myMsg.setText(title);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setTextSize(25);
        myMsg.setTextColor(Color.BLACK);


        AlertDialog dialog = new AlertDialog.Builder(StaffRegistration.this)
                .setCustomTitle(myMsg)
                .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            seletedItems.add(items[indexSelected]);
                        } else if (seletedItems.contains(items[indexSelected])) {
                            // Else, if the item is already in the array, remove it
                            seletedItems.remove(items[Integer.valueOf(indexSelected)]);
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        studentArrayList = new ArrayList<>();
                        for (int i = 0; i < seletedItems.size(); i++) {
                            studentArrayList.add(studentMap.get(seletedItems.get(i)));
                        }
                        mStudentAdapter.notifyData(studentArrayList);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel
                    }
                }).create();
        dialog.show();
    }

    private void multiSelectionSubject(String title) {
        // final CharSequence[] items = items;
        // arraylist to keep the selected items
        final ArrayList seletedItems = new ArrayList();
        boolean[] checkedItems = new boolean[cloudList.size()];
        final String[] items = new String[cloudList.size()];


        if (cloudList.size() > 0) {
            for (int j = 0; j < cloudList.size(); j++) {
                items[j] = cloudList.get(j).getName();
                for (int i = 0; i < subjectArrayList.size(); i++) {
                    if (subjectArrayList.get(i).getName().equals(cloudList.get(j).getName())) {
                        checkedItems[j] = true;
                        seletedItems.add(subjectArrayList.get(j).getName());
                        break;
                    }
                }
            }
        }

        TextView myMsg = new TextView(StaffRegistration.this);
        myMsg.setText(title);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setTextSize(25);
        myMsg.setTextColor(Color.BLACK);


        AlertDialog dialog = new AlertDialog.Builder(StaffRegistration.this)
                .setCustomTitle(myMsg)
                .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            seletedItems.add(items[indexSelected]);
                        } else if (seletedItems.contains(items[indexSelected])) {
                            // Else, if the item is already in the array, remove it
                            seletedItems.remove(items[Integer.valueOf(indexSelected)]);
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        subjectArrayList = new ArrayList<>();
                        for (int i = 0; i < seletedItems.size(); i++) {
                            subjectArrayList.add(subjectMap.get(seletedItems.get(i)));
                        }
                        mSubjectAdapter.notifyData(subjectArrayList);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel
                    }
                }).create();
        dialog.show();
    }


    private void createData(final String mData, final String password) {
        String tag_string_req = "req_register";
        pDialog.setMessage("Posting ...");
        showDialog();
        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CREATE_STAFF, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Register Response: ", response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    String msg = jObj.getString("message");
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    finish();
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
                localHashMap.put("name", name.getText().toString());
                localHashMap.put("password", password);
                localHashMap.put("data", mData);
                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
