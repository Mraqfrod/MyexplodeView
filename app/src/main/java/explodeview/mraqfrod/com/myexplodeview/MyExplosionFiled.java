package explodeview.mraqfrod.com.myexplodeview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;

/**
 * 爆炸的 "背景" view
 */
public class MyExplosionFiled extends View {

    private static final Canvas mCanvas = new Canvas(); //画布
    private ArrayList<MyExplosionAnimator> explosionAnimators;//记录每个view的动画
    private OnClickListener mOnClickListener;


    public MyExplosionFiled(Context context) {
        super(context);
        init();
    }

    public MyExplosionFiled(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     *  初始化方法
     */
    private void init() {
        explosionAnimators =new ArrayList<MyExplosionAnimator>();
            attach2Activity((Activity) getContext());
    }

    public void explode(final  View v){
        Rect rect = new Rect();
        v.getGlobalVisibleRect(rect);//得到view相对整个屏幕的坐标
        rect.offset(0,-Utils.dp2px(75));//减掉状态栏的高
        final MyExplosionAnimator animator =new MyExplosionAnimator(this,createBitmapFromView(v),rect);
        explosionAnimators.add(animator);
            //把 原view 跟爆炸关联起来
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                v.animate().alpha(0f).setDuration(150).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
               v.animate().alpha(1f).setDuration(150).start();
                explosionAnimators.remove(animation);
                animation = null;
            }
        });

            animator.start();

    }

    /**
     *  给Activity 加上全覆盖的ExplosionFiled
     * @param activity
     */
        private void attach2Activity(Activity activity){

            //把整个activity 的view 加进去
           ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
            Log.i("aaa"," root = "+rootView.getParent());
//            ViewGroup p = (ViewGroup) rootView.getParent();
//            if (p != null) {
//                p.removeAllViews();
//            }
            Log.i("aaa"," root = "+rootView.getParent());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT
            );
            rootView.addView(this, lp);
        }


        public void addListener(View v){
            if(v instanceof  ViewGroup){
                ViewGroup viewGroup =(ViewGroup)v;
                int count = viewGroup.getChildCount();
                for(int i=0; i<count;i++){
                    addListener(viewGroup.getChildAt(i));
                }
            } else {
                v.setClickable(true);
                v.setOnClickListener(getOnClickListener());
            }
        }

    public OnClickListener getOnClickListener() {

            if(null == mOnClickListener){
                mOnClickListener = new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        MyExplosionFiled.this.explode(v);

                     //   v.setOnClickListener(null);// 点击一次后就不需要了
                    }
                };
            }
        return mOnClickListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(MyExplosionAnimator animator : explosionAnimators){
            animator.draw(canvas);
        }
    }

    /**
     *   复制 需要爆炸的view
     * @param v
     * @return
     */
    private Bitmap createBitmapFromView(View v) {
        Bitmap bitmap = createBitmapSafely(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888, 1);
        if(bitmap!=null){
            synchronized (mCanvas){
                mCanvas.setBitmap(bitmap);//设置空白画布
                v.draw(mCanvas);//把自己画上去
                mCanvas.setBitmap(null);//画完清空引用
            }
        }
            return bitmap;
    }

    /**
     *
     * @param width
     * @param height
     * @param argb8888
     * @param retryCount
     * @return  复制原图 防止内存溢出。
     */
    private Bitmap createBitmapSafely(int width, int height, Bitmap.Config argb8888, int retryCount) {
        try {
            return Bitmap.createBitmap(width, height, argb8888);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (retryCount > 0) {
                System.gc();
                return createBitmapSafely(width, height, argb8888, retryCount - 1);
            }
            return null;
        }
    }
}
