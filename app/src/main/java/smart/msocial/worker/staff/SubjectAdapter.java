package smart.msocial.worker.staff;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import smart.msocial.worker.R;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder> {


    private List<Subject> moviesList;
    private Context mContext;
    private SubjectClick subjectClick;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle;
        private ImageView deleteImg;
        private RelativeLayout relativeClick;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            subtitle = (TextView) view.findViewById(R.id.subtitle);
            deleteImg = (ImageView) view.findViewById(R.id.deleteImg);
            relativeClick = (RelativeLayout) view.findViewById(R.id.relativeClick);
        }
    }


    public SubjectAdapter(List<Subject> moviesList, Context mContext, SubjectClick subjectClick) {
        this.moviesList = moviesList;
        this.mContext = mContext;
        this.subjectClick = subjectClick;
    }

    public void notifyData(List<Subject> moviesList) {
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Subject pra = moviesList.get(position);


        holder.title.setText(pra.getName());
        holder.subtitle.setText(pra.getCode());
        holder.relativeClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectClick.onItemClick(position);
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subjectClick.onItemClick(position);
            }
        });

        holder.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectClick.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
