package com.xinzhihui.backline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xinzhihui.backline.view.BackLineView;

public class MainActivity extends AppCompatActivity {

    private Button mEditBtn;
    private BackLineView mBackLineView;

    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditBtn = (Button) findViewById(R.id.btn_edit);
        mBackLineView = (BackLineView) findViewById(R.id.blv_daoche);

        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
                    isEdit = false;
                    mBackLineView.isEdit = false;
                    mBackLineView.postInvalidate();
                } else {
                    isEdit = true;
                    mBackLineView.isEdit = true;
                    mBackLineView.postInvalidate();
                }

            }
        });
    }
}
