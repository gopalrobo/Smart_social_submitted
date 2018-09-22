package smart.social.worker;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.fingerprint.FingerprintManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.vistrav.ask.Ask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import smart.social.worker.app.AppConfig;
import smart.social.worker.app.AppController;
import smart.social.worker.db.DbStudent;
import smart.social.worker.forms.MainActivityFragment;
import smart.social.worker.off.MainActivityOfficer;

public class FingerprintActivity extends AppCompatActivity {

    private KeyStore keyStore;
    // Variable used for storing the key in the Android Keystore container
    private static final String KEY_NAME = "androidHive";
    private Cipher cipher;
    private TextView textView;


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String update = "updateKey";
    public static final String vrpid = "vrpidKey";
    private DbStudent dbStudent;
    public static final String nameid = "nameidKey";
    public static final String seniorid = "farmeridKey";

    private ProgressDialog pDialog;
    private static final String TAG = FingerprintActivity.class.getSimpleName();
    private RadioButton student;
    private RadioButton Faculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);


        Ask.on(this)
                .debug(true)
                .id(123)
                .forPermissions(Manifest.permission.ACCESS_COARSE_LOCATION
                        , Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.CAMERA
                        , Manifest.permission.RECORD_AUDIO
                        , Manifest.permission.READ_CONTACTS
                        , Manifest.permission.WRITE_CONTACTS
                        , Manifest.permission.CALL_PHONE
                        , Manifest.permission.GET_ACCOUNTS
                        , Manifest.permission.READ_SMS
                        , Manifest.permission.RECEIVE_SMS
                        , Manifest.permission.BROADCAST_SMS
                        , Manifest.permission.READ_PHONE_STATE
                        , Manifest.permission.WRITE_CALENDAR
                        , Manifest.permission.READ_CALENDAR

                )
                .withRationales("Location permission need for map to work properly",
                        "In order to save file you will need to grant storage permission") //optional
                .go();


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        student = (RadioButton) findViewById(R.id.Student);
        Faculty = (RadioButton) findViewById(R.id.Faculty);

        student.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    student.setChecked(true);
                    Faculty.setChecked(false);
                } else {
                    Faculty.setChecked(true);
                    student.setChecked(false);
                }
            }
        });

        Faculty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    student.setChecked(true);
                    Faculty.setChecked(false);
                } else {
                    Faculty.setChecked(true);
                    student.setChecked(false);
                }
            }
        });
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        dbStudent = new DbStudent(this);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/maven.ttf");
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        final TextInputLayout usernameLayout = (TextInputLayout) findViewById(R.id.user);
        final TextInputLayout passwordLayout = (TextInputLayout) findViewById(R.id.pass);
        CustomFontTextView addvrp = (CustomFontTextView) findViewById(R.id.addfarmer);
        final CustomFontTextView login = (CustomFontTextView) findViewById(R.id.login);
        username.setTypeface(custom_font);
        password.setTypeface(custom_font);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().length() <= 0 || password.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    List<ArrayList<String>> vrpList = dbStudent.getAllData();
                    boolean trig = false;
                    for (int i = 0; i < vrpList.size(); i++) {
                        Student student = new Gson().fromJson(ConvertUtils.sample(vrpList.get(i).get(1)), Student.class);
                        if (username.getText().toString().equals(student.getStudentname())) {
                            if (password.getText().toString().equals(vrpList.get(i).get(2))) {
                                trig = true;
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(vrpid, vrpList.get(i).get(0));
                                editor.commit();
                                Intent io = new Intent(FingerprintActivity.this, MainActivityFragment.class);
                                startActivity(io);
                                finish();
                            }
                        }
                    }
                    if (!trig && checkInternetConnection()) {
                        if (student.isChecked()) {
                            LoginUser(username.getText().toString(), password.getText().toString());
                        } else {
                            LoginTeacher(username.getText().toString(), password.getText().toString());
                        }
                    } else if (!trig) {
                        Toast.makeText(getApplicationContext(), "No vrps found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        addvrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove(update);
                editor.commit();
                Intent io = new Intent(FingerprintActivity.this, StudentRegistration.class);
                startActivity(io);
            }
        });


        // Initializing both Android Keyguard Manager and Fingerprint Manager
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        textView = (TextView) findViewById(R.id.errorText);

        // Check whether the device has a Fingerprint sensor.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!fingerprintManager.isHardwareDetected()) {
                /**
                 * An error message will be displayed if the device does not contain the fingerprint hardware.
                 * However if you plan to implement a default authentication method,
                 * you can redirect the user to a default authentication activity from here.
                 * Example:
                 * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
                 * startActivity(intent);
                 */
                textView.setText("Your Device does not have a Fingerprint Sensor");
            } else {
                // Checks whether fingerprint permission is set on manifest
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                    textView.setText("Fingerprint authentication permission not enabled");
                } else {
                    // Check whether at least one fingerprint is registered
                    if (!fingerprintManager.hasEnrolledFingerprints()) {
                        textView.setText("Register at least one fingerprint in Settings");
                    } else {
                        // Checks whether lock screen security is enabled or not
                        if (!keyguardManager.isKeyguardSecure()) {
                            textView.setText("Lock screen security not enabled in Settings");
                        } else {
                            generateKey();

                            if (cipherInit()) {
                                FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                                FingerprintHandler helper = new FingerprintHandler(this);
                                helper.startAuth(fingerprintManager, cryptoObject);
                            }
                        }
                    }
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_lan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.language) {
            AppController.languageSwitcher.showChangeLanguageDialog(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
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

                        JSONArray vrp = jObj.getJSONArray("vrp");
                        String mvrpid = vrp.getJSONObject(0).getString("vrpid");
                        String data = vrp.getJSONObject(0).getString("data");
                        String pass = vrp.getJSONObject(0).getString("password");

                        dbStudent.addData(mvrpid, new Gson().toJson(data));
                        dbStudent.updatePassByvrpid(mvrpid, pass);

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(vrpid, mvrpid);
                        editor.commit();
                        Intent io = new Intent(FingerprintActivity.this, MainActivityFragment.class);
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


    private void LoginTeacher(final String username,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Login ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ALL_OFFICER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    if (success == 1) {
                        JSONArray farmer = jObj.getJSONArray("officer");
                        String name = farmer.getJSONObject(0).getString("name");
                        String senior = farmer.getJSONObject(0).getString("senior");
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(nameid, name);
                        editor.putString(seniorid, senior);
                        editor.commit();

                        Intent io = new Intent(FingerprintActivity.this, MainActivityOfficer.class);
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