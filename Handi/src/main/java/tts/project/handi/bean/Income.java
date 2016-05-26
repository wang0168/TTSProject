package tts.project.handi.bean;

import java.util.List;

/**
 * Created by chen on 2016/3/15.
 */
public class Income {

    /**
     * bl : [{"bill_id":"5","personal_id":"1","billcot":"服务","billtime":"2016-03-10 16:01:12","billmoney":"111","dlmoney":null,"state":"1","record":null,"rdtime":null},{"bill_id":"6","personal_id":"1","billcot":"服务","billtime":"2016-03-10 16:01:12","billmoney":"222","dlmoney":null,"state":"1","record":null,"rdtime":null},{"bill_id":"7","personal_id":"1","billcot":"服务","billtime":"2016-03-10 16:01:12","billmoney":"333","dlmoney":null,"state":"1","record":null,"rdtime":null}]
     * daynum : 0
     * weeknum : 0
     * money : 510.2
     * banknum : 0
     */

    private int daynum;
    private int weeknum;
    private String money;
    private String banknum;
    /**
     * bill_id : 5
     * personal_id : 1
     * billcot : 服务
     * billtime : 2016-03-10 16:01:12
     * billmoney : 111
     * dlmoney : null
     * state : 1
     * record : null
     * rdtime : null
     */

    private List<BillBean> bl;

    public void setDaynum(int daynum) {
        this.daynum = daynum;
    }

    public void setWeeknum(int weeknum) {
        this.weeknum = weeknum;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setBanknum(String banknum) {
        this.banknum = banknum;
    }

    public void setBl(List<BillBean> bl) {
        this.bl = bl;
    }

    public int getDaynum() {
        return daynum;
    }

    public int getWeeknum() {
        return weeknum;
    }

    public String getMoney() {
        return money;
    }

    public String getBanknum() {
        return banknum;
    }

    public List<BillBean> getBl() {
        return bl;
    }


}
