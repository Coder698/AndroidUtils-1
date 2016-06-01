package cloud.cn.applicationtest.activity.news;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.applicationtest.R;

/**
 * Created by Cloud on 2016/5/30.
 */
@ContentView(R.layout.activity_news_reader)
public class NewsReaderActivity extends BaseActivity{
    @ViewInject(R.id.iv_back)
    private ImageView iv_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.iv_share)
    private ImageView iv_share;
    @ViewInject(R.id.iv_textsize)
    private ImageView iv_textsize;
    @ViewInject(R.id.wv_content)
    private WebView wv_content;
    private String url;

    @Override
    protected void initVariables() {
        url = getIntent().getStringExtra("url");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        wv_content.getSettings().setJavaScriptEnabled(true);
        wv_content.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //页面跳转强制用自己的webview加载
                view.loadUrl(url);
                return true;
            }
        });
        wv_content.loadUrl(url);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
