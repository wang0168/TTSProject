package tts.moudle.baidumapapi.moudle;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import tts.moudle.api.utils.CustomUtils;

/**
 * Created by sjb on 2016/3/4.
 */
public class BaiduMoudle {
    public LocationClient mLocClient;
    private BDLocationListener listenner;

    private static class Moudle {
        protected final static BaiduMoudle mInstance = new BaiduMoudle();
    }

    public static BaiduMoudle getInstance() {
        return Moudle.mInstance;
    }

    /**
     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
     */
    public class BaiduSDKReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            String st1 = "网络异常";
            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {

                String st2 = "key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置";
                CustomUtils.showTipShort(context, st2);
            } else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                CustomUtils.showTipShort(context, st1);
            }
        }
    }


    /**
     * // 注册 SDK 广播监听者
     */
    public void startRegisterReceiver(Context context) {
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        BaiduSDKReceiver mBaiduReceiver = new BaiduSDKReceiver();
        context.registerReceiver(mBaiduReceiver, iFilter);
    }

    /**
     * 初始化定位系统
     *
     * @param context
     * @param listenner
     */
    public void startLocation(Context context, BDLocationListener listenner) {
        this.listenner = listenner;
        mLocClient = new LocationClient(context);
        mLocClient.registerLocationListener(listenner);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用

    private OnGeoCodeListener geoCodeListener;
    private OnAddressListener addressListener;

    public interface OnGeoCodeListener {
        void getGeocode(GeoCodeResult result);
    }

    public interface OnAddressListener {
        void getAddress(ReverseGeoCodeResult result);
    }

    public void getGeocodeFromAddress(String city, String address, OnGeoCodeListener listener) {
        getGeocodeFromAddress(null, city, address, listener);
    }

    /**
     * 根据地址 获得 经纬度
     */
    public void getGeocodeFromAddress(final Context context, String city, String address, OnGeoCodeListener listener) {
        this.geoCodeListener = listener;
        if (mSearch == null) {
            mSearch = GeoCoder.newInstance();
            mSearch.setOnGetGeoCodeResultListener(new CodeListener());
        }
        mSearch.geocode(new GeoCodeOption().city(city).address(address));
    }

    public void getAddressFromGeocode(Double latitude, Double longitude, OnAddressListener listener) {
        getAddressFromGeocode(null, latitude, longitude, listener);
    }

    /**
     * 根据经纬度获得地址
     */
    public void getAddressFromGeocode(final Context context, Double latitude, Double longitude, OnAddressListener listener) {
        this.addressListener = listener;
        if (mSearch == null) {
            mSearch = GeoCoder.newInstance();
            mSearch.setOnGetGeoCodeResultListener(new CodeListener());
        }
        LatLng ptCenter = new LatLng(latitude, longitude);
        // 反Geo搜索
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
    }

    public class CodeListener implements OnGetGeoCoderResultListener {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                return;
            }
            if (geoCodeListener != null) {
                geoCodeListener.getGeocode(result);
            }
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                return;
            }
            if (addressListener != null) {
                addressListener.getAddress(result);
            }
        }
    }
}
