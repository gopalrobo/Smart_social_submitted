package smart.social.worker;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import smart.social.worker.app.AppConfig;
import smart.social.worker.app.AppController;
import smart.social.worker.app.GlideApp;
import smart.social.worker.db.DbStudent;
import smart.social.worker.forms.MainActivityFragment;
import smart.social.worker.service.HttpService;
import katomaran.evao.lib.qrmodule.activity.QrScannerActivity;

/**
 * Created by vidhushi.g on 4/10/17.
 */

public class StudentRegistration extends AppCompatActivity

{
    private int CAMERA_PERMISSION_CODE = 23;
    DbStudent dbStudent;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String vrpid = "vrpidKey";
    public static final String update = "updateKey";
    String imageUri = "";
    private LinearLayout otpLin;
    GPSTracker gps;

    private ProgressDialog pDialog;
    private static final String TAG = StudentRegistration.class.getSimpleName();
    private NestedScrollView scroll;
    //private DbProfile dbProfile;
    private AutoCompleteTextView email;
    private CircleImageView image;
    CustomFontEditText aadharnumber;
    CustomFontEditText studentname;
    CustomFontEditText geotag;
    CustomFontEditText fathername;
    MaterialBetterSpinner sex;
    CustomFontEditText address;
    MaterialBetterSpinner eduQualification;
    CustomFontEditText mobile;
    CustomFontEditText whatsapp;
    CustomFontEditText accountnumber;
    CustomFontEditText ifscnumber;
    private CustomFontTextView submit;
    private ImageView aadharimage;
    CustomFontEditText password;
    CustomFontEditText confirmpassword;
    CustomFontEditText otp;


    private static final String[] EDU = new String[]{
            "Bachelors", "Masters"
    };
    private static final String[] CLASS = new String[]{
            "First", "Second", "Third"
    };
    private static final String[] RELIGION = new String[]{
            "Hindu", "Islam", "Christian", "Sikh", "Buddhism", "Jain", "Others"
    };
    private static final String[] CASTE = new String[]{
            "FC", "BC", "OBC", "MBC", "SC", "ST", "Others"
    };
    private static final String[] SEX = new String[]{
            "Male",
            "Female",
            "Third"
    };
    private CustomFontEditText degreeName;
    private MaterialBetterSpinner classMark;
    private CustomFontEditText percentGpa;
    private MaterialBetterSpinner religion;
    private MaterialBetterSpinner caste;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        gps = new GPSTracker(StudentRegistration.this);

        dbStudent = new DbStudent(this);
        //dbProfile = new DbProfile(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Student registration</font>"));
        aadharimage = (ImageView) findViewById(R.id.aadharimg);
        scroll = (NestedScrollView) findViewById(R.id.scroll);
        image = (CircleImageView) findViewById(R.id.imageview);
        aadharnumber = (CustomFontEditText) findViewById(R.id.aadharnumer);
        mobile = (CustomFontEditText) findViewById(R.id.mobile);
        fathername = (CustomFontEditText) findViewById(R.id.fathername);
        whatsapp = (CustomFontEditText) findViewById(R.id.whatsapp);
        studentname = (CustomFontEditText) findViewById(R.id.studentname);
        degreeName = (CustomFontEditText) findViewById(R.id.degreeName);
        percentGpa = (CustomFontEditText) findViewById(R.id.percentGpa);
        geotag = (CustomFontEditText) findViewById(R.id.geotag);

        eduQualification = ((MaterialBetterSpinner) findViewById(R.id.eduQualification));
        ArrayAdapter<String> blockAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, EDU);
        eduQualification.setAdapter(blockAdapter);

        classMark = ((MaterialBetterSpinner) findViewById(R.id.classMark));
        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, CLASS);
        classMark.setAdapter(classAdapter);

        religion = ((MaterialBetterSpinner) findViewById(R.id.religion));
        ArrayAdapter<String> religionAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, RELIGION);
        religion.setAdapter(religionAdapter);

        caste = ((MaterialBetterSpinner) findViewById(R.id.caste));
        ArrayAdapter<String> casteAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, CASTE);
        caste.setAdapter(casteAdapter);

        sex = ((MaterialBetterSpinner) findViewById(R.id.sex));
        ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SEX);
        sex.setAdapter(sexAdapter);

        address = (CustomFontEditText) findViewById(R.id.address);
        ImageView georefresh = (ImageView) findViewById(R.id.refresh);
        email = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        accountnumber = (CustomFontEditText) findViewById(R.id.accountnumber);
        ifscnumber = (CustomFontEditText) findViewById(R.id.ifscnumber);
        submit = (CustomFontTextView) findViewById(R.id.r_submittxt);
        password = (CustomFontEditText) findViewById(R.id.password);
        confirmpassword = (CustomFontEditText) findViewById(R.id.confirmpassword);
        otp = (CustomFontEditText) findViewById(R.id.otp);
        otpLin = (LinearLayout) findViewById(R.id.otplin);
        otpLin.setVisibility(View.GONE);
        georefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if GPS enabled
                gps = new GPSTracker(StudentRegistration.this);
                if (gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    geotag.setText(latitude + "," + longitude);
                    // \n is for new line
                    //       Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
            }
        });
        // check if GPS enabled
        if (gps.canGetLocation()) {
            gps = new GPSTracker(StudentRegistration.this);
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            geotag.setText(latitude + "," + longitude);
            // \n is for new line
            //       Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        GlideApp.with(StudentRegistration.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imageUri = pickResult.getUri().toString();
                    }
                })
                        .show(StudentRegistration.this);
            }
        });


        aadharimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isReadStorageAllowed()) {
                    requestStoragePermission();
                } else {
                    startActivityForResult(
                            new Intent(StudentRegistration.this, QrScannerActivity.class),
                            QrScannerActivity.QR_REQUEST_CODE);
                }
            }
        });
        if (sharedpreferences.contains(update)) {
            try {
                Student student = new Gson()
                        .fromJson(ConvertUtils.sample(dbStudent.getDataByvrpid(sharedpreferences.getString(vrpid, "")).get(1)), Student.class);
                imageUri = student.getImage();
                otpLin.setVisibility(View.GONE);

                aadharnumber.setText(student.getAadharnumber());
                geotag.setText(student.getGeotag());
                address.setText(student.getAddress());
                email.setText(student.getGmail());
                studentname.setText(student.getStudentname());
                degreeName.setText(student.getDegreeName());
                percentGpa.setText(student.getDegreeName());
                classMark.setText(student.getDegreeName());
                religion.setText(student.getDegreeName());
                caste.setText(student.getDegreeName());
                sex.setText(student.getSex());
                fathername.setText(student.getFathername());
                mobile.setText(student.getMobile());
                whatsapp.setText(student.getWhatsapp());
                eduQualification.setText(student.getEduQualification());
                accountnumber.setText(student.getAccountnumber());
                ifscnumber.setText(student.getIfscnumber());
                password.setText(dbStudent.getDataByvrpid(sharedpreferences.getString(vrpid, "")).get(2));
                confirmpassword.setText(dbStudent.getDataByvrpid(sharedpreferences.getString(vrpid, "")).get(2));
                GlideApp.with(StudentRegistration.this).load(imageUri)
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                submit.setText("SUBMIT");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (submit.getText().toString().trim().equals("SUBMIT")) {
                    if (!(mobile.getText().toString() != null && mobile.getText().toString().length() == 10)) {
                        mobile.setError("Enter valid number");
                    } else if (!(whatsapp.getText().toString() != null && whatsapp.getText().toString().length() == 10)) {
                        whatsapp.setError("Enter valid number");
                    } else if (!password.getText().toString().trim().equals(confirmpassword.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), "Password dosn't match", Toast.LENGTH_SHORT).show();
                    } else if (studentname.getText().toString().length() <= 0 || sex.getText().toString().length() <= 0
                            || address.getText().toString().length() <= 0
                            || eduQualification.getText().toString().length() <= 0
                            || degreeName.getText().toString().length() <= 0
                            || fathername.getText().toString().length() <= 0
                            || accountnumber.getText().toString().length() <= 0
                            || ifscnumber.getText().toString().length() <= 0
                            || address.getText().toString().length() <= 0
                            || email.getText().toString().length() <= 0
                            ) {
                        Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        String vrpidnew;
                        Student farmerdata = new Student();
                        farmerdata.setData(sharedpreferences.getString(vrpid, ""), imageUri
                                , aadharnumber.getText().toString()
                                , studentname.getText().toString()
                                , geotag.getText().toString()
                                , fathername.getText().toString()
                                , sex.getText().toString()
                                , eduQualification.getText().toString()
                                , degreeName.getText().toString()
                                , percentGpa.getText().toString()
                                , classMark.getText().toString()
                                , address.getText().toString()
                                , mobile.getText().toString()
                                , whatsapp.getText().toString()
                                , email.getText().toString()
                                , religion.getText().toString()
                                , caste.getText().toString()
                                , accountnumber.getText().toString()
                                , ifscnumber.getText().toString());
                        if (sharedpreferences.contains(update)) {
                            if (checkInternetConnection()) {
                                registerUser(sharedpreferences.getString(vrpid, ""), new Gson().toJson(farmerdata),
                                        password.getText().toString(), studentname.getText().toString(), mobile.getText().toString(), true);
                            } else {
                                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            String pincode = address.getText().toString().substring(address.getText().toString().length() - 6, address.getText().toString().length());
                            if (dbStudent.getCountByvrpid(pincode + "vrp_1") == 0) {
                                vrpidnew = pincode + "vrp_";
                            } else {
                                vrpidnew = pincode + "vrp_";
                            }
                            farmerdata.setId(vrpidnew);
                            if (checkInternetConnection()) {
                                registerUser(vrpidnew, new Gson().toJson(farmerdata),
                                        password.getText().toString(), studentname.getText().toString(), mobile.getText().toString(), false);
                                submit.setText("Enter OTP");
                                otpLin.setVisibility(View.VISIBLE);
                                scroll.fullScroll(View.FOCUS_DOWN);
                                gps = new GPSTracker(StudentRegistration.this);
                            } else {
                                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                } else if (submit.getText().toString().trim().equals("Enter OTP")) {
                    //do nothing
                } else if (submit.getText().toString().trim().equals("Verify")) {
                    String mOtp = otp.getText().toString().trim();

                    if (!mOtp.isEmpty()) {
                        Intent grapprIntent = new Intent(getApplicationContext(), HttpService.class);
                        grapprIntent.putExtra("otp", mOtp);
                        startService(grapprIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                submit.setText("Verify");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_logout, menu);
        if (!sharedpreferences.contains(update)) {
            MenuItem item = menu.findItem(R.id.logout);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                SharedPreferences.Editor editor1 = sharedpreferences.edit();
                editor1.remove(update);
                editor1.commit();
                finish();
                return true;
            case R.id.logout:
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove(vrpid);
                editor.remove(update);
                editor.commit();
                Intent io = new Intent(StudentRegistration.this, MainActivityFragment.class);
                io.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                io.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(io);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QrScannerActivity.QR_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                JSONObject jsonObj = null;
                try {
                    XmlToJson xmlToJson = new XmlToJson.Builder(data.getExtras().getString(QrScannerActivity.QR_RESULT_STR)).build();
                    jsonObj = xmlToJson.toJson();
                    JSONObject barcodedata = jsonObj.getJSONObject("PrintLetterBarcodeData");
                    Log.d("xxxxxxxxx", barcodedata.toString());
                    String addresstxt1 = "";
                    String addresstxt2 = "";
                    String pincode = "";
                    if (!barcodedata.isNull("street")) {
                        addresstxt1 = addresstxt1 + barcodedata.getString("street");
                    } else if (!barcodedata.isNull("lm")) {
                        addresstxt1 = addresstxt1 + barcodedata.getString("lm");
                    }

                    if (!barcodedata.isNull("vtc")) {
                        addresstxt2 = addresstxt2 + barcodedata.getString("vtc") + ",";
                    }
                    if (!barcodedata.isNull("subdist")) {
                        addresstxt2 = addresstxt2 + barcodedata.getString("subdist") + ",";
                    }
                    if (!barcodedata.isNull("dist")) {
                        addresstxt2 = addresstxt2 + barcodedata.getString("dist") + ",";
                        addresstxt2 = addresstxt2 + barcodedata.getString("state") + ",";
                        addresstxt2 = addresstxt2 + " India";
                    }
                    if (!barcodedata.isNull("uid")) {
                        aadharnumber.setText(String.valueOf(barcodedata.getLong("uid")));
                    }
                    if (!barcodedata.isNull("name")) {
                        studentname.setText(barcodedata.getString("name"));
                    }
                    if (!barcodedata.isNull("gname")) {
                        fathername.setText(barcodedata.getString("gname"));
                    }
                    if (!barcodedata.isNull("pc")) {
                        pincode = barcodedata.getString("pc");
                    }
                    address.setText(addresstxt1 + "\n" + addresstxt2 + "\n" + pincode);
                } catch (Exception e) {
                    Log.e("xxxxxxxxx", e.toString());
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(
                        new Intent(StudentRegistration.this, QrScannerActivity.class),
                        QrScannerActivity.QR_REQUEST_CODE);
            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     */
    private void registerUser(final String mvrpid, final String data,
                              final String password, final String username, final String mMobile, final boolean mUpdate) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        String url;
        if (mUpdate) {
            url = AppConfig.URL_UPDATE;
            pDialog.setMessage("Updating ...");
        } else {
            url = AppConfig.URL_REQUEST_SMS;
            pDialog.setMessage("Registering ...");
        }
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        if (mUpdate) {
                            dbStudent.updatedataByvrpid(sharedpreferences.getString(vrpid, ""), new Gson().toJson(data));
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.remove(update);
                            editor.commit();
                            Intent io = new Intent(StudentRegistration.this, ProfileActivity.class);
                            startActivity(io);
                            finish();
                        } else {
                            int mId = jObj.getInt("size");
                            dbStudent.addData(mvrpid + String.valueOf(mId + 1), new Gson().toJson(data));
                            dbStudent.updatePassByvrpid(mvrpid + String.valueOf(mId + 1), password);
                            Toast.makeText(getApplicationContext(), "vrp successfully registered. Try login now!", Toast.LENGTH_LONG).show();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(vrpid, mvrpid + String.valueOf(mId + 1));
                            editor.commit();
                        }
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("vrpid", mvrpid);
                params.put("data", data);
                params.put("password", password);
                params.put("username", username);
                params.put("mobile", mMobile);
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


    private boolean checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }


}
