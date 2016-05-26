package chexin.project.tts.chexin.bean;

/**
 * Created by sjb on 2016/3/23.
 */
public class HomeMenuBean {
    private String id;
    private String name;
    private int iconId;

    public String getId() {
        return id;
    }

    public HomeMenuBean setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public HomeMenuBean setName(String name) {
        this.name = name;
        return this;
    }

    public int getIconId() {
        return iconId;
    }

    public HomeMenuBean setIconId(int iconId) {
        this.iconId = iconId;
        return this;
    }
}
