package pay.eteclab.com.androidutils;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xndroid.cn.activity.BaseActivity;
import org.xndroid.cn.annotation.Layout;
import org.xndroid.cn.annotation.ViewCilck;
import org.xndroid.cn.annotation.ViewIn;
import org.xndroid.cn.database.entry.BaseDataEnery;
import org.xndroid.cn.database.entry.DatabaseDox;
import org.xndroid.cn.database.sql.SQLBulder;
import org.xndroid.cn.utils.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Layout(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewIn(R.id.recyclerView)
    private RecyclerView recyclerView;
    BaseDataEnery baseDataEnery = DatabaseDox.getIntences(null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e(SQLBulder.CREATETABLE(Bean.class));

        baseDataEnery.deleteAll(Bean.class);
    }

    @ViewCilck(R.id.button)
    private void onclickbutton(View v) {
        List<Bean> lista = baseDataEnery.selectAll(Bean.class);
        baseDataEnery.deleteAll(Bean.class);
        Bean bn = new Bean();
        bn.scores = "10";
        bn.name = "s name";
        bn.height = "s e";
        bn.score = "score e";
        bn.time = "time e";
        bn.heights = "heights e";
        baseDataEnery.insert(bn);
        List<Bean> list = baseDataEnery.selectAll(Bean.class);
        if (list != null) {
            for (Bean bns : list) {
                String json = new Gson().toJson(bns, new TypeToken<Bean>() {
                }.getType());
                LogUtils.e(json);
            }
            try {
                Bean b = list.get(0);
                b.time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
                baseDataEnery.updata(b);
            } catch (IndexOutOfBoundsException e) {

            }

        }

        Toast.makeText(getApplicationContext(), "recyclerView", Toast.LENGTH_SHORT).show();
    }
}
