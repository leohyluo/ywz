package com.alpha.his.utils;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpUtils {
	
	private static CloseableHttpClient httpClient = HttpClientFactory.getDefault();
	
	public static void setup() {
		httpClient = HttpClientFactory.getDefault();
	}

	public static String postWithAllParam(String uri, String param) {
		HttpPost httpPost = new HttpPost(uri);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("allParam", param));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(entity);
        
		CloseableHttpResponse response;
		String result = "";
		try {
			response = httpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			result = EntityUtils.toString(httpEntity);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
