package com.app.purecookbook.purecookbook;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FeedBackActivity extends BaseActivity {

    private EditText mFeedBackEt;
    private Button mSendBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_view);
        ((TextView) findViewById(R.id.activity_title)).setText("信息反馈");
        findViewById(R.id.back_image).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mFeedBackEt = (EditText) findViewById(R.id.fee_back_edit);
        mSendBtn = (Button) findViewById(R.id.feed_back_btn);
        mSendBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String content = mFeedBackEt.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Pure菜谱 - 信息反馈");
                    intent.putExtra(Intent.EXTRA_TEXT, content);
                    intent.setData(Uri.parse("mailto:jingtingfengguo@163.com"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    FeedBackActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(FeedBackActivity.this, "多说几句吧！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void initBefore() {

    }

    @Override
    public void init() {

    }

    @Override
    public void initAfter() {

    }
}
