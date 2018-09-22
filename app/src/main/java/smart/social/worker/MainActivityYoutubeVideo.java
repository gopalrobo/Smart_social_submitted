package smart.social.worker;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.prof.youtubeparser.Parser;
import com.prof.youtubeparser.models.videos.Video;

import java.util.ArrayList;
import java.util.List;

import smart.social.worker.off.MyDividerItemDecoration;

import static smart.social.worker.DividerItemDecoration.VERTICAL_LIST;


public class MainActivityYoutubeVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    RecyclerView recyclerView;
    List<Video> movies;
    VideoAdapter adapter;
    String TAG = "MainActivityYoutubeVideo - ";
    Context context;
    private ProgressBar progressBar;

    //TODO: delete
    private final String API_KEY = "AIzaSyBufZZ6hR1jYcnwqXg2egbpvCU8Vbd2UU0";
    private String nextToken;
    private int totalElement;
    private YouTubePlayerView youTubePlayerView;
    YouTubePlayer youTubePlayer;
    private EditText edit_query;
    private Button submit;
    String videoString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_video);
        context = this;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                videoString = null;
            } else {
                videoString = extras.getString("VIDEO");
            }
        } else {
            videoString = (String) savedInstanceState.getSerializable("VIDEO");
        }


        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);

        edit_query = (EditText) findViewById(R.id.edit_query);
        submit = (Button) findViewById(R.id.submit);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        movies = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar);
        adapter = new VideoAdapter(MainActivityYoutubeVideo.this, movies);
        adapter.setLoadMoreListener(new VideoAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("xxxxxxxxxx",videoString.replace(" ", "+"));
                        loadMore(videoString.replace(" ", "+"));

                    }
                });
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getApplicationContext(), VERTICAL_LIST, 2));
        recyclerView.setAdapter(adapter);

        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, this.recyclerView, new RecyclerTouchListener.ClickListener() {
            public void onClick(View paramView, int position) {
                if (youTubePlayer != null) {
                    youTubePlayer.cueVideo(movies.get(position).getVideoId());
                }
            }

            public void onLongClick(View paramView, int paramInt) {
            }
        }));
        Log.e("xxxxxxxxxx",videoString.replace(" ", "+"));
        loadVideo(videoString.replace(" ", "+"), true);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (edit_query.getText().toString().length() > 0) {
//                    movies=new ArrayList<>();
//                    loadVideo(edit_query.getText().toString().trim().replaceAll(" ", "+"),false);
//                } else {
//                    edit_query.setError("Enter something!!");
//                }
            }
        });
    }

    private void loadMore(String query) {

        //add loading progress view
        movies.add(new Video("load", "", "", ""));
        adapter.notifyItemInserted(movies.size() - 1);

        Parser parser = new Parser();
        if (nextToken != null) {
            String url = parser.generateMoreDataRequest(query, 20, Parser.ORDER_DATE, API_KEY, nextToken);
            parser.execute(url);
            parser.onFinish(new Parser.OnTaskCompleted() {
                @Override
                public void onTaskCompleted(ArrayList<Video> list, String nextPageToken) {


                    totalElement = adapter.getItemCount();
                    nextToken = nextPageToken;
                    //remove loading view
                    movies.remove(movies.size() - 1);
                    if (list.size() > 0) {
                        //add loaded data
                        movies.addAll(list);
                    } else {//result size 0 means there is no more data available at server
                        adapter.setMoreDataAvailable(false);
                        //telling adapter to stop calling load more as no more server data available
                        Toast.makeText(context, "No More Data Available", Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataChanged();
                    //should call the custom method adapter.notifyDataChanged here to get the correct loading status
                }

                @Override
                public void onError() {
                    Toast.makeText(MainActivityYoutubeVideo.this, "Error while loading data. Please retry", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(MainActivityYoutubeVideo.this, "Unable to load data. Please retry", Toast.LENGTH_SHORT).show();
        }
    }


//    public void loadVideo() {
//        progressBar.setVisibility(View.VISIBLE);
//        Parser parser = new Parser();
//        String url = parser.generateRequest(CHANNEL_ID, 20, Parser.ORDER_DATE, API_KEY);
//        Log.e("video url", url);
//        parser.execute(url);
//        parser.onFinish(new Parser.OnTaskCompleted() {
//            @Override
//            public void onTaskCompleted(ArrayList<Video> list, String nextPageToken) {
//                progressBar.setVisibility(View.GONE);
//                nextToken = nextPageToken;
//                movies.addAll(list);
//                totalElement = adapter.getItemCount();
//                adapter.notifyDataChanged();
//                youTubePlayerView.initialize(API_KEY, MainActivityYoutubeVideo.this);
//            }
//
//            @Override
//            public void onError() {
//                Toast.makeText(MainActivityYoutubeVideo.this, "Error while loading data. Please retry", Toast.LENGTH_LONG).show();
//                progressBar.setVisibility(View.GONE);
//            }
//        });
//    }

    public void loadVideo(String query, final boolean isFlag) {
        progressBar.setVisibility(View.VISIBLE);
        Parser parser = new Parser();
        String url = parser.generateRequest(query, 20, Parser.ORDER_DATE, API_KEY);
        Log.e("video url", url);
        parser.execute(url);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Video> list, String nextPageToken) {
                progressBar.setVisibility(View.GONE);
                nextToken = nextPageToken;
                movies.addAll(list);
                totalElement = adapter.getItemCount();
                adapter.notifyDataChanged();
                if (isFlag) {
                    youTubePlayerView.initialize(API_KEY, MainActivityYoutubeVideo.this);
                }
            }

            @Override
            public void onError() {
                Toast.makeText(MainActivityYoutubeVideo.this, "Error while loading data. Please retry", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer myouTubePlayer, boolean wasRestored) {
        //  Toast.makeText(this, "Success video load", Toast.LENGTH_LONG).show();
        youTubePlayer = myouTubePlayer;
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        if (!wasRestored) {
            if (movies.size() > 0) {
                youTubePlayer.cueVideo(movies.get(0).getVideoId());
            } else {
              //  youTubePlayer.cueVideo("octm222fXeU");
                Toast.makeText(getApplicationContext(),"No videos available",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {
            //      Toast.makeText(MainActivityYoutubeVideo.this, "Video is playing", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPaused() {
            //    Toast.makeText(MainActivityYoutubeVideo.this, "Video is paused", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStopped() {
            //     Toast.makeText(MainActivityYoutubeVideo.this, "Video is stopped", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };


    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {
            // Toast.makeText(MainActivityYoutubeVideo.this, "Video is loading", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onLoaded(String s) {
            youTubePlayer.play();
        }

        @Override
        public void onAdStarted() {
            // Toast.makeText(MainActivityYoutubeVideo.this, "Ad started", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onVideoStarted() {
            //   Toast.makeText(MainActivityYoutubeVideo.this, "Video started", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failed video load", Toast.LENGTH_LONG).show();
    }

}
