package tts.moudle.baidumapapi.bean;

import java.io.Serializable;

import moudle.project.tts.ttsmoudlebaidumapapi.R;

/**
 * Created by sjb on 2016/3/4.
 */
public class LocationBean implements Serializable{
    private Double Latitude;//经度
    private Double Longitude;//纬度
    private float radius;//精确度

    private int mark;//坐标显示位置

    public int getMark() {
        return mark==0? R.drawable.icon_marka:mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }
}
