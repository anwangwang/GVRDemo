package com.awwhome.gvrdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;

public class VRImageActivity extends AppCompatActivity {

    private static final String TAG = "VRImageActivity";

    private VrPanoramaView vrpv_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr_image);

        // 全景图片的浏览功能
        // 1.将所需要的库导入到工作控件 common commonwidget panowidget
        // 2.依赖到我们的项目中
        // 3.准备测试素材 放入到项目下的assets/andes.jpg
        // 4.开启内存设置 android:largeHeap="true" 尽可能使应用使用最大内存
        // 5.将全景图片加载到内存当中，再显示在控件上
        vrpv_view = (VrPanoramaView) findViewById(R.id.vrpv_view);
        ImageLoadTask imageLoadTask = new ImageLoadTask();
        imageLoadTask.execute();
    }

    // 第一步，加载图片，耗时操作，放置在子线程
    private class ImageLoadTask extends AsyncTask<Void,Void,Bitmap> {

        // doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected Bitmap doInBackground(Void... params) {
            InputStream inputStream = null;
            try {
                // 1.1 获取资源文件
                inputStream = getAssets().open("andes.jpg");
                // 1.2 将资源文件转化为位图
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (IOException e) {
                Log.e(TAG, "文件未找到" + e);
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        // 后台方法结束之后调用
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null){
                VrPanoramaView.Options options = new VrPanoramaView.Options();
                // 全景图，上半张显示在左眼，下半张显示在右眼
                options.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
                vrpv_view.setEventListener(new VrPanoramaEventListener(){

                    @Override
                    public void onLoadSuccess() {
                        super.onLoadSuccess();
                        Toast.makeText(VRImageActivity.this,"加载成功",Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"加载成功");
                    }

                    @Override
                    public void onLoadError(String errorMessage) {
                        super.onLoadError(errorMessage);
                        Toast.makeText(VRImageActivity.this,"加载失败"+errorMessage,Toast.LENGTH_LONG).show();
                        Log.d(TAG,"加载失败"+errorMessage);
                    }
                });
                vrpv_view.loadImageFromBitmap(bitmap, options);
            }
        }
    }


}
