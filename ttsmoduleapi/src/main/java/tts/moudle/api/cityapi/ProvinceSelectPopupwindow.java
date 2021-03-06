package tts.moudle.api.cityapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.List;

import okhttp3.Request;
import tts.moudle.api.bean.UpdateBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.FileUtils;
import tts.moudle.api.utils.SystemUtils;
import tts.moudle.api.widget.ProgressBarUpdate;
import tts.moudle.api.widget.wheel.OnWheelChangedListener;
import tts.moudle.api.widget.wheel.OnWheelScrollListener;
import tts.moudle.api.widget.wheel.WheelView;
import tts.moudle.ttsmoduleapi.R;

/**
 * Created by sjb on 2016/3/21.
 */
public class ProvinceSelectPopupwindow extends PopupWindow {
    private Context context;
    View view = null;
    int type = 1;//1:城市联动 2：城市区联动

    private WheelView province, city, area;
    private Button btn;
    List<CityBean> provinceBeans;
    List<CityBean> cityBeans;
    List<String> areaBeans;

    public ProvinceSelectPopupwindow(Context context, View view) {
        super(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                true);
        this.view = view;
        this.context = context;
        type = 1;
        onCreate();
    }

    /**
     * @param context
     * @param view
     * @param type
     */
    public ProvinceSelectPopupwindow(Context context, View view, int type) {
        super(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                true);
        this.view = view;
        this.context = context;
        this.type = type;
        onCreate();
    }

    private void onCreate() {
        findAllView();
        provinceBeans = CityMoudle.getInstance().getCityBeans(context);
        adapterProvince();
    }

    private int provinceIndex = 1;
    private int cityIndex = 1;
    private int areaIndex = 1;

    private void adapterProvince() {
        province.setViewAdapter(new CityAdapter(context, provinceBeans, null));
        provinceIndex = provinceBeans.size() / 2;
        province.setCurrentItem(provinceIndex);
        province.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                provinceIndex = newValue;
            }
        });
        province.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                adapterCity();
            }
        });
        adapterCity();
    }

    private void adapterCity() {
        cityBeans = provinceBeans.get(provinceIndex).getCityList();

        city.setViewAdapter(new CityAdapter(context, cityBeans, null));
        cityIndex = cityBeans.size() / 2;
        city.setCurrentItem(cityIndex);
        city.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                cityIndex = newValue;
            }
        });
        city.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                if (type != 1) {
                    adapterArea();
                }
            }
        });
        if (type != 1) {
            adapterArea();
        }
    }

    private void adapterArea() {
        areaBeans = cityBeans.get(cityIndex).getAreaList();
        area.setViewAdapter(new CityAdapter(context, null, areaBeans));
        areaIndex = areaBeans.size() / 2;
        area.setCurrentItem(areaIndex);
        area.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                areaIndex = newValue;
            }
        });
        area.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
            }
        });
    }


    private void findAllView() {
        province = (WheelView) view.findViewById(R.id.province);
        province.setShadowColor(context.getResources().getColor(R.color.RGBTransparent),
                context.getResources().getColor(R.color.RGBTransparent), context.getResources().getColor(R.color.RGBTransparent));
        province.setVisibleItems(3);

        city = (WheelView) view.findViewById(R.id.city);
        city.setShadowColor(context.getResources().getColor(R.color.RGBTransparent),
                context.getResources().getColor(R.color.RGBTransparent), context.getResources().getColor(R.color.RGBTransparent));
        city.setVisibleItems(3);

        area = (WheelView) view.findViewById(R.id.area);
        area.setShadowColor(context.getResources().getColor(R.color.RGBTransparent),
                context.getResources().getColor(R.color.RGBTransparent), context.getResources().getColor(R.color.RGBTransparent));
        area.setVisibleItems(3);

        if (type != 1) {
            area.setVisibility(View.VISIBLE);
        } else {
            area.setVisibility(View.GONE);
        }

        btn = (Button) view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.doClick(provinceBeans.get(provinceIndex).getName(), cityBeans.get(cityIndex).getName(), areaBeans == null ? "" : areaBeans.get(areaIndex));
                    dismiss();
                }
            }
        });
    }

    public interface OnClickListener {
        public void doClick(String province, String city, String area);
    }

    OnClickListener listener;

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }
}


