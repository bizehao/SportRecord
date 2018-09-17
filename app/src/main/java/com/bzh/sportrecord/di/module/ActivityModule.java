package com.bzh.sportrecord.di.module;

import android.content.Context;

import com.bzh.sportrecord.test.Person;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    @Provides
    public Person providesPerson(Context context) {
        System.out.println("ActivityModule 出现了" + context);
        return new Person(context);
    }
}
