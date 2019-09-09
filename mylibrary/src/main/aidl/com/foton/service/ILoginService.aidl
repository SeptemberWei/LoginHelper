// ILoginService.aidl
package com.foton.service;

// Declare any non-default types here with import statements
import com.foton.service.UserBean;
interface ILoginService {
    UserBean getUser();
    boolean isLogin();
    boolean setUser(inout UserBean userBean);
}
