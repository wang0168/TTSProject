package chexin.project.tts.chexin.bean;

/**
 * Created by sjb on 2016/3/25.
 */
public class PublicMenuBean {
    private String id;
    private String name;
    private String other;
    private int iconId;//左边图标
    private int iconRightId;//右边图标
    private boolean isRemoveRightIcon;//是否去掉右边图标
    private boolean isMargin;//是否要上边距
    private boolean isLine;//是否需要下面的分割线


    public String getOther() {
        return other;
    }

    public PublicMenuBean setOther(String other) {
        this.other = other;
        return this;
    }

    public boolean isRemoveRightIcon() {
        return isRemoveRightIcon;
    }

    public PublicMenuBean setIsRemoveRightIcon(boolean isRemoveRightIcon) {
        this.isRemoveRightIcon = isRemoveRightIcon;
        return this;
    }

    public int getIconRightId() {
        return iconRightId;
    }

    public PublicMenuBean setIconRightId(int iconRightId) {
        this.iconRightId = iconRightId;
        return this;
    }

    public boolean isMargin() {
        return isMargin;
    }

    public PublicMenuBean setIsMargin(boolean isMargin) {
        this.isMargin = isMargin;
        return this;
    }

    public boolean isLine() {
        return isLine;
    }

    public PublicMenuBean setIsLine(boolean isLine) {
        this.isLine = isLine;
        return this;
    }

    public String getId() {
        return id;
    }

    public PublicMenuBean setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PublicMenuBean setName(String name) {
        this.name = name;
        return this;
    }

    public int getIconId() {
        return iconId;
    }

    public PublicMenuBean setIconId(int iconId) {
        this.iconId = iconId;
        return this;
    }
}
