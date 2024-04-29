package com.cnor.expensetracker.constants;

public enum ErrorCodeConstant {

    BADCREDENTIAL("001"),
    INTERNAL_SERVER_ERROR("002"),
    FORBIDDEN("003"),
    PASSWORD("004"),
    VALIDATION("005");

    private String errorCode;
    
    ErrorCodeConstant(String errorCode){
        this.errorCode = errorCode;
    }

    public String getErrorCode(){
        return this.errorCode;
    }

}
