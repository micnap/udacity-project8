package com.mickeywilliamson.project8.Models;

import java.util.ArrayList;

public class ProtocolChemo extends Protocol {

    public ProtocolChemo() {
        buildProtocol();
    }

    public ArrayList<Hour> buildProtocol() {

        setType(PROTOCOL_CHEMO);

        ArrayList<Supplement> supplements = new ArrayList<>();

        supplements.add(new Supplement(Supplement.SUPPLEMENT_ACIDOL));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LUGOLS));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_THYROID));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LIVER));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_PANCREATIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_COQ10));
        HOUR_8 = new Hour(800, new Juice(Juice.JUICE_OJ), (ArrayList<Supplement>) supplements.clone(), new Meal(Meal.MEAL_BREAKFAST));
        schedule.add(HOUR_8);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_9 = new Hour(900, new Juice(Juice.JUICE_GREEN), (ArrayList<Supplement>) supplements.clone(), new CeCoe(CeCoe.CE));
        schedule.add(HOUR_9);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LUGOLS));
        HOUR_10 = new Hour(1000, new Juice(Juice.JUICE_CARROT_APPLE), (ArrayList<Supplement>) supplements.clone());
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
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_PANCREATIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LUGOLS));
        HOUR_17 = new Hour(1700, new Juice(Juice.JUICE_CARROT_APPLE), (ArrayList<Supplement>) supplements.clone());
        schedule.add(HOUR_17);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_18 = new Hour(1800, new Juice(Juice.JUICE_GREEN), (ArrayList<Supplement>) supplements.clone(), new CeCoe(CeCoe.CE));
        schedule.add(HOUR_18);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_FLAX));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_ACIDOL));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LUGOLS));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LIVER));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_PANCREATIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_COQ10));
        HOUR_19 = new Hour(1900, new Juice(Juice.JUICE_CARROT_APPLE), (ArrayList<Supplement>) supplements.clone(), new Meal(Meal.MEAL_SUPPER));
        schedule.add(HOUR_19);

        return schedule;
    }
}
