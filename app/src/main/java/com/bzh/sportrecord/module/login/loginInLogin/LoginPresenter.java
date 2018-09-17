package com.bzh.sportrecord.module.login.loginInLogin;

import android.content.Context;

import com.bzh.sportrecord.api.DataManager;
import com.bzh.sportrecord.model.Book;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/17 11:00
 */
public class LoginPresenter implements LoginContract.Presenter {

    private Context mContext;

    private LoginContract.View mView;

    public LoginPresenter(Context context, LoginContract.View view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void attachView(LoginContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void addRxBindingSubscribe(Disposable disposable) {

    }

    @Override
    public int getCurrentPage() {
        return 0;
    }

    @Override
    public void login() {
        mView.showLoading();
        String username = mView.getUsername();
        String password = mView.getPassword();
        System.out.println(username);
        System.out.println(password);
        DataManager dataManager = new DataManager();
        Observable<JSONObject> observable = dataManager.login(username, password);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("订阅之前");
                    }

                    @Override
                    public void onNext(JSONObject response) {
                        System.out.println(response);
                        System.out.println("下一个");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.getMessage());
                        System.out.println("发生错误");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("完全结束");
                    }
                });
        mView.shutDownLoading();
    }
}
