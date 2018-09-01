package Models;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class Task implements Parcelable {

    private boolean state = false;

    public Task() {}

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getState() {
        return state;
    }

    @Override
    public abstract String toString();

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
