package com.enetic.push.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lidroid.xutils.BitmapUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageUtils {

    /**
     * 根据bitmap压缩图片质量
     *
     * @param path 未压缩图片的路径
     * @return 压缩后的bitmap
     */
    public static String cQuality(String path) {

        if (FileUtils.getFileSize(path) < 100 * 1024) {
            return path;
        } else {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            int beginRate = 100;
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bOut);
            while (bOut.size() / 1024  > 100) {  //如果压缩后大于100Kb，则提高压缩率，重新压缩
                beginRate -= 1;
                bOut.reset();
                bitmap.compress(Bitmap.CompressFormat.JPEG, beginRate, bOut);
            }
            ByteArrayInputStream bInt = new ByteArrayInputStream(bOut.toByteArray());
            Bitmap newBitmap = BitmapFactory.decodeStream(bInt);
            return com.enetic.push.images.util.FileUtils.saveBitmap(newBitmap, path.substring(path.lastIndexOf("/")));
        }
    }

    /**
     * @param path
     * @return
     * @throws IOException
     */
    public static Bitmap revitionImageSize(String path) throws IOException {
        if (!new File(path).exists()) {
            return null;
        }
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        int size =1000;
        while (true) {
            if ((options.outWidth >> i <= size)
                    && (options.outHeight >> i <= size)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }
}