package smart.social.worker.field;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.androidadvance.androidsurvey.SurveyActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.github.hidroh.calendar.MainActivityEvent;
import smart.social.worker.CustomMarkerClusteringDemoActivity;
import smart.social.worker.DividerItemDecoration;
import smart.social.worker.FinalReport;
import smart.social.worker.MainActivityVideo;
import smart.social.worker.MapsFragActivity;
import smart.social.worker.Pra;
import smart.social.worker.PraAdapter;
import smart.social.worker.ProfileActivity;
import smart.social.worker.R;
import smart.social.worker.TeamMember;
import smart.social.worker.VideoClick;

public class MainActivityField extends AppCompatActivity  {
    private List<Pra> praList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FieldWorkAdapter mAdapter;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    private static final int SURVEY_REQUEST = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new FieldWorkAdapter(praList, this);
        getSupportActionBar().setTitle("Field Work List");
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        prepareMovieData();
    }

    private void prepareMovieData() {

        for (int i = 0; i < 25; i++) {
            Pra pra = new Pra("Field Work Sheet "+String.valueOf(i+1), "", "", "");
            praList.add(pra);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SURVEY_REQUEST) {
            if (resultCode == RESULT_OK) {

                String answers_json = data.getExtras().getString("answers");
                Log.d("****", "****************** WE HAVE ANSWERS ******************");
                Log.v("ANSWERS JSON", answers_json);
                Log.d("****", "*****************************************************");

                //do whatever you want with them...
            }
        }
    }



}
