package com.xinzhihui.backline.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BackLineView extends View implements View.OnTouchListener {

    public Context mContext;
    public boolean isEdit = false;
    public int mWidth; //View宽度
    public int mHeight; //View高度

    private static final int LEFT_ONE = 0x16;
    private static final int LEFT_TWO = 0x17;
    private static final int LEFT_THR = 0x18;
    private static final int LEFT_FOR = 0x19;
    private static final int RIGHT_ONE = 0x26;
    private static final int RIGHT_TWO = 0x27;
    private static final int RIGHT_THR = 0x28;
    private static final int RIGHT_FOR = 0x29;

    public int leftPadingX;  //左边距
    public int rightPadingX;  //右边距

    public int topPadingY;  //上边距
    public int bottomPadingY;  //下边距
    public int miniPadding = 16;  //最小边距


    public int realWidth;
    public int realHeight;

    public Paint mLinePaint;  //线条画笔
    public Path mPath;
    public Paint mCirclePaint;  //小圆圈画笔
    public Paint mTextPaint;  //字符画笔

    int[] point1;
    int[] point2;
    int[] point3;
    int[] point4;
    int[] point5;
    int[] point6;
    int[] point7;
    int[] point8;

    private int dragDirection;

    protected int lastX;
    protected int lastY;


    public BackLineView(Context context) {
        this(context, null);
    }

    public BackLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
//        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(mWidth, mHeight);

        init(mWidth, mHeight);
    }


    public void init(int width, int height) {
        mLinePaint = new Paint();
        mPath = new Path();

        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.GRAY);
        mCirclePaint.setStrokeWidth(2);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setAntiAlias(true); // 抗锯齿

        mTextPaint = new Paint();
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true); // 抗锯齿
        mTextPaint.setTextSize(14);

        leftPadingX = miniPadding;
        rightPadingX = miniPadding;
        topPadingY = miniPadding;
        bottomPadingY = miniPadding;

        realWidth = width - leftPadingX - rightPadingX;
        realHeight = height - topPadingY - bottomPadingY;

        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        point1 = new int[]{leftPadingX, topPadingY + realHeight};
        point2 = new int[]{realWidth * 1 / 3 * 1 / 3 + leftPadingX, topPadingY + realHeight * 2 / 3};
        point3 = new int[]{realWidth * 1 / 3 * 2 / 3 + leftPadingX, topPadingY + realHeight * 1 / 3};
        point4 = new int[]{realWidth * 1 / 3 * 3 / 3 + leftPadingX, topPadingY};
        point5 = new int[]{mWidth - realWidth * 1 / 3 - rightPadingX, topPadingY};
        point6 = new int[]{mWidth - realWidth * 1 / 3 * 2 / 3 - rightPadingX, topPadingY + realHeight * 1 / 3};
        point7 = new int[]{mWidth - realWidth * 1 / 3 * 1 / 3 - rightPadingX, topPadingY + realHeight * 2 / 3};
        point8 = new int[]{mWidth - rightPadingX, topPadingY + realHeight * 3 / 3};

        mLinePaint.reset();
        mLinePaint.setStyle(Paint.Style.STROKE);// 空心
        mLinePaint.setAntiAlias(true); // 抗锯齿
        mLinePaint.setStrokeWidth(5); // 粗细

        //TODO 实线
        // Line 3-4 5-6
        mLinePaint.setColor(Color.GREEN);
        mPath.moveTo(point3[0], point3[1]); // 3
        mPath.lineTo(point4[0], point4[1]); // 4
        mPath.moveTo(point5[0], point5[1]); // 5
        mPath.lineTo(point6[0], point6[1]); // 6
        canvas.drawPath(mPath, mLinePaint);
        mTextPaint.setColor(Color.GREEN);
        drawMyText(canvas, mTextPaint, point4[0], point4[1], "5m", true);
        drawMyText(canvas, mTextPaint, point5[0], point5[1], "5m", false);

        // Line 2-3 6-7
        mLinePaint.setColor(Color.YELLOW);
        mPath.reset();
        mPath.moveTo(point2[0], point2[1]); // 2
        mPath.lineTo(point3[0], point3[1]); // 3
        mPath.moveTo(point6[0], point6[1]); // 6
        mPath.lineTo(point7[0], point7[1]); // 7
        canvas.drawPath(mPath, mLinePaint);
        mTextPaint.setColor(Color.YELLOW);
        drawMyText(canvas, mTextPaint, point3[0], point3[1], "3m", true);
        drawMyText(canvas, mTextPaint, point6[0], point6[1], "3m", false);

        // Line 1-2 7-8
        mLinePaint.setColor(Color.RED);
        mPath.reset();
        mPath.moveTo(point1[0], point1[1]); // 1
        mPath.lineTo(point2[0], point2[1]); // 2
        mPath.moveTo(point7[0], point7[1]); // 7
        mPath.lineTo(point8[0], point8[1]); // 8
        canvas.drawPath(mPath, mLinePaint);
        mTextPaint.setColor(Color.RED);

        drawMyText(canvas, mTextPaint, point2[0], point2[1], "STOP", true);
        drawMyText(canvas, mTextPaint, point7[0], point7[1], "STOP", false);

        //TODO 虚线
        PathEffect effect = new DashPathEffect(new float[]{10, 10, 10, 10},
                1); // {实线,空白,实线,空白:偶数}
        mLinePaint.setPathEffect(effect);
        // 4--5
        mPath.reset();
        mLinePaint.setColor(Color.GREEN);
        mPath.moveTo(point4[0], point4[1]); // 4
        mPath.lineTo(point5[0], point5[1]); // 5
        canvas.drawPath(mPath, mLinePaint);
        // 3--6
        mPath.reset();
        mLinePaint.setColor(Color.YELLOW);
        mPath.moveTo(point3[0], point3[1]); // 3
        mPath.lineTo(point6[0], point6[1]); // 6
        canvas.drawPath(mPath, mLinePaint);
        // 2--7
        mPath.reset();
        mLinePaint.setColor(Color.RED);
        mPath.moveTo(point2[0], point2[1]); // 2
        mPath.lineTo(point7[0], point7[1]); // 7
        canvas.drawPath(mPath, mLinePaint);

        mPath.reset();


        //TODO 画圈圈
        if (isEdit) {
            int radius = 10;
            canvas.drawCircle(point1[0], point1[1], radius, mCirclePaint);
//        canvas.drawCircle(point2[0], point2[1], radius, mCirclePaint);
//        canvas.drawCircle(point3[0], point3[1], radius, mCirclePaint);
            canvas.drawCircle(point4[0], point4[1], radius, mCirclePaint);
            canvas.drawCircle(point5[0], point5[1], radius, mCirclePaint);
//        canvas.drawCircle(point6[0], point6[1], radius, mCirclePaint);
//        canvas.drawCircle(point7[0], point7[1], radius, mCirclePaint);
            canvas.drawCircle(point8[0], point8[1], radius, mCirclePaint);
        }
        Log.d("qiansheng", "dragDirection!!!!!!!!!!!!!!!");
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isEdit) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
//            oriLeft = v.getLeft();
//            oriRight = v.getRight();
//            oriTop = v.getTop();
//            oriBottom = v.getBottom();
                lastY = (int) event.getRawY();
                lastX = (int) event.getRawX();
                dragDirection = getDirection(v, (int) event.getX(),
                        (int) event.getY());
                Log.d("qiansheng", "dragDirection:" + dragDirection);
            }
            // 处理拖动事件

            delDrag(v, event, action);
            invalidate();
        }
        return true;
    }


    /**
     * 获取触摸点flag
     *
     * @param v
     * @param x
     * @param y
     * @return
     */
    protected int getDirection(View v, int x, int y) {
        int left = v.getLeft();
        int right = v.getRight();
        int bottom = v.getBottom();
        int top = v.getTop();
        if (getDistance(x, y, point1[0], point1[1]) <= 16) {
            return LEFT_ONE;
        }
        if (getDistance(x, y, point2[0], point2[1]) <= 16) {
            return LEFT_TWO;
        }
        if (getDistance(x, y, point3[0], point3[1]) <= 16) {
            return LEFT_THR;
        }
        if (getDistance(x, y, point4[0], point4[1]) <= 16) {
            return LEFT_FOR;
        }
        if (getDistance(x, y, point5[0], point5[1]) <= 16) {
            return RIGHT_ONE;
        }
        if (getDistance(x, y, point6[0], point6[1]) <= 16) {
            return RIGHT_TWO;
        }
        if (getDistance(x, y, point7[0], point7[1]) <= 16) {
            return RIGHT_THR;
        }
        if (getDistance(x, y, point8[0], point8[1]) <= 16) {
            return RIGHT_FOR;
        }
        return 0;
    }

    /**
     * 处理拖动事件
     *
     * @param v
     * @param event
     * @param action
     */
    protected void delDrag(View v, MotionEvent event, int action) {
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                switch (dragDirection) {
                    case LEFT_ONE: // 左边缘
                        left(v, dx);
                        break;
                    case LEFT_TWO: // 右边缘
//                        right(v, dx);
                        break;
                    case LEFT_THR: // 下边缘
//                        bottom(v, dy);
                        break;
                    case LEFT_FOR: // 上边缘
                        top(v, dy);
                        break;
                    case RIGHT_ONE: // 点击中心-->>移动
                        top(v, dy);
//                        center(v, dx, dy);
                        break;
                    case RIGHT_TWO: // 左下
//                        left(v, dx);
//                        bottom(v, dy);
                        break;
                    case RIGHT_THR: // 左上
//                        left(v, dx);
//                        top(v, dy);
                        break;
                    case RIGHT_FOR: // 右下
                        right(v, dx);
//                        bottom(v, dy);
                        break;

                    default:
                        ;
                        break;
                }
//                if (dragDirection != CENTER) {
//                    v.layout(oriLeft, oriTop, oriRight, oriBottom);
//                }
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                dragDirection = 0;
                break;
        }
    }

    public void left(View v, int dx) {
        leftPadingX = leftPadingX + dx;
        if (leftPadingX <= miniPadding) {
            leftPadingX = miniPadding;
        }
        if (leftPadingX >= mWidth - miniPadding) {
            leftPadingX = mWidth - miniPadding;
        }
        realWidth = mWidth - leftPadingX - rightPadingX;
    }

    public void right(View v, int dx) {
        rightPadingX = rightPadingX - dx;
        if (rightPadingX <= miniPadding) {
            rightPadingX = miniPadding;
        }
        if (rightPadingX >= mWidth - miniPadding) {
            rightPadingX = mWidth - miniPadding;
        }
        realWidth = mWidth - leftPadingX - rightPadingX;
    }

    public void top(View v, int dy) {
        topPadingY = topPadingY + dy;
        if (topPadingY >= mHeight - miniPadding) {
            topPadingY = mHeight - miniPadding;
        }
        if (topPadingY <= miniPadding) {
            topPadingY = miniPadding;
        }
        realHeight = mHeight - topPadingY - bottomPadingY;
    }

    /**
     * 获取两个坐标之间的距离
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static float getDistance(float x1, float y1, float x2, float y2) {//坐标轴上两点之间的距离
        float x = 0, y = 0;
        x = x1 > x2 ? (x1 - x2) : (x2 - x1);
        y = y1 > y2 ? (y1 - y2) : (y2 - y1);
        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    /**
     * 左右两边写字
     *
     * @param canvas
     * @param textPaint
     * @param pointX
     * @param pointY
     * @param text
     * @param isLeft
     */
    public void drawMyText(Canvas canvas, Paint textPaint, float pointX, float pointY, String text, boolean isLeft) {
        float textWidth = textPaint.measureText(text);
        float x;
        if (isLeft) {
            x = pointX - 28 * 1 / 2;
//            x = pointX - textWidth*1/2;
            textPaint.setTextAlign(Paint.Align.RIGHT);
//            x = pointX - 28*3/2;
        } else {
            x = pointX + 28 * 1 / 2;
//            x = pointX + textWidth*1/2;
            textPaint.setTextAlign(Paint.Align.LEFT);
//            x = pointX + 36*1/2;
        }


        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        //metrics.ascent为负数
        float dy = -(metrics.descent + metrics.ascent) / 2;
        float y = pointY + dy;
        canvas.drawText(text, x, y, textPaint);
    }
}
