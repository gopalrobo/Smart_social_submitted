package smart.social.worker.off;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import smart.social.worker.R;


public class OfficerAdapter extends RecyclerView.Adapter<OfficerAdapter.MyViewHolder> {

    private List<Officer> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public OfficerAdapter(List<Officer> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.officer_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Officer officer = moviesList.get(position);
        holder.title.setText(officer.getName());
        holder.genre.setText(officer.getDesignation());
        holder.year.setText(officer.getSenior().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void notifyData(List<Officer> moviesList) {
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }
}
