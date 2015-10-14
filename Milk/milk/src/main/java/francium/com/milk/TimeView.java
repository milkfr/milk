package francium.com.milk;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Francium on 2015/8/2.
 */
public class TimeView extends TextView {

    private TimeView timeView;

    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                refreshTime();
                if (getVisibility() == View.VISIBLE) {
                    timeHandler.sendEmptyMessageDelayed(0, 1000);
                }
            }
        }
    };

    // 初始化操作
    public TimeView(Context context) {
        super(context);
    }

    public TimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 从XML文件inflate的最终步骤执行
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (findViewById(R.id.time_view_white) == null) {
            timeView = (TimeView) findViewById(R.id.time_view_black);
        } else {
            timeView = (TimeView) findViewById(R.id.time_view_white);
        }
        timeView.setText("");
        timeHandler.sendEmptyMessage(0);
    }

    // view可视和不可视时的优化
    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        // 此处使用getVisibility出错了？？？
        if (visibility == View.VISIBLE) {
            timeHandler.sendEmptyMessage(0);
        } else {
            timeHandler.removeMessages(0);
        }
    }

    private void refreshTime() {
        if (timeView.getId() == R.id.time_view_white) {
            timeView.setText(Util.refreshLiveTime());
        } else {
            timeView.setText(Util.refreshDeathTime());
        }
    }
}
