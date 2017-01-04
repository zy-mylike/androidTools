package com.enetic.push.activity.businesses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.enetic.esale.R;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.adapter.TabPagerAdapter;
import com.enetic.push.images.util.FileUtils;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.track.annotation.TrackClick;

import java.io.File;
import java.util.ArrayList;

@Layout(R.layout.activity_product_imgs)
public class ProductImgsActivity extends ParentActivity {

    private ArrayList<View> listViews = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();
    @ViewIn(R.id.viewpager)
    private ViewPager imgs;

    TabPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolRight(R.mipmap.nav_delete);
        list = (ArrayList<String>) getIntent().getSerializableExtra("imgs");
        for (String pas : list) {
            initListViews(pas);
        }
        adapter = new TabPagerAdapter(listViews);
        imgs.setAdapter(adapter);
        int position = getIntent().getIntExtra("position", 0);
        setToolTitle((position+1)+"/"+list.size());
        imgs.setCurrentItem(position);

        imgs.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.i("lyw", "onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                //Log.i("lyw", position+"");
                setToolTitle((position+1)+"/"+list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("imgs", list);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("imgs", list);
            setResult(Activity.RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @TrackClick(R.id.tooltitle_right)
    private void clickDelete(View view) {
        if(list.size()>0)
        list.remove(imgs.getCurrentItem());
        if(listViews.size()>0)
        listViews.remove(imgs.getCurrentItem());

        if(list.size() == 0){
            Intent intent = new Intent();
            intent.putExtra("imgs", list);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
        adapter.setPagerViews(listViews);
        setToolTitle((imgs.getCurrentItem()+1)+"/"+listViews.size());
    }

    private void initListViews(String path) {
        if (listViews == null)
            listViews = new ArrayList<View>();
        ImageView img = new ImageView(this);// 构造textView对象
        img.setBackgroundColor(0xff000000);

        String paths = FileUtils.SDPATH + path.substring(path.lastIndexOf("/"));
        if (new File(paths).exists()) {
            path = paths;
        }
        new AsyncImageLoader(this, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(img, path);

        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));
        listViews.add(img);// 添加view
    }


}
