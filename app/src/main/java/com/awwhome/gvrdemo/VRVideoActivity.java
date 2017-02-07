package com.awwhome.gvrdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;

public class VRVideoActivity extends AppCompatActivity {

    private static final String TAG = "VRVideoActivity";

    private VrVideoView vr_vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr_video);

        // 全景视频的浏览功能
        // 1.将所需要的库导入到项目中 common commonwidget videowidget
        // 2.准备测试视频素材 assets/congo.mp4
        // 3.开启内存设置 android:largeHeap="true" 尽可能使应用使用最大内存
        // 4.将全景图片加载到内存当中，再显示在控件上

        vr_vv = (VrVideoView) findViewById(R.id.vr_vv);

        // 加载视频到内存，耗时操作，需要在异步中进行
        LoadVideoTask loadVideoTask = new LoadVideoTask();
        loadVideoTask.execute("congo.mp4");

    }

    private class LoadVideoTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            VrVideoView.Options options = new VrVideoView.Options();
            // 立体的视频资源，上半画面显示在左眼，下半画面显示在右眼
            options.inputType = VrVideoView.Options.TYPE_STEREO_OVER_UNDER;
            // 视频资源的显示方式 FORMAT_DEFAULT表示是本地存储的资源，如sd卡、assets
            // FORMAT_HLS表示资源来自流媒体，如在线直播等等
            options.inputFormat = VrVideoView.Options.FORMAT_DEFAULT;
            try {
                // 这里提示loadVideoFromAsset方法必须在UI线程中执行，而此方法是耗时操作，所以此处错误不用理会
                vr_vv.loadVideoFromAsset(params[0], options);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
