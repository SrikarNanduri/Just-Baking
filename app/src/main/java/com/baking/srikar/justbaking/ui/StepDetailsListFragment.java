package com.baking.srikar.justbaking.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baking.srikar.justbaking.Models.Step;
import com.baking.srikar.justbaking.R;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepDetailsListFragment extends Fragment implements ExoPlayer.EventListener{

    private static final String TAG = StepDetailsListFragment.class.getSimpleName();

    int positionValue = 1;
    int previousPositionValue = 1;

    @BindView(R.id.stepDetail_tv)
    TextView stepsTv;

    @BindView(R.id.simpleExoPlayerView)
     SimpleExoPlayerView simpleExoPlayerView;

    @BindView(R.id.nextButton)
    Button nextButton;

    @BindView(R.id.previousButton)
    Button previousButton;

    private SimpleExoPlayer mExoPlayer;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;


    public StepDetailsListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.steps_details_fragment_body, container, false);
        ButterKnife.bind(this, rootView);
        Gson gson = new Gson();
       final String  stepList = getArguments().getString("Steps");
       final List<Step> steps = gson.fromJson(stepList, new TypeToken<List<Step>>(){}.getType());
        final int position = getArguments().getInt("stepposition");
        Log.v("steps", steps.toString());
       // Log.v("position", String.valueOf(position));
        Configuration newConfig = getResources().getConfiguration();
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(getContext(), "Landscape", Toast.LENGTH_LONG).show();
        } else {
            if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
                stepsTv.setText(steps.get(position).getDescription());
                exoPlayer(steps.get(position));

                if(position == 0) {
                    previousButton.setEnabled(false);
                } else {
                    previousButton.setEnabled(true);
                }

                if(position == steps.size() -1){
                    nextButton.setEnabled(false);
                } else {
                    nextButton.setEnabled(true);
                }

                    nextButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            releasePlayer();
                            exoPlayer(steps.get(position + positionValue));
                            Log.v("previous Data", String.valueOf((steps.get((position+positionValue) - 1))));
                            if(steps.size() - 1 > position + positionValue) {
                                previousButton.setEnabled(true);
                                stepsTv.setText(steps.get(position + positionValue).getDescription());
                                positionValue++;

                        } else {
                                stepsTv.setText(steps.get(steps.size() - 1).getDescription());
                                nextButton.setEnabled(false);
                        }
                        }
                    });

                    previousButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                           if (position > 0) {
                               /* exoPlayer(steps.get(position - previousPositionValue));
                                previousPositionValue++;*/
                               previousButton.setEnabled(true);
                            }
                            releasePlayer();
                           if(position + positionValue >= 0)
                            exoPlayer(steps.get((position+positionValue) - 1));

                           stepsTv.setText(steps.get((position + positionValue) - 1).getDescription());
                            positionValue--;
                        }
                    });
            }
        }


        return rootView;
    }

    public void exoPlayer(Step steps) {
        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        String videoURL = steps.getVideoURL();
        Log.v("position", String.valueOf(steps.getId()));
        initializeMediaSession();
        initializePlayer(Uri.parse(videoURL));
    }
    private void initializePlayer(Uri mediaUri) {

        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        //mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mMediaSession.setActive(false);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

   /* private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }

    }*/
}
