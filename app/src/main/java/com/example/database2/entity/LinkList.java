package com.example.database2.entity;

/**
 * Created by root on 13.05.15.
 */
public class LinkList {
    public static String passToAllUser = "http://iva-api.vacau.com/";

    public static String GetPassToAddUser(String id, String name, Double x, Double y)
    {
        return String.format("https://who-is-there.herokuapp.com/hello/%s/%s/%s/%s", id, name, x.toString(), y.toString());
    }

    public static String GetPassToProfileFoto(String id)
    {
      return "http://graph.facebook.com/"+id+"/picture?type=large";
    }

    public static String GetPassToProfile(String id)
    {
        return "https://www.facebook.com/app_scoped_user_id/"+id+"/";
    }
}
