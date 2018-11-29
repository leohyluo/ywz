package com.alpha.user.pojo.vo;

import java.util.List;

/**
 * Created by HP on 2018/5/15.
 */
public class ResultVo {

    List<CountShowVO> allresult; //综合统计
    List<CountShowVO> docDetail; //
    List<CountShowVO> importDetail;
    List<CountShowVO> editDetail;

    public List<CountShowVO> getDocDetail() {
        return docDetail;
    }

    public void setDocDetail(List<CountShowVO> docDetail) {
        this.docDetail = docDetail;
    }

    public List<CountShowVO> getImportDetail() {
        return importDetail;
    }

    public void setImportDetail(List<CountShowVO> importDetail) {
        this.importDetail = importDetail;
    }

    public List<CountShowVO> getEditDetail() {
        return editDetail;
    }

    public void setEditDetail(List<CountShowVO> editDetail) {
        this.editDetail = editDetail;
    }

    public List<CountShowVO> getAllresult() {
        return allresult;
    }

    public void setAllresult(List<CountShowVO> allresult) {
        this.allresult = allresult;
    }










}
