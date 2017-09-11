package com.example.hosiluan.foodanddrink.showdirectionactivity;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Ho Si Luan on 8/31/2017.
 */

public class ShowDirectionModel {

    private Context mContext;
    private ArrayList<String> pointList = new ArrayList<>();
    private ShowDirectionModelListener showDirectionModelListener;

    public ShowDirectionModel(Context mContext,ShowDirectionModelListener showDirectionModelListener) {
        this.mContext = mContext;
        this.showDirectionModelListener = showDirectionModelListener;
    }

    public void getPolyLineListVolley(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response.toString());
                    JSONArray routes = object.getJSONArray("routes");
                    JSONObject routeObject = routes.getJSONObject(0);
                    JSONArray legs = routeObject.getJSONArray("legs");
                    JSONObject legObject = legs.getJSONObject(0);
                    JSONArray steps = legObject.getJSONArray("steps");
                    for (int i = 0; i < steps.length(); i++) {
                        JSONObject stepObject = steps.getJSONObject(i);
                        JSONObject polyline = stepObject.getJSONObject("polyline");
                        String point = (String) polyline.get("points");
                        pointList.add(point);
                    }
                    showDirectionModelListener.getPointListResult(pointList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Luan",error.toString());
            }
        });

        requestQueue.add(stringRequest);
    }

    interface ShowDirectionModelListener{
        void getPointListResult(ArrayList<String> pointList);
    }
}
