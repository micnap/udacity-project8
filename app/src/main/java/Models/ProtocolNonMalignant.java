package Models;

import java.util.ArrayList;

public class ProtocolNonMalignant extends Protocol {

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

    public static void setProtocol(ArrayList<Hour> protocol) {
        ProtocolNonMalignant.protocol = protocol;
    }

    public ProtocolNonMalignant() {}

    public ArrayList<Hour> buildProtocol() {

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

        return protocol;
    }

    public ArrayList<Hour> getProtocol() {
        return protocol;
    }
}
