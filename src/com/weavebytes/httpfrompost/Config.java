package com.weavebytes.httpfrompost;

import android.content.Context;
import android.widget.Toast;

/**
 * class of static variables and methods
 */
public class Config {

    public static String API_URL    = "http://weavebytes.com/development/api.php";
    public static Long U_ID         = null;
    public static String E_MAIL     = null;
    public static String FILE_TYPE  = "image";//default

    //method to print Toast message
    public static void toast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }
}//Config
