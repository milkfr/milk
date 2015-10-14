package francium.com.milk;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

/**
 * Created by Francium on 2015/8/2.
 */
public class ClockView extends View {

    // 时钟图像
    private Drawable drawableClock;
    private Drawable drawableHour;
    private Drawable drawableMinute;
    private Drawable drawableSecond;
    private Drawable drawableCenter;
    private Drawable drawableCircle;
    private Drawable drawableGuild;

    private int Status;

    // 线程处理
    private Handler clockHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (getVisibility() == View.VISIBLE) {
                postInvalidate();
                clockHandler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };


    // 初始化操作
    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (findViewById(R.id.clock_white) != null) {
            Status = 1;
        } else {
            Status = 2;
        }

        // 使用TypedArray方式获取资源，获取资源完毕以后要进行回收
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClockStyleable, defStyleAttr, 0);
        drawableClock = ta.getDrawable(R.styleable.ClockStyleable_clock);
        drawableSecond = ta.getDrawable(R.styleable.ClockStyleable_second);
        drawableCircle = ta.getDrawable(R.styleable.ClockStyleable_circle);
        if (Status == 1) {
            drawableCenter = ta.getDrawable(R.styleable.ClockStyleable_center_white);
            drawableHour = ta.getDrawable(R.styleable.ClockStyleable_hour_white);
            drawableMinute = ta.getDrawable(R.styleable.ClockStyleable_minute_white);
            drawableGuild = ta.getDrawable(R.styleable.ClockStyleable_guild_white);
        } else {
            drawableCenter = ta.getDrawable(R.styleable.ClockStyleable_center_black);
            drawableHour = ta.getDrawable(R.styleable.ClockStyleable_hour_black);
            drawableMinute = ta.getDrawable(R.styleable.ClockStyleable_minute_black);
            drawableGuild = ta.getDrawable(R.styleable.ClockStyleable_guild_black);
        }
        ta.recycle();

        clockHandler.sendEmptyMessage(0);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获取当前视图的中心位置
        int viewCenterX = (getRight() - getLeft()) / 2;
        int viewCenterY = (getBottom() - getTop()) / 2;

        // 获取时钟表盘的尺寸
        int clockWidth = drawableClock.getIntrinsicWidth();
        int clockHeight = drawableClock.getIntrinsicHeight();

        // 绑定表盘
        drawableClock.setBounds(viewCenterX - clockWidth / 2, viewCenterY - clockHeight / 2, viewCenterX + clockWidth / 2, viewCenterY + clockHeight / 2);
        // 画表盘
        drawableClock.draw(canvas);

        // 如果为黑，还circle
        if (Status == 2) {
            int circleWidth = drawableCircle.getIntrinsicWidth();
            int circleHeight = drawableCircle.getIntrinsicHeight();
            drawableCircle.setBounds(viewCenterX - circleWidth / 2, viewCenterY - circleHeight / 2, viewCenterX + circleWidth / 2, viewCenterY + circleHeight / 2);
            drawableCircle.draw(canvas);
        }


        // 画时间指示
        int hi = (int)(clockHeight * 0.3);
        for (int i = 0; i < 4; i++) {
            canvas.rotate(90, viewCenterX, viewCenterY);
            int w = drawableGuild.getIntrinsicWidth();
            int h = drawableGuild.getIntrinsicHeight();
            drawableGuild.setBounds(viewCenterX - w / 2, viewCenterY - hi - h, viewCenterX + w / 2, viewCenterY - hi);
            drawableGuild.draw(canvas);
        }

        // 获取时间
        Calendar calendar = Calendar.getInstance();

        // 画时针
        canvas.save();
        canvas.rotate(calendar.get(Calendar.HOUR) * 30.0F + calendar.get(Calendar.MINUTE) * 0.5F + calendar.get(Calendar.SECOND) * 0.5F / 60,  viewCenterX, viewCenterY);
        int hourWidth = drawableHour.getIntrinsicWidth();
        int hourHeight = drawableCenter.getIntrinsicHeight();
        drawableHour.setBounds(viewCenterX - hourWidth / 2, viewCenterY - (int)(hourHeight * 1.8) + 20, viewCenterX + hourWidth / 2, viewCenterY + 20);
        drawableHour.draw(canvas);
        canvas.restore();

        // 画分针
        canvas.save();
        canvas.rotate(calendar.get(Calendar.MINUTE) * 6.0F + calendar.get(Calendar.SECOND) * 6.0F / 60, viewCenterX, viewCenterY);
        int minuteWidth = drawableMinute.getIntrinsicWidth();
        int minuteHeight = drawableMinute.getIntrinsicHeight();
        drawableMinute.setBounds(viewCenterX - minuteWidth / 2, viewCenterY - minuteHeight + 20, viewCenterX + minuteWidth / 2, viewCenterY + 20);
        drawableMinute.draw(canvas);
        canvas.restore();

        // 画秒针
        canvas.save();
        canvas.rotate(calendar.get(Calendar.SECOND) * 6.0F,  viewCenterX, viewCenterY);
        int secondWidth = drawableSecond.getIntrinsicWidth();
        int secondHeight = drawableSecond.getIntrinsicHeight();
        drawableSecond.setBounds(viewCenterX - secondWidth / 2, viewCenterY - secondHeight + 50, viewCenterX + secondWidth / 2, viewCenterY + 50);
        drawableSecond.draw(canvas);
        canvas.restore();

        // 画中心
        int centerWidth = drawableCenter.getIntrinsicWidth();
        int centerHeight = drawableCenter.getIntrinsicHeight();
        drawableCenter.setBounds(viewCenterX - centerWidth / 2, viewCenterY - centerHeight / 2, viewCenterX + centerWidth / 2, viewCenterY + centerHeight / 2);
        drawableCenter.draw(canvas);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        if (visibility == View.VISIBLE) {
            clockHandler.sendEmptyMessage(0);
        } else {
            clockHandler.removeMessages(0);
        }
    }
}
