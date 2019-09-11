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
import smart.msocial.worker.Student;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {


    private List<Student> moviesList;
    private Context mContext;
    private StuedntClick subjectClick;

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


    public StudentAdapter(List<Student> moviesList, Context mContext, StuedntClick subjectClick) {
        this.moviesList = moviesList;
        this.mContext = mContext;
        this.subjectClick = subjectClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    public void notifyData(List<Student> moviesList) {
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Student pra = moviesList.get(position);


        holder.title.setText(pra.getStudentname());
        holder.subtitle.setText(pra.getSudentid());
        holder.relativeClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectClick.onStItemClick(position);
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subjectClick.onStItemClick(position);
            }
        });

        holder.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectClick.onStDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
