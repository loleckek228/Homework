package com.geekbrains.android.homework;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class ScreenHelper {
    static int convertDpToPixels(int dp, Context context) {
        Resources resources = context.getResources();
        return (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float)dp,
                resources.getDisplayMetrics()));
    }
}