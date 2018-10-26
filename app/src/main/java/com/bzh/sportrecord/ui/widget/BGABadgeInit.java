package com.bzh.sportrecord.ui.widget;

import android.widget.ImageView;
import android.widget.TextView;

import com.bzh.annotationlib.badge.BGABadge;

/**
 * @author 毕泽浩
 * @Description: 徽章
 * @time 2018/10/23 22:47
 */
@BGABadge({
        ImageView.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeImageView，不想用这个类的话就删了这一行
        TextView.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeFloatingTextView，不想用这个类的话就删了这一行
})
public class BGABadgeInit {
}
