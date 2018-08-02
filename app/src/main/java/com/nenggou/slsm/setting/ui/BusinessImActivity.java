package com.nenggou.slsm.setting.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.GlideHelper;
import com.nenggou.slsm.common.unit.FormatUtil;
import com.nenggou.slsm.common.unit.PersionAppPreferences;
import com.nenggou.slsm.common.widget.customeview.ActionSheet;
import com.nenggou.slsm.common.widget.pickphoto.beans.ImgBean;
import com.nenggou.slsm.data.entity.PersionInfoResponse;
import com.nenggou.slsm.setting.DaggerSettingComponent;
import com.nenggou.slsm.setting.SettingContract;
import com.nenggou.slsm.setting.SettingModule;
import com.nenggou.slsm.setting.presenter.ChangeAvatarPresenter;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/25.
 */

public class BusinessImActivity extends BaseActivity implements SettingContract.ChangeAvatarView, ActionSheet.OnPictureChoseListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.head_photo)
    RoundedImageView headPhoto;
    @BindView(R.id.item_head_photo)
    RelativeLayout itemHeadPhoto;
    @BindView(R.id.business_name)
    TextView businessName;
    @BindView(R.id.service_type)
    TextView serviceType;
    @BindView(R.id.ut_date)
    TextView utDate;
    @BindView(R.id.item_business_im)
    LinearLayout itemBusinessIm;

    private PersionAppPreferences persionAppPreferences;
    private String persionInfoStr;
    private Gson gson;
    private PersionInfoResponse persionInfoResponse;

    private ActionSheet actionSheet;
    private String headPhoneStr;
    @Inject
    ChangeAvatarPresenter changeAvatarPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, BusinessImActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_im);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        persionAppPreferences = new PersionAppPreferences(this);
        persionInfoStr = persionAppPreferences.getPersionInfo();
        gson = new Gson();
        if (!TextUtils.isEmpty(persionInfoStr)) {
            persionInfoResponse = gson.fromJson(persionInfoStr, PersionInfoResponse.class);
            GlideHelper.load(this, persionInfoResponse.getAvatar(), R.mipmap.default_head_image_icon, headPhoto);
            businessName.setText(persionInfoResponse.getName());
            serviceType.setText(persionInfoResponse.getCname());
            utDate.setText(FormatUtil.formatDateByLine(persionInfoResponse.getVerified_at()));
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return itemBusinessIm;
    }

    @OnClick({R.id.back, R.id.item_head_photo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.item_head_photo:
                if (actionSheet == null) {
                    actionSheet = ActionSheet.newInstance(false, headPhoto.getWidth(), headPhoto.getHeight());
                    actionSheet.setOnPictureChoseListener(BusinessImActivity.this);
                }
                actionSheet.setMax(1, "1");
                actionSheet.show(this);
                break;
            default:
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerSettingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .settingModule(new SettingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onPictureChose(File filePath) {
        actionSheet.dismiss();
        headPhoneStr = filePath.getAbsolutePath();
        showLoading();
        changeAvatarPresenter.uploadFile(headPhoneStr);
    }

    @Override
    public void onPhotoResult(List<ImgBean> selectedImgs) {

    }


    @Override
    public void setPresenter(SettingContract.ChangeAvatarPresenter presenter) {

    }

    @Override
    public void changeAvataSuccess() {
        dismissLoading();
        showMessage("头像修改成功");
        GlideHelper.load(this, headPhoneStr, R.mipmap.default_head_image_icon, headPhoto);
        persionInfoResponse.setAvatar(headPhoneStr);
        persionInfoStr = gson.toJson(persionInfoResponse);
        persionAppPreferences.setPersionInfo(persionInfoStr);
    }

    @Override
    public void uploadFileSuccess(String photoUrl) {
        headPhoneStr = photoUrl;
        changeAvatarPresenter.changeAvata(photoUrl);
    }
}
