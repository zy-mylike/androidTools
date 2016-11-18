package pay.eteclab.com.androidutils;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.xndroid.cn.activity.BaseActivity;
import org.xndroid.cn.annotation.Layout;
import org.xndroid.cn.annotation.ViewCilck;
import org.xndroid.cn.annotation.ViewIn;


@Layout(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewIn(R.id.recyclerView)
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @ViewCilck(R.id.button)
    private void onclickbutton(View v) {
        Toast.makeText(getApplicationContext(), "recyclerView", 0).show();

    }
}
