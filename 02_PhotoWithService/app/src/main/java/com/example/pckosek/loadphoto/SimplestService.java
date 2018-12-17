package com.example.pckosek.loadphoto;


import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.concurrent.ThreadLocalRandom;

public class SimplestService extends IntentService {

    /* -------------------------------- */
    /*    member variables              */

    final static String ACTION_KEY = "action_key";

    private final static String TAG = "SIMPLEST_SERVICE_TAG";

    /* -------------------------------- */
    /*    CONSTRUCTOR                   */

    public SimplestService() {

        // SUPER IMPORTANT!!!
        //
        //  The constructor must call super IntentService(String) with a name for the thread

        super("SimplestService");
    }

    /* -------------------------------- */
    /*    REQUIRED CLASS METHODS        */

    @Override
    protected void onHandleIntent(Intent intent_from_activity) {

        // THIS METHOD IS CALLED WHEN onClick in the activity triggers the start of the service
        Log.i(TAG, "GOT MESSAGE!");

        Bundle extras = intent_from_activity.getExtras();
        if (extras != null) {
            if (extras.containsKey(BitmapTransferEnum.KEY)) {

                Log.i(TAG, "Contains enum");
                BitmapTransferEnum bitmapTransferEnum = (BitmapTransferEnum)extras.getSerializable(BitmapTransferEnum.KEY);
                Bitmap b = bitmapTransferEnum.INSTANCE.getData();

                int w = b.getWidth();
                int h = b.getHeight();
                Log.i(TAG, "bitmap size: "+w+ " "+h);

                transformBitmap(b);
                respondToActivity(b);
            }
        }

    }

    private void transformBitmap(Bitmap bmp) {

        int numPixels = bmp.getHeight() * bmp.getWidth();
        int[] pixels = new int[numPixels];
        int r, b, g;

        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight() );
        for (int i=0; i< pixels.length; i++) {
            r = ThreadLocalRandom.current().nextInt(0, 256);
            g = ThreadLocalRandom.current().nextInt(0, 256);
            b = ThreadLocalRandom.current().nextInt(0, 256);
            pixels[i] = Color.argb(255, r, g, b);
        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight() );
    }

    /* -------------------------------- */
    /*    CUSTOM HELPER METHODS         */

    private void respondToActivity(Bitmap bmp) {

        final BitmapTransferEnum transferEnum = BitmapTransferEnum.INSTANCE;
        transferEnum.setData(bmp);

        // create a new intent to send data back to the activity
        Intent intent_from_service = new Intent();
        intent_from_service.setAction(ACTION_KEY);
        intent_from_service.putExtra(BitmapTransferEnum.KEY, transferEnum.INSTANCE);


        // send the local broadcast
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent_from_service);
    }
}