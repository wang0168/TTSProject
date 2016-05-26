package tts.project.handi.bean;

import java.io.Serializable;

/**
 * Created by lenove on 2016/4/1.
 */
public class DistinctsBean implements Serializable {
    private String id;
    private String parent_id;
    private String name;

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
}