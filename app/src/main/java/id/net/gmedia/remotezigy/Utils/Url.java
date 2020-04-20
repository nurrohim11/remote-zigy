package id.net.gmedia.remotezigy.Utils;

public class Url {
    public static final  String base_url_fcm= "https://fcm.googleapis.com/fcm/send";
    private static String base_url = "http://gmedia.bz/zigystreaming/api/";
    public static final String url_channel = base_url+"Live/get_channel/";
    public static final String url_streaming= base_url+"streaming/get_live_streaming";
    public static final String url_promo = base_url+"master/promo_for_android";
    public static final String url_profile_device = base_url+"authentication/profile_device";
}