package com.app.purecookbook.purecookbook;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class About extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        ((TextView) findViewById(R.id.activity_title)).setText("关 于");

        findViewById(R.id.back_image).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
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
