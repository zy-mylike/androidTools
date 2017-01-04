package com.enetic.push.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.enetic.esale.R;
import com.enetic.push.images.util.AlbumHelper;
import com.enetic.push.images.util.BitmapCache;
import com.enetic.push.images.util.FileUtils;
import com.enetic.push.images.util.ImageBucket;
import com.enetic.push.images.util.ImageItem;
import com.enetic.push.utils.AsyncImageLoader;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by json on 2016/6/8.
 */
@Layout(R.layout.adapter_img)
public class ImageAdapter extends CommonAdapter<String> {
    //Map<String, ImageItem> dataLists;
    //AlbumHelper helper;
    BitmapCache cache = new BitmapCache();
    BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() {
        @Override
        public void imageLoad(ImageView imageView, Bitmap bitmap,
                              Object... params) {
            if (imageView != null && bitmap != null) {
                String url = (String) params[0];
                //此处bug：加载一张显示两张图片。iv set tag 解决该bug很重要的一步。
                if (url != null&& url.equals((String) imageView.getTag()) ) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    };

    public ImageAdapter(Context context, List<String> list) { //list:图片的路径集合。
        super(context, list);
       // cache = new BitmapCache();
       // helper = AlbumHelper.getHelper();  //得到一个 AlbumHelper
       // helper.init(mContext);
       // dataLists = new HashMap();
    }


    public void updataImg() {
//        if (!dataLists.isEmpty()){
//            return;
//        }
//        dataLists.clear();
//        List<ImageBucket> datas = helper.getImagesBucketList(true);
//        for (ImageBucket bucket : datas) {
//            bucket.bucketName.equals("formats");
//            for (ImageItem its : bucket.imageList) {
//                its.isSelected = false;
//                if (new File(its.imagePath).exists())
//                    dataLists.put(its.imagePath, its);
//            }
//        }

    }

    @Override
    public int getCount() {
        return super.getCount() < 9 ? super.getCount() + 1 : super.getCount();
    }
    public int getListCount(){
        return super.getCount();
    }
   


    @Override
    protected void setViews(final Viewholder holder, final String o) { //o:图片的URL。
        ImageView iv = holder.getView(R.id.iv_img);
        ImageView iv_delete = holder.getView(R.id.iv_delete);
        TextView tv_mainpic = holder.getView(R.id.tv_mainpic);
        iv.setTag(o); //解决显示空白图片。
        if ((holder.getPosition() == super.getCount() && getCount() < 10)) { //判读控件最后位置应该显示的是哪张图片。
            iv.setImageResource(R.mipmap.icon_img_add); //添加细节图
            if (holder.getPosition() == 0) {
                iv.setImageResource(R.mipmap.icon_main_img_add); //添加主图
            }
            tv_mainpic.setVisibility(View.GONE);
            iv_delete.setVisibility(View.GONE);
        } else {
            iv_delete.setVisibility(View.VISIBLE);
            if (holder.getPosition() == 0) {
                tv_mainpic.setVisibility(View.VISIBLE);
            } else {
                tv_mainpic.setVisibility(View.GONE);
            }
            save(iv, o);
            //iv.setImageResource(R.mipmap.ic_launcher);
        }
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.iv_delete) {
                    if (holder.getPosition() == mData.size()) {
                        return;
                    } else {
                        removeItem(holder.getPosition());
                    }
                }
            }
        }, R.id.iv_delete);

        if (mData != null && holder.getPosition() == mData.size() - 1) {
            //updataImg();
            //iv.setImageResource(R.mipmap.icon_img_add); //添加细节图
        }
    }


    public void save(ImageView iv, String o) {

        final String name = o.substring(o.lastIndexOf("/")+1); //图片的名称。

        if(FileUtils.fileIsExists(o)){
            cache.displayBmp(iv, FileUtils.SDPATH + name, o, callback);
        } else {
            new AsyncImageLoader(mContext, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(iv, o, new DefaultBitmapLoadCallBack() {
                public void fadeInDisplay(ImageView imageView, Bitmap bitmap) {
                    TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{new ColorDrawable(17170445), new BitmapDrawable(imageView.getResources(), bitmap)});
                    imageView.setImageDrawable(transitionDrawable);
                    transitionDrawable.startTransition(500);
                }

                @Override
                public void onLoadCompleted(View container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
                    super.onLoadCompleted(container, uri, bitmap, config, from);
                    fadeInDisplay((ImageView) container, bitmap);
                    FileUtils.saveBitmap(bitmap, uri.substring(uri.lastIndexOf("/")+1));
                    cache.put(FileUtils.SDPATH + name, bitmap);
                }
            });
        }
    }

    @ViewIn(R.id.iv_delete)
    public ImageView iv_delete;

}