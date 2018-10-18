package com.mickeywilliamson.project8.Models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class ProtocolFull extends Protocol {

    @Exclude
    Hour HOUR_6;
    @Exclude
    Hour HOUR_930;
    @Exclude
    Hour HOUR_15;
    @Exclude
    Hour HOUR_16;
    @Exclude
    Hour HOUR_22;

    public ProtocolFull() {
        buildProtocol();
    }

    @Exclude
    public Hour getHOUR_6() {
        return HOUR_6;
    }
    @Exclude
    public void setHOUR_6(Hour HOUR_6) {
        this.HOUR_6 = HOUR_6;
    }
    @Exclude
    public Hour getHOUR_930() {
        return HOUR_930;
    }
    @Exclude
    public void setHOUR_930(Hour HOUR_930) {
        this.HOUR_930 = HOUR_930;
    }
    @Exclude
    public Hour getHOUR_15() {
        return HOUR_15;
    }
    @Exclude
    public void setHOUR_15(Hour HOUR_15) {
        this.HOUR_15 = HOUR_15;
    }
    @Exclude
    public Hour getHOUR_16() {
        return HOUR_16;
    }
    @Exclude
    public void setHOUR_16(Hour HOUR_16) {
        this.HOUR_16 = HOUR_16;
    }
    @Exclude
    public Hour getHOUR_22() {
        return HOUR_22;
    }
    @Exclude
    public void setHOUR_22(Hour HOUR_22) {
        this.HOUR_22 = HOUR_22;
    }


    public ArrayList<Hour> buildProtocol() {

        setType(PROTOCOL_FULL);

        ArrayList<Supplement> supplements = new ArrayList<>();

        HOUR_6 = new Hour(600, new CeCoe(CeCoe.CE));
        schedule.add(HOUR_6);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_ACIDOL));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LUGOLS));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_THYROID));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_PANCREATIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_COQ10));
        HOUR_8 = new Hour(800, new Juice(Juice.JUICE_OJ), (ArrayList<Supplement>) supplements.clone(), new Meal(Meal.MEAL_BREAKFAST));
        schedule.add(HOUR_8);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_9 = new Hour(900, new Juice(Juice.JUICE_GREEN), (ArrayList<Supplement>) supplements.clone());
        schedule.add(HOUR_9);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LUGOLS));
        HOUR_930 = new Hour(930, new Juice(Juice.JUICE_CARROT_APPLE), (ArrayList<Supplement>) supplements.clone());
        schedule.add(HOUR_930);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LUGOLS));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_THYROID));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        HOUR_10 = new Hour(1000, new Juice(Juice.JUICE_CARROT_APPLE), (ArrayList<Supplement>) supplements.clone(), new CeCoe(CeCoe.CE));
        schedule.add(HOUR_10);

        // TODO: Add injection every other day - need to get day of year and do on even days
        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LIVER));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_INJECTION));
        HOUR_11 = new Hour(1100, new Juice(Juice.JUICE_CARROT), (ArrayList<Supplement>) supplements.clone());
        schedule.add(HOUR_11);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_12 = new Hour(1200, new Juice(Juice.JUICE_GREEN), (ArrayList<Supplement>) supplements.clone());
        schedule.add(HOUR_12);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_FLAX));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_ACIDOL));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LUGOLS));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_THYROID));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_PANCREATIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_COQ10));
        HOUR_13 = new Hour( 1300, new Juice(Juice.JUICE_CARROT_APPLE), (ArrayList<Supplement>) supplements.clone(), new Meal(Meal.MEAL_LUNCH));
        schedule.add(HOUR_13);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_14 = new Hour(1400, new Juice(Juice.JUICE_GREEN), (ArrayList<Supplement>) supplements.clone(), new CeCoe(CeCoe.CE));
        schedule.add(HOUR_14);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LIVER));
        HOUR_15 = new Hour(1500, new Juice(Juice.JUICE_CARROT), (ArrayList<Supplement>) supplements.clone(), new CeCoe(CeCoe.CE));
        schedule.add(HOUR_15);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LIVER));
        HOUR_16 = new Hour(1600, new Juice(Juice.JUICE_CARROT), (ArrayList<Supplement>) supplements.clone(), new CeCoe(CeCoe.CE));
        schedule.add(HOUR_16);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LUGOLS));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_THYROID));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_PANCREATIN));
        HOUR_17 = new Hour(1700, new Juice(Juice.JUICE_CARROT_APPLE), (ArrayList<Supplement>) supplements.clone());
        schedule.add(HOUR_17);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        HOUR_18 = new Hour(1800, new Juice(Juice.JUICE_GREEN), (ArrayList<Supplement>) supplements.clone(), new CeCoe(CeCoe.CE));
        schedule.add(HOUR_18);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_FLAX));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_ACIDOL));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LUGOLS));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_THYROID));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_PANCREATIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_COQ10));
        HOUR_19 = new Hour(1900, new Juice(Juice.JUICE_CARROT_APPLE), (ArrayList<Supplement>) supplements.clone(), new Meal(Meal.MEAL_SUPPER));
        schedule.add(HOUR_19);

        supplements.clear();
        HOUR_22 = new Hour(2200, new CeCoe(CeCoe.CE));
        schedule.add(HOUR_22);

        return schedule;
    }
}
