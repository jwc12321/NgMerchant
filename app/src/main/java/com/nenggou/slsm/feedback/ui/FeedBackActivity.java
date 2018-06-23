package com.nenggou.slsm.feedback.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bankcard.ui.PutForwardActivity;
import com.nenggou.slsm.feedback.DaggerFeedbackComponent;
import com.nenggou.slsm.feedback.FeedbackContract;
import com.nenggou.slsm.feedback.FeedbackModule;
import com.nenggou.slsm.feedback.presenter.FeedbackPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by JWC on 2018/6/23.
 */

public class FeedBackActivity extends BaseActivity implements FeedbackContract.FeedbackView{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.text_number)
    TextView textNumber;
    @BindView(R.id.evaluate_et)
    EditText evaluateEt;
    @BindView(R.id.confirm_bt)
    Button confirmBt;

    @Inject
    FeedbackPresenter feedbackPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, FeedBackActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        setHeight(back,title,null);
    }

    @OnTextChanged(R.id.evaluate_et)
    public void onTextChange(Editable editable) {
        int length = editable.toString().length();
        textNumber.setText(length + "");
        textNumber.setTextColor(length > 80 ? getResources().getColor(R.color.appText11) : getResources().getColor(R.color.appText2));
        if(length>0){
            confirmBt.setEnabled(true);
        }else {
            confirmBt.setEnabled(false);
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back,R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_bt:
                feedbackPresenter.subFeedBack(evaluateEt.getText().toString());
                break;
            default:
        }
    }

    @Override
    public void setPresenter(FeedbackContract.FeedbackPresenter presenter) {

    }

    @Override
    protected void initializeInjector() {
        DaggerFeedbackComponent.builder()
                .applicationComponent(getApplicationComponent())
                .feedbackModule(new FeedbackModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void subSuccess() {
        showError("提交成功");
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

}
