package com.example.jerry.crimeintent.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jerry on 30.03.2016.
 */
public class Crime {
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private static final String JSON_PHOTO = "photo";
    private static final String JSON_SUSPECT = "suspect";

    public UUID Id;
    public String Title;
    public Date Date;
    public boolean Solved;
    public Photo Photo;
    public String Suspect;

    public Crime()
    {
        Id = UUID.randomUUID();
        Date = new Date();
    }

    public Crime(JSONObject json) throws JSONException{
        Id = UUID.fromString(json.getString(JSON_ID));
        Title = json.getString(JSON_TITLE);
        Solved = json.getBoolean(JSON_SOLVED);
        Date = new Date(json.getLong(JSON_DATE));

        if(json.has(JSON_PHOTO)){
            Photo = new Photo(json.getJSONObject(JSON_PHOTO));
        }

        if(json.has(JSON_SUSPECT))
            Suspect = json.getString(JSON_SUSPECT);
    }

    @Override
    public String toString() {
        return Title;
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_ID, Id.toString());
        json.put(JSON_TITLE, Title);
        json.put(JSON_SOLVED, Solved);
        json.put(JSON_DATE, Date.getTime());

        if(Photo != null)
            json.put(JSON_PHOTO, Photo.toJSON());

        json.put(JSON_SUSPECT, Suspect);

        return json;
    }
}
