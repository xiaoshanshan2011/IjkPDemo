package com.shan.shanijkplayerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;

import com.shan.ijkplayer_android.widget.media.AndroidMediaController;
import com.shan.ijkplayer_android.widget.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity {

    private IjkVideoView mVideoView;
    private boolean mBackPressed = false;

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
        getWindow().setFlags(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);  //设置全屏*/


        setContentView(R.layout.activity_main);
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        AndroidMediaController mMediaController;
        //这里使用的是Demo中提供的AndroidMediaController类控制播放相关操作
        mMediaController = new AndroidMediaController(this, false);
        //mMediaController.setSupportActionBar(actionBar);
        mVideoView = (IjkVideoView) findViewById(R.id.video_view);
        TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
        mVideoView.setMediaController(mMediaController);
        //mVideoView.setHudView(tableLayout);

        //mVideoView.setVideoPath("http://main.gslb.ku6.com/broadcast/sub?channel=910");
        //mVideoView.setVideoPath("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4");
        mVideoView.setVideoPath("http://192.168.20.172:8080/examples/app/zuoban.mp4");

        mVideoView.start();
    }

    @Override
    public void onBackPressed() {
        mBackPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mVideoView.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mVideoView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //点击返回或不允许后台播放时 释放资源
        if (mBackPressed || !mVideoView.isBackgroundPlayEnabled()) {
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();
        } else {
            mVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }
}
