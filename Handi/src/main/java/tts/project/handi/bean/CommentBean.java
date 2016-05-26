package tts.project.handi.bean;

import java.util.List;

/**
 * Created by chen on 2016/3/15.
 */
public class CommentBean {

    /**
     * one : 3
     * two : 1
     * three : 0
     * allnumber : 4
     * countext : [{"evaluatecot":"技术好，做事细心","nickname":"测试账号1","level":"0","img":"http://handi.tstmobile.com/img/2016-03-15/tu17250_5.jpg"},{"evaluatecot":"技术好，做事细心","nickname":"美丽动人的话","level":"1","img":"http://handi.tstmobile.com/img/2016-03-15/tu17250_5.jpg"},{"evaluatecot":"1231322","nickname":"业主","level":"0","img":"http://handi.tstmobile.com/img/2016-03-15/tu17250_5.jpg"},{"evaluatecot":"非常好","nickname":"业主","level":"0","img":"http://handi.tstmobile.com/img/2016-03-15/tu17250_5.jpg"}]
     */

    private int one;
    private int two;
    private int three;
    private int allnumber;
    /**
     * evaluatecot : 技术好，做事细心
     * nickname : 测试账号1
     * level : 0
     * img : http://handi.tstmobile.com/img/2016-03-15/tu17250_5.jpg
     */

    private List<CommentContext> countext;

    public void setOne(int one) {
        this.one = one;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public void setThree(int three) {
        this.three = three;
    }

    public void setAllnumber(int allnumber) {
        this.allnumber = allnumber;
    }

    public void setCountext(List<CommentContext> countext) {
        this.countext = countext;
    }

    public int getOne() {
        return one;
    }

    public int getTwo() {
        return two;
    }

    public int getThree() {
        return three;
    }

    public int getAllnumber() {
        return allnumber;
    }

    public List<CommentContext> getCountext() {
        return countext;
    }


}
