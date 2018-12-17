package com.example.pckosek.loadphoto;

import android.graphics.Bitmap;

enum BitmapTransferEnum {
    INSTANCE;

    private Bitmap mBitmap;
    public final static String KEY = "BitmapTransferEnum";


    public static void setData(final Bitmap b) {
        INSTANCE.mBitmap = b;
    }

    public static Bitmap getData() {
        final Bitmap retBitmap = INSTANCE.mBitmap;
        INSTANCE.mBitmap = null;
        return retBitmap;
    }

}

