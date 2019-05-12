package com.dannydiao.kuaihui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

public class AboutActivity extends AppCompatActivity {


    private static final String MARKDOWN = "# 快汇1.2 RELEASE\n" +
            "\n" +
            "快汇是一个高效率的汇率转换工具，开源托管在Github上。\n" +
            "\n" +
            "[Github项目地址 - 快汇](https://github.com/DannyDiao/KuaiHui)\n" +
            "\n" +
            "\n" +
            "\n" +
            "## 特性\n" +
            "\n" +
            "- 没有启动广告\n" +
            "\n" +
            "- 没有内置广告\n" +
            "\n" +
            "- 完全免费\n" +
            "\n" +
            "- 汇率实时获取\n" +
            "\n" +
            "- 低内存占用\n" +
            "\n" +
            "## 更新日志\n" +
            "### 1.2 RELEASE\n" +
            "- 增加了对越南盾VND、新西兰元NZD、瑞士法郎CHF的支持\n" +
            "- 现在汇率列表可以正常滑动了\n" +
            "\n" +
            "## 联系我\n" +
            "如果你有任何反馈或者建议，欢迎联系我。\n" +
            "- 邮箱：diaosu@diaosudev.cn\n" +
            "- Github Issue\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView textView = findViewById(R.id.markdown);
        RichText.fromMarkdown(MARKDOWN).into(textView);
    }
}
