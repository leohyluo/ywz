package com.alpha.commons.core.sql.enums;

public enum SQLConditionType {
    EQUAL("="),
    IN("in"),
    LIKE("like"),
    //MySQL 5.5 以上 如果搜索關鍵字是日期 需要加上 like binary
    LIKE_BINARY("like binary"),
    NOT_IN("not in"),
    GREATER_THAN(">"),
    LESS_THAH("<"),
    GREATER_THAN_OR_EQUAL(">="),
    LESS_THAN_OR_EQUAL("<="),
    NOT_EQUAL("<>"),
    NATIVE_SQL(""),
    OR("or");

    private String value;

    private SQLConditionType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
