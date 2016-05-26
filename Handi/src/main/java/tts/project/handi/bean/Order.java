package tts.project.handi.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chen on 2016/2/29.
 */
public class Order implements Serializable {


    /**
     * orders_id : 2
     * serviceitem_id : 1
     * servicenames : 水电维修
     * orderstype : 0
     * succid : null
     * service_id : 1
     * ordersnumber : 123123122131
     * ordersstate : 0
     * personaltype : 1
     * servicecoutext : 2强电系统施工的技术要求: 1. 配电箱安装位置:应安装在通风易操作位置,便于日常的操作及维修,箱体的底边离地
     * orderstime : 2016-03-07 12:00:15
     * servicetime : 2016-03-08 11:17:40
     * servaddress : 北京朝阳区
     * money : 1001
     * yesno : 1
     * yzname : 王三
     * yzphone : 18321114444
     * yztrue : 1
     * sjphone : 18300225544
     * sjname : 商家
     * sjaddress : 上海市长清路507
     * sfid : null
     * sftrue : 0
     * del : 0
     * mark : 备注
     */

    private String orders_id;
    private String serviceitem_id;
    private String servicenames;
    private String orderstype;
    private Object succid;
    private String service_id;
    private String ordersnumber;
    private String ordersstate;
    private String personaltype;
    private String servicecoutext;
    private String orderstime;
    private String servicetime;
    private String servaddress;
    private String money;
    private String yesno;
    private String yzname;
    private String yzphone;
    private String yztrue;
    private String sjphone;
    private String sjname;
    private String sjaddress;
    private Object sfid;
    private String sftrue;
    private String del;
    private String mark;
    private String b;
    private String l;
    private String province;
    private String city;
    private String area;
    private List<String> ordersimg;


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public List<String> getOrdersimg() {
        return ordersimg;
    }

    public void setOrdersimg(List<String> ordersimg) {
        this.ordersimg = ordersimg;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public void setServiceitem_id(String serviceitem_id) {
        this.serviceitem_id = serviceitem_id;
    }

    public void setServicenames(String servicenames) {
        this.servicenames = servicenames;
    }

    public void setOrderstype(String orderstype) {
        this.orderstype = orderstype;
    }

    public void setSuccid(Object succid) {
        this.succid = succid;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public void setOrdersnumber(String ordersnumber) {
        this.ordersnumber = ordersnumber;
    }

    public void setOrdersstate(String ordersstate) {
        this.ordersstate = ordersstate;
    }

    public void setPersonaltype(String personaltype) {
        this.personaltype = personaltype;
    }

    public void setServicecoutext(String servicecoutext) {
        this.servicecoutext = servicecoutext;
    }

    public void setOrderstime(String orderstime) {
        this.orderstime = orderstime;
    }

    public void setServicetime(String servicetime) {
        this.servicetime = servicetime;
    }

    public void setServaddress(String servaddress) {
        this.servaddress = servaddress;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setYesno(String yesno) {
        this.yesno = yesno;
    }

    public void setYzname(String yzname) {
        this.yzname = yzname;
    }

    public void setYzphone(String yzphone) {
        this.yzphone = yzphone;
    }

    public void setYztrue(String yztrue) {
        this.yztrue = yztrue;
    }

    public void setSjphone(String sjphone) {
        this.sjphone = sjphone;
    }

    public void setSjname(String sjname) {
        this.sjname = sjname;
    }

    public void setSjaddress(String sjaddress) {
        this.sjaddress = sjaddress;
    }

    public void setSfid(Object sfid) {
        this.sfid = sfid;
    }

    public void setSftrue(String sftrue) {
        this.sftrue = sftrue;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getOrders_id() {
        return orders_id;
    }

    public String getServiceitem_id() {
        return serviceitem_id;
    }

    public String getServicenames() {
        return servicenames;
    }

    public String getOrderstype() {
        return orderstype;
    }

    public Object getSuccid() {
        return succid;
    }

    public String getService_id() {
        return service_id;
    }

    public String getOrdersnumber() {
        return ordersnumber;
    }

    public String getOrdersstate() {
        return ordersstate;
    }

    public String getPersonaltype() {
        return personaltype;
    }

    public String getServicecoutext() {
        return servicecoutext;
    }

    public String getOrderstime() {
        return orderstime;
    }

    public String getServicetime() {
        return servicetime;
    }

    public String getServaddress() {
        return servaddress;
    }

    public String getMoney() {
        return money;
    }

    public String getYesno() {
        return yesno;
    }

    public String getYzname() {
        return yzname;
    }

    public String getYzphone() {
        return yzphone;
    }

    public String getYztrue() {
        return yztrue;
    }

    public String getSjphone() {
        return sjphone;
    }

    public String getSjname() {
        return sjname;
    }

    public String getSjaddress() {
        return sjaddress;
    }

    public Object getSfid() {
        return sfid;
    }

    public String getSftrue() {
        return sftrue;
    }

    public String getDel() {
        return del;
    }

    public String getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orders_id='" + orders_id + '\'' +
                ", serviceitem_id='" + serviceitem_id + '\'' +
                ", servicenames='" + servicenames + '\'' +
                ", orderstype='" + orderstype + '\'' +
                ", succid=" + succid +
                ", service_id='" + service_id + '\'' +
                ", ordersnumber='" + ordersnumber + '\'' +
                ", ordersstate='" + ordersstate + '\'' +
                ", personaltype='" + personaltype + '\'' +
                ", servicecoutext='" + servicecoutext + '\'' +
                ", orderstime='" + orderstime + '\'' +
                ", servicetime='" + servicetime + '\'' +
                ", servaddress='" + servaddress + '\'' +
                ", money='" + money + '\'' +
                ", yesno='" + yesno + '\'' +
                ", yzname='" + yzname + '\'' +
                ", yzphone='" + yzphone + '\'' +
                ", yztrue='" + yztrue + '\'' +
                ", sjphone='" + sjphone + '\'' +
                ", sjname='" + sjname + '\'' +
                ", sjaddress='" + sjaddress + '\'' +
                ", sfid=" + sfid +
                ", sftrue='" + sftrue + '\'' +
                ", del='" + del + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }
}
