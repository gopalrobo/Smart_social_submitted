package smart.social.worker.forms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

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

import static android.app.Activity.RESULT_OK;

public class MainSubjectFragment extends Fragment implements VideoClick {
    private List<Pra> praList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PraAdapter mAdapter;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    private static final int SURVEY_REQUEST = 1337;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_fragment, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new PraAdapter(praList, getActivity(), this);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        prepareMovieData();
        return view;
    }

    private void prepareMovieData() {


        Pra pra = new Pra("SEMESTER 1", "", "", "https://sites.google.com/site/faopratoolkit/");
        praList.add(pra);

        pra = new Pra("SEMESTER 2", "-07fOqnyXz8", "", "https://sites.google.com/site/faopratoolkit/historical-tiemline");
        praList.add(pra);

        pra = new Pra("SEMESTER 3", "Cobx2T95et0", "", "https://sites.google.com/site/faopratoolkit/gender-analysis");
        praList.add(pra);

        pra = new Pra("SEMESTER 4", "wlRoYJ2-U2g", "", "https://sites.google.com/site/faopratoolkit/village-resource-mapping");
        praList.add(pra);

        pra = new Pra("Special courses", "O0wa42N5Rtw", "", "https://sites.google.com/site/faopratoolkit/transect-walk");
        praList.add(pra);

//        pra = new Pra("PRA", "pra", "true", "https://sites.google.com/site/faopratoolkit/transect-walk");
//        praList.add(pra);

        pra = new Pra("FILED WORK", "Field", "true", "https://sites.google.com/site/faopratoolkit/transect-walk");
        praList.add(pra);

        pra = new Pra("ASSIGNMENT", "Assignment", "true", "https://sites.google.com/site/faopratoolkit/transect-walk");
        praList.add(pra);

        pra = new Pra("RESEARCH", "Research", "true", "https://sites.google.com/site/faopratoolkit/transect-walk");
        praList.add(pra);

        pra = new Pra("SURVEY", "survey", "true", "https://sites.google.com/site/faopratoolkit/transect-walk");
        praList.add(pra);
        mAdapter.notifyDataSetChanged();
    }



    @Override
    public void videoClick(int position) {
        if (praList.get(position).getYear().equals("true")) {
            Intent io = new Intent(getActivity(), TeamMember.class);
            startActivity(io);
        } else {
            Intent io = new Intent(getActivity(), MainActivityVideo.class);
            io.putExtra("video", praList.get(position).getGenre());
            startActivity(io);
        }
    }

    @Override
    public void webClick(int position) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(praList.get(position).getUrl()));
        startActivity(browserIntent);
    }

    @Override
    public void mapClick(int position) {
        Intent io = new Intent(getActivity(), MapsFragActivity.class);
        io.putExtra("tittle", praList.get(position).getTitle());
        startActivity(io);
    }

    @Override
    public void imageClick(int position) {
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(tittle, praList.get(position).getTitle());
        editor.commit();
        Intent io = new Intent(getActivity(), CustomMarkerClusteringDemoActivity.class);
        io.putExtra("tittle", praList.get(position).getTitle());
        startActivity(io);
    }

    @Override
    public void reportClick(int position) {
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(tittle, praList.get(position).getTitle());
        editor.commit();
        Intent io = new Intent(getActivity(), FinalReport.class);
        startActivity(io);
    }

    @Override
    public void tittleClick(int position) {
        Intent i_survey = new Intent(getActivity(), SurveyActivity.class);
        //you have to pass as an extra the json string.
        i_survey.putExtra("json_survey", loadSurveyJson("example_survey_1.json"));
        startActivityForResult(i_survey, SURVEY_REQUEST);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.profile) {
//            Intent io = new Intent(getActivity(), ProfileActivity.class);
//            startActivity(io);
//            return true;
//        }
//        if (id == R.id.timeTable) {
//            Intent io = new Intent(getActivity(), MainActivityEvent.class);
//            startActivity(io);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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


    //json stored in the assets folder. but you can get it from wherever you like.
    private String loadSurveyJson(String filename) {
        try {
            InputStream is = getActivity().getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}