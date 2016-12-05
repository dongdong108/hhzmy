package com.hhzmy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by asus on 2016/11/9.
 */
public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);


    }
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//
//            return true;
//
//        }
//
//        return super.dispatchTouchEvent(ev);
//
//    }
//
//    // 也可以写成这样，当用户按下的时候，我们告诉父组件，不要拦截我的事件（这个时候子组件是可以正常响应事件的），拿起之后就会告诉父组件可以阻止。
//    public boolean onTouch(View v, MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                requestDisallowInterceptTouchEvent(true);
//                return true;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                requestDisallowInterceptTouchEvent(false);
//                return true;
//        }
//        return false;
//    }

}
