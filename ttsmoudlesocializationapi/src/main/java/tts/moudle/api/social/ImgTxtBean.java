package tts.moudle.api.social;

/**
 * Created by sjb on 2016/3/26.
 */
public class ImgTxtBean {
    private String id;
    private String name;
    private int iconId;

    public String getId() {
        return id;
    }

    public ImgTxtBean setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ImgTxtBean setName(String name) {
        this.name = name;
        return this;
    }

    public int getIconId() {
        return iconId;
    }

    public ImgTxtBean setIconId(int iconId) {
        this.iconId = iconId;
        return this;
    }
}
