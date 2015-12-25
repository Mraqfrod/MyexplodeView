package explodeview.mraqfrod.com.myexplodeview;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

/**
 * 控制爆炸的 Animator
 */
public class MyExplosionAnimator extends ValueAnimator{

    public static final int DEFAULT_DURATION =1500;//默认动画持续时间
    private Paint mPaint ;//画笔
    private View mContainner ;// 需要爆炸的view
    private MyParticle [] [] myParticles ; //记录粒子的二维数组;

    public MyExplosionAnimator (View v,Bitmap bitmap,Rect bound){

        mPaint = new Paint();
        mContainner = v  ;
        setFloatValues(0.0f,1.0f);//设置view从透明到不透明;
        setDuration(DEFAULT_DURATION);
        myParticles = getParticles(bitmap,bound);

    }

    private MyParticle[][] getParticles(Bitmap bitmap, Rect bound) {

        int w = bound.width();
        int h = bound.height();

        int partW_Count = w / MyParticle.PART_WH; // 记录一排有多少个粒子
        int partH_Count = h /MyParticle.PART_WH; // 记录一列有多少个粒子

        int bitmap_part_w = bitmap.getWidth()/partH_Count; // 记录粒子 x轴坐标 ， 用于获取颜色
        int bitmap_part_h = bitmap.getHeight()/partH_Count;// y轴

        MyParticle [] [] particles = new MyParticle[partH_Count][partW_Count];//创建容器
        Point point =null;//记录粒子的点
        for (int row = 0 ; row<partH_Count; row++ ){//每行
            for(int colum =0;colum < partW_Count; colum++){//每列
                //通过 每行 每列  乘 x  y  获得 每个粒子的颜色值
                int color = bitmap.getPixel(colum * bitmap_part_w,row*bitmap_part_h );
                point = new Point(colum,row);//记录坐标
                //生成每个点的粒子
                particles [row][colum] = MyParticle.getParticle(color,bound,point);
            }
        }
        return particles;
    }

    /**
     *   画粒子运动的方法
     * @param canvas
     */
    public void draw(Canvas canvas){
        if(!isStarted()){//动画结束就停止
            return ;
        }
        for(MyParticle [] particle : myParticles){
            for(MyParticle p : particle){
                p.explosion((Float) getAnimatedValue());//不断制造爆炸
                mPaint.setColor(p.color);
                mPaint.setAlpha((int) (Color.alpha(p.color)*p.alpha));
                canvas.drawCircle(p.cx,p.cy,p.radius,mPaint); //画粒子
            }
        }
    }

    @Override
    public void start() {
        super.start();
        mContainner.invalidate();
    }
}
