package com.alpha.commons.api.tencent.offical.dto;

/**
 * 关注/取消关注
 * @author Administrator
 *
 */
public class FollowDTO extends CallBackDTO {
	
	//开发者微信号
	private String ToUserName;	
	//发送方帐号（一个OpenID）
	private String FromUserName;
	//消息创建时间 （整型）
	private String CreateTime;
	//消息类型，event
	private String MsgType;
	//事件类型，subscribe(订阅)、unsubscribe(取消订阅)
	private String Event;
	//事件KEY值，qrscene_为前缀，后面为二维码的参数值
	private String EventKey;
	//二维码的ticket，可用来换取二维码图片
	private String Ticket;
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	
	
}
