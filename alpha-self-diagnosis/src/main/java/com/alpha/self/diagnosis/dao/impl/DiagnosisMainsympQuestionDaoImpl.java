package com.alpha.self.diagnosis.dao.impl;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.redis.RedisMrg;
import com.alpha.self.diagnosis.dao.DiagnosisMainsympQuestionDao;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by xc.xiong on 2017/9/5.
 */
@Repository
public class DiagnosisMainsympQuestionDaoImpl extends BaseDao<DiagnosisMainsympQuestion, Long> implements DiagnosisMainsympQuestionDao {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.pwd}")
    private String redisPwd;

    @Autowired
    public DiagnosisMainsympQuestionDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Class<DiagnosisMainsympQuestion> getClz() {
        return DiagnosisMainsympQuestion.class;
    }

    /**
     * 查询主症状下的所有问题
     *
     * @param mainSympCode
     * @return
     */
    public List<DiagnosisMainsympQuestion> listDiagnosisMainsympQuestion(String mainSympCode) {
         //redis问题数量比数据库的多，先屏蔽，待找原因
        /*List<DiagnosisMainsympQuestion> diagnosisMainsympQuestionList = (List<DiagnosisMainsympQuestion>) RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey(mainSympCode, RedisMrg.DB2);
        if(null != diagnosisMainsympQuestionList && !diagnosisMainsympQuestionList.isEmpty())
            return diagnosisMainsympQuestionList;*/

        Map<String, Object> params = new HashMap<>();
        params.put("mainSympCode", mainSympCode);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion.queryDiagnosisMainsympQuestion", params);
        List<DiagnosisMainsympQuestion> dmQuestions = new ArrayList<>();
        dmQuestions = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympQuestion.class);
        return dmQuestions;
    }

    public List<DiagnosisMainsympQuestion> listDiagnosisMainsympQuestionAll() {
        List<DataRecord> datas = super.selectList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion.queryDiagnosisMainsympQuestionAll");
        List<DiagnosisMainsympQuestion> dmQuestions = new ArrayList<>();
        dmQuestions = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympQuestion.class);
        return dmQuestions;
    }

    /**
     * 查询主症状的下一个问题
     *
     * @param mainSympCode
     * @return
     */
    public DiagnosisMainsympQuestion getNextQuestion(String mainSympCode, int defaultOrder) {
        Map<String, Object> params = new HashMap<>();
        params.put("mainSympCode", mainSympCode);
        params.put("defaultOrder", defaultOrder);
        DiagnosisMainsympQuestion dmQuestions = super.selectOne("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion.getDiagnosisMainsympQuestion", params);
        return dmQuestions;
    }

    /**
     * 查询主症状的未回答问题
     *
     * @param mainSympCode
     * @return
     */
    public List<DiagnosisMainsympQuestion> listNextAllQuestion(String mainSympCode, int defaultOrder) {
        List<DiagnosisMainsympQuestion> dmQuestions = new ArrayList<>();
        dmQuestions = (List<DiagnosisMainsympQuestion>) RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey(mainSympCode, RedisMrg.DB2);
        if(CollectionUtils.isNotEmpty(dmQuestions)) {
            //查询主症状下的未回答问题，and default_order>#{defaultOrder} and (is_show > 0 OR char_length(dependency_question_code) > 0) order by default_order ASC
            dmQuestions = dmQuestions.stream().filter(e->e.getDefaultOrder() != null && e.getDefaultOrder() > defaultOrder)
                    .filter(e-> (e.getShowFlag() != null && e.getShowFlag() > 0) || StringUtils.isNotEmpty(e.getDependencyQuestionCode()))
                    .sorted(Comparator.comparing(DiagnosisMainsympQuestion::getDefaultOrder)).collect(Collectors.toList());
            return dmQuestions;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("mainSympCode", mainSympCode);
        params.put("defaultOrder", defaultOrder);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion.queryUntreatedQuestion", params);
        dmQuestions = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympQuestion.class);
        return dmQuestions;
    }

    /**
     * 查询主症状下的所有自动计算问题(年龄、季节)
     *
     * @param mainSympCode
     * @return
     */
    public List<DiagnosisMainsympQuestion> listAutoQuestion(String mainSympCode) {
        List<DiagnosisMainsympQuestion> diagnosisMainsympQuestionList = (List<DiagnosisMainsympQuestion>) RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey(mainSympCode, RedisMrg.DB2);
        if(CollectionUtils.isNotEmpty(diagnosisMainsympQuestionList)) {
            //and is_show=0 and question_type in(103,104)
            diagnosisMainsympQuestionList = diagnosisMainsympQuestionList.stream().filter(e->e.getShowFlag() != null && e.getShowFlag().intValue() == 0)
                    .filter(e->e.getQuestionType() != null && (e.getQuestionType().intValue() == 103) || e.getQuestionType().intValue() == 104)
                    .collect(Collectors.toList());
            return diagnosisMainsympQuestionList;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("mainSympCode", mainSympCode);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion.queryAutoQuestion", params);
        List<DiagnosisMainsympQuestion> dmQuestions = new ArrayList<>();
        dmQuestions = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympQuestion.class);
        return dmQuestions;
    }

    /**
     * 查询主症状下所有疾病
     *
     * @param mainSympCode
     * @return
     */
    public List<DiagnosisMainsympQuestion> listDiseaseQuestion(String mainSympCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("mainSympCode", mainSympCode);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion.queryDiseaseQuestion", params);
        List<DiagnosisMainsympQuestion> dmQuestions = new ArrayList<>();
        dmQuestions = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympQuestion.class);
        return dmQuestions;
    }

    /**
     * 查询主症状下疾病下的所有问题，并计算答案数量
     *
     * @param mainSympCode
     * @return
     */
    public List<DiagnosisMainsympQuestion> listAnswerCount(String mainSympCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("mainSympCode", mainSympCode);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion.queryAnswerCount", params);
        List<DiagnosisMainsympQuestion> dmQuestions = new ArrayList<>();
        dmQuestions = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympQuestion.class);
        return dmQuestions;
    }

    @Override
	public List<DiagnosisMainsympQuestion> listConcSymptomCount(String mainSympCode) {
        List<DiagnosisMainsympQuestion> dmQuestions = new ArrayList<>();
        try {
            long startTime = System.currentTimeMillis();
            String key = GlobalConstants.REDIS_KEY_MAIN_COCN_PREFIX + mainSympCode;
            List<Object> objectList = RedisMrg.getInstance(redisIp, redisPort, redisPwd).lrange(key, RedisMrg.DB1);
            for(Object obj : objectList) {
                DiagnosisMainsympQuestion dmq = (DiagnosisMainsympQuestion) obj;
                dmQuestions.add(dmq);
            }
            DateUtils.printMonitorLog(startTime, "从redis获取伴随症状");
        } catch (Exception e) {
            logger.error("从redis获取伴随症状出现异常", e);
        }
        if(CollectionUtils.isEmpty(dmQuestions)) {
            Map<String, Object> params = new HashMap<>();
            params.put("mainSympCode", mainSympCode);
            List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion.queryConcSymptomCount", params);
            dmQuestions = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympQuestion.class);
        }
        return dmQuestions;
	}
    
    /**
     * 查询主症状的单个问题
     *
     * @param questionCode
     * @return
     */
    @Override
    public DiagnosisMainsympQuestion getDiagnosisMainsympQuestion(String questionCode, String mainSympCode) {
        DiagnosisMainsympQuestion dmQuestions = new DiagnosisMainsympQuestion();
        Map<String, Object> params = new HashMap<>();
        params.put("questionCode", questionCode);
        params.put("mainSympCode", mainSympCode);
        dmQuestions = super.selectOne("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion.getQuestionByCode", params);
//        DataRecord dataRecord = super.selectOne("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion.getQuestionByCode", params);
//        if (dataRecord == null)
//            return null;
//
//        JavaBeanMap.convertMapToJavaBean(dataRecord, dmQuestions);
        return dmQuestions;
    }

}
