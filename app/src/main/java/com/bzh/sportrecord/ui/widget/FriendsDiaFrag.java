package com.bzh.sportrecord.ui.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bzh.sportrecord.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 毕泽浩
 * @Description: 弹框
 * @time 2018/9/22 18:41
 */
public class FriendsDiaFrag extends DialogFragment {

    Unbinder unbinder;
    @BindView(R.id.addfriends_close)
    ImageButton imageButton;

    public static FriendsDiaFrag newInstance() {
        return new FriendsDiaFrag();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.bran_online_supervise_dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_addfriends, container, false);
        unbinder = ButterKnife.bind(this, view);
        imageButton.setOnClickListener(new View.OnClickListener() { // 关闭弹框
            @Override
            public void onClick(View v) {
                onDestroyView();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
