package com.example.database2.task;

import com.example.database2.entity.Dish;
import com.example.database2.entity.ResponseCode;
import com.example.database2.entity.Wrapper;
import com.octo.android.robospice.request.SpiceRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharEncoding;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by root on 06.05.15.
 */
public class GetPeopleDataRequest extends SpiceRequest<Wrapper> {

    String url;

    public GetPeopleDataRequest(String url) {
        super(Wrapper.class);
        this.url = url;
    }

    @Override
    public Wrapper loadDataFromNetwork() throws Exception {

        Wrapper wrapper = new Wrapper();
        wrapper.code= ResponseCode.GET_USER_DATA_OK;
        wrapper.obj=Parser(IOUtils.toString(new InputStreamReader(new URL(url).openStream(), CharEncoding.UTF_8)));

return wrapper;
    }


    private ArrayList<Dish> Parser(String json_array) {
        ArrayList<Dish> arr = new ArrayList<Dish>();
        String r = json_array.replace("[", "{ \"Users\" : [");
        String rr = r.replace("]", "] }");


        JSONObject json_obj = null;
        try {
            json_obj = new JSONObject(rr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray jsonMainNode = json_obj.optJSONArray("Users");
            int lengthJsonArr = jsonMainNode.length();

            for (int i = 0; i < lengthJsonArr; i++) {
                JSONObject jsonChildNode = null;
                try {
                    jsonChildNode = jsonMainNode.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String name = jsonChildNode.optString("dish_name").toString();
                String id = jsonChildNode.optString("_id").toString();
                String x = jsonChildNode.optString("recipe").toString();
                String y = jsonChildNode.optString("type").toString();

                arr.add(new Dish(id,name,x,y));


        }
        return arr;
    }
}
