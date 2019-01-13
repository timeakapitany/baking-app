package com.timeakapitany.bakingapp.detail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.timeakapitany.bakingapp.R;
import com.timeakapitany.bakingapp.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionStepFragment extends Fragment {

    private static final String CURRENT_STEP = "current.step";
    private static final String POSITION = "position";
    private static final String WINDOW = "window";
    private static final String AUTO_PLAY = "auto.play";
    @BindView(R.id.playerView)
    PlayerView playerView;
    @Nullable
    @BindView(R.id.step_description)
    TextView descriptionTextView;
    @Nullable
    @BindView(R.id.back_button)
    Button backButton;
    @Nullable
    @BindView(R.id.next_button)
    Button nextButton;
    private ExoPlayer exoPlayer;
    private int startWindow;
    private long startPosition;
    private boolean startAutoPlay;
    private Step step;
    private StepNavigationListener listener;

    public static InstructionStepFragment newInstance(@NonNull Step step) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CURRENT_STEP, step);
        InstructionStepFragment instructionStepFragment = new InstructionStepFragment();
        instructionStepFragment.setArguments(bundle);
        return instructionStepFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            startPosition = savedInstanceState.getLong(POSITION);
            startWindow = savedInstanceState.getInt(WINDOW);
            startAutoPlay = savedInstanceState.getBoolean(AUTO_PLAY);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        step = bundle.getParcelable(CURRENT_STEP);
        if (!step.getVideoUrl().isEmpty() && !getResources().getBoolean(R.bool.action_bar_visibility))
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        try {
            listener = ((StepNavigationListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement StepNavigationListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);
        if (descriptionTextView != null) {
            descriptionTextView.setText(step.getDescription());
        }

        if (nextButton != null) {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNextClick(v);
                }
            });
        }

        if (backButton != null) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onBackClick(v);
                }
            });
        }


        return rootView;
    }

    private void initializePlayer(Uri videoUri) {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext());

            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(videoUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

            exoPlayer.setPlayWhenReady(startAutoPlay);

            boolean haveStartPosition = startWindow != C.INDEX_UNSET;
            if (haveStartPosition) {
                exoPlayer.seekTo(startWindow, startPosition);
            }
            exoPlayer.prepare(mediaSource, !haveStartPosition, false);
            playerView.setPlayer(exoPlayer);

        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            updatePosition();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;

        }

    }

    private void updatePosition() {
        if (exoPlayer != null) {
            startAutoPlay = exoPlayer.getPlayWhenReady();
            startWindow = exoPlayer.getCurrentWindowIndex();
            startPosition = Math.max(0, exoPlayer.getContentPosition());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            releasePlayer();
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!step.getVideoUrl().isEmpty()) {
            initializePlayer(Uri.parse(step.getVideoUrl()));
        } else {
            playerView.setVisibility(View.GONE);
            descriptionTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        updatePosition();
        outState.putLong(POSITION, startPosition);
        outState.putInt(WINDOW, startWindow);
        outState.putBoolean(AUTO_PLAY, startAutoPlay);
        super.onSaveInstanceState(outState);
    }

    public interface StepNavigationListener {

        void onNextClick(View v);

        void onBackClick(View v);
    }
}
