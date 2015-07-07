package de.tum.in.research.smartwatchinteraction.votingactivities;

import android.content.Context;
import android.graphics.drawable.Drawable;

import de.tum.in.research.smartwatchinteraction.R;

/**
 * Created by janosch on 07.07.15.
 */
public class VotingHelper {

    /**
     * Get the image from the location identifier
     * @param location
     * @return
     */
    public static Drawable getLocationImage(Context context, String location) {
        if (location.equals(context.getResources().getString(R.string.lmu_mensa))) {
            return context.getResources().getDrawable(R.drawable.lmu_mensa, null);
        } else if (location.equals(context.getResources().getString(R.string.lmu_losteria))) {
            return context.getResources().getDrawable(R.drawable.lmu_losteria, null);
        } else if (location.equals(context.getResources().getString(R.string.lmu_tijuana))) {
            return context.getResources().getDrawable(R.drawable.lmu_tijuana, null);
        }
        return null;
    }

    /**
     * Get the name from the location identifier
     * @param location
     * @return
     */
    public static String getLocationName(Context context, String location) {
        return context.getResources().getString(context.getResources().getIdentifier(location, "string", context.getPackageName()));
    }

}
