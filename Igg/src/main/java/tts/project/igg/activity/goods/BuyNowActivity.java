package tts.project.igg.activity.goods;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import tts.project.igg.BaseActivity;
import tts.project.igg.R;

public class BuyNowActivity extends BaseActivity {
    private FlexboxLayout tags_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_now);
        tags_layout = (FlexboxLayout) findViewById(R.id.tags_layout);
        for (int i = 0; i < 20; i++) {
            TextView tv = new TextView(this);
            tv.setText("感时花溅泪" + i);
            tags_layout.addView(tv);
        }
    }
}
