package chexin.project.tts.chexin.bean;

import java.io.Serializable;

/**
 * Created by lenove on 2016/4/14.
 */
public class CarDetailBean implements Serializable{

    /**
     * vehicle_id : 44
     * UserId : 99
     * CarType : 平板车
     * CarWeight : 20T
     * CarLength : 14
     * CarWidth : 4
     * CarCode : 15698746
     * vehicle_state :
     */

    private int vehicle_id;
    private String UserId;
    private String CarType;
    private String CarWeight;
    private String CarLength;
    private String CarWidth;
    private String CarCode;
    private String vehicle_state;

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getCarType() {
        return CarType;
    }

    public void setCarType(String CarType) {
        this.CarType = CarType;
    }

    public String getCarWeight() {
        return CarWeight;
    }

    public void setCarWeight(String CarWeight) {
        this.CarWeight = CarWeight;
    }

    public String getCarLength() {
        return CarLength;
    }

    public void setCarLength(String CarLength) {
        this.CarLength = CarLength;
    }

    public String getCarWidth() {
        return CarWidth;
    }

    public void setCarWidth(String CarWidth) {
        this.CarWidth = CarWidth;
    }

    public String getCarCode() {
        return CarCode;
    }

    public void setCarCode(String CarCode) {
        this.CarCode = CarCode;
    }

    public String getVehicle_state() {
        return vehicle_state;
    }

    public void setVehicle_state(String vehicle_state) {
        this.vehicle_state = vehicle_state;
    }
}
