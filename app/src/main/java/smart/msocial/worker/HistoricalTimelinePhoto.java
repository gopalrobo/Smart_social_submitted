package smart.msocial.worker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import info.androidhive.recyclerview.app.GlideApp;
import smart.msocial.worker.app.AppConfig;
import smart.msocial.worker.app.Imageutils;
import smart.msocial.worker.db.DbImage;

/**
 * Created by Gopal on 08-11-2017.
 */

public class HistoricalTimelinePhoto extends AppCompatActivity implements Imageutils.ImageAttachmentListener{

    Imageutils imageutils;
    private MultiSnapRecyclerView mRecyclerViewhistory;
    private ProfileAdapter mRecyclerAdapterhistory;
    private ArrayList<Plot> historyList = new ArrayList<>();
    private String imagePath;
    GPSTracker gps;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    public static final String filename = "filenameKey";
    public static final String vrpid = "vrpidKey";
    public static final String staffName = "staffNameKey";


    String tittleString;
    String vrpString;
    DbImage dbImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historical_timeline_photo);
        imageutils = new Imageutils(this);
        gps = new GPSTracker(HistoricalTimelinePhoto.this);
        dbImage = new DbImage(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
        }

        ImageView addimage = (ImageView) findViewById(R.id.historyaddimage);
        if (sharedpreferences.getString(AppConfig.isStaff, "").equals("no")) {
            addimage.setVisibility(View.INVISIBLE);
        }

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImage(-1, "", "Add Image");
            }
        });
        mRecyclerViewhistory = (MultiSnapRecyclerView) findViewById(R.id.historylist);
        mRecyclerAdapterhistory = new ProfileAdapter(HistoricalTimelinePhoto.this, historyList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewhistory.setLayoutManager(thirdManager);
        mRecyclerViewhistory.setAdapter(mRecyclerAdapterhistory);
        mRecyclerViewhistory.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewhistory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                if (sharedpreferences.contains(staffName)) {
                    addImage(position, historyList.get(position).getTag(), "Update images");
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        TextView submit = (TextView) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent io = new Intent(HistoricalTimelinePhoto.this, AdditionalInformation.class);
                startActivity(io);
            }
        });

        prepareData();
    }

    private void prepareData() {
        historyList = new ArrayList<>();
        List<String> history = dbImage.getAllData(vrpString, tittleString, getResources().getString(R.string.history));
        for (int i = 0; i < history.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject(history.get(i));
                Plot plot = new Plot(jsonObject.getString("geotag"), jsonObject.getString("name"),
                        jsonObject.getString("image"), jsonObject.getString("description"), null);
                historyList.add(plot);
            } catch (JSONException e) {
                Log.d("HistoricalTimelinePhoto", e.toString());
            }
        }
        mRecyclerAdapterhistory.notifyData(historyList);
    }


    public void addImage(final int position, final String name, String tittle) {
        imagePath = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(HistoricalTimelinePhoto.this);
        LayoutInflater inflater = HistoricalTimelinePhoto.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.addimage_popup, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final CustomFontEditText description = (CustomFontEditText) dialogView.findViewById(R.id.description);
        final LinearLayout imagelin = (LinearLayout) dialogView.findViewById(R.id.imglin);
        final CircleImageView image = (CircleImageView) dialogView.findViewById(R.id.image);
        final CustomFontEditText geotag = (CustomFontEditText) dialogView.findViewById(R.id.geotag);
        final ImageView georefresh = (ImageView) dialogView.findViewById(R.id.refresh);
        final CustomFontEditText imagetxt = (CustomFontEditText) dialogView.findViewById(R.id.imagetxt);
        CustomFontTextView submit = (CustomFontTextView) dialogView.findViewById(R.id.r_submittxt);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.cancel();
            }
        });
        final CustomFontTextView itemtittle = (CustomFontTextView) dialogView.findViewById(R.id.itemtittle);
        itemtittle.setText(tittle);
        if (position != -1) {
            String data = dbImage.getData(vrpString, tittleString, getResources().getString(R.string.history), name);
            try {
                JSONObject jsonObject = new JSONObject(data);
                geotag.setText(jsonObject.getString("geotag"));
                description.setText(jsonObject.getString("description"));
                GlideApp.with(HistoricalTimelinePhoto.this).load(jsonObject.getString("image"))
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                imagePath = jsonObject.getString("image");
                imagetxt.setText(jsonObject.getString("name"));
            } catch (JSONException e) {
                Log.d("HistoricalTimelinePhoto", e.toString());
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (description.getText().toString().length() <= 0
                        || imagetxt.getText().toString().length() <= 0
                        || geotag.getText().toString().length() <= 0
                        || description.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("geotag", geotag.getText().toString());
                        jsonObject.put("description", description.getText().toString());
                        jsonObject.put("image", imagePath);
                        jsonObject.put("name", imagetxt.getText().toString());
                        if (position == -1) {
                            dbImage.addData(vrpString, tittleString, getResources().getString(R.string.history)
                                    , geotag.getText().toString(), jsonObject.toString());
                            Plot plot = new Plot(jsonObject.getString("geotag"), jsonObject.getString("name"),
                                    jsonObject.getString("image"), jsonObject.getString("description"), null);
                            historyList.add(plot);
                            mRecyclerAdapterhistory.notifyData(historyList);
                        } else {
                            dbImage.updatedata(vrpString, tittleString, getResources().getString(R.string.history)
                                    , name, geotag.getText().toString(), jsonObject.toString());
                            historyList.get(position).setPlotname(imagetxt.getText().toString());
                            historyList.get(position).setTag(geotag.getText().toString());
                            historyList.get(position).setPlotarea(description.getText().toString());
                            historyList.get(position).setPlotimage(imagePath);
                            mRecyclerAdapterhistory.notifyData(historyList);
                        }
                    } catch (JSONException e) {
                        Log.d("HistoricalTimelinePhoto", e.toString());
                    }
                }
                b.cancel();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageutils.imagepicker(1);

            }
        });

        georefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if GPS enabled
                gps = new GPSTracker(HistoricalTimelinePhoto.this);
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
            gps = new GPSTracker(HistoricalTimelinePhoto.this);
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
        b.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageutils.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        imageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        String path = Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        imageutils.createImage(file, filename, path, false);
        imagePath=imageutils.getPath(uri);
    }
}
