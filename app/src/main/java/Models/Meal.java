package Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Meal extends Task implements Parcelable {

    public static final String MEAL_BREAKFAST = "Breakfast";
    public static final String MEAL_LUNCH = "Lunch";
    public static final String MEAL_SUPPER = "Supper";
    public static final String MEAL_FLAXSEED_OIL = "flaxseed oil";
    public static final String MEAL_HIPPO_SOUP = "hipp soup";

    private String type;

    public Meal() {}

    public Meal(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    public String getClassName() {
        return "Meal";
    }

    protected Meal(Parcel in) {
        type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Meal> CREATOR = new Parcelable.Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };
}