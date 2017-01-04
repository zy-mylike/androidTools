package com.enetic.push.fragment.business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.DataanalysisActivity;
import com.enetic.push.activity.agent.AgentsetActivity;
import com.enetic.push.activity.agent.UserCardActivity;
import com.enetic.push.activity.businesses.MineDealActivity;
import com.enetic.push.activity.businesses.OurAccountActivity;
import com.enetic.push.dialog.ImageDialog;
import com.enetic.push.images.util.FileUtils;
import com.enetic.push.utils.AppTools;
import com.enetic.push.utils.HttpRequest;
import com.enetic.push.utils.ImageUtils;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.base.utils.CommonUtil;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.track.fragment.TrackFragment;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.IdentityHashMap;

/**
 * Created by json on 2016/4/1.
 */
@Layout(R.layout.fragment_us)
public class UserFragment extends TrackFragment {
    private final static String TITLE = "我的";

    @ViewIn(R.id.user_img)
    private ImageView user_img;
    @ViewIn(R.id.user_name)
    private EditText mUserName;
    @ViewIn(R.id.proxy_num)
    private TextView proxy_num;
    @ViewIn(R.id.version)
    private TextView version;
    @ViewIn(R.id.product_sum)
    private TextView product_sum;
    private String mPhotoPath;
    private final static int TAKE_PRICTURE = 0x002;
    private final static int GET_PRICTURE = 0x001;

    @Override
    protected void setDatas(Bundle bundle) {
        mUserName.setEnabled(false);
        HttpUtil.httpGet(getActivity(), Constants.URL_BUSINESS_ACCONT + Constants.CURRENT_USER.getUserId(), new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    obj = new JSONObject(obj.getString("data"));
                    proxy_num.setText(AppTools.numFormat(obj.getString("agentCount"))+"人");
                    version.setText(AppTools.numFormat(obj.getString("visitCount"))+"人");
                    product_sum.setText(obj.getString("orderCount")+"人");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });

        mUserName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    //开始搜索
                    startSeacher(mUserName.getText().toString());
                    CommonUtil.hideKeyboard(getActivity(), mUserName);
                    return true;
                }

                return false;
            }


        });
        setViews();
    }

    private void startSeacher(final String s) {

        HttpRequest.userInfos(getActivity(), new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    Constants.CURRENT_USER.setNickName(s);
                    setViews();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                try {
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, "nickName", s);

    }

    private void setViews() {
        mUserName.setEnabled(false);
        mUserName.setText(Constants.CURRENT_USER.getNickName());
        new AsyncImageLoader(getActivity(), R.mipmap.us_default_header, R.mipmap.us_default_header).display(user_img, Constants.CURRENT_USER.getPortrait());
    }


    @TrackClick(value = R.id.user_img, location = TITLE, eventName = "头像")
    private void clickHeader(View view) {
        final ImageDialog dialog = new ImageDialog(getActivity(),R.style.Transparent);
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.take_images) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mPhotoPath = "img-" + System.currentTimeMillis() + ".jpg";
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        mPhotoPath = Environment.getExternalStorageDirectory().getPath() + "/" + mPhotoPath;
                    } else {
                        return;
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mPhotoPath)));
                    startActivityForResult(intent, TAKE_PRICTURE);
                } else if (v.getId() == R.id.images) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, GET_PRICTURE);
                }
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @TrackClick(value = R.id.user_edit, location = TITLE, eventName = "修改昵称")
    private void clickNickname(View view) {
        mUserName.setEnabled(true);
        mUserName.requestFocus();
        mUserName.setSelection(mUserName.getText().length());
        InputMethodManager inputManager =
                (InputMethodManager) mUserName.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(mUserName, 0);
    }

    //设置
    @TrackClick(value = R.id.layout_set, location = TITLE, eventName = "进入设置界面")
    private void clickSet(View view) {
        startActivity(new Intent(getActivity(), AgentsetActivity.class));
    }

    @TrackClick(value = R.id.layout_deal, location = TITLE, eventName = "进入我的交易界面")
    private void clickDeal(View view) {
        startActivity(new Intent(getActivity(), MineDealActivity.class));
    }

    @TrackClick(value = R.id.layout_data, location = TITLE, eventName = "进入数据分析界面")
    private void clickData(View view) {
        startActivity(new Intent(getActivity(), DataanalysisActivity.class));

    }

    @TrackClick(value = R.id.layout_Usercar, location = TITLE, eventName = "进入身份信息界面")
    private void clickCarInfos(View view) {
        startActivity(new Intent(getActivity(), UserCardActivity.class));
    }

    @TrackClick(value = R.id.layout_us, location = TITLE, eventName = "进入我的账户界面")
    private void clickUZ(View view) {
        startActivity(new Intent(getActivity(), OurAccountActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GET_PRICTURE:
                    mPhotoPath = getSelectMediaPath(data);
                case TAKE_PRICTURE:
                    try {
                        user_img.setImageBitmap(ImageUtils.revitionImageSize(mPhotoPath));
                        mPhotoPath = FileUtils.saveBitmap(ImageUtils.revitionImageSize(mPhotoPath), mPhotoPath.substring(mPhotoPath.lastIndexOf("/")));
                        submmintHeader(mPhotoPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
            }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void submmintHeader(String path) {
        File file = new File(path);
        IdentityHashMap<String, File> files = new IdentityHashMap<>();
        files.put("file", file);

        JSONObject body = new JSONObject();
        try {
            HttpUtil.httpUpload(getActivity(), Constants.APP_HEADER_URL + Constants.CURRENT_USER.getUserId(), body, files, new HttpCallback() {
                @Override
                public void doUploadSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doUploadSuccess(result, obj);
                    try {
                        Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
                        Constants.CURRENT_USER.setPortrait(obj.getString("image"));
                        setViews();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doUploadFailure(Exception exception, String msg) {
                    Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
                    super.doUploadFailure(exception, msg);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private String getSelectMediaPath(Intent data) {
        String path = "";
        Uri uri = data.getData(); // 获取别选中图片的uri
        if (uri.toString().contains("file:///")) {
            path = uri.toString().replace("file:///", "");
        } else {
            String[] filePathColumn = {MediaStore.Images.Media.DATA}; // 获取图库图片路径
            Cursor cursor = getActivity().getContentResolver().query(uri,
                    filePathColumn, null, null, null); // 查询选中图片路径
            cursor.moveToFirst();
            path = cursor.getString(cursor
                    .getColumnIndex(filePathColumn[0]));
            cursor.close();
        }
        return path;
    }
}