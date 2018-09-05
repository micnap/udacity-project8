package Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Hour implements Parcelable {

    private int militaryHour;
    private Juice juice;
    private Meal meal;
    private CeCoe ce;
    private ArrayList<Supplement> supplements;
    private boolean state;

    public Hour() {}

    public Hour(int militaryHour, Juice juice, ArrayList<Supplement> supplements) {
        this.militaryHour = militaryHour;
        this.juice = juice;
        this.meal = meal;
        this.ce = ce;
        this.supplements = supplements;
    }

    public Hour(int militaryHour, Juice juice, ArrayList<Supplement> supplements, Meal meal) {
        this.militaryHour = militaryHour;
        this.juice = juice;
        this.meal = meal;
        this.ce = ce;
        this.supplements = supplements;
    }
    public Hour(int militaryHour, Juice juice, ArrayList<Supplement> supplements, CeCoe ce) {
        this.militaryHour = militaryHour;
        this.juice = juice;
        this.meal = meal;
        this.ce = ce;
        this.supplements = supplements;
    }

    public int getMilitaryHour() {
        return militaryHour;
    }

    public void setMilitaryHour(int militaryHour) {
        this.militaryHour = militaryHour;
    }

    public Juice getJuice() {
        return juice;
    }

    public void setJuice(Juice juice) {
        this.juice = juice;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public CeCoe getCe() {
        return ce;
    }

    public void setCe(CeCoe ce) {
        this.ce = ce;
    }

    public ArrayList<Supplement> getSupplements() {
        return supplements;
    }

    public void setSupplements(ArrayList<Supplement> supplements) {
        this.supplements = supplements;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;

        if (juice != null) {
            juice.setState(state);
        }

        if (meal != null) {
            meal.setState(state);
        }

        if (ce != null) {
            ce.setState(state);
        }

        if (supplements != null) {
            for (Supplement supplement: supplements) {
                supplement.setState(state);
            }
        }
    }

    public String[] fetchHourItems() {
        List<String> itemsList = new ArrayList<String>();
        itemsList.add(juice.getType());

        if (meal != null) {
            itemsList.add(meal.getType());
        }

        if (ce != null) {
            itemsList.add(ce.getType());
        }

        for (int i = 0; i < supplements.size(); i++) {
            itemsList.add(supplements.get(i).getType());
        }

        return itemsList.toArray(new String[itemsList.size()]);
    }

    public boolean[] fetchHourItemsState() {
        List<Boolean> itemsStateList = new ArrayList<Boolean>();

        itemsStateList.add(juice.getState());

        if (meal != null) {
            itemsStateList.add(meal.getState());
        }

        if (ce != null) {
            itemsStateList.add(ce.getState());
        }

        for (int i = 0; i < supplements.size(); i++) {
            itemsStateList.add(supplements.get(i).getState());

        }

        // AlertDialog builder's setMultiChoiceItems requires this be a primitive boolean array.
        Boolean[] arrayBoolean = itemsStateList.toArray(new Boolean[itemsStateList.size()]);
        boolean[] arrayBooleanPrimitive = new boolean[arrayBoolean.length];
        for (int i = 0; i < arrayBoolean.length; i++) {
            arrayBooleanPrimitive[i] = arrayBoolean[i];
        }

        return arrayBooleanPrimitive;
    }

    public void updateHourItemsState(boolean[] states) {

        int counter = 0;
        juice.setState(states[counter]);

        if (meal != null) {
            counter++;
            meal.setState(states[counter]);
        }

        if (ce != null) {
            counter++;
            ce.setState(states[counter]);
        }

        for (int i = 0; i < supplements.size(); i++) {
            counter++;
            supplements.get(i).setState(states[counter]);
        }

        Log.d("JUICE" + militaryHour, String.valueOf(juice.getState()));
        if (meal != null) {
            Log.d("MEAL" + militaryHour, String.valueOf(meal.getState()));
        }
        if (ce != null) {
            Log.d("CE" + militaryHour, String.valueOf(ce.getState()));
        }

        for (int j = 0; j < supplements.size(); j++) {
            Log.d("SUPPLEMENT" + j +  + militaryHour, String.valueOf(supplements.get(j).getState()));
        }
    }

    @Override
    public String toString() {
        String hourString = "";

        if (juice != null) {
            hourString += juice.toString();
        }

        if (meal != null) {
            hourString += ", " + meal.toString();
        }

        if (ce != null) {
            hourString += ", " + ce.toString();
        }

        if (supplements != null) {
            for (Supplement supplement: supplements) {
                hourString += ", " + supplement;
            }
        }

        return hourString;
    }

    protected Hour(Parcel in) {
        militaryHour = in.readInt();
        juice = (Juice) in.readValue(Juice.class.getClassLoader());
        meal = (Meal) in.readValue(Meal.class.getClassLoader());
        ce = (CeCoe) in.readValue(CeCoe.class.getClassLoader());
        if (in.readByte() == 0x01) {
            supplements = new ArrayList<Supplement>();
            in.readList(supplements, Supplement.class.getClassLoader());
        } else {
            supplements = null;
        }
        state = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(militaryHour);
        dest.writeValue(juice);
        dest.writeValue(meal);
        dest.writeValue(ce);
        if (supplements == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(supplements);
        }
        dest.writeByte((byte) (state ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Hour> CREATOR = new Parcelable.Creator<Hour>() {
        @Override
        public Hour createFromParcel(Parcel in) {
            return new Hour(in);
        }

        @Override
        public Hour[] newArray(int size) {
            return new Hour[size];
        }
    };
}