/**
 *
 */
package com.enetic.push.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import com.lidroid.xutils.util.LogUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @description 有关文件操作工具
 */
public class FileUtils {

    public static String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static String saveBitmap(Bitmap bm, String picName) {
        String path = "";
        Log.e("", "保存图片");
        try {
            File f = new File(SDPATH + "formats/", picName);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 50, out);
            path = f.getAbsolutePath();
            out.flush();
            out.close();

            Log.e("", "已经保存");

            return path;
        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException e) {
            return "";
        }

    }

    /**
     * 文件大小转换
     *
     * @param fileS
     * @return
     */
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "0.00";
        if (fileS == 0) {
        } else if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     */
    public static long getFileSize(String file) {
        return getFolderSize(getFile(file));
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            if (file.isDirectory()) {
                java.io.File[] fileList = file.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i]);
                    } else {
                        size = size + fileList[i].length();
                    }
                }
                return size;
            } else {
                if (file != null && file.exists()) {
                    return file.length();
                }
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;

    }

    public static File getFile(String dir) {
        if (TextUtils.isEmpty(dir)) {
            return null;
        } else {
            return new File(dir);
        }
    }

    public static void deleteFile(String file) {
        deleteFile(new File(file));
    }

    public static void deleteFile(File file) {
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (int index = 0; index < files.length; index++) {
                File f = files[index];
                if (f.exists()) {
                    if (f.isFile()) {
                        f.delete();
                    } else if (f.isDirectory()) {
                        deleteFile(f);
                    }
                }
            }

            if (file != null && file.listFiles().length == 0) {
                file.delete();
            }

        } else {
            file.delete();
        }

    }

   public static void  saveFile(String name,String data){

       File  f =   new File(SDPATH,name);

       try {
           FileWriter writer =  new FileWriter(f);
           writer.write(data);
           writer.close();


       } catch (IOException e) {
           e.printStackTrace();
       }

   }

}
