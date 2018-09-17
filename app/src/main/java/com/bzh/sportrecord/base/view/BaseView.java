package com.bzh.sportrecord.base.view;

/**
 * view的定义
 */
public interface BaseView {

    /**
     *  显示错误消失
     * @param errorMsg 错误消失
     */
    void showErrorMsg(String errorMsg);

    /**
     * 显示正常
     */
    void showNormal();

    /**
     * 显示错误
     */
    void showError();

    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 结束加载
     */
    void shutDownLoading();

}
