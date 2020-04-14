package id.net.gmedia.remotezigy.Utils;

import java.lang.ref.SoftReference;

public class Url {
    public static final  String base_url_fcm= "https://fcm.googleapis.com/fcm/send";
    private static String base_url = "http://gmedia.bz/zigystreaming/api/";
    public static final String url_channel = base_url+"Live/get_channel/";
    public static final String url_streaming= base_url+"streaming/get_live_streaming";
}