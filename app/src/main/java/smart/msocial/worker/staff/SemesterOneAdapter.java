package smart.msocial.worker.staff;

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

import java.util.List;

import smart.msocial.worker.MainActivitySubject;
import smart.msocial.worker.Pra;
import smart.msocial.worker.R;
import smart.msocial.worker.VideoClick;
import smart.msocial.worker.assignment.MainActivityAssignmentStaff;
import smart.msocial.worker.group.MainActivityGroupProjectStaff;
import smart.msocial.worker.observation.MainActivityObservationStaff;

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
        private final LinearLayout descriptionlin, bottomLin;
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
            bottomLin = (LinearLayout) view.findViewById(R.id.bottomLin);
            imagevideo = (ImageView) view.findViewById(R.id.imagevideo);
            relativeClick = (RelativeLayout) view.findViewById(R.id.relativeClick);
        }
    }


    public SemesterOneAdapter(List<Pra> moviesList, Context mContext, VideoClick videoClick) {
        this.moviesList = moviesList;
        this.mContext = mContext;
        this.videoClick = videoClick;
        sharedpreferences = this.mContext.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
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

        holder.title.setText(pra.getTitle());
        holder.bottomLin.setVisibility(View.GONE);
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
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(tittle, "SEMESTER 1");
        editor.putString("semester", "SEMESTER 1");
        editor.commit();
        if (pra.getTitle().equals("Observation Visits")) {
            Intent intent = new Intent(mContext, MainActivityObservationStaff.class);
            mContext.startActivity(intent);
        } else if (pra.getTitle().equals("Assignment")) {
            Intent intent = new Intent(mContext, MainActivityAssignmentStaff.class);
            mContext.startActivity(intent);
        }else if (pra.getTitle().equals("Group Project")) {
            Intent intent = new Intent(mContext, MainActivityGroupProjectStaff.class);
            mContext.startActivity(intent);
        }else if (pra.getTitle().equalsIgnoreCase("Theory")) {

            Intent io = new Intent(mContext, MainActivitySubject.class);
            mContext.startActivity(io);
        }
    }
}
