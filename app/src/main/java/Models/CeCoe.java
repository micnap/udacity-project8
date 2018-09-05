package Models;

import android.os.Parcel;
import android.os.Parcelable;

public class CeCoe extends Task implements Parcelable {

    public static final String CE = "CE";
    public static final String COE = "COE";

    private String type;
    private boolean completed;

    public CeCoe() {}

    public CeCoe(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCompleted() { return completed; }

    public void setCompleted(boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        return type;
    }

    public String getClassName() {
        return "CeCoe";
    }

    protected CeCoe(Parcel in) {
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
    public static final Parcelable.Creator<CeCoe> CREATOR = new Parcelable.Creator<CeCoe>() {
        @Override
        public CeCoe createFromParcel(Parcel in) {
            return new CeCoe(in);
        }

        @Override
        public CeCoe[] newArray(int size) {
            return new CeCoe[size];
        }
    };
}
