package explodeview.mraqfrod.com.myexplodeview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;

/**
 * 爆炸的 "背景" view
 *
  */
public class MyExplosionFiled extends View{
    public MyExplosionFiled(Context context) {
        super(context);
    }

    public MyExplosionFiled(Context context, AttributeSet attrs) {
        super(context, attrs);
        Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888);
    }


}
