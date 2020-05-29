package com.example.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener {

    private Recipe mRecipe;
    private int mStepIndex;

    private Button previousButton;
    private Button nextButton;
    private Button homeScreenButton;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private TextView descriptionTextView;

    private final String PLAYER_CURRENT_POS_KEY = "";
    private final String PLAYER_IS_READY_KEY = "";

    //Mandatory blank constructor
    public StepDetailFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);

        descriptionTextView = rootView.findViewById(R.id.full_decription);
        mPlayerView = rootView.findViewById(R.id.video);

        if(savedInstanceState != null){
            mRecipe = (Recipe) savedInstanceState.getSerializable(DetailActivity.RECIPE);
            resumePlaybackFromStateBundle(savedInstanceState);
        }

        if(mRecipe.getSteps().get(mStepIndex).getVideoUrl().equals("")){
            mPlayerView.setVisibility(View.GONE);
        }

        previousButton = rootView.findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mRecipe.getSteps().get(mStepIndex).getVideoUrl().equals("")){
                    releasePlayer();
                }
                mStepIndex -= 1;
                if(!mRecipe.getSteps().get(mStepIndex).getVideoUrl().equals("")){
                    mPlayerView.setVisibility(View.VISIBLE);
                } else{
                    mPlayerView.setVisibility(View.GONE);
                }
                displayStep(mStepIndex);
            }
        });
        nextButton = rootView.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mRecipe.getSteps().get(mStepIndex).getVideoUrl().equals("")){
                    releasePlayer();
                }
                mStepIndex += 1;
                if(!mRecipe.getSteps().get(mStepIndex).getVideoUrl().equals("")){
                    mPlayerView.setVisibility(View.VISIBLE);
                }else{
                    mPlayerView.setVisibility(View.GONE);
                }
                displayStep(mStepIndex);
            }
        });
        homeScreenButton = rootView.findViewById(R.id.home_screen_button);
        homeScreenButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                returnToMainActivity();
            }
        });
        displayStep(mStepIndex);

        return rootView;
    }
    public void setStepIndex(int stepIndex){
        mStepIndex = stepIndex;
    }
    public void setRecipe(Recipe recipe){
        mRecipe = recipe;
    }
    public void displayStep(int index){
        if(index == 0){
            previousButton.setVisibility(View.GONE);
        } else if(index == mRecipe.getSteps().size()-1){
            nextButton.setVisibility(View.GONE);
        } else {
            nextButton.setVisibility(View.VISIBLE);
            previousButton.setVisibility(View.VISIBLE);
        }
        descriptionTextView.setText(mRecipe.getSteps().get(mStepIndex).getFullDescription());
        if(!mRecipe.getSteps().get(mStepIndex).getVideoUrl().equals("")){
            initializePlayer(Uri.parse(mRecipe.getSteps().get(mStepIndex).getVideoUrl()));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!mRecipe.getSteps().get(mStepIndex).getVideoUrl().equals("")){
            initializePlayer(Uri.parse(mRecipe.getSteps().get(mStepIndex).getVideoUrl()));
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(!mRecipe.getSteps().get(mStepIndex).getVideoUrl().equals("")){
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putSerializable(DetailActivity.RECIPE, mRecipe);
        if(mExoPlayer == null){
            initializePlayer(Uri.parse(mRecipe.getSteps().get(mStepIndex).getVideoUrl()));
        }
        currentState.putLong(PLAYER_CURRENT_POS_KEY, Math.max(0, mExoPlayer.getCurrentPosition()));
        currentState.putBoolean(PLAYER_IS_READY_KEY, mExoPlayer.getPlayWhenReady());
    }

    private void resumePlaybackFromStateBundle(@Nullable Bundle inState) {
        if (inState != null) {
            if(mExoPlayer == null){
                initializePlayer(Uri.parse(mRecipe.getSteps().get(mStepIndex).getVideoUrl()));
            }
            mExoPlayer.setPlayWhenReady(inState.getBoolean(PLAYER_IS_READY_KEY));
            mExoPlayer.seekTo(inState.getLong(PLAYER_CURRENT_POS_KEY));
        }
    }

    private void returnToMainActivity(){
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        startActivity(intent);
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

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
