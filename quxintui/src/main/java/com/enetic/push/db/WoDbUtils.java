package com.enetic.push.db;

import android.content.Context;

import com.enetic.push.bean.SeacherBean;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by json on 2016/5/5.
 */
public class WoDbUtils {
    private static com.lidroid.xutils.DbUtils instance;
    private static final int VERSION = 1;


    public static synchronized com.lidroid.xutils.DbUtils initialize(Context context) {
        if (instance == null) {
            instance = com.lidroid.xutils.DbUtils.create(context, "wodm.db", VERSION, new com.lidroid.xutils.DbUtils.DbUpgradeListener() {
                public void onUpgrade(com.lidroid.xutils.DbUtils utils, int oldVersion, int newVersion) {
                    try {
                        utils.dropTable(SeacherBean.class);
                        utils.createTableIfNotExist(SeacherBean.class);
                    } catch (DbException var5) {
                        var5.printStackTrace();
                    }
                }
            });
            instance.configAllowTransaction(true);
        }
        return instance;
    }

    public static List find(Context context, int type) {
        List<SeacherBean> list = null;
       // List<SeacherBean> lists = new ArrayList<>();
        try {
            //搜索type类型的数据，按时间排序，然后取前六条。
            list = WoDbUtils.initialize(context).findAll(Selector.from(SeacherBean.class).where("type", "=", type).orderBy("createTime", true).limit(6));

            if (list == null) {
                list = new ArrayList<>();
            }
//            for (SeacherBean bean : list) {
//                if (bean.getType() == type)
//                    lists.add(bean);
//            }
            return list;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }

}