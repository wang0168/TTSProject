package tts.moudle.baidumapapi.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import java.util.ArrayList;

import moudle.project.tts.ttsmoudlebaidumapapi.R;
import tts.moudle.api.TTSBaseActivity;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.baidumapapi.bean.LocationBean;
import tts.moudle.baidumapapi.bean.MapBean;
import tts.moudle.baidumapapi.moudle.BaiduMoudle;

/**
 * Created by sjb on 2016/3/3.
 */
public class BaiduMapBaseActivity extends TTSBaseActivity {
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    MapView mMapView;
    BaiduMap mBaiduMap;
    // UI相关
    boolean isFirstLoc = true; // 是否首次定位
    private MapBean mapBean;
    private String title;

    public BDLocation lastLocation;
    BitmapDescriptor bdGround;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baidumap_base_activity);
        getIntentData();
        bdGround = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        findAllView();
        initBaidu();
        startLocation();
      if (mapBean.getLocations()!=null) {
            MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);//縮放等級
            mBaiduMap.setMapStatus(msu);
            initOverlay();
        }
    }


    public void initOverlay() {
        BitmapDescriptor bd;
        for (int i = 0; i < mapBean.getLocations().size(); i++) {
            bd = BitmapDescriptorFactory
                    .fromResource(mapBean.getLocations().get(i).getMark());
            LatLng ll = new LatLng(mapBean.getLocations().get(i).getLatitude(), mapBean.getLocations().get(i).getLongitude());
            MarkerOptions ooA = new MarkerOptions().position(ll).icon(bd)
                    .zIndex(i).draggable(true);
            Marker mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
        }
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("locationBean", mapBean.getLocations().get(marker.getZIndex()));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                return true;
            }
        });
    }

    /**
     * 定位在自己位置
     */
    private void startLocation() {
        BaiduMoudle.getInstance().startRegisterReceiver(this);
        BaiduMoudle.getInstance().startLocation(this, new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                // map view 销毁后不在处理新接收的位置
                if (location == null || mMapView == null) {
                    return;
                }
                lastLocation = location;
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(0).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                if (isFirstLoc) {
                    isFirstLoc = false;
                    LatLng ll = new LatLng(location.getLatitude(),
                            location.getLongitude());
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }
            }
        });
    }

    private void getIntentData() {
        mapBean = (MapBean) getIntent().getSerializableExtra("mapBean");
        title = getIntent().getStringExtra("title");
        if (mapBean == null) {
            mapBean = new MapBean();
        }
    }

    private void findAllView() {
        setTitle(new BarBean().setMsg(title == null ? "位置信息" : title));
        if (mapBean.getLocations()==null) {
            addMenu(new MenuItemBean().setTitle("确定"));
        }
        mMapView = (MapView) findViewById(R.id.bmapView);
    }

    /**
     * 初始化百度地图
     */
    private void initBaidu() {
        mCurrentMode = mapBean.getmCurrentMode();
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(mapBean.getIconId());

        // 地图初始化
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker));
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
        super.doMenu(menuItemBean);
        sendLocation();
    }

    public void sendLocation() {
        if (lastLocation != null) {
            Intent intent = this.getIntent();
            intent.putExtra("latitude", lastLocation.getLatitude());
            intent.putExtra("longitude", lastLocation.getLongitude());
            intent.putExtra("address", lastLocation.getAddrStr());
            this.setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        }
    }


    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        if (BaiduMoudle.getInstance().mLocClient != null) {
            BaiduMoudle.getInstance().mLocClient.stop();
        }
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

}

