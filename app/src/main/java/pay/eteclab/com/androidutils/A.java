package pay.eteclab.com.androidutils;

import org.xndroid.cn.BaseApplication;
import org.xndroid.cn.database.entry.BaseDataEnery;
import org.xndroid.cn.database.Config;
import org.xndroid.cn.database.entry.DatabaseDox;
import org.xndroid.cn.database.call.DataVersionChangListener;


/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class A extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Config config = new Config();
        config.setVersion(2);
        config.setVersionChangListener(new DataVersionChangListener() {
            @Override
            public void onUpgrade(DatabaseDox var1, int newVersion, int oldVersion) {
                switch (oldVersion) {
                    case 1:
                    case 2:
                        var1.addColumn(Bean.class, "tody", String.class);
                    default:
                        break;
                }

            }

            @Override
            public void onLowgrade(DatabaseDox var1, int newVersion, int oldVersion) {

            }
        });
        BaseDataEnery baseDataEnery = DatabaseDox.getIntences(config);

        baseDataEnery.createTable(Bean.class);
        baseDataEnery.close();

    }
}
