package com.bzh.sportrecord.base.presenter;

import com.bzh.sportrecord.base.view.BaseView;

import io.reactivex.disposables.Disposable;

public interface BasePresenter<T extends BaseView> {
    /**
     * 注入View
     *
     * @param view view
     */
    void attachView(T view);

    /**
     * 回收View
     */
    void detachView();

    /**
     * Add rxBing subscribe manager
     *
     * @param disposable Disposable
     */
    void addRxBindingSubscribe(Disposable disposable);

    /**
     * 获取当前页
     *
     * @return current page
     */
    int getCurrentPage();
}
