package smart.msocial.worker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import smart.msocial.worker.app.AppConfig;
import smart.msocial.worker.app.AppController;
import smart.msocial.worker.forms.MainActivityFragment;
import smart.msocial.worker.staff.MainActivityStaffFragment;

import static smart.msocial.worker.CustomMarkerClusteringDemoActivity.tittle;
import static smart.msocial.worker.app.AppConfig.isStaff;
import static smart.msocial.worker.app.AppConfig.loginKey;
import static smart.msocial.worker.app.AppConfig.mypreference;

public class SplashScreen extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashpra);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.splashScreen);

        //thread for splash screen running
        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // relativeLayout.setBackground(getResources().getDrawable(R.drawable.));
                        }
                    });
                    sleep(2000);

                } catch (InterruptedException e) {
                    Log.d("Exception", "Exception" + e);
                } finally {
                    if (sharedpreferences.contains(loginKey)) {
                        if (sharedpreferences.contains(isStaff) && sharedpreferences.getString(isStaff, "").equals("yes")) {
                            startActivity(new Intent(SplashScreen.this, MainActivityStaffFragment.class));
                        } else {
                            startActivity(new Intent(SplashScreen.this, MainActivityFragment.class));

                        }
                    } else {
                        startActivity(new Intent(SplashScreen.this, FingerprintActivity.class));
                    }
                }
                finish();
            }
        };
        logoTimer.start();
    }



}
