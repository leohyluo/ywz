package com.alpha.self.diagnosis.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.self.diagnosis.dao.UserDiagnosisDetailDao;
import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xc.xiong on 2017/9/5.
 */
@Repository
public class UserDiagnosisDetailDaoImpl extends BaseDao<UserDiagnosisDetail, Long> implements UserDiagnosisDetailDao {


    @Autowired
    public UserDiagnosisDetailDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Class<UserDiagnosisDetail> getClz() {
        return UserDiagnosisDetail.class;
    }

    /**
     * 查询问题
     *
     * @param diagnosisId
     * @param questionCode
     * @return
     */
    public UserDiagnosisDetail getUserDiagnosisDetail(Long diagnosisId, String questionCode) {

        Map<String, Object> params = new HashMap<>();
        params.put("diagnosisId", diagnosisId);
        params.put("questionCode", questionCode);
        DataRecord data = super.selectForDataRecord("com.alpha.server.rpc.user.pojo.UserDiagnosisDetail.getByQuestionCode", params);
        if (data == null)
            return null;
        UserDiagnosisDetail udd = new UserDiagnosisDetail();
        JavaBeanMap.convertMapToJavaBean(data, udd);
        return udd;
    }

    /**
     * 查询问题
     *
     * @param diagnosisId
     * @return
     */
    public List<UserDiagnosisDetail> listUserDiagnosisDetail(Long diagnosisId) {

        Map<String, Object> params = new HashMap<>();
        params.put("diagnosisId", diagnosisId);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.user.pojo.UserDiagnosisDetail.queryByDiagnosisId", params);
        List<UserDiagnosisDetail> udds = JavaBeanMap.convertListToJavaBean(datas, UserDiagnosisDetail.class);
        return udds;
    }

    @Override
    public List<UserDiagnosisDetail> listAllUserDiagnosisDetail(Long diagnosisId) {
        Map<String, Object> params = new HashMap<>();
        params.put("diagnosisId", diagnosisId);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.user.pojo.UserDiagnosisDetail.queryAllByDiagnosisId", params);
        List<UserDiagnosisDetail> udds = JavaBeanMap.convertListToJavaBean(datas, UserDiagnosisDetail.class);
        return udds;
    }

    @Override
	public List<UserDiagnosisDetail> listUserDiagnosisDetail(Long diagnosisId, String sympCode) {
		Map<String, Object> params = new HashMap<>();
        params.put("diagnosisId", diagnosisId);
        params.put("sympCode", sympCode);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.user.pojo.UserDiagnosisDetail.queryBySympCode", params);
        List<UserDiagnosisDetail> udds = JavaBeanMap.convertListToJavaBean(datas, UserDiagnosisDetail.class);
        return udds;
	}

	@Override
	public void deleteUserDiagnosisDetail(Long diagnosisId, String sympCode) {
		Map<String, Object> params = new HashMap<>();
        params.put("diagnosisId", diagnosisId);
        params.put("sympCode", sympCode);
        super.deleteByStatement("com.alpha.server.rpc.user.pojo.UserDiagnosisDetail.delBySympcode", params);
	}

    @Override
    public void deleteAdditonalUserDiagnosisDetail(Long diagnosisId) {
        Map<String, Object> params = new HashMap<>();
        params.put("diagnosisId", diagnosisId);
        super.deleteByStatement("com.alpha.server.rpc.user.pojo.UserDiagnosisDetail.deleteAdditonalUserDiagnosisDetail", params);
    }

    @Override
    public void deleteAdditonalUserDiagnosisDetailById(Long diagnosisId, Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("diagnosisId", diagnosisId);
        params.put("id", id);
        super.deleteByStatement("com.alpha.server.rpc.user.pojo.UserDiagnosisDetail.deleteAdditonalUserDiagnosisDetailById", params);
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<UserDiagnosisDetail> listUserDiagnosisDetail(Long diagnosisId, List<String> questionCodeList) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<>();
        params.put("diagnosisId", diagnosisId);
        params.put("questionCodeList", questionCodeList);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.user.pojo.UserDiagnosisDetail.queryByQuestionCodeList", params);
        List<UserDiagnosisDetail> udds = JavaBeanMap.convertListToJavaBean(datas, UserDiagnosisDetail.class);
		return udds;
	}


}
