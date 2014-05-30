package com.airensoft.reboundex.app;

import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.widget.TextView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {


    @ViewById(R.id.mTextView)
    TextView mTextView;

    private SpringSystem mSpringSystem;
    private Spring mScaleSpring;
    private ScaleSpringListener mScaleSpringListener;

    @AfterViews
    public void onAfterViews() {
        mSpringSystem = SpringSystem.create();
        mScaleSpring = mSpringSystem.createSpring();
        mScaleSpringListener = new ScaleSpringListener();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mScaleSpring.setEndValue(1);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mScaleSpring.setEndValue(0);
                break;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScaleSpring.removeListener(mScaleSpringListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScaleSpring.addListener(mScaleSpringListener);
    }

    private class ScaleSpringListener extends SimpleSpringListener {
        @Override
        public void onSpringUpdate(Spring spring) {
            float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(mScaleSpring.getCurrentValue(), 0, 1, 1, 0.5);
            mTextView.setScaleY(mappedValue);
            mTextView.setScaleX(mappedValue);
        }
    }



    @Click(R.id.mOrigamiEx)
    public void onClickOrigamiEx() {
        OrigamiExActivity_.intent(this).start();
    }
}
