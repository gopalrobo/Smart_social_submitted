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
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayer;

import java.util.List;

import smart.msocial.worker.MainActivitySubject;
import smart.msocial.worker.Pra;
import smart.msocial.worker.R;
import smart.msocial.worker.VideoClick;
import smart.msocial.worker.assignment.MainActivityAssignment;
import smart.msocial.worker.group.GroupProjectActivity;
import smart.msocial.worker.observation.MainActivityObservation;

public class SemesterOneAdapter extends RecyclerView.Adapter<SemesterOneAdapter.MyViewHolder> {


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


    public SemesterOneAdapter(List<Pra> moviesList, Context mContext, VideoClick videoClick) {
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
                callActivity(pra);
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callActivity(pra);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    private void callActivity(Pra pra) {
        sharedpreferences = mContext.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(tittle, "SEMESTER 1");
        editor.putString("semester", "SEMESTER 1");
        editor.commit();
        if (pra.getTitle().equalsIgnoreCase("Theory")) {
            Intent io = new Intent(mContext, MainActivitySubject.class);
            mContext.startActivity(io);
        } else if (pra.getTitle().equalsIgnoreCase("Group Project")) {
            Intent io = new Intent(mContext, GroupProjectActivity.class);
            mContext.startActivity(io);
        } else if (pra.getTitle().equalsIgnoreCase("Observation Visits")) {
            Intent io = new Intent(mContext, MainActivityObservation.class);
            mContext.startActivity(io);
        } else if (pra.getTitle().contains("Assignment")) {
            Intent io = new Intent(mContext, MainActivityAssignment.class);
            mContext.startActivity(io);
        } else {
            Toast.makeText(mContext, "Under construction", Toast.LENGTH_SHORT).show();
        }
    }
}
