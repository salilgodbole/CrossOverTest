package com.salilgodbole.silnetworklibrary.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONObject;

/**
 * Created by salil on 17/9/16.
 */
public class Place implements Parcelable {
    private String id;
    private String name;
    private Location location;

    public Place(String id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public static Place fromJSONObject(JSONObject jsonObject) {
        Place place = null;

        if (jsonObject != null) {
            String id = jsonObject.optString("id");
            String name = jsonObject.optString("name");
            JSONObject locationObj = jsonObject.optJSONObject("location");

            Location location = Location.fromJSONObject(locationObj);

            if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(name) && location != null) {
                place = new Place(id, name, location);
            }
        }

        return place;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeParcelable(this.location, flags);
    }

    protected Place(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.location = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}
