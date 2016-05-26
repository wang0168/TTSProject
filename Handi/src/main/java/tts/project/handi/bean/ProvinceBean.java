package tts.project.handi.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenove on 2016/4/1.
 */
public class ProvinceBean implements Serializable {

    /**
     * id : 2
     * parent_id : 1
     * name : 北京
     * citys : [{"id":"52","parent_id":"2","name":"北京","distincts":[{"id":"500","parent_id":"52","name":"东城区"},{"id":"501","parent_id":"52","name":"西城区"},{"id":"502","parent_id":"52","name":"海淀区"},{"id":"503","parent_id":"52","name":"朝阳区"},{"id":"504","parent_id":"52","name":"崇文区"},{"id":"505","parent_id":"52","name":"宣武区"},{"id":"506","parent_id":"52","name":"丰台区"},{"id":"507","parent_id":"52","name":"石景山区"},{"id":"508","parent_id":"52","name":"房山区"},{"id":"509","parent_id":"52","name":"门头沟区"},{"id":"510","parent_id":"52","name":"通州区"},{"id":"511","parent_id":"52","name":"顺义区"},{"id":"512","parent_id":"52","name":"昌平区"},{"id":"513","parent_id":"52","name":"怀柔区"},{"id":"514","parent_id":"52","name":"平谷区"},{"id":"515","parent_id":"52","name":"大兴区"},{"id":"516","parent_id":"52","name":"密云县"},{"id":"517","parent_id":"52","name":"延庆县"}]}]
     */

    private String id;
    private String parent_id;
    private String name;
    /**
     * id : 52
     * parent_id : 2
     * name : 北京
     * distincts : [{"id":"500","parent_id":"52","name":"东城区"},{"id":"501","parent_id":"52","name":"西城区"},{"id":"502","parent_id":"52","name":"海淀区"},{"id":"503","parent_id":"52","name":"朝阳区"},{"id":"504","parent_id":"52","name":"崇文区"},{"id":"505","parent_id":"52","name":"宣武区"},{"id":"506","parent_id":"52","name":"丰台区"},{"id":"507","parent_id":"52","name":"石景山区"},{"id":"508","parent_id":"52","name":"房山区"},{"id":"509","parent_id":"52","name":"门头沟区"},{"id":"510","parent_id":"52","name":"通州区"},{"id":"511","parent_id":"52","name":"顺义区"},{"id":"512","parent_id":"52","name":"昌平区"},{"id":"513","parent_id":"52","name":"怀柔区"},{"id":"514","parent_id":"52","name":"平谷区"},{"id":"515","parent_id":"52","name":"大兴区"},{"id":"516","parent_id":"52","name":"密云县"},{"id":"517","parent_id":"52","name":"延庆县"}]
     */

    private List<CitysBean> citys;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CitysBean> getCitys() {
        return citys;
    }

    public void setCitys(List<CitysBean> citys) {
        this.citys = citys;
    }


}
