package com.enetic.push.images;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.images.util.AlbumHelper;
import com.enetic.push.images.util.Bimp;
import com.enetic.push.images.util.ImageBucket;
import com.enetic.push.images.util.ImageItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ImageGridActivity extends Activity {
    public static final String EXTRA_IMAGE_LIST = "imagelist";
    public static Bitmap bimap;

    // ArrayList<Entity> dataList;//鐢ㄦ潵瑁呰浇鏁版嵁婧愮殑鍒楄〃
    List<ImageItem> dataLists;
    GridView gridView;
    ImageGridAdapter adapter;// 鑷畾涔夌殑閫傞厤鍣�
    AlbumHelper helper;
    TextView bt;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(ImageGridActivity.this, "最多选择9张图片", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    int count=0;  //已选择
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid);
        Bimp.getBm().recycle();
        count=getIntent().getIntExtra("count",0);
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());
        gridView = (GridView) findViewById(R.id.gridview);
        dataLists = new ArrayList<>();
        List<ImageBucket> dataList = helper.getImagesBucketList(true);

        dataLists.clear();
        for (ImageBucket bucket : dataList) {
            for (ImageItem its : bucket.imageList) {
                its.isSelected = false;
                if (new File(its.imagePath).exists() && !dataLists.contains(its))
                    dataLists.add(its);
            }
        }
        initView();

        bt = (TextView) findViewById(R.id.bt); // 完成按钮
        bt.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ArrayList<String> list = (ArrayList<String>) adapter.map;
//                Collection<String> c = adapter.map.values();
//                Iterator<String> it = c.iterator();
//                for (; it.hasNext(); ) {
//                    list.add(0,it.next());
//                }
                for (int i = 0; i < list.size(); i++) {
                    if (Bimp.getBm().drr.size() < 9) {
                        Bimp.getBm().drr.add(list.get(i));
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("images", list);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });


        //返回按钮
        findViewById(R.id.finish).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 鍒濆鍖杤iew瑙嗗浘
     */
    private void initView() {
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new ImageGridAdapter(ImageGridActivity.this, dataLists,
                mHandler);
        adapter.setChosed(count);
        gridView.setAdapter(adapter);

        adapter.setTextCallback(new ImageGridAdapter.TextCallback() {
            public void onListen(int count) {
                bt.setText("完成" + "(" + count + ")");
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                adapter.notifyDataSetChanged();
            }

        });

    }
}