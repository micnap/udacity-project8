package Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Juice extends Task implements Parcelable {

    public static final String JUICE_OJ = "Orange juice";
    public static final String JUICE_GREEN = "Green";
    public static final String JUICE_CARROT = "Carrot";
    public static final String JUICE_CARROT_APPLE = "Carrot apple";


    private String type;

    public Juice() {}

    public Juice(String type) { this.type = type; }

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
        return "Juice";
    }

    protected Juice(Parcel in) {
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
    public static final Parcelable.Creator<Juice> CREATOR = new Parcelable.Creator<Juice>() {
        @Override
        public Juice createFromParcel(Parcel in) {
            return new Juice(in);
        }

        @Override
        public Juice[] newArray(int size) {
            return new Juice[size];
        }
    };
}
