package smart.social.worker.allstudent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.lang.ref.WeakReference;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import smart.social.worker.R;
import smart.social.worker.Student;
import smart.social.worker.app.GlideApp;

public class HeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<Student> mData;
    private Context context;
    private LayoutInflater mLayoutInflater;

    private boolean mIsSpaceVisible = true;

    public interface ItemClickListener {
        void onItemClicked(int position);
    }

    private WeakReference<ItemClickListener> mCallbackRef;

    public HeaderAdapter(Context ctx, List<Student> data, ItemClickListener listener) {
        context = ctx;
        mLayoutInflater = LayoutInflater.from(ctx);
        mData = data;
        mCallbackRef = new WeakReference<>(listener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            //inflate your layout and pass it to view holder
            View v = mLayoutInflater.inflate(R.layout.simple_list_item_studentall, parent, false);
            return new MyItem(v);
        } else if (viewType == TYPE_HEADER) {
            View v = mLayoutInflater.inflate(R.layout.transparent_header_view, parent, false);
            return new HeaderItem(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyItem) {
            Student allBreed = getItem(position);
            ((MyItem) holder).mTitleView.setText(allBreed.getStudentname());
            ((MyItem) holder).subtittle.setText(allBreed.getDegreeName());
            ((MyItem) holder).farmername.setText(allBreed.getAadharnumber());
            GlideApp.with(context).load(allBreed.getImage())
                    .centerCrop()
                    .dontAnimate()
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.profilemember)
                    .into(((MyItem) holder).farmericon);

            GlideApp.with(context).load(allBreed.getImage())
                    .centerCrop()
                    .dontAnimate()
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.profilemember)
                    .into(((MyItem) holder).breedimg);
            ((MyItem) holder).mPosition = position;
        } else if (holder instanceof HeaderItem) {
            ((HeaderItem) holder).mSpaceView.setVisibility(mIsSpaceVisible ? View.VISIBLE : View.GONE);
            ((HeaderItem) holder).mPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private Student getItem(int position) {
        return mData.get(position - 1);
    }

    class MyItem extends HeaderItem {
        TextView mTitleView;
        private final ImageView locationbtn;
        private final TextView farmername;
        public TextView subtittle;
        CircleImageView farmericon;
        ImageView breedimg;
        RatingBar ratingBar;

        public MyItem(View itemView) {
            super(itemView);
            mTitleView = (TextView) itemView.findViewById(R.id.text1);
            subtittle = (TextView) itemView.findViewById(R.id.subtittle);
            breedimg = (ImageView) itemView.findViewById(R.id.breedimg);
            locationbtn = (ImageView) itemView.findViewById(R.id.locationbtn);
            farmericon = (CircleImageView) itemView.findViewById(R.id.farmericon);
            farmername = (TextView) itemView.findViewById(R.id.title1);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }
    }

    class HeaderItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        View mSpaceView;
        int mPosition;

        public HeaderItem(View itemView) {
            super(itemView);
            mSpaceView = itemView.findViewById(R.id.space);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ItemClickListener callback = mCallbackRef != null ? mCallbackRef.get() : null;
            if (callback != null) {
                callback.onItemClicked(mPosition);
            }

        }
    }

    public void hideSpace() {
        mIsSpaceVisible = false;
        notifyItemChanged(0);
    }

    public void showSpace() {
        mIsSpaceVisible = true;
        notifyItemChanged(0);
    }

    public void notifyData(List<Student> moviesList) {
        this.mData = moviesList;
        notifyDataSetChanged();
    }
}