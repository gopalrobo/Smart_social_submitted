package smart.msocial.worker.observation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import smart.msocial.worker.DividerItemDecoration;
import smart.msocial.worker.Pra;
import smart.msocial.worker.R;

public class MainActivityObservation extends AppCompatActivity  {
    private List<Pra> praList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OnservationVisitAdapter mAdapter;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    private static final int SURVEY_REQUEST = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new OnservationVisitAdapter(praList, this);
        getSupportActionBar().setTitle("Observation visit List");
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        prepareMovieData();
    }

    private void prepareMovieData() {

        for (int i = 0; i < 35; i++) {
            Pra pra = new Pra("Observation visit Sheet "+String.valueOf(i+1), "", "", "");
            praList.add(pra);
        }
        Pra pra = new Pra("Consolidated Sheet", "", "", "");
        praList.add(pra);
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
