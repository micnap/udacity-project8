package Models;

import java.util.ArrayList;

public abstract class Protocol {

    public static final String CE = "CE";

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
