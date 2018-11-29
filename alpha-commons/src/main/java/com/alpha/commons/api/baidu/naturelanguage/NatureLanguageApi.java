package com.alpha.commons.api.baidu.naturelanguage;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alpha.commons.api.baidu.APIConstant;
import com.baidu.aip.nlp.AipNlp;

public class NatureLanguageApi {
	
	private AipNlp client;

	public NatureLanguageApi() {
		client = new AipNlp(APIConstant.APP_ID, APIConstant.API_KEY, APIConstant.SECERT_KEY);
	}
	
	/**
	 * 词法分析（传入文本，获取词法分析结果）
	 * @param text 待解析文本,长度不超过65536字节
	 * @return 拆词结果集
	 */
	public List<String> lexer(String text) {
		JSONObject json =  client.lexer(text);
		JSONArray jarr = json.has("items") ? json.getJSONArray("items") : new JSONArray();
		List<String> wordList = new ArrayList<>();
		for(int i = 0; i < jarr.length(); i++) {
			JSONObject wordItem = jarr.getJSONObject(i);
			String word = wordItem.getString("item");
			wordList.add(word);
		}
		return wordList;
	}
	
	/**
	 * 传入两个短文本，计算相似度
	 * @param source 第一个短文本
	 * @param target 第二个短文本
	 * @return 短文本相似度
	 */
	public Double simnet(String source, String target) {
		//默认算法为"BOW",可选"BOW", "CNN", "GRNN"
		JSONObject json = client.simnet(source, target, null);
		Double score = json.getDouble("score");
		return score;
	}
}
