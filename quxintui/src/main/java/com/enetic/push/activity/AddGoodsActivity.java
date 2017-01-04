package com.enetic.push.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.enetic.esale.R;
import com.enetic.push.adapter.TabPagerAdapter;
import com.enetic.push.images.PhotoActivity;
import com.enetic.push.images.util.Bimp;
import com.enetic.push.images.util.FileUtils;
import com.enetic.push.view.NoScrollGridView;
import com.enetic.push.view.PopupWindows;
import com.lidroid.xutils.util.LogUtils;


import org.eteclab.base.annotation.InflateView;
import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by moon1 on 2016/4/5.
 */
@Layout(R.layout.activity_add_goods)
public class AddGoodsActivity extends ParentActivity {

    private String path;
    private static final int TAKE_PICTURE = 0x000001;
    @ViewIn(R.id.goods_tab)
    private TabLayout mBtnGoodsTab;
    @ViewIn(R.id.view_pager)
    private ViewPager mViewPager;
    private ArrayList<View> pagerViews;
    @InflateView(R.layout.fragment_add_goods_desp)
    private View mAddGoodsDesp;
    @InflateView(R.layout.fragment_goods_photos)
    private View mAddGoodsPhotos;
    private PullToLoadView mAddGoodsAbout;
    private GridAdapter adapter;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddGoodsAbout = new PullToLoadView(this);
        GridLayoutManager lm = new GridLayoutManager(this, 2);
        initPagerOne();
        initPagerTwo();
        initPagerThree();
        pagerViews = new ArrayList<>();
        pagerViews.add(mAddGoodsDesp);
        pagerViews.add(mAddGoodsPhotos);
        pagerViews.add(mAddGoodsAbout);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(pagerViews);
        mViewPager.setAdapter(tabPagerAdapter);
        mBtnGoodsTab.setupWithViewPager(mViewPager);
        mBtnGoodsTab.setTabTextColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorPrimary));
        mBtnGoodsTab.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        mBtnGoodsTab.getTabAt(0).setText("描述");
        mBtnGoodsTab.getTabAt(1).setText("照片");
        mBtnGoodsTab.getTabAt(2).setText("相关");
    }

    private void initPagerThree() {
        GridLayoutManager lm = new GridLayoutManager(this, 2);
        mAddGoodsAbout.setLoadingColor(R.color.colorPrimary);
        mAddGoodsAbout.setPullCallback(new PullCallbackImpl(mAddGoodsAbout, lm) {
            @Override
            protected void requestData(int pager, boolean follow) {
                loadOK();
            }
        });
        mAddGoodsAbout.initLoad();
    }

    private void initPagerTwo() {
        Init();
    }

    private void initPagerOne() {

    }

    public void Init() {
        final NoScrollGridView noScrollgridview = (NoScrollGridView) mAddGoodsPhotos.findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                if (arg2 == Bimp.getBm().bmp.size()) {
                    new PopupWindows(AddGoodsActivity.this, noScrollgridview);
                } else {
                    Intent intent = new Intent(AddGoodsActivity.this, PhotoActivity.class);
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }

            }
        });
    }

    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater; // 视图容器
        private int selectedPosition = -1;// 选中的位置
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            return (Bimp.getBm().bmp.size() + 1);
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        /**
         * ListView Item设置
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            final int coord = position;
            ViewHolder holder = null;
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.layout_item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.getBm().bmp.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.mipmap.icon_addpic_unfocused));
                if (position == 9 || (type == 2 && Bimp.getBm().drr.size() == 1)) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                if (position < Bimp.getBm().bmp.size())
                    holder.image.setImageBitmap(Bimp.getBm().bmp.get(position));
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    for (String ste : Bimp.getBm().drr) {
                        LogUtils.e("loading path == " + ste);
                    }


                    while (true) {
                        if (Bimp.getBm().max == Bimp.getBm().drr.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            try {
                                String path = Bimp.getBm().drr.get(Bimp.getBm().max);
                                Bitmap bm = Bimp.getBm().revitionImageSize(path);
                                Bimp.getBm().bmp.add(bm);
                                String newStr = path.substring(
                                        path.lastIndexOf("/") + 1,
                                        path.lastIndexOf("."));
                                FileUtils.saveBitmap(bm, "" + newStr);
                                Bimp.getBm().max += 1;
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }


    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory() + "/myimage/", String.valueOf(System.currentTimeMillis()) + ".jpg");
        path = file.getPath();
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }
}