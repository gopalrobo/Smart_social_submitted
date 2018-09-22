package smart.social.worker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PraAdapter extends RecyclerView.Adapter<PraAdapter.MyViewHolder> {


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

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            vidolin = (LinearLayout) view.findViewById(R.id.vidolin);
            infolin = (LinearLayout) view.findViewById(R.id.infolin);
            locationlin = (LinearLayout) view.findViewById(R.id.locationlin);
            addimagelin = (LinearLayout) view.findViewById(R.id.addimagelin);
            descriptionlin = (LinearLayout) view.findViewById(R.id.descriptionlin);
            imagevideo = (ImageView) view.findViewById(R.id.imagevideo);
        }
    }


    public PraAdapter(List<Pra> moviesList, Context mContext, VideoClick videoClick) {
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
        if (pra.getYear().equalsIgnoreCase("true")) {
            holder.infolin.setVisibility(View.GONE);
            holder.vidolin.setVisibility(View.GONE);
        } else {
            holder.infolin.setVisibility(View.VISIBLE);
            holder.vidolin.setVisibility(View.VISIBLE);
            holder.imagevideo.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
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
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pra.getYear().equalsIgnoreCase("true")) {
                    sharedpreferences = mContext.getSharedPreferences(mypreference,
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(tittle, pra.getTitle());
                    editor.commit();
                    Intent io = new Intent(mContext, MainActivitySubject.class);
                    mContext.startActivity(io);
                } else if (pra.getGenre().equalsIgnoreCase("pra")) {
                    sharedpreferences = mContext.getSharedPreferences(mypreference,
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(tittle, pra.getTitle());
                    editor.commit();
                    Intent io = new Intent(mContext, info.androidhive.recyclerview.MainActivity.class);
                    mContext.startActivity(io);
                } else if (pra.getYear().equalsIgnoreCase("true")) {
                    JSONObject result = new JSONObject();
                    try {
                        result.put("semester", pra.getTitle());
                        result.put("subject", pra.getGenre());
                        result.put("chapter", pra.getGenre());
                        sharedpreferences = mContext.getSharedPreferences(mypreference,
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(tittle, result.toString());
                        editor.commit();
                        if (pra.getGenre().equals("Field")) {
                            Intent io = new Intent(mContext, FieldWork.class);
                            mContext.startActivity(io);
                        } else if (pra.getGenre().equals("Research")) {
                            Intent io = new Intent(mContext, Research.class);
                            mContext.startActivity(io);
                        }else if (pra.getGenre().equals("Assignment")) {
                            Intent io = new Intent(mContext, Assignment.class);
                            mContext.startActivity(io);
                        }else if (pra.getGenre().equals("survey")) {
                            videoClick.tittleClick(position);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (pra.getYear().equalsIgnoreCase("true")) {
                    if (pra.getGenre().equals("true")) {
                        Intent io = new Intent(mContext, TeamMember.class);
                        mContext.startActivity(io);
                    }
                } else {
                    Toast.makeText(mContext, pra.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
