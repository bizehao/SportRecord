package com.bzh.sportrecord.module.login.loginInRegister;

import com.bzh.sportrecord.api.RetrofitHelper;
import com.bzh.sportrecord.model.ApiRegister;
import java.util.Map;
import javax.inject.Inject;
import io.reactivex.Observable;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View mView;

    @Inject
    RetrofitHelper retrofitHelper;

    @Inject
    public RegisterPresenter() {
    }

    @Override
    public void register() {
        Map<String, String>  map = mView.getAllUserInfo();
        Observable<ApiRegister> observable = retrofitHelper.getServer().register(map);
        retrofitHelper.successHandler(observable, new RetrofitHelper.callBack() {
            @Override
            public <T> void run(T t) {
                ApiRegister apiRegister = (ApiRegister) t;
                if(apiRegister.getData() == 1){
                    mView.showNormal();
                }else if(apiRegister.getData() == 0){
                    mView.showError();
                }else {
                    mView.showErrorMsg("未知错误，请稍后再试");
                }
            }
        });
    }

    @Override
    public void takeView(RegisterContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
