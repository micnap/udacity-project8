package Models;

import java.util.ArrayList;

public class ProtocolNonMalignant extends Protocol {

    public static final ArrayList<Integer> hours = new ArrayList<>();

    private static ArrayList<Task> HOUR_8 = new ArrayList<>();
    private static ArrayList<Task> HOUR_9 = new ArrayList<>();
    private static ArrayList<Task> HOUR_10 = new ArrayList<>();
    private static ArrayList<Task> HOUR_11 = new ArrayList<>();
    private static ArrayList<Task> HOUR_12 = new ArrayList<>();
    private static ArrayList<Task> HOUR_13 = new ArrayList<>();
    private static ArrayList<Task> HOUR_14 = new ArrayList<>();
    private static ArrayList<Task> HOUR_17 = new ArrayList<>();
    private static ArrayList<Task> HOUR_18 = new ArrayList<>();
    private static ArrayList<Task> HOUR_19 = new ArrayList<>();

    private static ArrayList<ArrayList<Task>> protocol = new ArrayList<ArrayList<Task>>();

    public ProtocolNonMalignant() {

        populateHours();

        HOUR_8.add(new Juice(Juice.JUICE_OJ));
        HOUR_8.add(new Meal(Meal.MEAL_BREAKFAST));
        HOUR_8.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_8.add(new Supplement(Supplement.SUPPLEMENT_LUGOLS));
        HOUR_8.add(new Supplement(Supplement.SUPPLEMENT_THYROID));
        HOUR_8.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        HOUR_8.add(new Supplement(Supplement.SUPPLEMENT_LIVER));
        HOUR_8.add(new Supplement(Supplement.SUPPLEMENT_PANCREATIN));
        HOUR_8.add(new Supplement(Supplement.SUPPLEMENT_COQ10));
        protocol.add(HOUR_8);

        HOUR_9.add(new Juice(Juice.JUICE_GREEN));
        HOUR_9.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_9.add(new CeCoe(CeCoe.CE));
        protocol.add(HOUR_9);

        HOUR_10.add(new Juice(Juice.JUICE_CARROT_APPLE));
        HOUR_10.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_10.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        protocol.add(HOUR_10);

        // TODO: Add injection every other day - need to get day of year and do on even days
        HOUR_11.add(new Juice(Juice.JUICE_CARROT));
        HOUR_11.add(new Supplement(Supplement.SUPPLEMENT_LIVER));
        protocol.add(HOUR_11);

        HOUR_12.add(new Juice(Juice.JUICE_GREEN));
        HOUR_12.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        protocol.add(HOUR_12);

        HOUR_13.add(new Juice(Juice.JUICE_CARROT_APPLE));
        HOUR_13.add(new Meal(Meal.MEAL_LUNCH));
        HOUR_13.add(new Supplement(Supplement.SUPPLEMENT_ACIDOL));
        HOUR_13.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_13.add(new Supplement(Supplement.SUPPLEMENT_LUGOLS));
        HOUR_13.add(new Supplement(Supplement.SUPPLEMENT_THYROID));
        HOUR_13.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        HOUR_13.add(new Supplement(Supplement.SUPPLEMENT_PANCREATIN));
        HOUR_13.add(new Supplement(Supplement.SUPPLEMENT_COQ10));
        protocol.add(HOUR_13);

        HOUR_14.add(new Juice(Juice.JUICE_GREEN));
        HOUR_14.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_14.add(new CeCoe(CeCoe.CE));
        protocol.add(HOUR_14);

        HOUR_17.add(new Juice(Juice.JUICE_CARROT_APPLE));
        HOUR_17.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_17.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        HOUR_17.add(new Supplement(Supplement.SUPPLEMENT_PANCREATIN));
        protocol.add(HOUR_17);

        HOUR_18.add(new Juice(Juice.JUICE_GREEN));
        HOUR_18.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_18.add(new CeCoe(CeCoe.CE));
        protocol.add(HOUR_18);

        HOUR_19.add(new Juice(Juice.JUICE_CARROT_APPLE));
        HOUR_19.add(new Meal(Meal.MEAL_SUPPER));
        HOUR_19.add(new Supplement(Supplement.SUPPLEMENT_ACIDOL));
        HOUR_19.add(new Supplement(Supplement.SUPPLEMENT_POTASSIUM));
        HOUR_19.add(new Supplement(Supplement.SUPPLEMENT_LUGOLS));
        HOUR_19.add(new Supplement(Supplement.SUPPLEMENT_NIACIN));
        HOUR_19.add(new Supplement(Supplement.SUPPLEMENT_LIVER));
        HOUR_19.add(new Supplement(Supplement.SUPPLEMENT_PANCREATIN));
        HOUR_19.add(new Supplement(Supplement.SUPPLEMENT_COQ10));
        protocol.add(HOUR_19);

    }

    private void populateHours() {
        // {8, 9, 10, 11, 12, 13, 14, 17, 18, 19};
        hours.add(8);
        hours.add(9);
        hours.add(10);
        hours.add(11);
        hours.add(12);
        hours.add(13);
        hours.add(14);
        hours.add(17);
        hours.add(18);
        hours.add(19);
    }

    public ArrayList<ArrayList<Task>> getProtocol() {
        return protocol;
    }

    public static String toHourlyString(ArrayList<String> hourlyList) {

        String juice = hourlyList.get(0).toString();
        String meal = hourlyList.get(1).toString();
        if (meal != null) {
            meal = ", " + meal;
        }
        String supplements = hourlyList.get(2).toString();
        if (supplements != null) {
            supplements = ", " + supplements;
        }

        return juice + meal + supplements + Protocol.CE;
    }

    public int convertHourToIndex(int hour) {
        return hours.indexOf(hour);
    }
}
