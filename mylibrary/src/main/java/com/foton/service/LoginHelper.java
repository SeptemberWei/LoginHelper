package com.foton.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * 登录工具
 */
public class LoginHelper {

    private static volatile LoginHelper instance;

    private ILoginService iLoginService;
    private boolean connected;

    private OnBindListener listener;

    public static final int request_code = 1;

    private LoginHelper() {
    }

    public static LoginHelper getInstance() {
        if (instance == null) {
            synchronized (LoginHelper.class) {
                if (instance == null) {
                    instance = new LoginHelper();
                }
            }
        }
        return instance;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iLoginService = ILoginService.Stub.asInterface(service);
            connected = true;
            if (listener != null) {
                listener.onBind(instance);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connected = false;
        }
    };

    public LoginHelper create(Context context) {
        return create(context, null);
    }

    public LoginHelper create(Context context, OnBindListener listener) {
        this.listener = listener;
        Intent intent = new Intent();
        intent.setPackage("com.foton.fotonpersonal");
        intent.setAction("com.foton.service.ILoginService");
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        return this;
    }

    public LoginHelper setOnBindListener(OnBindListener listener) {
        this.listener = listener;
        return this;
    }

    public UserBean getUserInfo() throws Exception {
        if (!connected) {
            throw new Exception("the service is not establish,please try again!");
        }
        return iLoginService.getUser();
    }

    public boolean isLogin() throws Exception {
        if (!connected) {
            throw new Exception("the service is not establish,please try again!");
        }

        return iLoginService.isLogin();
    }

    public boolean addUser(UserBean user) throws Exception {
        if (!connected) {
            throw new Exception("the service is not establish,please try again!");
        }
        if (user != null) {
            return iLoginService.setUser(user);
        } else {
            throw new Exception("the param user is null,please check it again!");
        }

    }

    public void startLogin(Activity activity) throws Exception {
        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.foton.fotonpersonal", "com.foton.fotonpersonal.ui.activity.login.LoginInputPhoneActivity");
        intent.setComponent(cn);
        intent.putExtra("router", "boot");
        activity.startActivityForResult(intent, request_code);
    }

    public interface OnBindListener {
        void onBind(LoginHelper helper);
    }
}
