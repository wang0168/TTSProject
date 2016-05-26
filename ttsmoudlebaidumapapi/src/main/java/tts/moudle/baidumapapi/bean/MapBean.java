package tts.moudle.baidumapapi.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import moudle.project.tts.ttsmoudlebaidumapapi.R;

/**
 * Created by sjb on 2016/3/3.
 */
public class MapBean implements Serializable {
    private MyLocationConfiguration.LocationMode mCurrentMode;//定位模式  普通:LocationMode.NORMAL  跟随：LocationMode.FOLLOWING  罗盘:LocationMode.COMPASS
    private int iconId;//定位自己的图标
    private List<LocationBean> locationBeans;//位置数组





    public List<LocationBean> getLocations() {
        return locationBeans;
    }


    public void setLocations(List<LocationBean> locations) {
        this.locationBeans = locations;
    }


    public MyLocationConfiguration.LocationMode getmCurrentMode() {
        return mCurrentMode == null ? MyLocationConfiguration.LocationMode.NORMAL : mCurrentMode;
    }

    public void setmCurrentMode(MyLocationConfiguration.LocationMode mCurrentMode) {
        this.mCurrentMode = mCurrentMode;
    }

    public int getIconId() {
        return iconId == 0 ? R.drawable.icon_marka : iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
