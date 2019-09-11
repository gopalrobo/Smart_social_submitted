package smart.msocial.worker.forms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import smart.msocial.worker.MainActivitySubject;
import smart.msocial.worker.Pra;
import smart.msocial.worker.R;
import smart.msocial.worker.communityPlace.MainActivityCommPlacement;
import smart.msocial.worker.concurrent.MainActivityConCurrent;
import smart.msocial.worker.concurrent.MainActivityConcurrentStaff;
import smart.msocial.worker.internship.MainActivityInternship;
import smart.msocial.worker.research.ResearchActivity;
import smart.msocial.worker.VideoClick;
import smart.msocial.worker.assignment.MainActivityAssignment;
import smart.msocial.worker.schoolPlace.MainActivitySchoolPlacement;

public class SemesterTwoAdapter extends RecyclerView.Adapter<SemesterTwoAdapter.MyViewHolder> {


    private List<Pra> moviesList;
    private Context mContext;
    YouTubePlayer player;
    private VideoClick videoClick;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout vidolin;
        private final LinearLayout infolin;
        private final LinearLayout locationlin;
        private final ImageView imagevideo;
        private final LinearLayout descriptionlin;
        public TextView title;
        private final LinearLayout addimagelin;
        private RelativeLayout relativeClick;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            vidolin = (LinearLayout) view.findViewById(R.id.vidolin);
            infolin = (LinearLayout) view.findViewById(R.id.infolin);
            locationlin = (LinearLayout) view.findViewById(R.id.locationlin);
            addimagelin = (LinearLayout) view.findViewById(R.id.addimagelin);
            descriptionlin = (LinearLayout) view.findViewById(R.id.descriptionlin);
            imagevideo = (ImageView) view.findViewById(R.id.imagevideo);
            relativeClick = (RelativeLayout) view.findViewById(R.id.relativeClick);
        }
    }


    public SemesterTwoAdapter(List<Pra> moviesList, Context mContext, VideoClick videoClick) {
        this.moviesList = moviesList;
        this.mContext = mContext;
        this.videoClick = videoClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Pra pra = moviesList.get(position);

        if (pra.getTitle().equalsIgnoreCase("Theory")) {
            holder.locationlin.setVisibility(View.GONE);
            holder.addimagelin.setVisibility(View.GONE);
        }

        holder.title.setText(pra.getTitle());
        holder.infolin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoClick.webClick(position);
            }
        });
        holder.vidolin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoClick.videoClick(position);

            }
        });
        holder.locationlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoClick.mapClick(position);
            }
        });
        holder.addimagelin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoClick.imageClick(position);
            }
        });
        holder.descriptionlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoClick.reportClick(position);
            }
        });
        holder.relativeClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callActivity(pra, position);
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callActivity(pra, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    private void callActivity(Pra pra, int position) {
        {
            if (pra.getTitle().equalsIgnoreCase("Theory")) {
                sharedpreferences = mContext.getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(tittle, "SEMESTER 2");
                editor.commit();
                //  Intent io = new Intent(mContext, MainActivitySubject.class);
                Intent io = new Intent(mContext, MainActivitySubject.class);
                mContext.startActivity(io);
            } else if (pra.getTitle().equalsIgnoreCase("pra")) {
                sharedpreferences = mContext.getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(tittle, pra.getTitle());
                editor.commit();
                Intent io = new Intent(mContext, info.androidhive.recyclerview.MainActivity.class);
                mContext.startActivity(io);
            } else {
                JSONObject result = new JSONObject();
                try {
                    result.put("semester", "SEMESTER 2");
                    result.put("subject", "Field");
                    result.put("chapter", "Field");
                    sharedpreferences = mContext.getSharedPreferences(mypreference,
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(tittle, result.toString());
                    editor.putString("semester", "SEMESTER 2");
                    editor.commit();
                    if (pra.getTitle().contains("Community Placement")) {
                        Intent io = new Intent(mContext, MainActivityCommPlacement.class);
                        mContext.startActivity(io);
                    } else if (pra.getTitle().contains("School Placement")) {
                        Intent io = new Intent(mContext, MainActivitySchoolPlacement.class);
                        mContext.startActivity(io);
                    } else if (pra.getTitle().contains("ResearchActivity")) {
                        Intent io = new Intent(mContext, ResearchActivity.class);
                        mContext.startActivity(io);
                    } else if (pra.getTitle().contains("Assignment")) {
                        Intent io = new Intent(mContext, MainActivityAssignment.class);
                        mContext.startActivity(io);
                    } else if (pra.getTitle().contains("Summer Internship")) {
                        Intent io = new Intent(mContext, MainActivityInternship.class);
                        mContext.startActivity(io);
                    }else if (pra.getTitle().equals("Concurrent Field Work")) {
                        Intent intent = new Intent(mContext, MainActivityConCurrent.class);
                        mContext.startActivity(intent);
                    }  else if (pra.getTitle().contains("survey")) {
                        videoClick.tittleClick(position);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
