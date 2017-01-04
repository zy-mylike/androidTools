package com.enetic.push.images.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    public static String SDPATH = Environment.getExternalStorageDirectory()
            + "/formats/";

    public static String saveBitmap(Bitmap bm, String picName) {
        String path = "";
        Log.e("", "保存图片");
        try {
            if (!isFileExist("")) {
                File tempf = createSDDir("");
            }

            File f = new File(SDPATH, picName);
            if (f.exists()) {
                f.delete();
            }
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }

            FileOutputStream out = new FileOutputStream(f);

            bm.compress(Bitmap.CompressFormat.PNG, 100, out);

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

    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

        }
        return dir;
    }

    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        file.isFile();
        return file.exists();
    }

    public static void delFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }

    public static void deleteDir() {
        File dir = new File(SDPATH);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {

            return false;
        }
        return true;
    }

}
