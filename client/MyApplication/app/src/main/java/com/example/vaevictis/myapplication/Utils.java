package com.example.vaevictis.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.vaevictis.myapplication.models.User;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vaevictis on 30.11.17.
 */

final public class Utils {

    static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean checkIfUserIsAlreadyHelping(User user,ArrayList<User> usersThatAreHelping){

        for(User userThatHelps: usersThatAreHelping){
            if(userThatHelps.getEmail().equals(user.getEmail())) {
                userThatHelps = user;
                return true;
            }
        }
        return false;
    }

    static boolean isValidPassword(String pass) {
        return pass != null && pass.length() > 0;
    }

    static public Bitmap addColorBorder(Bitmap bmp, int borderSize, int color) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.drawColor(color);
        canvas.drawBitmap(bmp, borderSize, borderSize, null);
        return bmpWithBorder;
    }

    public static Bitmap getCircular(Bitmap bm, int cornerRadiusPx) {
        int w = bm.getWidth();
        int h = bm.getHeight();

        int radius = (w < h) ? w : h;
        w = radius;
        h = radius;

        Bitmap bmOut = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmOut);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xff424242);

        Rect rect = new Rect(0, 0, w, h);
        RectF rectF = new RectF(rect);

        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(rectF.left + (rectF.width()/2), rectF.top + (rectF.height()/2), radius / 2, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bm, rect, rect, paint);

        return bmOut;
    }
}
