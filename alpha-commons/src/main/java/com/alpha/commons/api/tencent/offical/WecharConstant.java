package com.alpha.commons.api.tencent.offical;

public class WecharConstant {

	//	事件KEY值，qrscene_为前缀，后面为二维码的参数值
	public static final String prefix = "qrscene_";
	// 个人APPID和Secret
//	public static final String APPID = "wx7be655eb8bf0631b";//个人
//	public static final String SECRET = "4cbee4a8e6ef083b11818a93c7463a72";//个人
	// 公司APPID和Secret
	public static final String APPID = "wxfdda8ab049068d08";//公司
	public static final String SECRET = "40b61d4e6f94a38c23ac7562c83faf33";//公司
	//获取token url
	public static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	//获取ticket url
	public static final String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
	//获取二维码URL
	public static final String QRCODE_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
	//发送模板消息URL
	public static final String TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";
	//通过code换取网页授权access_token
	//这里通过code换取的是一个特殊的网页授权access_token,与基础支持中的access_token（该access_token用于调用其他接口）不同。
	public static final String WEB_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	//创建菜单URL
	public static final String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create";
	//前端授权回调地址
	//public static final String H5_CALLBACK_URL = "http%3A%2F%2Fgmws.ebmsz.com%2Falpha%2Frecord.html";
	//public static final String H5_CALLBACK_URL = "http%3A%2F%2Fjcyx18081.ebmsz.com%2Falpha%2Frecord.html";
	//前端阿尔法回调地址
	//public static final String H5_ALPHA_CALLBACK_URL = "http%3A%2F%2Fjcyx18081.ebmsz.com%2Falpha%2Falpha.html";
	//公众号阿尔法入口
	//public static final String H5_ALPHA_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="+H5_ALPHA_CALLBACK_URL+"&response_type=code&scope=snsapi_base&state=1256#wechat_redirect";
	//public static final String H5_ALPHA_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfdda8ab049068d08&redirect_uri=http%3A%2F%2Fgmws.ebmsz.com%2Falpha%2Falpha.html&response_type=code&scope=snsapi_base&state=1256#wechat_redirect";
	
	public static final String QRCODE_SAVE_PATH = "F:/Test/tomcat7_mid/webapps/alpha/"; 

}
