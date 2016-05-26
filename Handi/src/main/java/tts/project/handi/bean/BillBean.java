package tts.project.handi.bean;

/**
 * Created by chen on 2016/3/15.
 */
public class BillBean {

    private String bill_id;
    private String personal_id;
    private String billcot;
    private String billtime;
    private String billmoney;
    private Object dlmoney;
    private String state;
    private Object record;
    private Object rdtime;

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public void setPersonal_id(String personal_id) {
        this.personal_id = personal_id;
    }

    public void setBillcot(String billcot) {
        this.billcot = billcot;
    }

    public void setBilltime(String billtime) {
        this.billtime = billtime;
    }

    public void setBillmoney(String billmoney) {
        this.billmoney = billmoney;
    }

    public void setDlmoney(Object dlmoney) {
        this.dlmoney = dlmoney;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setRecord(Object record) {
        this.record = record;
    }

    public void setRdtime(Object rdtime) {
        this.rdtime = rdtime;
    }

    public String getBill_id() {
        return bill_id;
    }

    public String getPersonal_id() {
        return personal_id;
    }

    public String getBillcot() {
        return billcot;
    }

    public String getBilltime() {
        return billtime;
    }

    public String getBillmoney() {
        return billmoney;
    }

    public Object getDlmoney() {
        return dlmoney;
    }

    public String getState() {
        return state;
    }

    public Object getRecord() {
        return record;
    }

    public Object getRdtime() {
        return rdtime;
    }

}
