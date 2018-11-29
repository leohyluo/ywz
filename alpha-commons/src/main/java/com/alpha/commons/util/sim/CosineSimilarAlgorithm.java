package com.alpha.commons.util.sim;



import com.alpha.commons.util.StringUtils;
import com.alpha.commons.util.py4j.Py4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iEBM on 2017-05-11.
 */
public class CosineSimilarAlgorithm {

    /**
     * @param @param  first 用户输入词
     * @param @param  second 待比较词
     * @param @return
     * @return Double
     * @throws
     * @Title: cosSimilarityByString
     * @Description: 得到两个字符串的相似性
     */
    public static Double cosSimilarityByString(String first, String second) {
        try {
            Map<String, Integer> firstTfMap = null;
            Map<String, Integer> secondTfMap = null;
            if (!StringUtils.isChinese(first)) {
                Py4j py = new Py4j();
                //两者是拼音使用另外一套算法，不与分词共同使用
                first = Py4j.getPyinPreFix(first); // py.getPyinPreFix(first);
                second = Py4j.getPyinPreFix(second); // py.getPyinPreFix(second);
                Similarity similarity = new Similarity(Py4j.getPyinPreFix(first), Py4j.getPyinPreFix(second));
                return similarity.sim();
            } else {
                Similarity similarity = new Similarity(first, second);
                return similarity.sim();
//                firstTfMap = TfIdfAlgorithm.segStr(first);
//                secondTfMap = TfIdfAlgorithm.segStr(second);
            }
//            if (firstTfMap.size() < secondTfMap.size()) {
//                Map<String, Integer> temp = firstTfMap;
//                firstTfMap = secondTfMap;
//                secondTfMap = temp;
//            }
//            return calculateCos((LinkedHashMap<String, Integer>) firstTfMap, (LinkedHashMap<String, Integer>) secondTfMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0d;
    }

    /**
     * @param @param  first
     * @param @param  second
     * @param @return
     * @return Double
     * @throws
     * @Title: calculateCos
     * @Description: 计算余弦相似性 余弦值越接近1，就表明夹角越接近0度，也就是两个向量越相似
     */
    private static Double calculateCos(LinkedHashMap<String, Integer> first, LinkedHashMap<String, Integer> second) {

        List<Map.Entry<String, Integer>> firstList = new ArrayList<Map.Entry<String, Integer>>(first.entrySet());
        List<Map.Entry<String, Integer>> secondList = new ArrayList<Map.Entry<String, Integer>>(second.entrySet());
        //计算相似度
        double vectorFirstModulo = 0.00;//向量1的模
        double vectorSecondModulo = 0.00;//向量2的模
        double vectorProduct = 0.00; //向量积
        int secondSize = second.size();
        for (int i = 0; i < firstList.size(); i++) {
            if (i < secondSize) {
                vectorSecondModulo += secondList.get(i).getValue().doubleValue() * secondList.get(i).getValue().doubleValue();
                vectorProduct += firstList.get(i).getValue().doubleValue() * secondList.get(i).getValue().doubleValue();
            }
            vectorFirstModulo += firstList.get(i).getValue().doubleValue() * firstList.get(i).getValue().doubleValue();
        }
        return vectorProduct / (Math.sqrt(vectorFirstModulo) * Math.sqrt(vectorSecondModulo));
    }

    public static void main(String[] args) {
        String s1 = "补肾益精";
        String s2 = "补肾";
        Double result = cosSimilarityByString(s1,
                s2);
        System.out.println(result);


    }
}
