package com.lf.tempcore.tempViews;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.lf.tempcore.R;
import com.rey.material.widget.ProgressView;


public class TempCustomProgressDialog extends Dialog {
    ProgressView mProgressView;
    public TempCustomProgressDialog(Context context, String strMessage) {
        this(context, R.style.CustomProgressDialog, strMessage);
    }
    public TempCustomProgressDialog(Context context, int theme , String strMessage) {
        super(context, theme);
        this.setContentView(R.layout.temp_custom_progress_dialog_layout);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        mProgressView = (ProgressView) this.findViewById(R.id.loadingImageView);

        TextView tvMsg = (TextView) this.findViewById(R.id.id_tv_loadingmsg);
        if (tvMsg != null) {  
            tvMsg.setText(strMessage);  
        }


    }  

    @Override  
    public void onWindowFocusChanged(boolean hasFocus) {  
  
        if (!hasFocus) {
//            Debug.info("mProgressView stop()");
//            if (mProgressView!=null){
//                mProgressView.stop();
//                Debug.info("mProgressView stop()");
//            }
            dismiss();
        }else{
//            if (mProgressView!=null){
//                mProgressView.start();
//                Debug.info("mProgressView start()");
//            }
        }

    }  
}  