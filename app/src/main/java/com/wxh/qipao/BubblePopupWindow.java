package com.wxh.qipao;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public class BubblePopupWindow extends PopupWindow {
    private int screenWidth;    //用于存储屏幕宽度
    private int screenHeight;   //用于存储屏幕高度
    private BubbleRelativeLayout bubbleView;
    private Context context;

    public BubblePopupWindow(Context context) {
        this.context = context;
        //获取设备屏幕的宽高
        getScreenMetrics(context);

        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setFocusable(true);
        setOutsideTouchable(false);
        setClippingEnabled(false);

        ColorDrawable dw = new ColorDrawable(0);
        setBackgroundDrawable(dw);
    }

    public void setBubbleView(View view) {
        bubbleView = new BubbleRelativeLayout(context);
        bubbleView.setBackgroundColor(Color.TRANSPARENT);
        bubbleView.addView(view);
        setContentView(bubbleView);
    }

    public void setParam(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

//    public void show(View parent) {
//        show(parent, Gravity.TOP, getMeasuredWidth() / 2);
//    }

    public void show(View parent, int gravity) {
        show(parent, gravity, getMeasuredWidth() / 2);
    }

    /**
     * 显示弹窗
     *
     * @param parent
     * @param gravity
     * @param bubbleOffset 气泡尖角位置偏移量。默认位于中间
     */
    public void show(View parent, int gravity, float bubbleOffset) {
        BubbleRelativeLayout.BubbleLegOrientation orientation = BubbleRelativeLayout.BubbleLegOrientation.TOP;
        if (!this.isShowing()) {
            switch (gravity) {
                case Gravity.BOTTOM:
                    orientation = BubbleRelativeLayout.BubbleLegOrientation.TOP;
                    break;
                case Gravity.TOP:
                    orientation = BubbleRelativeLayout.BubbleLegOrientation.BOTTOM;
                    break;
                default:
                    break;
            }
//            bubbleView.setBubbleParams(orientation, bubbleOffset); // 设置气泡布局方向及尖角偏移

            int[] location = new int[2];
            parent.getLocationOnScreen(location);

            switch (gravity) {
                case Gravity.BOTTOM:
                    showAsDropDown(parent);
                    break;
                case Gravity.TOP:
                    showAtLocation(parent, Gravity.NO_GRAVITY, location[0], location[1] - getMeasureHeight());
                    break;
                case Gravity.RIGHT:
                    showAtLocation(parent, Gravity.NO_GRAVITY, location[0] + parent.getWidth(), location[1] - (parent.getHeight() / 2));
                    break;
                case Gravity.LEFT:
                    showAtLocation(parent, Gravity.NO_GRAVITY, location[0] - getMeasuredWidth(), location[1] - (parent.getHeight() / 2));
                    break;
                default:
                    break;
            }
        } else {
            this.dismiss();
        }
    }

    /**
     * 显示弹窗
     * @param parent
     * @param x 在屏幕中的位置x
     * @param y 在屏幕中的位置y
     */
    public void show(View parent,float x,float y) {
        BubbleRelativeLayout.BubbleLegOrientation orientation;
        if (!this.isShowing()) {
            int width = getMeasuredWidth();
            int height = getMeasureHeight();
            int model = 0;
            //调整出最好的尖角方向（只有上下两个模式）
            if(y-height>screenHeight-y-height){
                model = 0;
                orientation = BubbleRelativeLayout.BubbleLegOrientation.BOTTOM;
            }else {
                model = 1;
                orientation = BubbleRelativeLayout.BubbleLegOrientation.TOP;
            }
            //传入在屏幕中x,y值,在Layout中计算偏移量,
            bubbleView.setBubbleParams(orientation,x,y); // 设置气泡布局方向及尖角偏移

            switch (model) {
                case 1:
                    showAtLocation(parent, Gravity.CENTER_HORIZONTAL, 0,height+parent.getHeight()+30);
                    break;
                case 0:
                    showAtLocation(parent, Gravity.CENTER_HORIZONTAL, (screenWidth-width)/2, (int)y);
                    break;
                default:
                    break;
            }
        } else {
            this.dismiss();
        }
    }
    /**
     * 测量高度
     *
     * @return
     */
    public int getMeasureHeight() {
        getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popHeight = getContentView().getMeasuredHeight();
        return popHeight;
    }

    /**
     * 测量宽度
     *
     * @return
     */
    public int getMeasuredWidth() {
        getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popWidth = getContentView().getMeasuredWidth();
        return popWidth;
    }


    /**
     * 获取屏幕宽高，单位px
     * @param context
     * @return
     */
    public void getScreenMetrics(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }
}