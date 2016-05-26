package tts.moudle.huanxinapi.bean;

import java.io.Serializable;

public class HxUserBean implements Serializable{
	private String MyAccount;//自己账号
	private String MyName;//自己名字
	private String MyImg;//自己头像
	private String FriendAccount;//好友账号
	private String FriendName;//好友名字
	private String FriendImg;//好友头像
	
	private int titleId;//最上面的title  不传则使用默认的
	
	
	private boolean isCloseImg;//是否关闭图片功能
	private boolean isCloseTakic;//是否关闭拍照功能
	private boolean isCloseLocal;//是否关闭位置功能
	private boolean isCloseVoiceCall;//是否关闭语音通话功能
	private boolean isCloseVideoCall;//是否关闭视频通话功能
	private boolean isCloseVideo;//是否关闭视频功能
	private boolean isCloseFile;//是否关闭文件功能
	
	private GroupBean groupBean;
	
	public GroupBean getGroupBean() {
		return groupBean;
	}
	public void setGroupBean(GroupBean groupBean) {
		this.groupBean = groupBean;
	}
	public boolean isCloseImg() {
		return isCloseImg;
	}
	public void setCloseImg(boolean isCloseImg) {
		this.isCloseImg = isCloseImg;
	}
	public boolean isCloseTakic() {
		return isCloseTakic;
	}
	public void setCloseTakic(boolean isCloseTakic) {
		this.isCloseTakic = isCloseTakic;
	}
	public boolean isCloseLocal() {
		return isCloseLocal;
	}
	public void setCloseLocal(boolean isCloseLocal) {
		this.isCloseLocal = isCloseLocal;
	}
	public boolean isCloseVoiceCall() {
		return isCloseVoiceCall;
	}
	public void setCloseVoiceCall(boolean isCloseVoiceCall) {
		this.isCloseVoiceCall = isCloseVoiceCall;
	}
	public boolean isCloseVideoCall() {
		return isCloseVideoCall;
	}
	public void setCloseVideoCall(boolean isCloseVideoCall) {
		this.isCloseVideoCall = isCloseVideoCall;
	}
	public boolean isCloseVideo() {
		return isCloseVideo;
	}
	public void setCloseVideo(boolean isCloseVideo) {
		this.isCloseVideo = isCloseVideo;
	}
	public boolean isCloseFile() {
		return isCloseFile;
	}
	public void setCloseFile(boolean isCloseFile) {
		this.isCloseFile = isCloseFile;
	}
	public int getTitleId() {
		return titleId;
	}
	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}
	public String getMyAccount() {
		return MyAccount;
	}
	public void setMyAccount(String myAccount) {
		MyAccount = myAccount;
	}
	public String getMyName() {
		return MyName;
	}
	public void setMyName(String myName) {
		MyName = myName;
	}
	public String getMyImg() {
		return MyImg;
	}
	public void setMyImg(String myImg) {
		MyImg = myImg;
	}
	public String getFriendAccount() {
		return FriendAccount;
	}
	public void setFriendAccount(String friendAccount) {
		FriendAccount = friendAccount;
	}
	public String getFriendName() {
		return FriendName;
	}
	public void setFriendName(String friendName) {
		FriendName = friendName;
	}
	public String getFriendImg() {
		return FriendImg;
	}
	public void setFriendImg(String friendImg) {
		FriendImg = friendImg;
	}
	
	
}
