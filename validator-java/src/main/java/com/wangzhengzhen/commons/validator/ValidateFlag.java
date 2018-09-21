package com.wangzhengzhen.commons.validator;

public enum ValidateFlag {

    CHECK(1), CHECK_ALL(2), CHECK_GROUP(3), CHECK_AND_GROUP(4), CHECK_ALL_AND_GROUP(5);

    private int flag;

    private ValidateFlag(int flag) {

        this.flag = flag;
    }
}
