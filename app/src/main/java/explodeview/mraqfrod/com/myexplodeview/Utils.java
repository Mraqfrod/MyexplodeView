package explodeview.mraqfrod.com.myexplodeview;

import android.content.res.Resources;

/**
 * Created by Administrator on 2015/12/25 0025.
 */
public class Utils {
    /**
     * 密度
     */
    public static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    public static int dp2px(int dp){
        return  Math.round(dp * DENSITY);
    }

}
