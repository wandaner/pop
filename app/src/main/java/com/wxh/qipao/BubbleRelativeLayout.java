package com.wxh.qipao;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * 气泡布局
 */
public class BubbleRelativeLayout extends FrameLayout {

    /**
     * 气泡尖角方向，只有尖角在上和尖角在下
     */
    public enum BubbleLegOrientation {
        TOP, BOTTOM
    }

    private int offsetX;
    public static int PADDING_V = 30;
    public static int PADDING_H = 10;
    public static int LEGSIZE = 30;
    public static float STROKE_WIDTH = 2.0f;
    public static float CORNER_RADIUS = 8.0f;
    public static int SHADOW_COLOR = Color.argb(100, 0, 0, 0);

    private Paint mFillPaint = null;
    private final Path mPath = new Path();
    private final Path mBubbleLegPrototype = new Path();
    private final Paint mPaint = new Paint(Paint.DITHER_FLAG);

    private BubbleLegOrientation mBubbleOrientation = BubbleLegOrientation.TOP;

    public BubbleRelativeLayout(Context context) {
        this(context, null);
    }

    public BubbleRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(final Context context) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);

        mPaint.setColor(SHADOW_COLOR);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(STROKE_WIDTH);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        mPaint.setPathEffect(new CornerPathEffect(CORNER_RADIUS));

        if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(LAYER_TYPE_SOFTWARE, mPaint);
        }

        mFillPaint = new Paint(mPaint);
        mFillPaint.setColor(Color.WHITE);
        mFillPaint.setShader(new LinearGradient(100f, 0f, 100f, 200f, Color.WHITE, Color.WHITE, Shader.TileMode.CLAMP));

        if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(LAYER_TYPE_SOFTWARE, mFillPaint);
        }
        mPaint.setShadowLayer(2f, 2F, 5F, SHADOW_COLOR);

        renderBubbleLegPrototype();

        setPadding(PADDING_V, PADDING_V, PADDING_V, PADDING_V);

    }


    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 尖角path
     */
    private void renderBubbleLegPrototype() {
        mBubbleLegPrototype.moveTo(0, 0);
        mBubbleLegPrototype.lineTo(LEGSIZE * 1.5f, -LEGSIZE / 1.5f);
        mBubbleLegPrototype.lineTo(LEGSIZE * 1.5f, LEGSIZE / 1.5f);
        mBubbleLegPrototype.close();
    }

    public void setBubbleParams(final BubbleLegOrientation bubbleOrientation,int offsetX,int offsetY) {
        mBubbleOrientation = bubbleOrientation;
        offsetX = offsetX>LEGSIZE?offsetX:LEGSIZE;
        this.offsetX = offsetX;
    }

    /**
     * 根据显示方向，获取尖角位置矩阵
     * @param width
     * @param height
     * @return
     */
    private Matrix renderBubbleLegMatrix(final float width, final float height) {

        float dstX = 0;
        float dstY = 0;
        final Matrix matrix = new Matrix();
        if(offsetX>width){
            offsetX = (int) (width-LEGSIZE-PADDING_H);
        }
        switch (mBubbleOrientation) {
            case TOP:
                dstX = offsetX;
                dstY = 0;
                matrix.postRotate(90);
                break;
            case BOTTOM:
                dstX = offsetX;
                dstY = height;
                matrix.postRotate(270);
                break;
        }
        matrix.postTranslate(dstX, dstY);
        return matrix;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        final float width = canvas.getWidth();
        final float height = canvas.getHeight();
        mPath.rewind();
        mPath.addRoundRect(new RectF(PADDING_H, PADDING_V, width-PADDING_H, height-PADDING_V), CORNER_RADIUS, CORNER_RADIUS, Path.Direction.CW);
        mPath.addPath(mBubbleLegPrototype, renderBubbleLegMatrix(width, height));

        canvas.drawPath(mPath, mPaint);
        canvas.scale((width - STROKE_WIDTH) / width, (height - STROKE_WIDTH) / height, width / 2f, height / 2f);

        canvas.drawPath(mPath, mFillPaint);
    }
}