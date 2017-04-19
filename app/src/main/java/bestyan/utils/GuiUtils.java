package bestyan.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Bastian on 20.04.2017.
 */

public class GuiUtils {
    public static void disableEnableControls(boolean enable, ViewGroup viewGroup){
        for (int i = 0; i < viewGroup.getChildCount(); i++){
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup){
                disableEnableControls(enable, (ViewGroup)child);
            } else {
                child.setEnabled(enable);
            }
        }
    }
}
