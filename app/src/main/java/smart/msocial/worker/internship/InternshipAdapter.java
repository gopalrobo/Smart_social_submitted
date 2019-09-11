package smart.msocial.worker.internship;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import smart.msocial.worker.Pra;
import smart.msocial.worker.R;
import smart.msocial.worker.field.FieldWorkActivity;
import smart.msocial.worker.observation.ObservationVisitActivity;

public class InternshipAdapter extends RecyclerView.Adapter<InternshipAdapter.MyViewHolder> {


    private List<Pra> moviesList;
    private Context mContext;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }


    public InternshipAdapter(List<Pra> moviesList, Context mContext) {
        this.moviesList = moviesList;
        this.mContext = mContext;
        sharedpreferences = mContext.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.field_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Pra pra = moviesList.get(position);

        holder.title.setText(pra.getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position == 0) {
                    sharedpreferences = mContext.getSharedPreferences(mypreference,
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("sheetno", "Internship Observation Visit");
                    editor.commit();

                    Intent intent = new Intent(mContext, ObservationVisitActivity.class);
                    mContext.startActivity(intent);
                } else {
                    sharedpreferences = mContext.getSharedPreferences(mypreference,
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("sheetno", "Internship Sheet " + String.valueOf(position + 1));
                    editor.commit();

                    Intent intent = new Intent(mContext, FieldWorkActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
