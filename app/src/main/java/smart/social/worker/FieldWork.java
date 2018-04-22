package smart.social.worker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import smart.social.worker.db.DbReport;

/**
 * Created by Gopal on 08-11-2017.
 */

public class FieldWork extends AppCompatActivity {
    DbReport dbReport;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    public static final String vrpid = "vrpidKey";
    String tittleString;
    String vrpString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_work);
        dbReport = new DbReport(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
        }

        getSupportActionBar().setTitle("Field Work");
        TextView submit = (TextView) findViewById(R.id.submit);
        final EditText planForTheDay = (EditText) findViewById(R.id.planForTheDay);
        final EditText technicalDetails = (EditText) findViewById(R.id.technicalDetails);
        final EditText futurePlan = (EditText) findViewById(R.id.futurePlan);
        final EditText conclusion = (EditText) findViewById(R.id.conclusion);
        try {
            String obs = dbReport.getData(vrpString, tittleString, getResources().getString(R.string.fieldWork));
            if (obs != null) {
                JSONObject jsonObject = new JSONObject(obs);
                planForTheDay.setText(jsonObject.getString("planForTheDay"));
                technicalDetails.setText(jsonObject.getString("technicalDetails"));
                futurePlan.setText(jsonObject.getString("futurePlan"));
                conclusion.setText(jsonObject.getString("conclusion"));
            }
        } catch (Exception e) {
            Log.d("field Work ", e.toString());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (planForTheDay.getText().toString().length() > 0
                        || technicalDetails.getText().toString().length() > 0
                        || futurePlan.getText().toString().length() > 0
                        || conclusion.getText().toString().length() > 0) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("planForTheDay", planForTheDay.getText().toString());
                        jsonObject.put("technicalDetails", technicalDetails.getText().toString());
                        jsonObject.put("futurePlan", futurePlan.getText().toString());
                        jsonObject.put("conclusion", conclusion.getText().toString());
                    } catch (Exception e) {
                        Log.e("Error", e.toString());
                    }
                    int success = dbReport.updatedata(vrpString, tittleString,
                            getResources().getString(R.string.fieldWork), jsonObject.toString());
                    if (success == 0)
                        dbReport.addData(vrpString, tittleString,
                                getResources().getString(R.string.fieldWork), jsonObject.toString());
                    Intent io = new Intent(FieldWork.this, MainActivity.class);
                    startActivity(io);
                } else {
                    Toast.makeText(getApplicationContext(), "Enter Field Work", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
