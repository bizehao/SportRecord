package com.bzh.sportrecord.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.api.RetrofitService;
import com.bzh.sportrecord.model.Book;
import com.google.gson.GsonBuilder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity {

    Button button;
    AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        button = findViewById(R.id.test_button);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.douban.com/v2/")
                        .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持RxJava
                        .build();
                RetrofitService service = retrofit.create(RetrofitService.class);
                Observable<Book> observable = service.getSearchBook("金瓶梅", null, 0, 1);
                /*call.enqueue(new Callback<Book>() {
                    @Override
                    public void onResponse(Call<Book> call, Response<Book> response) {
                        System.out.println(response.body());
                        autoCompleteTextView.setText(response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Book> call, Throwable t) {
                    }
                });*/

                observable.subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
                        .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更显UI
                        .subscribe(new Observer<Book>() {

                            @Override
                            public void onError(Throwable e) {
                                e.getStackTrace();
                                System.out.println("00000000000000000000");
                            }

                            @Override
                            public void onComplete() {
                                System.out.println("11111111111111111111111");
                            }

                            @Override
                            public void onSubscribe(Disposable d) {
                                System.out.println("22222222222222222222222");
                            }

                            @Override
                            public void onNext(Book book) {
                                System.out.println(book.toString());
                                autoCompleteTextView.setText(book.toString());
                            }//订阅


                        });

            }
        });
    }
}
