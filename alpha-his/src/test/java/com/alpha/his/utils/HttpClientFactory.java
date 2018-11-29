package com.alpha.his.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClientFactory {

	public static CloseableHttpClient getDefault() {
		RequestConfig defaultConfig = RequestConfig.custom().setSocketTimeout(ClientConfig.socketTimeout).setConnectionRequestTimeout(ClientConfig.connectTimeout).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClients = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(defaultConfig).build();
		return httpClients;
	}
}
