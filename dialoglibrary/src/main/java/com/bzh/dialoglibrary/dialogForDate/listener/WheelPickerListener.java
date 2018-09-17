package com.bzh.dialoglibrary.dialogForDate.listener;

import com.bzh.dialoglibrary.dialogForDate.bean.DateBean;
import com.bzh.dialoglibrary.dialogForDate.bean.DateType;


/**
 * Created by codbking on 2016/9/22.
 */

public interface WheelPickerListener {
     void onSelect(DateType type, DateBean bean);
}
