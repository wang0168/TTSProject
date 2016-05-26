package tts.project.igg.bean;

/**
 * Created by lenove on 2016/5/10.
 */
public class PushItemBean {


    private boolean bottomLine;
    /**
     * information_class_id : 1
     * member_id :
     * information_name : 美食
     * is_have : 1
     */

    private int information_class_id;
    private String member_id;
    private String information_name;
    private String is_have;

    public boolean isBottomLine() {
        return bottomLine;
    }

    public void setBottomLine(boolean bottomLine) {
        this.bottomLine = bottomLine;
    }

    public int getInformation_class_id() {
        return information_class_id;
    }

    public void setInformation_class_id(int information_class_id) {
        this.information_class_id = information_class_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getInformation_name() {
        return information_name;
    }

    public void setInformation_name(String information_name) {
        this.information_name = information_name;
    }

    public String getIs_have() {
        return is_have;
    }

    public void setIs_have(String is_have) {
        this.is_have = is_have;
    }
}
