package smart.social.worker.forms;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import smart.social.worker.R;
import smart.social.worker.Student;
import smart.social.worker.db.DbStudent;


/**
 * Created by user_1 on 11-07-2018.
 */

public class ProfileFragment extends Fragment {


    private View view;
    SharedPreferences sharedpreferences;
    String studentId = null;
    DbStudent dbStudent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.first_form, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);




        return view;
    }

}
