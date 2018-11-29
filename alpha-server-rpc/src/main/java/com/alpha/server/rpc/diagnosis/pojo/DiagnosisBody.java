package com.alpha.server.rpc.diagnosis.pojo;

public class DiagnosisBody {


    /**
     * id
     */
    private Long id;

    /**
     * 部位编码
     */
    private String bodyCode;

    /**
     * 部位名称
     */
    private String bodyName;

    /**
     * 拼音简码
     */
    private String symbol;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }


    public void setBodyCode(String bodyCode) {
        this.bodyCode = bodyCode;
    }

    public String getBodyCode() {
        return this.bodyCode;
    }


    public void setBodyName(String bodyName) {
        this.bodyName = bodyName;
    }

    public String getBodyName() {
        return this.bodyName;
    }


    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }


}
