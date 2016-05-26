package tts.project.igg.bean;

/**
 * Created by lenove on 2016/5/10.
 */
public class BaseInfoItemBean {
    public BaseInfoItemBean(String name, String context, String context_left, boolean isIcon, boolean bottomLine) {
        this.name = name;
        this.context = context;
        this.isIcon = isIcon;
        this.bottomLine = bottomLine;
        this.context_left = context_left;
    }

    private String name;
    private String context;
    private String context_left;
    private boolean isIcon;
    private boolean bottomLine;

    public String getContext_left() {
        return context_left;
    }

    public void setContext_left(String context_left) {
        this.context_left = context_left;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public boolean isIcon() {
        return isIcon;
    }

    public void setIcon(boolean icon) {
        isIcon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBottomLine() {
        return bottomLine;
    }

    public void setBottomLine(boolean bottomLine) {
        this.bottomLine = bottomLine;
    }
}
