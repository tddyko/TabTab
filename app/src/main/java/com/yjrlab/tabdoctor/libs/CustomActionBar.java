package com.yjrlab.tabdoctor.libs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yjrlab.tabdoctor.dialog.DialogDefault;
import com.yjrlab.tabdoctor.view.LoginActivity;
import com.yjrlab.tabdoctor.view.main.MainActivity;
import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.view.setting.DoubtDistributionActivity;
import com.yjrlab.tabdoctor.view.setting.DoubtRegisterActivity;
import com.yjrlab.tabdoctor.view.setting.DoubtResultActivity;
import com.yjrlab.tabdoctor.view.setting.NPorganizationActivity;
import com.yjrlab.tabdoctor.view.setting.QuestionHistoryActivity;
import com.yjrlab.tabdoctor.view.setting.QuestionHistoryDetailActivity;
import com.yjrlab.tabdoctor.view.setting.SettingActivity;


/**
 * Created by jongrakmoon on 2017. 3. 31..
 */

public class CustomActionBar extends LinearLayoutCompat implements View.OnClickListener {

    private TextView mTextViewTitle;
    private ImageView mImageViewIcon;
    private ImageView mImageViewHome;
    private Button mLogoutBtn;
    private int currentResId;
    private boolean isVisiblity;

    public CustomActionBar(Context context) {
        this(context, null);

    }

    public CustomActionBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomActionBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayout();
        getAttrs(attrs, defStyleAttr);
    }


    public void setLayout() {
        Context context = getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.view_action_bar, this, false);
        view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(view);

        mTextViewTitle = (TextView) view.findViewById(R.id.tv_title);
        mImageViewIcon = (ImageView) view.findViewById(R.id.img_icon);
        mImageViewHome = (ImageView) view.findViewById(R.id.img_home);
        mLogoutBtn     = (Button) view.findViewById(R.id.btn_logout);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomActionBar, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {

        int icon = typedArray.getResourceId(R.styleable.CustomActionBar_titleIcon, R.drawable.icon_list);
        boolean iconVisible = typedArray.getBoolean(R.styleable.CustomActionBar_iconVisible, false);
        boolean logoutVisible = typedArray.getBoolean(R.styleable.CustomActionBar_logoutVisible, true);
        float textSize = typedArray.getDimension(R.styleable.CustomActionBar_textSize, mTextViewTitle.getTextSize());
        boolean homeVisible = typedArray.getBoolean(R.styleable.CustomActionBar_homeVisible, true);

        setIcon(icon);
        setIconVisible(iconVisible);
        setLogOutVisible(logoutVisible);
        setHomeVisible(homeVisible);
        setTitleTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        setOnIconClickListener(this, icon);
        setOnLogoutClickListener(this);
        setOnHomeClickListener(this);
        typedArray.recycle();
    }

    public void setLogOutVisible(boolean LogoutVisible) {
        mLogoutBtn.setVisibility(LogoutVisible ? VISIBLE : GONE);
    }

    public void setIconVisible(boolean iconVisible) {
        isVisiblity = iconVisible;

        mImageViewIcon.setVisibility(iconVisible ? VISIBLE : GONE);
    }

    public void setHomeVisible(boolean homeVisible) {
        mImageViewHome.setVisibility(homeVisible ? VISIBLE : INVISIBLE);
    }


    public void setTitleTextSize(float size) {
        mTextViewTitle.setTextSize(size);
    }

    public void setTitleTextSize(int unit, float size) {
        mTextViewTitle.setTextSize(unit, size);
    }

    public void setIcon(int res) {
        mImageViewIcon.setImageResource(res);
    }

    public void setIcon(Bitmap bitmap) {
        mImageViewIcon.setImageBitmap(bitmap);
    }

    public void setOnIconClickListener(OnClickListener listener, int resId) {
        this.currentResId = resId;
        mImageViewIcon.setOnClickListener(listener);
    }

    public void setOnLogoutClickListener (OnClickListener listener) {
        mLogoutBtn.setOnClickListener(listener);
    }
    public void setOnHomeClickListener(OnClickListener listener) {
        mImageViewHome.setOnClickListener(listener);
    }

    public void setOnTitleClickListener(OnClickListener listener) {
        mTextViewTitle.setOnClickListener(listener);
    }


    @Override
    public void onClick(View v) {
        final Context context = getContext();

        switch (v.getId()) {
            case R.id.img_icon:
                if (isVisiblity) {

                    if (currentResId == R.drawable.icon_list) {
                        context.startActivity(new Intent(context, SettingActivity.class));
                        }
                    }
                break;
            case R.id.btn_logout:
                if(context instanceof Activity) {
                    DialogDefault.showDlg(getContext(), "로그아웃 하시겠습니까?", "예", "아니오", new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PreferenceUtils.removePrefrence(context);
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            ((Activity) context).finishAffinity();
                        }
                    }, null);
                }
                break;
            case R.id.img_home:
                Intent intent = new Intent(context, MainActivity.class);
                if (context instanceof SettingActivity) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                } else if (context instanceof DoubtDistributionActivity
                        | context instanceof DoubtRegisterActivity
                        | context instanceof DoubtResultActivity
                        | context instanceof NPorganizationActivity
                        | context instanceof QuestionHistoryActivity
                        | context instanceof QuestionHistoryDetailActivity) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                } else {
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);

                }
                break;
        }

    }
}
