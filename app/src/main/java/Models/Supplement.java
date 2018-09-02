package Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Supplement extends Task implements Parcelable {

    public static final String SUPPLEMENT_NIACIN = "niacin";
    public static final String SUPPLEMENT_ACIDOL = "acidol";
    public static final String SUPPLEMENT_POTASSIUM = "potassium";
    public static final String SUPPLEMENT_LUGOLS = "Lugol's";
    public static final String SUPPLEMENT_THYROID = "thyroid";
    public static final String SUPPLEMENT_LIVER = "liver capsules";
    public static final String SUPPLEMENT_PANCREATIN = "pancreatin";
    public static final String SUPPLEMENT_INJECTION = "liver/B12 injection";
    public static final String SUPPLEMENT_COQ10 = "CoQ10";

    private String type;

    public Supplement(String type) {
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
        return "Supplement";
    }

    protected Supplement(Parcel in) {
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
    public static final Parcelable.Creator<Supplement> CREATOR = new Parcelable.Creator<Supplement>() {
        @Override
        public Supplement createFromParcel(Parcel in) {
            return new Supplement(in);
        }

        @Override
        public Supplement[] newArray(int size) {
            return new Supplement[size];
        }
    };
}
