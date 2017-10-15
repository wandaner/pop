package com.wxh.qipao;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public class BubblePopupWindow extends PopupWindow {
    private int screenWidth;    //用于存储屏幕宽度
    private int screenHeight;   //用于存储屏幕高度

    private int maxHeight;
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
    /**
     * 显示弹窗
     * @param parent
     */
    public void show(View parent,View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        float x = location[0]+view.getWidth()/2;
        float y = location[1];
        BubbleRelativeLayout.BubbleLegOrientation orientation;
        if (!this.isShowing()) {
            int width = getMeasuredWidth();
            int height = getMeasureHeight();
            Log.e("xukai","width:"+width+"   height:"+height);
            int positionX;
            int positionY;
            int offsetX = 0;
            int offsetY = 0;
            if(x<(width/2)){
                positionX = 0;
                offsetX = (int) (x);
                if(positionX+width>screenHeight-BubbleRelativeLayout.PADDING_H){
                    positionX = screenHeight-BubbleRelativeLayout.PADDING_H - width;
                }
            }else if((x+width/2)>screenWidth){
                positionX = screenWidth-width;
                offsetX = (int) (x-positionX);
                if(positionX+width>screenHeight-BubbleRelativeLayout.PADDING_H){
                    positionX = screenHeight-BubbleRelativeLayout.PADDING_H - width;
                }
            }else {
                positionX = (int) (x-width/2);
                offsetX = width/2;
                if(positionX+width>screenHeight-BubbleRelativeLayout.PADDING_H){
                    positionX = screenHeight-BubbleRelativeLayout.PADDING_H - width;
                }
            }
            //调整出最好的尖角方向（只有上下两个模式）
            if(y-height>screenHeight-y-height){//上方模式
                orientation = BubbleRelativeLayout.BubbleLegOrientation.BOTTOM;
                bubbleView.setBubbleParams(orientation,offsetX,offsetY); // 设置气泡布局方向及尖角偏移
                //在Layout中计算偏移量
                showAtLocation(parent, Gravity.NO_GRAVITY, positionX, (int)(y-height));
            }else {//下方模式
                orientation = BubbleRelativeLayout.BubbleLegOrientation.TOP;
                bubbleView.setBubbleParams(orientation,offsetX,offsetY); // 设置气泡布局方向及尖角偏移
                //在Layout中计算偏移量
                showAtLocation(parent, Gravity.NO_GRAVITY, positionX, (int) y+view.getHeight());
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
        popHeight = popHeight<(screenHeight-2*BubbleRelativeLayout.PADDING_V)?popHeight:(screenHeight-2*BubbleRelativeLayout.PADDING_V);
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
        popWidth = popWidth<(screenWidth-2*BubbleRelativeLayout.PADDING_H)?popWidth:(screenWidth-2*BubbleRelativeLayout.PADDING_H);
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