package com.wxh.qipao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private BubblePopupWindow pop;
    private ListView lv_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pop = new BubblePopupWindow(MainActivity.this);
        lv_1 = (ListView) findViewById(R.id.lv_1);
        lv_1.setAdapter(new BaseAdapter() {
            ImageView iv_1;
            ImageView iv_2;
            ImageView iv_3;
            ImageView iv_4;
            ImageView iv_5;
            @Override
            public int getCount() {
                return 20;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.main_lv_item,null);
                iv_1 = (ImageView) convertView.findViewById(R.id.iv_1);
                iv_2 = (ImageView) convertView.findViewById(R.id.iv_2);
                iv_3 = (ImageView) convertView.findViewById(R.id.iv_3);
                iv_4 = (ImageView) convertView.findViewById(R.id.iv_4);
                iv_5 = (ImageView) convertView.findViewById(R.id.iv_5);
                setClick();
                return convertView;
            }
            public void setClick(){
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Log.e("xukai","x:"+v.getX());
//                        Log.e("xukai","y:"+v.getY());
                        show(v);
                    }
                };
                iv_1.setOnClickListener(listener);
                iv_2.setOnClickListener(listener);
                iv_3.setOnClickListener(listener);
                iv_4.setOnClickListener(listener);
                iv_5.setOnClickListener(listener);
            }
        });
        lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View bubbleView = inflater.inflate(R.layout.bubble_pop_content, null);
        BubbleContextListView lv = (BubbleContextListView) bubbleView.findViewById(R.id.lv);
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 14;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.bubble_pop_item,null);
                TextView tv = (TextView) convertView.findViewById(R.id.tv_id);
                tv.setText("id_"+position);
                return convertView;
            }
        });
        pop.setBubbleView(bubbleView); // 设置气泡内容
         // 显示弹窗
    }

    @Override
    public void onClick(View v) {
        show(v);
    }

    private void show(View view){
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        Log.e("xukai","locationX:"+location[0]);
        Log.e("xukai","locationY:"+location[1]);
        pop.show(contentView,view);
    }
}
