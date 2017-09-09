package com.bakingapp.src;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bakingapp.R;
import com.bakingapp.src.model.Recipe;
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

public class RecipeStepDetailsFragment extends Fragment implements ExoPlayer.EventListener {

    private final String RECIPE_STEP = "Recipe_Step";

    private static final String TAG = RecipeStepDetailsFragment.class.getSimpleName();

    private View mRootView;

    private Recipe recipe;
    private int recipeStep;

    private TextView mDescriptionTextView;
    private Button mNextButton;
    private Button mPreviousButton;

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    public RecipeStepDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(RECIPE_STEP, recipeStep);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            recipe = bundle.getParcelable(RecipeStepsFragment.BUNDLE_EXTRA_RECIPE);
            recipeStep = bundle.getInt(RecipeStepsFragment.BUNDLE_EXTRA_RECIPE_STEP_NUMBER);
        }

        if (savedInstanceState != null) {
            recipeStep = savedInstanceState.getInt(RECIPE_STEP);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_step_details, container, false);

        initSimpleExoPlayerView();

        // If its landscape Layout it will not contain the container_constraint_layout
        if (mRootView.findViewById(R.id.container_constraint_layout) == null) {
            return mRootView;
        } else if (getResources().getBoolean(R.bool.isTablet)) {
            mDescriptionTextView = (TextView) mRootView.findViewById(R.id.description_textView);
            mDescriptionTextView.setText(recipe.getSteps().get(recipeStep).getDescription());
            return mRootView;
        }

        mDescriptionTextView = (TextView) mRootView.findViewById(R.id.description_textView);
        mDescriptionTextView.setText(recipe.getSteps().get(recipeStep).getDescription());

        mPreviousButton = (Button) mRootView.findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(mPreviousButtonClickListener);

        mNextButton = (Button) mRootView.findViewById(R.id.next_button);
        mNextButton.setOnClickListener(mNextButtonClickListener);

        return mRootView;
    }

    private View.OnClickListener mNextButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (recipeStep < recipe.getSteps().size() - 1) {
                ++recipeStep;
                mDescriptionTextView.setText(recipe.getSteps().get(recipeStep).getDescription());
                initializePlayer(Uri.parse(recipe.getSteps().get(recipeStep).getVideoURL()));
            }
        }
    };

    private View.OnClickListener mPreviousButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (recipeStep > 0) {
                --recipeStep;
                mDescriptionTextView.setText(recipe.getSteps().get(recipeStep).getDescription());
                initializePlayer(Uri.parse(recipe.getSteps().get(recipeStep).getVideoURL()));
            }
        }
    };

    private void initSimpleExoPlayerView() {
        // Initialize the player view.
        mPlayerView = (SimpleExoPlayerView) mRootView.findViewById(R.id.playerView);
        // Load the question mark as the background image until the user answers the question.
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                        (getResources(), android.R.drawable.presence_video_online));
        // Initialize the Media Session.
        initializeMediaSession();

        // Initialize the player.
        initializePlayer(Uri.parse(recipe.getSteps().get(recipeStep).getVideoURL()));
    }

    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {

        Log.e(TAG, "Step Number: " + recipeStep);
        Log.e(TAG, "Video URL: " + recipe.getSteps().get(recipeStep).getVideoURL());

        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "Recipe Step Details");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        } else {
            mExoPlayer.stop();
            String userAgent = Util.getUserAgent(getContext(), "Recipe Step Details");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
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
                                                        PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());
        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());
        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mMediaSession.setActive(false);
    }

    // -----------------------------------Exo Player Event Listener Impl---------------------------------------------

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

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
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
    }
}
