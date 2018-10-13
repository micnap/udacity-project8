package com.mickeywilliamson.project8.Models;

import java.util.ArrayList;

public abstract class Protocol {

    public static final String CE = "CE";
    public static final String PROTOCOL_NONMALIGNANT = "nonmalignant";
    public static final String PROTOCOL_FULL = "full";
    public static final String PROTOCOL_CHEMO = "chemo";

    String type;
    ArrayList<Hour> schedule = new ArrayList<Hour>();

    Hour HOUR_8;
    Hour HOUR_9;
    Hour HOUR_10;
    Hour HOUR_11;
    Hour HOUR_12;
    Hour HOUR_13;
    Hour HOUR_14;
    Hour HOUR_17;
    Hour HOUR_18;
    Hour HOUR_19;

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public Hour getHOUR_8() {
        return HOUR_8;
    }

    public void setHOUR_8(Hour HOUR_8) {
        this.HOUR_8 = HOUR_8;
    }

    public Hour getHOUR_9() {
        return HOUR_9;
    }

    public void setHOUR_9(Hour HOUR_9) {
        this.HOUR_9 = HOUR_9;
    }

    public Hour getHOUR_10() {
        return HOUR_10;
    }

    public void setHOUR_10(Hour HOUR_10) {
        this.HOUR_10 = HOUR_10;
    }

    public Hour getHOUR_11() {
        return HOUR_11;
    }

    public void setHOUR_11(Hour HOUR_11) {
        this.HOUR_11 = HOUR_11;
    }

    public Hour getHOUR_12() {
        return HOUR_12;
    }

    public void setHOUR_12(Hour HOUR_12) {
        this.HOUR_12 = HOUR_12;
    }

    public Hour getHOUR_13() {
        return HOUR_13;
    }

    public void setHOUR_13(Hour HOUR_13) {
        this.HOUR_13 = HOUR_13;
    }

    public Hour getHOUR_14() {
        return HOUR_14;
    }

    public void setHOUR_14(Hour HOUR_14) {
        this.HOUR_14 = HOUR_14;
    }

    public Hour getHOUR_17() {
        return HOUR_17;
    }

    public void setHOUR_17(Hour HOUR_17) {
        this.HOUR_17 = HOUR_17;
    }

    public Hour getHOUR_18() {
        return HOUR_18;
    }

    public void setHOUR_18(Hour HOUR_18) {
        this.HOUR_18 = HOUR_18;
    }

    public Hour getHOUR_19() {
        return HOUR_19;
    }

    public void setHOUR_19(Hour HOUR_19) {
        this.HOUR_19 = HOUR_19;
    }

    public void setSchedule(ArrayList<Hour> schedule) {
        this.schedule = schedule;
    }

    public ArrayList<Hour> getSchedule() {
        return schedule;
    }



    public ArrayList<String> setHourlyTasks(Juice juice, Meal meal, ArrayList<Supplement> supplements, boolean ce) {

        ArrayList<String> tasks = new ArrayList<>();

        tasks.add(juice.toString());

        String mealString = meal != null ? meal.toString() : null;
        tasks.add(mealString);

        if (mealString == Meal.MEAL_LUNCH || mealString == Meal.MEAL_SUPPER) {
            tasks.add(Meal.MEAL_HIPPO_SOUP);
            tasks.add(Meal.MEAL_FLAXSEED_OIL);
        }

        for (Supplement supplement: supplements) {
            tasks.add(supplement.toString());
        }

        if (ce) {
            tasks.add(Protocol.CE);
        }

        return tasks;
    }
}
