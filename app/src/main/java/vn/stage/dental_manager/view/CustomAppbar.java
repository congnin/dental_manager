package vn.stage.dental_manager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.stage.dental_manager.R;

/**
 * Created by congn on 9/11/2017.
 */

public class CustomAppbar extends RelativeLayout implements View.OnClickListener {
    private View rootView;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvSubtitle;
    private ImageView ivIconRight;

    private OnAppbarCallback callback;

    public CustomAppbar(Context context) {
        super(context);
        init(context);
    }

    public CustomAppbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomAppbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.view_custom_app_bar, this);
        ivBack = rootView.findViewById(R.id.iv_back);
        tvTitle = rootView.findViewById(R.id.tv_title_bar);
        tvSubtitle = rootView.findViewById(R.id.tv_subtitle_bar);
        ivIconRight = rootView.findViewById(R.id.iv_log_out);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setSubtitle(String subtitle) {
        tvSubtitle.setText(subtitle);
        tvSubtitle.setVisibility(VISIBLE);
    }

    public void setIconBack(int icon) {
        ivBack.setImageResource(icon);
        ivBack.setVisibility(VISIBLE);
        ivBack.setOnClickListener(this);
    }

    public void addIconRight() {
        ivIconRight.setVisibility(VISIBLE);
        ivIconRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (callback != null) {
                    callback.onBackClicked();
                }
                break;
            case R.id.iv_log_out:
                if (callback != null) {
                    callback.onRightIconClicked();
                }
                break;
        }
    }

    public interface OnAppbarCallback {
        void onBackClicked();

        void onRightIconClicked();
    }

    public void setCallback(OnAppbarCallback callback) {
        this.callback = callback;
    }
}
