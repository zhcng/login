package com.zhcng.login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnLayoutChangeListener {

    private LinearLayout loginCard;
    private ImageView icon;
    private TextView iconName;
    private Button btnLogin;

    //最外层的Layout视图
    private LinearLayout rootView;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        loginCard = (LinearLayout) findViewById(R.id.login_card);
        icon = (ImageView) findViewById(R.id.icon);
        iconName = (TextView) findViewById(R.id.icon_name);
        btnLogin = (Button) findViewById(R.id.btn_login);
        initAnimation();
        rootView = (LinearLayout) findViewById(R.id.root_view);
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //添加layout大小发生改变监听器
        rootView.addOnLayoutChangeListener(this);
    }

    private void initAnimation() {
        ObjectAnimator
                .ofFloat(loginCard, "translationY", 0, -BaseUtils.dipTopx(this, 360))
                .setDuration(1000)
                .start();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            ObjectAnimator
                    .ofFloat(loginCard, "translationY", 0, -BaseUtils.dipTopx(this, 240))
                    .setDuration(500)
                    .start();

            ObjectAnimator iconMove = ObjectAnimator.ofFloat(icon, "translationY", 0, -BaseUtils.dipTopx(this, 80));
            ObjectAnimator iconScaleX = ObjectAnimator.ofFloat(icon, "scaleX", 1f, 0.5f);
            ObjectAnimator iconScaleY = ObjectAnimator.ofFloat(icon, "scaleY", 1f, 0.5f);
            AnimatorSet iconAnimSet = new AnimatorSet();
            iconAnimSet.play(iconMove).with(iconScaleX).with(iconScaleY);
            iconAnimSet.setDuration(500).start();

            ObjectAnimator tvMove = ObjectAnimator.ofFloat(iconName, "translationY", 0, -BaseUtils.dipTopx(this, 80));
            ObjectAnimator tvScaleX = ObjectAnimator.ofFloat(iconName, "scaleX", 1f, 0f);
            ObjectAnimator tvScaleY = ObjectAnimator.ofFloat(iconName, "scaleY", 1f, 0f);
            ObjectAnimator tvAlpha = ObjectAnimator.ofFloat(iconName, "alpha", 1f, 0f);
            AnimatorSet tvAnimSet = new AnimatorSet();
            tvAnimSet.play(tvMove).with(tvAlpha).with(tvScaleX).with(tvScaleY);
            tvAnimSet.setDuration(500).start();

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            ObjectAnimator
                    .ofFloat(loginCard, "translationY", 0, -BaseUtils.dipTopx(this, 360))
                    .setDuration(500)
                    .start();

            ObjectAnimator iconMove = ObjectAnimator.ofFloat(icon, "translationY", -BaseUtils.dipTopx(this, 80), 0);
            ObjectAnimator iconScaleX = ObjectAnimator.ofFloat(icon, "scaleX", 0.5f, 1f);
            ObjectAnimator iconScaleY = ObjectAnimator.ofFloat(icon, "scaleY", 0.5f, 1f);
            AnimatorSet iconAnimSet = new AnimatorSet();
            iconAnimSet.play(iconMove).with(iconScaleX).with(iconScaleY);
            iconAnimSet.setDuration(500).start();

            ObjectAnimator tvMove = ObjectAnimator.ofFloat(iconName, "translationY", -BaseUtils.dipTopx(this, 80), 0);
            ObjectAnimator tvScaleX = ObjectAnimator.ofFloat(iconName, "scaleX", 0f, 1f);
            ObjectAnimator tvScaleY = ObjectAnimator.ofFloat(iconName, "scaleY", 0f, 1f);
            ObjectAnimator tvAlpha = ObjectAnimator.ofFloat(iconName, "alpha", 0f, 1f);
            AnimatorSet tvAnimSet = new AnimatorSet();
            tvAnimSet.play(tvMove).with(tvAlpha).with(tvScaleX).with(tvScaleY);
            tvAnimSet.setDuration(500).start();
        }
    }
}
