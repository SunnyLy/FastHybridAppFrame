package com.sunny.fhaf.model.login;


import com.sunny.baselibs.utils.ACache;
import com.sunny.fhaf.FHAFApplication;

public class LoginCache {
    private final static String fileName = "logincache";
    private static LoginCache instance = null;
    private ACache mAcache = null;

    public LoginCache() {
        if (FHAFApplication.getContext() != null) {
            mAcache = ACache.get(FHAFApplication.getContext(), fileName);
        }
    }

    public static LoginCache getInstance() {
        if (instance == null) {
            synchronized (LoginCache.class) {
                if (instance == null) {
                    instance = new LoginCache();
                }
            }
        }
        return instance;
    }

    public void save(String username, String password) {
        if (mAcache != null) {
            mAcache.put(this.getClass().getSimpleName(), new LoginInfo(username, password));
        } else {
            if (FHAFApplication.getContext() != null) {
                mAcache = ACache.get(FHAFApplication.getContext(), fileName);
                mAcache.put(this.getClass().getName(), new LoginInfo(username, password));
            }
        }
    }

    public LoginInfo get() {
        if (mAcache == null)
            return null;
        return (LoginInfo) mAcache.getAsObject(this.getClass().getSimpleName());
    }
}
