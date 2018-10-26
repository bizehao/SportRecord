package com.bzh.sportrecord.module.home.homePlan;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.bzh.apilibrary.badge.BGABadgeTextView;
import com.bzh.apilibrary.badge.BGABadgeable;
import com.bzh.apilibrary.badge.BGADragDismissDelegate;
import com.bzh.chatkit.commons.models.IDialog;
import com.bzh.chatkit.dialogs.DialogsListAdapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/24 15:39
 */
public class SubDialogViewHolder extends DialogsListAdapter.DialogViewHolder {

    public SubDialogViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void setBadge(IDialog dialog) {
        super.setBadge(dialog);
        if(tvBubble != null && tvBubble.getClass().equals(BGABadgeTextView.class)){
            ((BGABadgeTextView) tvBubble).setDragDismissDelegate(new BGADragDismissDelegate() {
                @Override
                public void onDismiss(BGABadgeable badgeable) {
                    onDragDismissDelegate.onDragClick(dialog);
                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((BGABadgeTextView) tvBubble).showTextBadge(String.valueOf(dialog.getUnreadCount()));
                }
            }, 500);

            tvBubble.setVisibility(dialog.getUnreadCount() > 0 ? VISIBLE : GONE);

        }
    }

}
