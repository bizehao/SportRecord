package com.bzh.sportrecord.base.presenter;

import com.bzh.sportrecord.base.view.BaseView;

import io.reactivex.disposables.Disposable;

public interface BasePresenter<T extends BaseView> {
    /**
     * Binds presenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this presenter
     */
    void takeView(T view);

    /**
     * Drops the reference to the view when destroyed
     */
    void dropView();
}
