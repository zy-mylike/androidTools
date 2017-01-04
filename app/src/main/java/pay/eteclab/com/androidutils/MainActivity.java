package pay.eteclab.com.androidutils;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.xndroid.cn.activity.BaseActivity;
import org.xndroid.cn.annotation.EventCilck;
import org.xndroid.cn.annotation.Layout;


@Layout(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @EventCilck(R.id.button)
    private void onBtn(View view) {
        Toast.makeText(getApplicationContext(), "", 0).show();
    }
}
