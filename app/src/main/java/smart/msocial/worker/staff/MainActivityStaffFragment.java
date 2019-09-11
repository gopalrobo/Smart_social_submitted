package smart.msocial.worker.staff;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;
import info.androidhive.recyclerview.app.GlideApp;
import smart.msocial.worker.FingerprintActivity;
import smart.msocial.worker.ProfileActivity;
import smart.msocial.worker.R;
import smart.msocial.worker.Student;
import smart.msocial.worker.app.AppConfig;
import smart.msocial.worker.db.DbStudent;

import static smart.msocial.worker.app.AppConfig.loginKey;
import static smart.msocial.worker.app.AppConfig.mypreference;
import static smart.msocial.worker.app.AppConfig.studentIdKey;


/**
 * Created by user_1 on 14-07-2018.
 */

public class MainActivityStaffFragment extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    DbStudent dbStudent;
    private static final String TAG_SEMESTER1 = "SEMESTER 1";
    private static final String TAG_SEMESTER2 = "SEMESTER 2";
    private static final String TAG_SEMESTER3 = "SEMESTER 3";
    private static final String TAG_SEMESTER4 = "SEMESTER 4";
    private static final String TAG_HOME = "Home";
    private static final String TAG_PROFILE = "Profile";
    public static String CURRENT_TAG = TAG_HOME;


    FloatingActionButton semester1;
    FloatingActionButton semester2;
    FloatingActionButton semester3;
    FloatingActionButton semester4;
    private Animation animZoomOut;
    private Animation animZoomIn;
    public static final String vrpid = "vrpidKey";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appbar_main);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        TextView name = (TextView) findViewById(R.id.name);
        TextView className = (TextView) findViewById(R.id.className);
        CircleImageView studentImage = (CircleImageView) findViewById(R.id.imageview);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        dbStudent = new DbStudent(this);


        try {
            Student student = new Gson().fromJson(dbStudent.getDataByvrpid(sharedpreferences.getString(vrpid, "")).get(1), Student.class);
            name.setText(student.getStudentname());
            className.setText(student.getDegreeName());
            GlideApp.with(MainActivityStaffFragment.this)
                    .load(student.getImage())
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .placeholder(R.drawable.profile)
                    .into(studentImage);
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "No Student found", Toast.LENGTH_SHORT).show();
        }


        semester1 = (FloatingActionButton) findViewById(R.id.semester1);
        semester2 = (FloatingActionButton) findViewById(R.id.semester2);
        semester3 = (FloatingActionButton) findViewById(R.id.semester3);
        semester4 = (FloatingActionButton) findViewById(R.id.semester4);

        loadHomeFragment();


        semester1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CURRENT_TAG = TAG_SEMESTER1;

                startFragment(new SemesterOneFragment());
                semester1.setBackgroundTintList(getResources().getColorStateList(R.color.blueGray));
                semester2.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                semester3.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                semester4.setBackgroundTintList(getResources().getColorStateList(R.color.white));

            }
        });
        semester2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CURRENT_TAG = TAG_SEMESTER2;

                startFragment(new SemesterTwoFragment());
                semester2.setBackgroundTintList(getResources().getColorStateList(R.color.blueGray));
                semester1.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                semester3.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                semester4.setBackgroundTintList(getResources().getColorStateList(R.color.white));
            }
        });
        semester3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CURRENT_TAG = TAG_SEMESTER3;

                startFragment(new SemesterThreeFragment());
                semester3.setBackgroundTintList(getResources().getColorStateList(R.color.blueGray));
                semester2.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                semester1.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                semester4.setBackgroundTintList(getResources().getColorStateList(R.color.white));
            }
        });
        semester4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CURRENT_TAG = TAG_SEMESTER4;

                startFragment(new SemesterFourFragment());
                semester4.setBackgroundTintList(getResources().getColorStateList(R.color.blueGray));
                semester2.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                semester3.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                semester1.setBackgroundTintList(getResources().getColorStateList(R.color.white));
            }
        });


        studentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProfileFragment();
            }
        });
        studentImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                loadProfileFragment();
                AppConfig.styleToast(" Profile  ", getApplicationContext());
                return false;
            }
        });
    }


    private void startFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }


    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(R.menu.main_menu, paramMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        switch (paramMenuItem.getItemId()) {
            default:
                return super.onOptionsItemSelected(paramMenuItem);
            case R.id.editProfile:
//                Intent io = new Intent(MainActivityStaffFragment.this, ProfileActivity.class);
//                startActivity(io);
                return true;
            case R.id.exit:
                AlertDialog diaBox = Option();
                diaBox.show();
                return true;
        }
    }

    @Override
    public void onBackPressed() {

        if (!CURRENT_TAG.equals(TAG_HOME)) {
            loadHomeFragment();
        } else {
            finishAffinity();
            super.onBackPressed();
        }
    }

    private void loadHomeFragment() {
        CURRENT_TAG = TAG_HOME;

        semester1.setBackgroundTintList(getResources().getColorStateList(R.color.blueGray));
        semester2.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        semester3.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        semester4.setBackgroundTintList(getResources().getColorStateList(R.color.white));

        startFragment(new SemesterOneFragment());
    }

    private void loadProfileFragment() {
        CURRENT_TAG = TAG_PROFILE;
        semester1.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        semester2.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        semester3.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        semester4.setBackgroundTintList(getResources().getColorStateList(R.color.white));

        startFragment(new ProfileFragment());
    }

    private AlertDialog Option() {

        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Alert")
                .setMessage("Are you sure want to exit?")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.remove(studentIdKey);
                        editor.remove(loginKey);
                        editor.commit();
                        Intent io = new Intent(MainActivityStaffFragment.this, FingerprintActivity.class);
                        startActivity(io);
                        finish();
                        dialog.dismiss();
                    }
                })


                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }


}
