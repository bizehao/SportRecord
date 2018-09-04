package com.bzh.dialoglibrary.dialogForDate.listener;

import com.djts.datalibrary.bean.DateBean;
import com.djts.datalibrary.bean.DateType;

/**
 * Created by codbking on 2016/9/22.
 */

public interface WheelPickerListener {
     void onSelect(DateType type, DateBean bean);
}
