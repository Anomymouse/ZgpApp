package com.zgp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.zgp.zgpapp.R;

/**
 * Created by 61720 on 2016/4/13.
 */
public class Acitvity_Splash extends Activity {

    private View view;
    private AlphaAnimation start_anima;
    private boolean isShowWelcome;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);

        initData();
    }

    private void initData() {
        start_anima = new AlphaAnimation(0.3f, 1.0f);
        start_anima.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                animateEnd();
            }
        });

//        start_anima.setDuration(2000);
//        view.startAnimation(start_anima);

        startTime = System.currentTimeMillis();

        if (System.currentTimeMillis() - startTime <= 2000) {
            start_anima.setDuration(2000 - (System.currentTimeMillis() - startTime));
            view.startAnimation(start_anima);
        } else {
            animateEnd();
        }
    }

    private void animateEnd() {
        Intent intent;

        if (isShowWelcome) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            intent = new Intent(this, Activity_Login.class);
            startActivity(intent);
            finish();
        }
    }

}
