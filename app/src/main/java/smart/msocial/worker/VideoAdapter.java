package smart.msocial.worker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prof.youtubeparser.models.videos.Video;

import java.util.List;

/**
 * Created by sab99r
 */
public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_MOVIE = 0;
    public final int TYPE_LOAD = 1;

    static Context context;
    List<Video> movies;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    /*
    * isLoading - to set the remote loading and complete status to fix back to back load more call
    * isMoreDataAvailable - to set whether more data from server available or not.
    * It will prevent useless load more request even after all the server data loaded
    * */


    public VideoAdapter(Context context, List<Video> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType==TYPE_MOVIE){
            return new MovieHolder(inflater.inflate(R.layout.row_video,parent,false));
        }else{
            return new LoadHolder(inflater.inflate(R.layout.row_load,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if(getItemViewType(position)==TYPE_MOVIE){
            ((MovieHolder)holder).bindData(movies.get(position));
        }
        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {
        if(movies.get(position).getTitle().equals("load")){
            return TYPE_LOAD;
        }else{
            return TYPE_MOVIE;
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    /* VIEW HOLDERS */

    static class MovieHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        TextView tvTitle;
        TextView tvRating;
        public MovieHolder(View itemView) {
            super(itemView);
            tvTitle=(TextView)itemView.findViewById(R.id.title);
            tvRating=(TextView)itemView.findViewById(R.id.rating);
            image = (ImageView)itemView.findViewById(R.id.imageYoutube);
        }

        void bindData(Video movieModel){
            tvTitle.setText(movieModel.getTitle());
            tvRating.setText(movieModel.getDate());
//            Picasso.with(context)
//                    .load(movieModel.getCoverLink())
//                    .placeholder(R.drawable.progress)
//                    .fit().centerCrop()
//                    .into(image);

            Glide.with(context)
                    .load(movieModel.getCoverLink())
                    .into(image);
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder{
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }


    interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
