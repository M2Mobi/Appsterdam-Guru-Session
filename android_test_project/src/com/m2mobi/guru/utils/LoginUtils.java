package com.m2mobi.guru.utils;

/**
 * Created by m2mobi on 2/6/14.
 */
public class LoginUtils {

	private static final String LOG_TAG = "LoginUtils";

    public static boolean isValidUsername(final String pUsername) {
        if (pUsername.length() < 3) {
            return false;
        }

        return true;
    }

    public static boolean isValidPassword(final String pPassword) {
        if (pPassword.length() < 3) {
            return false;
        }

        return true;
    }


    public static boolean isValidLogin(final String pUsername, final String pPassword) {

        if(!pUsername.equals("guru")){
            return false;
        }

        if(!pPassword.equals("secret")){
            return false;
        }

        return true;
	}



}
