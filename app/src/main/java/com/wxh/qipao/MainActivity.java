package com.wxh.qipao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bt_1;
    private BubblePopupWindow pop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_1 = (Button) findViewById(R.id.bt_1);
        bt_1.setOnClickListener(this);
        pop = new BubblePopupWindow(MainActivity.this);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View bubbleView = inflater.inflate(R.layout.layout_popup_view, null);
        TextView tvContent = (TextView) bubbleView.findViewById(R.id.tvContent);
        tvContent.setText("错误信息如下：\n" +
                "Error:Cannot change dependencies of configuration ':app:_debugAnnotationProcessor' after it has been\n" +
                "起因，在项目中我开启了jack编译器，使用了butterknife第三方工具的时候，引入了annotationProcessor,起初是没问题的，之后我再修改编译版本的时候，比如把BUILD_TOOLS_VERSION升到最高，然后对应的support-v7 v4的版本也提升到相应的版本后在运行 就会报这个问题。\n" +
                "解决方案\n" +
                "先把引入annotationProcessor的那句话注释掉在升级同步，之后在解开即可。\n" +
                "\n" +
                "作者：Ggx的代码之旅\n" +
                "链接：http://www.jianshu.com/p/4f04832e01a7\n" +
                "來源：简书\n" +
                "著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。");
        pop.setBubbleView(bubbleView); // 设置气泡内容
         // 显示弹窗
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_1:
                pop.show(bt_1,bt_1.getX(),bt_1.getY());
                break;
        }
    }
}
