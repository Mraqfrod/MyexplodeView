package explodeview.mraqfrod.com.myexplodeview;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.Random;

/**
 *  爆炸的 粒子 类
 */
public class MyParticle {
    public static final int PART_WH = 8 ;//默认粒子的宽高为8
    float cx; // 粒子x轴坐标
    float cy;// 粒子y轴坐标
    float radius;//粒子 半径
    int  color ; //粒子颜色
    float alpha;//粒子透明度
    static Random random = new Random();
    Rect mBound;//粒子所在位置的矩阵

    /**
     *    用于生成每个坐标点的 粒子
     * @param color
     * @param bound
     * @param point
     * @return
     */
    public static MyParticle getParticle(int color, Rect bound , Point point){
        int row = point.x;
        int column =point.y;

        MyParticle particle =new MyParticle();
        particle.mBound = bound;
        particle.color=color;
        particle.alpha =1f;

        particle.radius = PART_WH/2;
        // bount .left 是原view 距离屏幕的距离 加上 粒子的宽  就是粒子的坐标了
        particle.cx = bound.left +PART_WH * column;//
        particle.cy =bound.top + PART_WH * row;
        return  particle;
    }

    /**
     *   粒子爆炸的方法
     * @param factor 透明度 0-1 不断增大
     */
 public void explosion(float  factor){
     //随机改变 cx 大小 让 粒子 左右飘
     cx = cx +factor * random.nextInt(mBound.width())*(random.nextFloat() - 0.5f);
     // cx 不断增大 让 粒子下落
     cy = cy +factor * random .nextInt(mBound.height()/2);
    // 半径越来越小
     radius =radius - factor * random.nextInt(2);
     // 越来越透明
     alpha = (1f - factor)* (1+random.nextFloat());
 }



}
