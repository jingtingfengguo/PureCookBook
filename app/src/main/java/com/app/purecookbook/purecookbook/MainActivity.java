package com.app.purecookbook.purecookbook;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.app.purecookbook.purecookbook.adapter.MainMenuAdapter;
import com.app.purecookbook.purecookbook.cookbook.CookActivity;
import com.app.purecookbook.purecookbook.index.IndexActivity;
import com.app.purecookbook.purecookbook.view.IconCenterEditText;
import com.lidroid.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.content_main)
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /** ViewPager中ImageView的容器 */
    private List<ImageView> imageViewContainer = null;
    /** 上一个被选中的小圆点的索引，默认值为0 */
    private int preDotPosition = 0;
    /** Banner文字描述数组 */
    private String[] bannerTextDescArray = {
            "营养早餐，让你充满活力迎接新的每一天",
            "八大菜系，午间尽有",
            "低脂素食，使你的晚餐附有健康",
            "深夜食堂，带你领略日本美食的魅力"
    };

    /** Banner滚动线程是否销毁的标志，默认不销毁 */
    private boolean isStop = false;

    /** Banner的切换下一个page的间隔时间 */
    private long scrollTimeOffset = 5000;

    private ViewPager viewPager;

    /** Banner的文字描述显示控件 */
    private TextView tvBannerTextDesc;

    /** 小圆点的父控件 */
    private LinearLayout llDotGroup;

    private GridView main_mygridview;
    private List<String> list;
    private IconCenterEditText icet_search;
    private TextView allmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        initBefore();
        init();
        initAfter();
        startBannerScrollThread();


    }

    /**
     * 开启Banner滚动线程
     */
    private void startBannerScrollThread() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (!isStop) {
                    //每个两秒钟发一条消息到主线程，更新viewpager界面
                    SystemClock.sleep(scrollTimeOffset);

                    runOnUiThread(new Runnable() {
                        public void run() {
                            int newindex = viewPager.getCurrentItem() + 1;
                            viewPager.setCurrentItem(newindex);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this,"功能尚未开发，尽请期待后续版本!",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            onBackPressed();
        } else if (id == R.id.nav_collection) {

            Intent intent = new Intent(MainActivity.this, CookActivity.class);
            intent.putExtra("title", "我的收藏");
            intent.putExtra("tab_cursor", 2);
            startActivity(intent);
            overridePendingTransition(R.anim.new_to_left, R.anim.old_to_left);

        } else if (id == R.id.nav_recent) {
            Intent intent = new Intent(MainActivity.this, CookActivity.class);
            intent.putExtra("title", "最近浏览");
            intent.putExtra("tab_cursor", 1);
            startActivity(intent);
            overridePendingTransition(R.anim.new_to_left, R.anim.old_to_left);

        }  else if (id == R.id.nav_feedback) {
            Intent intent = new Intent(MainActivity.this, FeedBackActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.new_to_left, R.anim.old_to_left);

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
            overridePendingTransition(R.anim.new_to_left, R.anim.old_to_left);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    protected void onPause() {
        isStop = false;
        super.onPause();
    }

    @Override
    protected void onRestart() {
        isStop = true;
        super.onRestart();
    }


    @Override
    public void initBefore() {

        icet_search = (IconCenterEditText) findViewById(R.id.icet_search);
        // 实现TextWatcher监听即可
        icet_search.setOnSearchClickListener(new IconCenterEditText.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view) {
                String content = icet_search.getText().toString();
                if (content.length() != 0) {
                    Intent intent = new Intent(MainActivity.this, CookActivity.class);
                    intent.putExtra("title", "搜索菜谱");
                    intent.putExtra("search_key", content);
                    startActivity(intent);
                    overridePendingTransition(R.anim.new_to_left, R.anim.old_to_left);
                }
            }
        });

        allmenu=(TextView)findViewById(R.id.all_menu);
        allmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IndexActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.new_to_top, R.anim.old_to_top);
            }
        });



    }

    @Override
    public void init() {
        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        llDotGroup = (LinearLayout) findViewById(R.id.banner_btn);
        tvBannerTextDesc = (TextView) findViewById(R.id.banner_text);

        imageViewContainer = new ArrayList<ImageView>();
        int[] imageIDs = new int[] {
                R.drawable.a,
                R.drawable.b,
                R.drawable.c,
                R.drawable.d

        };

        ImageView imageView = null;
        View dot = null;
        LayoutParams params = null;
        for (int id : imageIDs) {
            imageView = new ImageView(this);
            imageView.setBackgroundResource(id);
            imageViewContainer.add(imageView);

            // 每循环一次添加一个点到线行布局中
            dot = new View(this);
            dot.setBackgroundResource(R.drawable.dtn_bg_selector);
            params = new LayoutParams(5, 5);
            params.leftMargin = 10;
            dot.setEnabled(false);
            dot.setLayoutParams(params);
            llDotGroup.addView(dot); // 向线性布局中添加"点"
        }

        viewPager.setAdapter(new BannerAdapter());
        viewPager.addOnPageChangeListener(new BannerPageChangeListener());

        // 选中第一个图片、文字描述
        tvBannerTextDesc.setText(bannerTextDescArray[0]);
        llDotGroup.getChildAt(0).setEnabled(true);
        viewPager.setCurrentItem(0);

    }


    @Override
    public void initAfter() {
        list = new ArrayList<>();
        list.add("家常菜");
        list.add("快手菜");
        list.add("创意菜");
        list.add("素菜");
        list.add("凉菜");
        list.add("烘焙");
        list.add("面食");
        list.add("汤");
        list.add("自制调味料");
        main_mygridview=(GridView)findViewById(R.id.main_mygridview);
        MainMenuAdapter menuadapter = new MainMenuAdapter(list);
        main_mygridview.setAdapter(menuadapter);
        main_mygridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, CookActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", position + 1);
                bundle.putString("title", list.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.new_to_left, R.anim.old_to_left);
            }
        });

    }
    /**
     * ViewPager的适配器
     */
    private class BannerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViewContainer.get(position % imageViewContainer.size()));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = imageViewContainer.get(position % imageViewContainer.size());

            // 为每一个page添加点击事件
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "更多精彩，尽在全部菜谱分类", Toast.LENGTH_SHORT).show();
                }

            });

            try {
                if (imageViewContainer.get(position % imageViewContainer.size()).getParent() == null) {
                    container.addView(imageViewContainer.get(position % imageViewContainer.size()), 0);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return imageViewContainer.get(position % imageViewContainer.size());
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    /**
     * Banner的Page切换监听器
     */
    private class BannerPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // Nothing to do
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // Nothing to do
        }

        @Override
        public void onPageSelected(int position) {
            // 取余后的索引，得到新的page的索引
            int newPositon = position % imageViewContainer.size();
            // 根据索引设置图片的描述
            tvBannerTextDesc.setText(bannerTextDescArray[newPositon]);
            // 把上一个点设置为被选中
            llDotGroup.getChildAt(preDotPosition).setEnabled(false);
            // 根据索引设置那个点被选中
            llDotGroup.getChildAt(newPositon).setEnabled(true);
            // 新索引赋值给上一个索引的位置
            preDotPosition = newPositon;
        }

    }




    @Override
    protected void onDestroy() {
        isStop = true;
        super.onDestroy();
    }



    }

