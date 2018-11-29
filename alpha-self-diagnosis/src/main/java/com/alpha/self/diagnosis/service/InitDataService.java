package com.alpha.self.diagnosis.service;

/**
 * Created by MR.Wu on 2018-04-09.
 */
public interface InitDataService {

    void getAllDiagnosisDisease();

    void cacheMainsymps();

    void getConcSymptom();

    void cacheOpenDepartment();

    void cacheSysConfig();

    void cachePreQuestionAnswer();
}
