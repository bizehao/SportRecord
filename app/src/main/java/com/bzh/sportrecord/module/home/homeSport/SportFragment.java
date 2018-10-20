package com.bzh.sportrecord.module.home.homeSport;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;
import com.bzh.sportrecord.module.home.HomeViewModel;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class SportFragment extends BaseFragment {

    @BindView(R.id.ws_edittext)
    EditText editText;

    @BindView(R.id.ws_button)
    Button button;

    @BindView(R.id.ws_textview)
    TextView textView;

    private HomeViewModel mHomeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHomeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);

        mHomeViewModel.getmCurrentName().observe(this, s -> {
            textView.setText(s);
            Timber.d(s);
        });

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mHomeViewModel.setmCurrentName(s.toString());
            }
        });
    }

    @Override
    protected void inject() {
    }

    @OnClick(R.id.ws_button)
    public void buttonClick() {
    }

}
