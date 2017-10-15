package com.wxh.qipao;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 气泡中显示的listView,重写高度
 * Created by xukai on 2017-10-14.
 */

public class BubbleContextListView extends ListView{
    //最大显示item数
    private static final int limetItem = 4;
    public BubbleContextListView(Context context) {
        super(context);
    }

    public BubbleContextListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BubbleContextListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 复写setAdapter，计算宽度，同时可以限制高度:最大显示4个item
     * @param adapter
     */
    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        setMaxHeight();
    }

    public void setMaxHeight(){
        ListAdapter adapter = getAdapter();
        if (adapter == null||adapter.getCount()<=0) {
            return;
        }
        int totalHeight = 0;
        int maxWidth = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, this);
            listItem.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            //大于限制item数则不继续增加高度，但是最大宽度继续计算
            if(i<limetItem){
                totalHeight+= listItem.getMeasuredHeight()+this.getDividerHeight();
            }
            int width = listItem.getMeasuredWidth();
            if(width>maxWidth)maxWidth = width;
        }
        int maxHeight = totalHeight;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = maxHeight;
        params.width = maxWidth;
        this.setLayoutParams(params);
    }
}
