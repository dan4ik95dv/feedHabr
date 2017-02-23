package name.sportivka.feed.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by daniil on 23.02.17.
 */

public class AndroidUtils {
    public static void startActivitySafe(Context context, Intent activityIntent, int toastMessageId) {
        try {
            context.startActivity(activityIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, context.getString(toastMessageId), Toast.LENGTH_SHORT).show();
        }
    }
}
