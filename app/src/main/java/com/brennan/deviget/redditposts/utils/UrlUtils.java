package com.brennan.deviget.redditposts.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class UrlUtils {
    public static void openWebPage(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
