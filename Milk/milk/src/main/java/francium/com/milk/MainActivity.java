package francium.com.milk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import francium.com.milk.game.GameActivity;
import francium.com.milk.note.NoteActivity;
import francium.com.milk.settings.SettingsActivity;

/**
 * Created by Francium on 2015/8/2.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    // 主页面TabHost
    private TabHost tabHost;

    // 生牛乳和死胖子页面TextView
    private TextView textLiveMilk;
    private TextView textLiveMango;
    private TextView textLiveEgg;
    private TextView textLiveMeat;
    private TextView textDeathMilk;
    private TextView textDeathMango;
    private TextView textDeathEgg;
    private TextView textDeathMeat;

    // 今日牛奶推荐页面
    private LinearLayout linearLayoutRecommendation;
    private LinearLayout recommendationHeader;
    private LinearLayout recommendationFooter;
    private ImageView imageViewRecommendation;
    private ImageView imageGame;
    private ImageView imageNote;
    private ImageView imageSettings;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 绑定TabHost的头部
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("生牛乳").setContent(R.id.tab_live));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("奶香").setContent(R.id.tab_milk));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("死胖子").setContent(R.id.tab_death));

        // 初始化生牛乳和死胖子页面文字
        initLiveAndDeathText();

        imageGame = (ImageView) findViewById(R.id.image_game);
        imageNote = (ImageView) findViewById(R.id.image_note);
        imageSettings = (ImageView) findViewById(R.id.image_settings);
        imageGame.setOnClickListener(this);
        imageNote.setOnClickListener(this);
        imageSettings.setOnClickListener(this);

        tabHost.setCurrentTab(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_game:
                intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
                break;
            case R.id.image_note:
                intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(intent);
                break;
            case R.id.image_settings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus) {
            // 初始化牛奶推荐页面,用onWindowFocusChanged可以获取View的height和width
            initRecommendation();
        }
        super.onWindowFocusChanged(hasFocus);
    }

    /**
     * 初始化牛奶推荐页面
     */
    private void initRecommendation() {
        linearLayoutRecommendation = (LinearLayout) findViewById(R.id.tab_milk);
        recommendationHeader = (LinearLayout) findViewById(R.id.recommendation_header);
        recommendationFooter = (LinearLayout) findViewById(R.id.recommendation_footer);
        imageViewRecommendation = (ImageView) findViewById(R.id.image_recommendation);
        int viewHeight = linearLayoutRecommendation.getHeight();
        int viewWidth = linearLayoutRecommendation.getWidth();
        LinearLayout.LayoutParams lpHeader = (LinearLayout.LayoutParams) recommendationHeader.getLayoutParams();
        lpHeader.height = viewHeight / 4;
        recommendationHeader.setLayoutParams(lpHeader);
        LinearLayout.LayoutParams lpFooter = (LinearLayout.LayoutParams) recommendationFooter.getLayoutParams();
        lpFooter.height = viewHeight / 2;
        recommendationFooter.setLayoutParams(lpFooter);
        int w = getResources().getDrawable(R.drawable.recommendation).getIntrinsicWidth();
        int h = getResources().getDrawable(R.drawable.recommendation).getIntrinsicHeight();
        imageViewRecommendation.setPadding(viewWidth / 4 - w / 2, viewHeight / 4 - h / 2, viewWidth / 4 + w / 2, viewHeight / 4 + h / 2);
    }

    /**
     * 初始化生牛乳和死胖子页面文字
     */
    private void initLiveAndDeathText() {
        textLiveMilk = (TextView) findViewById(R.id.text_live_milk);
        textLiveMango = (TextView) findViewById(R.id.text_live_mango);
        textLiveEgg = (TextView) findViewById(R.id.text_live_egg);
        textLiveMeat = (TextView) findViewById(R.id.text_live_meat);
        textDeathMilk = (TextView) findViewById(R.id.text_death_milk);
        textDeathMango = (TextView) findViewById(R.id.text_death_mango);
        textDeathEgg = (TextView) findViewById(R.id.text_death_egg);
        textDeathMeat = (TextView) findViewById(R.id.text_death_meat);

        textLiveMilk.setText(Util.refreshLiveMilk());
        textLiveMango.setText(Util.refreshLiveMango());
        textLiveEgg.setText(Util.refreshLiveEgg());
        textLiveMeat.setText(Util.refreshLiveMeat());
        textDeathMilk.setText(Util.refreshDeathMilk());
        textDeathMango.setText(Util.refreshDeathMango());
        textDeathEgg.setText(Util.refreshDeathEgg());
        textDeathMeat.setText(Util.refreshDeathMeat());
    }

    // 以下代码用来使TabHost随手势切换tab，做得不是很好，体验不是很好
    private int TAB_PAGE = 0;
    private float move = 0;
    private float MOVE_MIN = 60;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                move = 0;
                move = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                move = event.getX() - move;
                if (move < -MOVE_MIN && TAB_PAGE < 2) {
                    TAB_PAGE++;
                    tabHost.setCurrentTab(TAB_PAGE);
                } else if (move > MOVE_MIN && TAB_PAGE > 0) {
                    TAB_PAGE--;
                    tabHost.setCurrentTab(TAB_PAGE);
                }
                break;
        }
        return true;
    }
}
