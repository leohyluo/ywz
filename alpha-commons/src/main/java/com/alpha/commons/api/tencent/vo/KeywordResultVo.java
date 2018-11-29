/**
  * Copyright 2017 bejson.com 
  */
package com.alpha.commons.api.tencent.vo;
import java.util.List;

/**
 * Auto-generated: 2017-09-18 18:20:36
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class KeywordResultVo {

    private String codeDesc;
    private int code;
    private List<Keywords> keywords;
    private String message;
    public void setCodeDesc(String codeDesc) {
         this.codeDesc = codeDesc;
     }
     public String getCodeDesc() {
         return codeDesc;
     }

    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setKeywords(List<Keywords> keywords) {
         this.keywords = keywords;
     }
     public List<Keywords> getKeywords() {
         return keywords;
     }

    public void setMessage(String message) {
         this.message = message;
     }
     public String getMessage() {
         return message;
     }

}