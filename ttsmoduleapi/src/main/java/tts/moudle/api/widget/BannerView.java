package tts.moudle.api.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tts.moudle.api.adapter.BannerAdapter;
import tts.moudle.ttsmoduleapi.R;

/**
 * Created by sjb on 2016/2/26.
 */
public class BannerView extends RelativeLayout {
    protected int mIdForViewPager;
    protected int mIdForIndicator;
    protected int mTimeInterval = 5;
    private final int imgChange = 1001;// 图片切换
    private int curItemCount = 0;

    private ScheduledExecutorService scheduledExecutorService;// 异步处理广告 多少秒切换一次
    private ViewPager mViewPager;
    private FlowIndicator flowIndicator;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private PagerAdapter bannerAdapter;

    public BannerView(Context context) {
        super(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.tts_banner, 0, 0);
        if (arr != null) {
            if (arr.hasValue(R.styleable.tts_banner_banner_pager)) {
                mIdForViewPager = arr.getResourceId(R.styleable.tts_banner_banner_pager, 0);
            }
            if (arr.hasValue(R.styleable.tts_banner_banner_indicator)) {
                mIdForIndicator = arr.getResourceId(R.styleable.tts_banner_banner_indicator, 0);
            }
            mTimeInterval = arr.getInt(R.styleable.tts_banner_banner_time_interval, mTimeInterval);
            arr.recycle();
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }


    @Override
    protected void onFinishInflate() {
        mViewPager = (ViewPager) findViewById(mIdForViewPager);
        flowIndicator = (FlowIndicator) findViewById(mIdForIndicator);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (flowIndicator != null) {
                    flowIndicator.setSeletion(position);
                }
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageSelected(position);
                }
                curItemCount = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
    }


    public void setAdapter(PagerAdapter adapter) {
        bannerAdapter = adapter;
        mViewPager.setAdapter(adapter);
        if (flowIndicator != null) {
            flowIndicator.setCount(adapter.getCount());
        }
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每7秒钟切换一次图片
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), mTimeInterval, mTimeInterval, TimeUnit.SECONDS);
    }

    /**
     * 执行图片切换任务
     */
    private class ScrollTask implements Runnable {
        @Override
        public void run() {
            curItemCount = (curItemCount + 1) % bannerAdapter.getCount();
            handler.sendEmptyMessage(imgChange);// 通过handler切换图片
        }
    }

    /**
     * handler实现图片自动滑动
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case imgChange:
                    mViewPager.setCurrentItem(curItemCount);// 切换当前显示的图片
                    break;
            }

        }

    };

    /**
     * 在onStop()时执行：图片停止切换
     */
    public void onStop() {
        // 当Activity不可见的时候停止切换
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }
}
