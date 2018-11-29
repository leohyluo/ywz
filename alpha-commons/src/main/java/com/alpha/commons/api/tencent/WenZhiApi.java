package com.alpha.commons.api.tencent;

import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alpha.commons.api.tencent.qcloud.QcloudApiModuleCenter;
import com.alpha.commons.api.tencent.qcloud.Module.Wenzhi;
import com.alpha.commons.api.tencent.qcloud.Utilities.Json.JSONObject;

public class WenZhiApi {
	
	QcloudApiModuleCenter module;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public WenZhiApi() {
		TreeMap<String, Object> config = new TreeMap<String, Object>();
		String secretId = ApiConstants.secretId;
		String secretKey = ApiConstants.secretKey;
		config.put("SecretId", secretId);
		config.put("SecretKey", secretKey);
		/* 请求方法类型 POST、GET */
		config.put("RequestMethod", "GET");
		/* 区域参数，可选: gz:广州; sh:上海; hk:香港; ca:北美;等。 */
		config.put("DefaultRegion", "gz");
		
		module = new QcloudApiModuleCenter(new Wenzhi(), config);
	}
	
	public String invoke(String action, TreeMap<String, Object> params) {
		/* 将需要输入的参数都放入 params 里面，必选参数是必填的。 */
		String result = null;
		try {
			result = module.call(action, params);
			JSONObject json_result = new JSONObject(result);
			logger.info("WenZhiApi." +action + " response content is " +json_result.toString());
		} catch (Exception e) {
			logger.info("error on WenZhiApi.inovke", e);
		}
		return result;
	}
}
