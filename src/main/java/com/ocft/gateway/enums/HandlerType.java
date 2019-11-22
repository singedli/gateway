package com.ocft.gateway.enums;

/**
 * @author lijiaxing
 * @Title: HandlerType
 * @ProjectName gateway
 * @date 2019/11/22上午11:51
 * @Description: TODO
 */
public enum HandlerType {
    COMPLICATE("复杂类型"),
    CONCURRENT("并发"),
    PASS("透传");

    private String decrption;

    public String getDecrption() {
        return decrption;
    }

    public void setDecrption(String decrption) {
        this.decrption = decrption;
    }

    private HandlerType(String descrption) {
        this.decrption = descrption;
    }

    @Override
    public String toString() {
        return this.name() + "_" + this.decrption;
    }
}
