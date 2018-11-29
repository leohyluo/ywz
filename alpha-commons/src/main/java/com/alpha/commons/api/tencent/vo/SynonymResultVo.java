/**
  * Copyright 2017 bejson.com 
  */
package com.alpha.commons.api.tencent.vo;
import java.util.List;

/**
 * Auto-generated: 2017-09-18 18:35:17
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class SynonymResultVo {

    private String codeDesc;
    private int code;
    private String query;
    private String message;
    private List<Syns> syns;
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

    public void setQuery(String query) {
         this.query = query;
     }
     public String getQuery() {
         return query;
     }

    public void setMessage(String message) {
         this.message = message;
     }
     public String getMessage() {
         return message;
     }

    public void setSyns(List<Syns> syns) {
         this.syns = syns;
     }
     public List<Syns> getSyns() {
         return syns;
     }

}