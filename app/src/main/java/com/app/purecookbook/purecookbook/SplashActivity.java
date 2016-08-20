package com.app.purecookbook.purecookbook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.splash_activity)
public class SplashActivity extends BaseActivity {
    private static final int MSG_START_ANIM = 1;
    private static final int MSG_GOTO_MAIN  = 2;

    @ViewInject(R.id.welcome_image)
    ImageView welcomeImg;

    private AlphaAnimation anim;

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case MSG_START_ANIM:
                    welcomeImg.startAnimation(anim);
                    welcomeImg.setVisibility(View.VISIBLE);
                    break;
                case  MSG_GOTO_MAIN:
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    overridePendingTransition(R.anim.new_to_left,R.anim.old_to_left);
                    finish();
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBefore() {

    }

    @Override
    public void init() {

        anim=new AlphaAnimation(0,1);
        anim.setDuration(2000);
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.sendEmptyMessageDelayed(MSG_GOTO_MAIN, 1000);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    public void initAfter() {
        handler.sendEmptyMessageDelayed(MSG_START_ANIM, 1000);
    }
}
