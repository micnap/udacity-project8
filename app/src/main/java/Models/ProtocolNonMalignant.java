package Models;

import java.util.ArrayList;

public class ProtocolNonMalignant extends Protocol {

    public static final ArrayList<Integer> hours = new ArrayList<>();

    private Hour HOUR_8;
    private Hour HOUR_9;
    private Hour HOUR_10;
    private Hour HOUR_11;
    private Hour HOUR_12;
    private Hour HOUR_13;
    private Hour HOUR_14;
    private Hour HOUR_17;
    private Hour HOUR_18;
    private Hour HOUR_19;

    private static ArrayList<Hour> protocol = new ArrayList<Hour>();

    public ProtocolNonMalignant() {

        ArrayList<Supplement> supplements = new ArrayList<>();

        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LUGOLS));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_THYROID));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LIVER));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_PANCREATIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_COQ10));
        HOUR_8 = new Hour(8, new Juice(Juice.JUICE_OJ), (ArrayList<Supplement>) supplements.clone(), new Meal(Meal.MEAL_BREAKFAST));
        protocol.add(HOUR_8);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_9 = new Hour(9, new Juice(Juice.JUICE_GREEN), (ArrayList<Supplement>) supplements.clone(), new CeCoe(CeCoe.CE));
        protocol.add(HOUR_9);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        HOUR_10 = new Hour(10, new Juice(Juice.JUICE_CARROT_APPLE), (ArrayList<Supplement>) supplements.clone());
        protocol.add(HOUR_10);

        // TODO: Add injection every other day - need to get day of year and do on even days
        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LIVER));
        HOUR_11 = new Hour(11, new Juice(Juice.JUICE_CARROT), (ArrayList<Supplement>) supplements.clone());
        protocol.add(HOUR_11);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_12 = new Hour(12, new Juice(Juice.JUICE_GREEN), (ArrayList<Supplement>) supplements.clone());
        protocol.add(HOUR_12);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_ACIDOL));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LUGOLS));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_THYROID));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_PANCREATIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_COQ10));
        HOUR_13 = new Hour( 13, new Juice(Juice.JUICE_CARROT_APPLE), (ArrayList<Supplement>) supplements.clone(), new Meal(Meal.MEAL_LUNCH));
        protocol.add(HOUR_13);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_14 = new Hour(14, new Juice(Juice.JUICE_GREEN), (ArrayList<Supplement>) supplements.clone(), new CeCoe(CeCoe.CE));
        protocol.add(HOUR_14);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_PANCREATIN));
        HOUR_17 = new Hour(17, new Juice(Juice.JUICE_CARROT_APPLE), (ArrayList<Supplement>) supplements.clone());
        protocol.add(HOUR_17);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_18 = new Hour(18, new Juice(Juice.JUICE_GREEN), (ArrayList<Supplement>) supplements.clone(), new CeCoe(CeCoe.CE));
        protocol.add(HOUR_18);

        supplements.clear();
        supplements.add(new Supplement(Supplement.SUPPLEMENT_ACIDOL));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LUGOLS));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_LIVER));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_PANCREATIN));
        supplements.add(new Supplement(Supplement.SUPPLEMENT_COQ10));
        HOUR_19 = new Hour(19, new Juice(Juice.JUICE_CARROT_APPLE), (ArrayList<Supplement>) supplements.clone(), new Meal(Meal.MEAL_SUPPER));
        protocol.add(HOUR_19);

    }

    public ArrayList<Hour> getProtocol() {
        return protocol;
    }
}
