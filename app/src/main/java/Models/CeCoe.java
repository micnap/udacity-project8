package Models;

import android.os.Parcel;
import android.os.Parcelable;

public class CeCoe extends Task implements Parcelable {

    public static final String CE = "CE";
    public static final String COE = "COE";


    private String type;

    public CeCoe(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
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
