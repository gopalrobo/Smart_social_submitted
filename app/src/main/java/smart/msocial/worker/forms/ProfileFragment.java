package smart.msocial.worker.forms;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import smart.msocial.worker.R;
import smart.msocial.worker.db.DbStudent;


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
