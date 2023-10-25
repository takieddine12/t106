package com.example.onboardingscreen;

import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class BoardingFragment extends Fragment {

    private TextView title;
    private final String pageTitle;
    private final int videoPath;
    private final int position;
    private CardView cardView;

    private CustomVideoView customVideoView;

    private int width,height;
    private int mWidth,mHeight;

    int currentPosition = 0;

    public BoardingFragment(String pageTitle, int videoPath, int position) {
        this.pageTitle = pageTitle;
        this.videoPath = videoPath;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        title = view.findViewById(R.id.title);
        cardView = view.findViewById(R.id.container);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title.setText(pageTitle);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        // Set Width / height based on device screen width
        if (screenWidth >= 1080) {
            // Large screen (e.g., large phones)
            mWidth = 650;
            mHeight = 800;
            width  = 800;
            height = 800;
        } else if (screenWidth >= 720) {
            // Normal screen (e.g., most phones)
            mWidth = 550;
            mHeight = 600;
            width  = 700;
            height = 700;
        } else {
            // Small screen (e.g., small phones)
            mWidth = 450;
            mHeight = 500;
            width  = 500;
            height = 500;
        }

        // TODO : RESIZE VIDEO TO 400 ( Width ) * 500 ( Height )  &&  500 ( Width ) * 500 ( Height )
        if(position >= 1){
            customVideoView  = new CustomVideoView(requireContext(),mWidth,mHeight);
            cardView.addView(customVideoView);
        } else {
            customVideoView  = new CustomVideoView(requireContext(),width,height);
            cardView.addView(customVideoView);
            Log.d("TAG","Position 1 called");
        }


        // TODO : Set Video Path and start
        String path = "android.resource://" + requireContext().getPackageName() + "/" + R.raw.vid;
        customVideoView.setVideoURI(Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + videoPath));
        customVideoView.start();


    }

 

    // TODO : Release VideoView if it is playing to not cause memory leak onDestroy
    @Override
    public void onResume() {
        super.onResume();
        if (customVideoView != null) {
            customVideoView.seekTo(currentPosition);
            customVideoView.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (customVideoView.isPlaying()) {
            customVideoView.pause();
            currentPosition = customVideoView.getCurrentPosition();
        }
    }

    @Override
    public void onDestroy() {
        if(customVideoView != null){
            customVideoView.stopPlayback();
            customVideoView = null;
        }
        super.onDestroy();
    }
}
