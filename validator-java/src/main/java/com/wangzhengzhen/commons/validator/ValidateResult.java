package com.wangzhengzhen.commons.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class ValidateResult {

    private Field field;

    private Annotation annot;

    private String failCode;

    private String failDesc;

    private boolean isSuccess;

    private Map<Field, List<Validator>> failValidators;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Annotation getAnnot() {
        return annot;
    }

    public void setAnnot(Annotation annot) {
        this.annot = annot;
    }

    public String getFailCode() {
        return failCode;
    }

    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }

    public String getFailDesc() {
        return failDesc;
    }

    public void setFailDesc(String failDesc) {
        this.failDesc = failDesc;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Map<Field, List<Validator>> getFailValidators() {
        return failValidators;
    }

    public void setFailValidators(Map<Field, List<Validator>> failValidators) {
        this.failValidators = failValidators;
    }

}
