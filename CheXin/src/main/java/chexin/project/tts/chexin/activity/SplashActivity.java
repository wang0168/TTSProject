package chexin.project.tts.chexin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.MainActivity;
import chexin.project.tts.chexin.R;


/**
 * 启动页
 * Created by chen on 2016/3/15.
 */
public class SplashActivity extends BaseActivity implements Animation.AnimationListener {
    private Animation alphaAnimation;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        imageView = (ImageView) findViewById(R.id.welcome);
        init();
    }

    private void init() {
        alphaAnimation = AnimationUtils.loadAnimation(this,
                R.anim.welcome_alpha);
        alphaAnimation.setFillEnabled(true);
        alphaAnimation.setFillAfter(true);
        imageView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(this);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
            startActivity(new Intent(this, MainActivity.class));
//        startService(new Intent(this, LocationService.class));
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
