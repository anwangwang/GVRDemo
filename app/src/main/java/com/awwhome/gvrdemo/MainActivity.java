package com.awwhome.gvrdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_image;
    private Button btn_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_image = (Button) findViewById(R.id.btn_image);
        btn_video = (Button) findViewById(R.id.btn_video);
        btn_image.setOnClickListener(this);
        btn_video.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_image:
                Intent intent1 = new Intent();
                intent1.setClass(this, VRImageActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_video:
                Intent intent2 = new Intent();
                intent2.setClass(this, VRVideoActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }

    }
}
