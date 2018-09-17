package com.bzh.sportrecord.test;

import android.content.Context;
import android.util.Log;

public class Person {

    private Context context;
    private int sign;

    public Person(Context context){
        this.context = context;
    }

    public void say(){
        System.out.println("你好啊  我是调用了方法的");
        System.out.println(context);
    }


}
