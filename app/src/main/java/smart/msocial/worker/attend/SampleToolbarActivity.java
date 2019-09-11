package smart.msocial.worker.attend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.davidecirillo.multichoicerecyclerview.MultiChoiceAdapter;
import com.davidecirillo.multichoicerecyclerview.MultiChoiceToolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import smart.msocial.worker.R;
import smart.msocial.worker.ToolReport;
import smart.msocial.worker.db.DbReport;

public class SampleToolbarActivity extends BaseActivity {


    DbReport dbReport;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    public static final String vrpid = "vrpidKey";
    String tittleString;
    String vrpString;

    private static final int DEFAULT_QUANTITY_MODE = QuantityMode.STRING;
    private TextView hours;
    private TextView marks;
    private TextView credit;
    private TextView code;

    @IntDef({
            QuantityMode.NONE,
            QuantityMode.STRING,
            QuantityMode.PLURALS,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface QuantityMode {
        int NONE = 0;
        int STRING = 1;
        int PLURALS = 2;
    }

    public static final String SELECTED_ITEMS = "selectedItems";

    @BindView(R.id.multiChoiceRecyclerView)
    RecyclerView mMultiChoiceRecyclerView;

    private ArrayList<String> stringList;
    private MySampleToolbarAdapter mMySampleToolbarAdapter;
    private @SampleToolbarActivity.QuantityMode
    int quantityMode =QuantityMode.PLURALS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stringList = new ArrayList<>();

        hours = (TextView) findViewById(R.id.hours);
        marks = (TextView) findViewById(R.id.marks);
        code = (TextView) findViewById(R.id.code);
        credit = (TextView) findViewById(R.id.credit);
        dbReport = new DbReport(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
        }

        try {
            JSONObject jsonObject = new JSONObject(tittleString);
            JSONObject jsonObject1 = new JSONObject(loadJSONFromAsset());
            JSONObject result = jsonObject1.getJSONObject(jsonObject.getString("semester")).getJSONObject(jsonObject.getString("subject"));

            credit.setText(result.getString("CREDIT"));
            code.setText(result.getString("CODE"));
            marks.setText(result.getString("MARKS"));
            hours.setText(result.getString("HRS/WEEK"));

        } catch (JSONException e) {
            Log.e("Error", e.toString());
        }

        setUpMultiChoiceRecyclerView();
    }

    @Override
    protected int setActivityIdentifier() {
        return R.layout.activity_sample_toolbar;
    }

    @OnClick(R.id.doneAll)
    void result() {

        if (stringList != null && mMySampleToolbarAdapter != null) {

            ArrayList<String> resultItems = new ArrayList<>();
            Log.d("selected Items", "below");
            for (int i : mMySampleToolbarAdapter.getSelectedItemList()) {
                resultItems.add(stringList.get(i));
                Log.d(String.valueOf(i) + "-->", stringList.get(i));
            }
        }


        if (hours.getText().toString().length() > 0
                && credit.getText().toString().length() > 0
                && marks.getText().toString().length() > 0
                && code.getText().toString().length() > 0) {
            JSONObject attendanceObject = new JSONObject();
            try {
                attendanceObject.put("hours", hours.getText().toString());
                attendanceObject.put("credit", credit.getText().toString());
                attendanceObject.put("marks", marks.getText().toString());
                attendanceObject.put("code", code.getText().toString());
            } catch (Exception e) {
                Log.d("Attendance ", e.toString());
            }
            int success = dbReport.updatedata(vrpString, tittleString,
                    getResources().getString(R.string.attendance), attendanceObject.toString());
            if (success == 0)
                dbReport.addData(vrpString, tittleString,
                        getResources().getString(R.string.attendance), attendanceObject.toString());
            Intent io = new Intent(SampleToolbarActivity.this, ToolReport.class);
            startActivity(io);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ((MultiChoiceAdapter) mMultiChoiceRecyclerView.getAdapter()).onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        ((MultiChoiceAdapter) mMultiChoiceRecyclerView.getAdapter()).onRestoreInstanceState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void setUpMultiChoiceRecyclerView() {
        mMultiChoiceRecyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));

        stringList = getSampleList();

        MultiChoiceToolbar.Builder builder = new MultiChoiceToolbar.Builder(SampleToolbarActivity.this, toolbar)
                .setMultiChoiceColours(R.color.colorPrimaryMulti, R.color.colorPrimaryDarkMulti)
                .setDefaultIcon(R.drawable.ic_arrow_back_white_24dp, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });

        switch (quantityMode) {
            case QuantityMode.NONE:
                /*
                 * You may use one of the setTitles()-methods
                 * or none (it will then show the count of selected items instead).
                 */
                break;

            case QuantityMode.STRING:
                builder.setTitles(getString(toolbarTitle()), "item selected");
                break;

            case QuantityMode.PLURALS:
                builder.setTitles(toolbarTitle(), R.plurals.numberOfSelectedItems);
                break;
        }

        MultiChoiceToolbar multiChoiceToolbar = builder.build();

        mMySampleToolbarAdapter = new MySampleToolbarAdapter(stringList, getApplicationContext());
        mMySampleToolbarAdapter.setMultiChoiceToolbar(multiChoiceToolbar);

        mMultiChoiceRecyclerView.setAdapter(mMySampleToolbarAdapter);
    }

    private ArrayList<String> getSampleList() {
        ArrayList<String> sampleList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            sampleList.add("Student Name " + i);
        }
        return sampleList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mMySampleToolbarAdapter != null) {
            switch (item.getItemId()) {
                case R.id.select_all:
                    mMySampleToolbarAdapter.selectAll();
                    return true;

                case R.id.deselect_all:
                    mMySampleToolbarAdapter.deselectAll();
                    return true;

                case R.id.select_3:
                    boolean select = mMySampleToolbarAdapter.select(2);
                    if (!select) {
                        Toast.makeText(this, "Item not selected because not in multi choice mode or single click mode, select something first.",
                                Toast.LENGTH_LONG).show();
                    }
                    return true;

                case R.id.single_click_mode:
                    mMySampleToolbarAdapter.setSingleClickMode(!mMySampleToolbarAdapter.isInSingleClickMode());
                    Toast.makeText(getApplicationContext(),
                            "Always Single Click Mode [" + mMySampleToolbarAdapter.isInSingleClickMode() + "]",
                            Toast.LENGTH_SHORT).show();

                    return true;

                case R.id.plural_mode:
                   // setQuantityMode(QuantityMode.PLURALS);

                    Toast.makeText(getApplicationContext(), "set toolbar to use QuantityMode.PLURALS", Toast.LENGTH_SHORT).show();
                    return true;
            }
        }

        return false;
    }

    @Override
    protected boolean showBackHomeAsUpIndicator() {
        return true;
    }

    @Override
    protected int toolbarTitle() {
        return R.string.toolbar_controls;
    }

    @QuantityMode
    public int getQuantityMode() {
        return quantityMode;
    }

    public void setQuantityMode(@QuantityMode int quantityMode) {
        mMySampleToolbarAdapter.deselectAll();

        this.quantityMode = quantityMode;
        setUpMultiChoiceRecyclerView();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = SampleToolbarActivity.this.getAssets().open("api/subject.json");
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
}
