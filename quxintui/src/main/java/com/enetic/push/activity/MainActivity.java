package com.enetic.push.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.PushApplication;
import com.enetic.push.activity.businesses.ProductEditActivity;
import com.enetic.push.fragment.agent.BusinessFragment;
import com.enetic.push.fragment.agent.TaskFragment;
import com.enetic.push.fragment.agent.UserFragment;
import com.enetic.push.fragment.business.AgentFragment;
import com.enetic.push.fragment.business.HomeFragment;
import com.enetic.push.fragment.business.OutboxFragment;
import com.enetic.push.utils.UpdateUtils;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.ReflectionUtil;
import org.eteclab.track.annotation.TrackClick;


@Layout(R.layout.activity_main)
public class MainActivity extends ParentActivity {

    private static final SparseArray<SurfaceParam> mSurfaceParams = new SparseArray<>();
    private FragmentManager mFragmentManager;
    private int mFragmantIndex = 0;

    @ViewIn(R.id.main_bottom)
    private LinearLayout mBottomView;

    private int type = 0; //1:代理 0:商户


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
        type = getIntent().getIntExtra("type", type);
        initBottomViews();
        setTabSelection(mFragmantIndex);
        if (getIntent().hasExtra("fragmentIndex")) {
            setTabSelection(getIntent().getIntExtra("fragmentIndex", 0));
        }
        new UpdateUtils(this).checkUpdate(false);
    }

    private void initBottomViews() {
        String text_btms[] = getResources().getStringArray(type == 1 ? R.array.agent_btm_names : R.array.business_btm_names);
        for (int index = 0; index < text_btms.length; index++) {
            LinearLayout layout = (LinearLayout) View.inflate(this, R.layout.layout_tab_item, null);
            LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(-1, -1);
            Params.weight = 1;
            layout.setLayoutParams(Params);
            setTabStyle(layout, index, text_btms[index]);
            layout.setTag(index);
            layout.setOnClickListener(clickListener);
            mBottomView.addView(layout);
            mSurfaceParams.put(index, new SurfaceParam(getFragmentClass(index), text_btms[index], layout));
        }

    }

    private void setTabStyle(View layout, int index, String tabs) {
        int img_pressed[] = {R.mipmap.icon_btm_task_pressed, R.mipmap.icon_btm_business_pressed, R.mipmap.icon_btm_us_pressed};
        int img_nromal[] = {R.mipmap.icon_btm_task_normal, R.mipmap.icon_btm_business_normal, R.mipmap.icon_btm_us_normal};
        if (type == 0) { //商户
            img_pressed = new int[]{R.mipmap.icon_btm_business_pressed, R.mipmap.icon_btm_agent_pressed, R.mipmap.icon_btm_box_pressed, R.mipmap.icon_btm_us_pressed};
            img_nromal = new int[]{R.mipmap.icon_btm_business_normal, R.mipmap.icon_btm_agent_normal, R.mipmap.icon_btm_box_normal, R.mipmap.icon_btm_us_normal};
        }

        TextView tab_text = (TextView) layout.findViewById(R.id.tab_text);
        ImageView tab_img = (ImageView) layout.findViewById(R.id.tab_img);
        tab_img.setImageResource(mFragmantIndex != index ? img_nromal[index] : img_pressed[index]);
        if (!TextUtils.isEmpty(tabs))
            tab_text.setText(tabs);
        setToolTitle(tab_text.getText().toString());
        tab_text.setTextColor(mFragmantIndex != index ? getResources().getColor(R.color.text_color) : getResources().getColor(R.color.text_color_selector));
        if (type == 0 && index == 0) {
            setToolRight(R.mipmap.nav_addation);
        } else {
            mToolBar.setNavigationOnClickListener(null);
            mToolBar.setNavigationIcon(new BitmapDrawable());
            setToolRight(0);
        }
    }

    private void setTabSelection(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        int size = mSurfaceParams.size();
        for (int i = 0; i < size; i++) {
            Fragment f = mSurfaceParams.valueAt(i).fragment;
            if (f != null) {
                setTabStyle(mSurfaceParams.get(i).layout, i, "");
                transaction.hide(f);
            }
        }
        if (mSurfaceParams.get(index).fragment == null) {
            mSurfaceParams.get(index).fragment = (Fragment) ReflectionUtil.generateObject(mSurfaceParams.get(index).clazz);
            transaction.add(R.id.content, mSurfaceParams.get(index).fragment);
        } else {
            transaction.show(mSurfaceParams.get(index).fragment);
        }

        setTabStyle(mSurfaceParams.get(index).layout, index, "");
        transaction.commit();

    }

    long mExitTime = 0;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show();
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
            ((PushApplication) getApplication()).exitApp();
        }

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mFragmantIndex == (int) v.getTag()) {
                return;
            }
            mFragmantIndex = (int) v.getTag();
            setTabSelection(mFragmantIndex);
        }
    };

    private static class SurfaceParam {
        private Class clazz;
        private View layout;
        private String titleId;
        private Fragment fragment;

        public SurfaceParam(Class clazz, String titleId, View layout) {
            this.clazz = clazz;
            this.titleId = titleId;
            this.layout = layout;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < mSurfaceParams.size(); i++) {
            mSurfaceParams.valueAt(i).fragment = null;
        }
    }


    private Class getFragmentClass(int index) {
        if (type == 1) {//代理
            Class classes[] = {TaskFragment.class, BusinessFragment.class, UserFragment.class};
            return classes[index];
        } else {//商家
            Class classes[] = {HomeFragment.class, AgentFragment.class, OutboxFragment.class, com.enetic.push.fragment.business.UserFragment.class};
            return classes[index];
        }
    }

    @TrackClick(value = R.id.tooltitle_right, eventName = "添加")
    private void clickAddAction(View v) {
        if (type == 0) {
            if (mFragmantIndex == 0) {
                Intent intent=new Intent(this, ProductEditActivity.class);
                intent.putExtra("startFrom","add");
                startActivity(intent);
            }
        }
    }

}