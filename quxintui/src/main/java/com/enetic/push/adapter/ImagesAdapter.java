package com.enetic.push.adapter;

import android.content.Context;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.enetic.esale.R;
import com.enetic.push.images.util.FileUtils;


import org.eteclab.base.annotation.Layout;
import org.eteclab.base.utils.AsyncImageLoader;

import java.io.File;
import java.util.List;

/**
 * Created by json on 2016/6/8.
 */
@Layout(R.layout.adapter_imgs)
public class ImagesAdapter extends CommonAdapter<String> {

    public ImagesAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    protected void setViews(Viewholder holder, final String o) {
        ImageView iv = (ImageView) holder.getConvertView();
       ViewGroup.LayoutParams params = iv.getLayoutParams();
        Display display = ((WindowManager)mContext.getSystemService("window")).getDefaultDisplay();
        params.height  = display.getHeight();
        params.width  = display.getWidth();
        iv.setLayoutParams(params);
       String path =  FileUtils.SDPATH+ o.substring(o.lastIndexOf("/"));
        if (new File(path).exists()){
            save(iv, path);
        }else{
            save(iv, o);
        }
    }


    public void save(ImageView iv, String o) {
        new AsyncImageLoader(mContext, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(iv, o);
    }
}