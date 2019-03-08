package com.bzh.sportrecord.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author 毕泽浩
 * @Description: 俯卧撑计划表
 * @time 2018/10/31 13:24
 */
@Entity
public class PushUpInfo {
    @PrimaryKey
    private int id;
    private int level; //等级
    private String group; //分组数量
    private int count; //总数量
}
