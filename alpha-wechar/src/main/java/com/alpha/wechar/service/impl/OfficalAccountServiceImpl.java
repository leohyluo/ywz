package com.alpha.wechar.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alpha.commons.api.tencent.offical.WecharConstant;
import com.alpha.commons.api.tencent.offical.dto.AccessTokenDTO;
import com.alpha.commons.api.tencent.offical.dto.AuthDTO;
import com.alpha.commons.api.tencent.offical.dto.QRCodeDTO;
import com.alpha.commons.api.tencent.offical.dto.TicketDTO;
import com.alpha.commons.config.pojo.FTPInfo;
import com.alpha.commons.enums.WecharTemplate;
import com.alpha.commons.util.HttpRequestUtil;
import com.alpha.commons.util.HttpUtils;
import com.alpha.wechar.service.OfficalAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.TreeMap;

@Service
public class OfficalAccountServiceImpl implements OfficalAccountService {
	
	@Resource
	private FTPInfo ftpInfo;

	@Value("${wechar.menu.diagnosisRecord}")
	private String wecharMenu4record;

	@Value("${wechar.menu.alpha}")
	private String wecharMenu4alpha;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public QRCodeDTO getTempQRCode(String userId, Long diagnosisId) {
		QRCodeDTO qrCodeDTO = null;
		try {
			TicketDTO ticketDTO = getTicket(userId);
			if(ticketDTO == null) {
				return null;
			}
			String ticket = ticketDTO.getTicket();
			
			ticket = URLEncoder.encode(ticket,"UTF-8");
			TreeMap<String,String> params = new TreeMap<String,String>();  
		    params.put("ticket",ticket);
		    
		    qrCodeDTO = HttpRequestUtil.download(params, HttpRequestUtil.GET_METHOD,
		    		WecharConstant.QRCODE_URL, ftpInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qrCodeDTO;
	}

	@Override
	public void sendQueueInfo(String wecharId, WecharTemplate template, String data) {
		try {
			AccessTokenDTO accessToken = getAccessToken(0);
			if(accessToken == null) {
				logger.info("AccessToken为空,无法推送消息");
			} else {
				String url = WecharConstant.TEMPLATE_URL.concat("?access_token=").concat(accessToken.getAccess_token());

				if(template == null) {
					logger.info("没有找到合适的消息模板");
					return;
				}

				JSONObject json = new JSONObject();
				//接收者openid
				json.put("touser", wecharId);
				//模板ID
				json.put("template_id", template.getValue());
				//模板跳转链接
				json.put("url", "");
				//模板数据
				json.put("data", data);
				HttpUtils.doPost(url, json.toJSONString());
				logger.info("推送排队信息给微信用户{}成功", wecharId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public AuthDTO getAuthAccessToken(String code) {
		String uri = WecharConstant.WEB_TOKEN_URL.concat("?appid=").concat(WecharConstant.APPID)
				.concat("&secret=").concat(WecharConstant.SECRET).concat("&code=").concat(code)
				.concat("&grant_type=authorization_code");
		String result = HttpUtils.doGet(uri);
		logger.warn("获取网页授权token,微信返回结果为"+result);
		AuthDTO authDto = JSON.parseObject(result, AuthDTO.class);
		return authDto;
	}

	@Override
	public void createMenu() {
		try {
			String diagnosisRecordURL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WecharConstant.APPID
					+"&redirect_uri="+wecharMenu4record
					+ "&response_type=code&scope=snsapi_base&state=1256#wechat_redirect";
			logger.info("就诊记录URL:{}", wecharMenu4record);

			String alphaURL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WecharConstant.APPID
					+"&redirect_uri="+wecharMenu4alpha+"&response_type=code&scope=snsapi_base&state=1256#wechat_redirect";
			logger.info("阿尔法URL:{}", wecharMenu4alpha);

			AccessTokenDTO accessToken = getAccessToken(0);
			String uri = WecharConstant.MENU_CREATE_URL.concat("?access_token=").concat(accessToken.getAccess_token());
			
			//我模块-就医记录
			JSONObject diagnosisRecordMenu = new JSONObject();
			diagnosisRecordMenu.put("name", "就医记录");
			diagnosisRecordMenu.put("type", "view");
			diagnosisRecordMenu.put("url", diagnosisRecordURL);
			
			JSONArray meArr = new JSONArray();
			meArr.add(diagnosisRecordMenu);
			
			JSONObject me = new JSONObject();
			me.put("name", "我");
			me.put("sub_button", meArr);
			
			//自诊模块-阿尔法医生
			JSONObject alpha = new JSONObject();
			alpha.put("name", "阿尔法医生");
			alpha.put("type", "view");
			alpha.put("url", alphaURL);
			
			JSONArray alphaArr = new JSONArray();
			alphaArr.add(alpha);
			
			JSONObject zizen = new JSONObject();
			zizen.put("name", "自诊/用药");
			zizen.put("sub_button", alphaArr);
			
			JSONObject json = new JSONObject();
			JSONArray jarr = new JSONArray();
			jarr.add(me);
			jarr.add(zizen);
			json.put("button", jarr);
			String param = json.toJSONString();
			logger.info("创建菜单");
			logger.info("创建菜单参数为{}", param);
			String result = HttpUtils.doPost(uri, param);
			logger.info("创建菜单返回结果:{}", result);
		} catch (Exception e) {
			logger.error("创建菜单失败");
			e.printStackTrace();
		}
		
	}

}
