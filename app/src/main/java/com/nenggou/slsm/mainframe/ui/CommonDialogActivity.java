package com.nenggou.slsm.mainframe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/9/5.
 */

public class CommonDialogActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.negative)
    Button negative;
    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.item_common_dialog)
    RelativeLayout itemCommonDialog;

    private String titleData;
    private String contentData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_dialog);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleData=getIntent().getStringExtra(StaticData.TITLE_DATA);
        contentData=getIntent().getStringExtra(StaticData.CONTENT_DATA);
        title.setText(titleData);
        content.setText(contentData);
    }

    @OnClick({R.id.item_common_dialog, R.id.confirm,R.id.negative})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_common_dialog:
                finish();
                break;
            case R.id.confirm:
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.negative:
                finish();
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
