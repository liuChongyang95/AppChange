package Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class SlideLayout extends FrameLayout {


    private View mDragView;
    private Activity mActivity;
    private ViewDragHelper mViewDragHelper;
    private float mSlideWidth;
    private int mScreenWidth;
    private int mScreenHeight;
    private int curSlideX;

    public SlideLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mActivity = (Activity) context;
        mViewDragHelper = ViewDragHelper.create(this, new DragCallback());
        //设置可拖动的边缘
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    /**
     * 把DecorView下的子View移除添加到SlideLayout,再将SlideLayout添加到DecorView
     */
    public void bind() {
        ViewGroup mDecorView = (ViewGroup) mActivity.getWindow().getDecorView();
        mDragView = mDecorView.getChildAt(0);
        mDecorView.removeView(mDragView);
        this.addView(mDragView);
        mDecorView.addView(this);

        //计算屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        //触发Activity滑出的宽度
        mSlideWidth = dm.widthPixels * 0.5f;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return mViewDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    class DragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int left = releasedChild.getLeft();
            if (left <= mSlideWidth) {
                //返回
                mViewDragHelper.settleCapturedViewAt(0, 0);
            } else {
                //滑出
                mViewDragHelper.settleCapturedViewAt(mScreenWidth, 0);
            }
            //需要重绘
            invalidate();
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            curSlideX = left;
            invalidate();
            if (changedView == mDragView && left >= mScreenWidth) {
                mActivity.finish();
            }
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //限制左右拖拽的位移
            left = left >= 0 ? left : 0;
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            //触发边缘时，主动捕捉mDragView
            mViewDragHelper.captureChildView(mDragView, pointerId);
        }

    }

    @Override
    public void computeScroll() {
        //持续滚动期间，不断重绘
        if (mViewDragHelper.continueSettling(true))
            invalidate();

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //进行阴影绘制
        canvas.save();
        Shader mShader = new LinearGradient(curSlideX - 40, 0, curSlideX, 0,
                new int[]{Color.parseColor("#00000000"), Color.parseColor("#50000000")},
                null, Shader.TileMode.REPEAT);
        //绘制阴影画笔
        Paint mPaint = new Paint();
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);
        mPaint.setShader(mShader);
        RectF rectF = new RectF(curSlideX - 40, 0, curSlideX, mScreenHeight);
        canvas.drawRect(rectF, mPaint);
        canvas.restore();
    }
}

