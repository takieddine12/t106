package com.example.onboardingscreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.onboardingscreen.Extras;
import com.example.onboardingscreen.NextActivity;
import com.example.onboardingscreen.R;

public class BoardingFragment extends Fragment {

    private View view;

    private TextView title;
    private VideoView videoView;

    private final String pageTitle;
    private final int videoPath;
    private final int position;

    int currentPosition = 0;

    public BoardingFragment(String pageTitle, int videoPath, int position) {
        this.pageTitle = pageTitle;
        this.videoPath = videoPath;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one,container,false);

        title = view.findViewById(R.id.title);
        videoView = view.findViewById(R.id.videoView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        title.setText(pageTitle);

        String path = "android.resource://" + requireContext().getPackageName() + "/" + R.raw.vid;
        videoView.setVideoURI(Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + videoPath));
        videoView.start();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.seekTo(currentPosition);
            videoView.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoView.isPlaying()) {
            videoView.pause();
            currentPosition = videoView.getCurrentPosition();
        }
    }

    @Override
    public void onDestroy() {
        if(videoView != null){
            videoView.stopPlayback();
            videoView = null;
        }
        super.onDestroy();
    }
}
