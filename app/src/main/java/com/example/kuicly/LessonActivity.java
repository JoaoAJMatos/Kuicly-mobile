package com.example.kuicly;

import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

public class LessonActivity extends AppCompatActivity {

    // initiate a video view
    private VideoView videoView;
    private RequestQueue requestQueue;
    private String videoUrl = "URL_DA_SUA_API_AQUI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

    }
}