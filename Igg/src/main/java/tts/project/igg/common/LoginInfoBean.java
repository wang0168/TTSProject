package tts.project.igg.common;

/**
 * Created by lenove on 2016/5/6.
 */
public class LoginInfoBean {
    private String mobile;
    private String password;
    private String response;

    public String getMobile() {
        return mobile;
    }

    public LoginInfoBean setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginInfoBean setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getResponse() {
        return response;
    }

    public LoginInfoBean setResponse(String response) {
        this.response = response;
        return this;
    }
}
