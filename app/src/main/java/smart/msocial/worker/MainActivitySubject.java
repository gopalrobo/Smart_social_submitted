package smart.msocial.worker;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import smart.msocial.worker.app.AppConfig;
import smart.msocial.worker.attend.SampleToolbarActivity;
import smart.msocial.worker.field.FieldWorkActivity;

public class MainActivitySubject extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    public static final String vrpid = "vrpidKey";
    String tittleString;
    String vrpString;
    String[] subjects = new String[10];
    String[] unit = new String[10];
    String[] chapter = new String[10];
    String[] subchapter = new String[10];
    private JSONObject subectsJson = null;
    private Spinner subjectsSpin;
    private Spinner unitSpin;
    private Spinner chapterSpin;
    private Spinner subchapterSpin;
    private CustomFontTextView proceed;
    private CustomFontTextView fieldWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        //Getting the instance of Spinner and applying OnItemSelectedListener on it

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
        }
        proceed = (CustomFontTextView) findViewById(R.id.proceed);
        fieldWork = (CustomFontTextView) findViewById(R.id.fieldWork);
        subjectsSpin = (Spinner) findViewById(R.id.subjects);
        unitSpin = (Spinner) findViewById(R.id.unit);
        chapterSpin = (Spinner) findViewById(R.id.chapter);
        subchapterSpin = (Spinner) findViewById(R.id.subchapter);

        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            if (!jsonObject.isNull(tittleString)) {
                subectsJson = jsonObject.getJSONObject(tittleString);
                Iterator<String> keys = subectsJson.keys();
                subjects = toArray(keys);
                ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, subjects);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                subjectsSpin.setAdapter(aa);


                subjectsSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        String item = subjectsSpin.getSelectedItem().toString();
                        try {
                            final JSONObject unitJson = subectsJson.getJSONObject(item);
                            if (!unitJson.isNull("UNITS")) {
                                final JSONObject resultUnitJson = unitJson.getJSONObject("UNITS");
                                Iterator<String> keys = resultUnitJson.keys();
                                unit = toArray(keys);

                                ArrayAdapter unitAdapter = new ArrayAdapter(MainActivitySubject.this, android.R.layout.simple_spinner_item, unit);
                                unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Setting the ArrayAdapter data on the Spinner
                                unitSpin.setAdapter(unitAdapter);

                                unitSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        String unitItem = unitSpin.getSelectedItem().toString();
                                        try {
                                            final JSONObject chapterJson = resultUnitJson.getJSONObject(unitItem);
                                            Iterator<String> keys = chapterJson.keys();
                                            chapter = toArray(keys);
                                            ArrayAdapter chapterAdapter = new ArrayAdapter(MainActivitySubject.this, android.R.layout.simple_spinner_item, chapter);
                                            chapterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            //Setting the ArrayAdapter data on the Spinner
                                            chapterSpin.setAdapter(chapterAdapter);


                                            chapterSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                    String chapterItem = chapterSpin.getSelectedItem().toString();
                                                    try {
                                                        JSONArray subchapterJson = chapterJson.getJSONArray(chapterItem);
                                                        subchapter = toStringArray(subchapterJson);
                                                        if (subchapter.length == 0) {
                                                            subchapter = new String[]{
                                                                    "No sub chapter"
                                                            };
                                                        }
                                                        ArrayAdapter subchapterAdapter = new ArrayAdapter(MainActivitySubject.this, android.R.layout.simple_spinner_item, subchapter);
                                                        subchapterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                        //Setting the ArrayAdapter data on the Spinner
                                                        subchapterSpin.setAdapter(subchapterAdapter);
                                                    } catch (Exception e) {
                                                        Log.e("Error", e.toString());
                                                    }
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> adapterView) {

                                                }
                                            });
                                        } catch (Exception e) {
                                            Log.e("Error", e.toString());
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.toString());
                        }

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }

            Log.e("Json object", jsonObject.toString());
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    JSONObject result = new JSONObject();
                    result.put("semester", tittleString);
                    result.put("subject", subjectsSpin.getSelectedItem().toString());
                    result.put("unit", unitSpin.getSelectedItem().toString());
                    result.put("chapter", chapterSpin.getSelectedItem().toString());
                    result.put("subchapter", subchapterSpin.getSelectedItem().toString());
                    editor.putString(tittle, result.toString());
                    editor.commit();
                    if (sharedpreferences.getString(AppConfig.isStaff, "").equals("yes")) {
                        Intent io = new Intent(MainActivitySubject.this, SampleToolbarActivity.class);
                        startActivity(io);
                    } else {
                        Intent io = new Intent(MainActivitySubject.this, ToolReport.class);
                        startActivity(io);
                    }
                } catch (Exception e) {
                    Log.e("error", e.toString());
                }
            }
        });

        fieldWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    JSONObject result = new JSONObject();
                    result.put("semester", tittleString);
                    result.put("subject", subjectsSpin.getSelectedItem().toString());
                    result.put("unit", unitSpin.getSelectedItem().toString());
                    result.put("chapter", chapterSpin.getSelectedItem().toString());
                    result.put("subchapter", subchapterSpin.getSelectedItem().toString());
                    editor.putString(tittle, result.toString());
                    editor.commit();
                    Intent io = new Intent(MainActivitySubject.this, FieldWorkActivity.class);
                    startActivity(io);
                } catch (Exception e) {
                    Log.e("error", e.toString());
                }
            }
        });
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = MainActivitySubject.this.getAssets().open("api/subject.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public static String[] toArray(Iterator<String> itr) {
        ArrayList<String> ret = new ArrayList<>();
        while (itr.hasNext()) {
            ret.add(itr.next());
        }
        return ret.toArray(new String[ret.size()]);
    }


    public static String[] toStringArray(JSONArray array) {
        if (array == null)
            return null;

        String[] arr = new String[array.length()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = array.optString(i);
        }
        return arr;
    }
}