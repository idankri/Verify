package com.example.verify.data;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.verify.components.ApartmentProfileEnriched;
import com.example.verify.components.ApartmentReview;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class to fetch review details from the server using HTTP GET request
 */
public class ApartmentDetailsFetcher {
    private RequestQueue _queue;
    private String REQUEST_URL = "http://192.168.43.132:3000/review/";

    public class ApartmentDetailsResponse {
        private boolean isError;
        private ApartmentProfileEnriched profile;


        public ApartmentDetailsResponse(boolean isError, ApartmentProfileEnriched profile){
            this.isError = isError;
            this.profile = profile;
        }

        public boolean hasError(){ return isError;}

        public ApartmentProfileEnriched getProfile(){ return profile; }
    }

    public interface IApartmentDetailsResponseListener {
        public void onResponse(ApartmentDetailsResponse response);
    }

    public ApartmentDetailsFetcher(Context context) {
        this._queue = Volley.newRequestQueue(context);
    }

    private ApartmentDetailsResponse createErrorResponse() {
        return new ApartmentDetailsResponse(true, null);
    }

    public void dispatchRequest(final String city, final String streetAddress, final int streetNumber,
                                final int floor, final int apartment, final IApartmentDetailsResponseListener listener){
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("city", city);
            requestBody.put("street", streetAddress);
            requestBody.put("number", streetNumber);
            requestBody.put("floor", floor);
            requestBody.put("apartment", apartment);
        }
        catch (JSONException e) {
            listener.onResponse(createErrorResponse());
            return;
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, REQUEST_URL, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ApartmentReview review = new ApartmentReview(response);
                            List<ApartmentReview> reviewsList = new ArrayList<>();
                            reviewsList.add(review);

                            ApartmentProfileEnriched profile = new ApartmentProfileEnriched(city, streetAddress,
                                    streetNumber, floor, apartment, reviewsList);

                            ApartmentDetailsResponse res = new ApartmentDetailsResponse(false, profile);

                            listener.onResponse(res);
                        } catch (JSONException e) {
                            listener.onResponse(createErrorResponse());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponse(createErrorResponse());
            }
        });

        _queue.add(req);
    }

}
