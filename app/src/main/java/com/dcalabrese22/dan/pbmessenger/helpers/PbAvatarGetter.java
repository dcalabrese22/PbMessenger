package com.dcalabrese22.dan.pbmessenger.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.dcalabrese22.dan.pbmessenger.R;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dan on 10/9/17.
 */

public class PbAvatarGetter extends AsyncTask<String, Void, Bitmap> {

    private RemoteViews mViews;

    public PbAvatarGetter(RemoteViews views) {
        mViews = views;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            InputStream in = new URL(params[0]).openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        mViews.setImageViewBitmap(R.id.user_avatar, bitmap);
    }
}
