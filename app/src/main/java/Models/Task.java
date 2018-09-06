package Models;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class Task implements Parcelable {

    private String type;

    public Task() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public abstract String toString();

    public String getClassName() {
        return "Task";
    }

    protected Task(Parcel in) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            //return new Task(in);
            return null;
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
