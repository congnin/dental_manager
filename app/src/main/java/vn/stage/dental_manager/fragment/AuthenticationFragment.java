package vn.stage.dental_manager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import vn.stage.dental_manager.R;
import vn.stage.dental_manager.data.ServiceGenerator;
import vn.stage.dental_manager.data.model.AccessModel;
import vn.stage.dental_manager.data.model.ResponseModel;
import vn.stage.dental_manager.utils.KeyboardUtils;
import vn.stage.dental_manager.view.CustomAppbar;

/**
 * Created by congn on 9/11/2017.
 */

public class AuthenticationFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "AuthenticationFragment";
    private CustomAppbar customAppbar;
    private EditText tvAccount;
    private EditText tvPassword;
    private CheckBox cbRemember;
    private Button btLogin;
    private ImageView ivBackground;
    private RelativeLayout pgLoading;
    Animation shakeAnimation;

    private String username = "";
    private String password = "";

    private Subscription subscription;

    public static AuthenticationFragment newInstance() {
        Bundle args = new Bundle();
        AuthenticationFragment fragment = new AuthenticationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authentication, container, false);
        customAppbar = view.findViewById(R.id.custom_app_bar);
        tvAccount = view.findViewById(R.id.tv_account);
        tvPassword = view.findViewById(R.id.tv_password);
        cbRemember = view.findViewById(R.id.cb_remember);
        ivBackground = view.findViewById(R.id.iv_background);
        btLogin = view.findViewById(R.id.bt_login);
        pgLoading = view.findViewById(R.id.progLoading);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        customAppbar.setTitle(getString(R.string.title_login));
        customAppbar.setSubtitle(getString(R.string.subtitle_login));

        tvAccount.setHint(getString(R.string.account));
        tvAccount.addTextChangedListener(new GenericTextWatcher(tvAccount));
        tvPassword.setHint(getString(R.string.password));
        tvPassword.addTextChangedListener(new GenericTextWatcher(tvPassword));

        Glide.with(getActivity())
                .load(R.drawable.waitingroom)
                .placeholder(R.color.light_gray)
                .error(R.color.light_gray)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .dontAnimate()
                .into(ivBackground);

        btLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                KeyboardUtils.hideSoftInput(getActivity());
                if (validate()) {

                }
        }
    }

    private class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text = s.toString();
            switch (view.getId()) {
                case R.id.tv_account:
                    username = text;
                    break;
                case R.id.tv_password:
                    password = text;
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private boolean validate() {
        boolean isValidate = true;

        if (TextUtils.isEmpty(account)) {
            tvAccount.startAnimation(shakeAnimation);
            tvAccount.setError(getString(R.string.blank_field));
            isValidate = false;
        }
        if (TextUtils.isEmpty(password)) {
            tvPassword.startAnimation(shakeAnimation);
            tvPassword.setError(getString(R.string.blank_field));
            isValidate = false;
        }
        return isValidate;
    }

    private void login() {
        Log.d(TAG, "login()");

        if (subscription != null) {
            subscription.unsubscribe();
        }

        pgLoading.setVisibility(View.VISIBLE);
        subscription = Observable.just(ServiceGenerator.login(username, password))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseModel -> {
                    pgLoading.setVisibility(View.GONE);
                    if (responseModel != null) {
                        AccessModel accessModel = 
                    }
                }, error -> {
                    pgLoading.setVisibility(View.GONE);
                    Log.e(TAG, Log.getStackTraceString(error));
                    Toast.makeText(getActivity(), getString(R.string.unknown_error_network),
                            Toast.LENGTH_LONG).show();
                });

    }
}
