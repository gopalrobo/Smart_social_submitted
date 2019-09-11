package smart.msocial.worker.communityPlace;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import smart.msocial.worker.R;
import smart.msocial.worker.observation.BaseBean;

public class CommunityPlaceStaffAdapter extends RecyclerView.Adapter<CommunityPlaceStaffAdapter.MyViewHolder> {


    private List<BaseBean> moviesList;
    private Context mContext;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sheet, semester, name, marks, status;
        public LinearLayout itemLinear;

        public MyViewHolder(View view) {
            super(view);
            sheet = (TextView) view.findViewById(R.id.sheet);
            semester = (TextView) view.findViewById(R.id.semester);
            name = (TextView) view.findViewById(R.id.name);
            marks = (TextView) view.findViewById(R.id.marks);
            status = (TextView) view.findViewById(R.id.status);
            itemLinear = (LinearLayout) view.findViewById(R.id.itemLinear);
        }
    }


    public CommunityPlaceStaffAdapter(List<BaseBean> moviesList, Context mContext) {
        this.moviesList = moviesList;
        this.mContext = mContext;
        sharedpreferences = mContext.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
    }

    public void notifyData(List<BaseBean> moviesList) {
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.observation_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final BaseBean baseBean = moviesList.get(position);
        holder.marks.setText(baseBean.marks);
        holder.name.setText(baseBean.name);
        holder.semester.setText(baseBean.semester);
        holder.sheet.setText(baseBean.sheet);
        holder.status.setText(baseBean.status);
        holder.itemLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedpreferences = mContext.getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sheetno", baseBean.sheet);
                editor.putString("stdName", baseBean.name);
                editor.putString("vrpidKey", baseBean.userId);

                editor.commit();
                Intent intent = new Intent(mContext, CommunityPlacementActivityStaff.class);
                intent.putExtra("data", baseBean.data);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
