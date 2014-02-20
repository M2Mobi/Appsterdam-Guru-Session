package com.m2mobi.guru.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.*;

/**
 * Created by m2mobi on 2/6/14.
 */
public class AssetUtils {

    private static final String LOG_TAG = "AssetUtils";

    /**
     * Method to read files from assets folder.
     *
     * @param pAssetManager AssetManager to use for fetching assets
     * @param pFileName     String filename (including relative path) to fetch from assets
     * @return String contents of the provided filename
     */
    public static String assetFileContents(final AssetManager pAssetManager, final String pFileName) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream;

        try {
            inputStream = pAssetManager.open(pFileName);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            MyLog.e(LOG_TAG, "Cannot find file [" + pFileName + "] in assets folder.", e);
        }

        return stringBuilder.toString();
    }

    /**
     * Method to read files from assets folder.
     *
     * @param pContext  Context object to access saved files
     * @param pFileName String filename (including relative path) to fetch from assets
     * @return String contents of the provided filename
     */
    public static String savedFileContents(final Context pContext, final String pFileName) {
        StringBuilder stringBuilder = new StringBuilder();
        FileInputStream fileInputStream;

        try {
            fileInputStream = pContext.openFileInput(pFileName);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            MyLog.e(LOG_TAG, "Cannot find file [" + pFileName + "] in assets folder.", e);
        }

        return stringBuilder.toString();
    }

    public static void saveFile(final Context pContext, final String pFileName, final String pContents) {
        FileOutputStream fos = null;

        try {
            fos = pContext.openFileOutput(pFileName, Context.MODE_PRIVATE);
            fos.write(pContents.getBytes());
        } catch (IOException fileException) {
            MyLog.e(LOG_TAG, "Could not save contents to file [" + pFileName + "].", fileException);
        } finally {
            try {
                fos.close();
            } catch (IOException fileOutputException) {
                MyLog.w(LOG_TAG, "Could not close outputstream.");
            }
        }
    }


}
