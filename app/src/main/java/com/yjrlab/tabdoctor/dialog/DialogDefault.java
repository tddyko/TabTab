package com.yjrlab.tabdoctor.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yjrlab.tabdoctor.R;


public class DialogDefault extends DialogBase {
    private boolean mIsAutoDismiss = true;

    private TextView mTitle;
    private TextView mTitleContent;
    private TextView mMessage;
    private Button mButtonOk;
    private Button mButtonClose;

    private View.OnClickListener mOkClickListener;
    private View.OnClickListener mCloseClickListener;
    private static DismissListener mDismissListener;

    private static DialogDefault instance;

    public DialogDefault(@NonNull  Context context) {
        super(context);
    }

    private static DialogDefault getInstance(Context context) {
        instance = new DialogDefault(context);
        initDialog(instance);
        return instance;
    }

    private static DialogDefault getInstance(Activity activity) {
        instance = new DialogDefault(activity);
        initDialog(instance);
        return instance;
    }

    private static void initDialog(DialogDefault instance) {
        if (instance == null) {
            return;
        }

        if (instance.mButtonOk != null) {
            instance.mButtonOk.setVisibility(View.GONE);
            instance.mButtonOk.setOnClickListener(null);
        }

        if (mDismissListener != null) {
            mDismissListener = null;
        }

        instance.setOnDismissListener(null);

        if(instance.isShowing() == false) {
            instance.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        setContentView(R.layout.dialog_default);
        setLayout();
        setClickListener();
    }

    public static DialogDefault showDlg (Activity activity, String message, final DialogDefault.DismissListener dismissListener) {
        final DialogDefault dlg = getInstance(activity);
        mDismissListener = dismissListener;
        dlg.setMessage(message);
        dlg.mButtonClose.setVisibility(View.GONE);
        dlg.mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

        return dlg;
    }

    public static DialogDefault showDlg(Activity activity, String message, View.OnClickListener okListener, View.OnClickListener closeListener) {
        DialogDefault dlg = getInstance(activity);
        dlg.setMessage(message);
        dlg.setButtonOk(okListener);
        dlg.setButtonClose(closeListener);

        return dlg;
    }

    public static DialogDefault showDlg(Activity activity, String message, String title, View.OnClickListener okListener, View.OnClickListener closeListener) {
        DialogDefault dlg = getInstance(activity);
        dlg.setMessage(message);
        dlg.setButtonOk(okListener);
        dlg.setButtonClose(closeListener);

        return dlg;
    }

    public static DialogDefault showDlg (Context context, String message, final DialogDefault.DismissListener dismissListener) {
        final DialogDefault dlg = getInstance(context);
        mDismissListener = dismissListener;
        dlg.setMessage(message);
        dlg.mButtonClose.setVisibility(View.GONE);
        dlg.mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

        return dlg;
    }

    public static DialogDefault showDlg(Context context, String message, String btnOk, String btnClose, View.OnClickListener okListener, View.OnClickListener closeListener) {
        DialogDefault dlg = getInstance(context);
        dlg.setMessage(message);
        dlg.mTitleContent.setVisibility(View.GONE);
        dlg.setButtonOk(btnOk, okListener);
        dlg.setButtonClose(btnClose, closeListener);
        return dlg;
    }

    public static DialogDefault showDlg(Context context, String message, String title, View.OnClickListener okListener, View.OnClickListener closeListener) {
        DialogDefault dlg = getInstance(context);
        dlg.setMessage(message);
        dlg.setButtonOk(okListener);
        dlg.setButtonClose(closeListener);

        return dlg;
    }

    private void setButtonOk(View.OnClickListener okClickListener) {
        String label = mButtonOk.getText().toString();
        setButtonOk(label, okClickListener);
    }

    private void setButtonOk(String label, final View.OnClickListener okClickListener) {
        this.mOkClickListener = okClickListener;
        mButtonOk.setText(label);
        setAlertButtonClickListener(mButtonOk, okClickListener);
    }

    private void setButtonClose(View.OnClickListener closeClickListener) {
        mButtonClose.setVisibility(View.VISIBLE);
        this.mCloseClickListener = closeClickListener;
        setAlertButtonClickListener(mButtonClose, closeClickListener);
    }

    private void setButtonClose(String label, View.OnClickListener closeClickListener) {
        mButtonClose.setVisibility(View.VISIBLE);
        this.mCloseClickListener = closeClickListener;
        mButtonClose.setText(label);
        setAlertButtonClickListener(mButtonClose, closeClickListener);
    }

    private void setTitle (String title) {
        mTitle.setText(title);
    }

    private void setMessage (String message) {
        mMessage.setText(message);
    }

    private void setClickListener() {   //final View.OnClickListener noClickListener, final View.OnClickListener okClickListener) {


        mButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCloseClickListener != null) {
                    mCloseClickListener.onClick(v);

                    if (mIsAutoDismiss) {
                        DialogDefault.this.dismiss();
                    }
                } else {
                    DialogDefault.this.dismiss();
                }
            }
        });

        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOkClickListener != null) {
                    mOkClickListener.onClick(v);

                    if (mIsAutoDismiss) {
                        DialogDefault.this.dismiss();
                    }
                } else {
                    DialogDefault.this.dismiss();
                }
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mDismissListener != null) {
            mDismissListener.onDismiss();
        }
    }

    public interface DismissListener {
        void onDismiss();
    }

    private void setAlertButtonClickListener(Button button, final View.OnClickListener clickListener) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick(v);

                    if (mIsAutoDismiss) {
                        DialogDefault.this.dismiss();
                    }
                } else {
                    DialogDefault.this.dismiss();
                }
            }
        });
    }

    private void setLayout() {
        mTitle = (TextView) findViewById(R.id.alertTitle);
        mMessage = (TextView) findViewById(R.id.alertMessage);
        mTitleContent = (TextView) findViewById(R.id.titleContent);
        mButtonClose = (Button) findViewById(R.id.buttonClose);
        mButtonOk = (Button) findViewById(R.id.buttonOk);
    }
}