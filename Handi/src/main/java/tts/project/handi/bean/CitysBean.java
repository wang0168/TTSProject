package tts.project.handi.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenove on 2016/4/1.
 */
public class CitysBean implements Serializable {
    private String id;
    private String parent_id;
    private String name;
    /**
     * id : 500
     * parent_id : 52
     * name : 东城区
     */

    private List<DistinctsBean> distincts;

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

    public List<DistinctsBean> getDistincts() {
        return distincts;
    }

    public void setDistincts(List<DistinctsBean> distincts) {
        this.distincts = distincts;
    }
}