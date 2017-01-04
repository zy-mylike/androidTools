package com.enetic.push.activity.businesses;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.adapter.ImageAdapter;
import com.enetic.push.bean.IMGBean;
import com.enetic.push.bean.ProductBean;
import com.enetic.push.dialog.GeneralDialog;
import com.enetic.push.dialog.ImageDialog;
import com.enetic.push.dialog.LoadingDialog;
import com.enetic.push.images.ImageGridActivity;
import com.enetic.push.images.util.FileUtils;
import com.enetic.push.utils.ImageUtils;
import com.enetic.push.utils.HttpRequest;
import com.enetic.push.view.CashierInputFilter;
import com.enetic.push.view.IntegerInputFilter;
import com.enetic.push.view.NoScrollGridView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.CommonUtil;
import org.eteclab.track.annotation.TrackClick;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_proudct_edit)
public class ProductEditActivity extends ParentActivity {
    private final static String TITLE = "新增商品";

    ImageAdapter adapter;

    @ViewIn(R.id.gridview)  //添加图片
    private NoScrollGridView mImagsList;

    private ArrayList<String> mImages;
    ArrayList<IMGBean> ImgBeans;
    private String mPhotoPath; //拍照图片的路径。
    private final static int TAKE_PRICTURE = 0x002; //TAKE_PICTURE
    public final static int GET_PRICTURE = 0x001; //GET_PICTURE

    @ViewIn(R.id.deal_reward)
    private EditText mDealReward;//代理折扣

    @ViewIn(R.id.share_reward)
    private EditText mShareReward;//分享奖励

    @ViewIn(R.id.product_name)
    private EditText mProductName;
    @ViewIn(R.id.product_price)
    private EditText mProductPrice;
    @ViewIn(R.id.product_desp)
    private EditText mProductDesp;//商品描述
    @ViewIn(R.id.product_sum)
    private EditText mProductNum;

    @ViewIn(R.id.tv_alert)
    private TextView tv_shareReward;//分享奖励提示

    @ViewIn(R.id.tv_alert2)
    private TextView tv_dealReward;//代理折扣提示

    private ProductBean mProBean;

    public String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        str = getIntent().getStringExtra("startFrom");
        if (str.equals("add")) {
            setToolTitle(TITLE);
        } else { //"edit"
            setToolTitle("编辑商品");
        }


        FileUtils.deleteDir();//先删除 formats 目录。

        IntegerInputFilter integerInputFilter = new IntegerInputFilter();
        integerInputFilter.setMAX_VALUE(100);//设置输入0～100的整数。

        mProBean = (ProductBean) getIntent().getSerializableExtra("bean"); //从商品详情页带过来的数据。

        mProductPrice.setFilters(new InputFilter[]{new CashierInputFilter().setmDigit(9)});
        mShareReward.setFilters(new InputFilter[]{new CashierInputFilter()});

        mDealReward.setFilters(new InputFilter[]{new CashierInputFilter()});//代理折扣
//        mDealReward.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        mDealReward.setFilters(new InputFilter[]{integerInputFilter});//代理折扣



        mImages = new ArrayList<>(); //所有图片的路径集合。
        adapter = new ImageAdapter(this, mImages);
        mImagsList.setAdapter(adapter); //为控件设置适配器。
        mImagsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getAdapter().getItem(position) == null) {
                    showImages(); //选择相机或相册
                } else {
                    startActivityForResult(new Intent(getApplicationContext(), ProductImgsActivity.class).putExtra("imgs", mImages).putExtra("position", position), 100);
                }
            }
        });

        if (mProBean != null) {
            mProductPrice.setText(mProBean.getPrice());
            mShareReward.setText(mProBean.getShareReward());
            mDealReward.setText(mProBean.getOrderReward());
            mProductName.setText(mProBean.getName());
            mProductDesp.setText(mProBean.getDesp());
            mProductNum.setText(mProBean.getRepertory());
            ImgBeans = (ArrayList<IMGBean>) getIntent().getSerializableExtra("imgs");
            if (ImgBeans != null) {
                for (IMGBean bean : ImgBeans) {
                    mImages.add(bean.url);
                }
            }
            adapter.notifyDataSetChanged(mImages);
        }
    }

    @ViewIn(R.id.submint)
    private Button mSubmint;//发布

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    submintProduct((List<String>) msg.obj);
                    break;
                case 2:
                    mSubmint.setEnabled(true);
                    mSubmint.setBackgroundColor(Color.parseColor("#ffffff"));
                    break;
            }

        }
    };

    @TrackClick(value = R.id.submint, location = TITLE, eventName = "发布")
    private void clickSubmint(View view) {

        mSubmint.setEnabled(false);
        mSubmint.setBackgroundColor(Color.parseColor("#4d000000"));
        new Thread() {
            @Override
            public void run() {
                super.run();
                ArrayList<String> datas = new ArrayList<>();
                for (String paths : mImages) {
                    String path = paths;
                    if (paths.startsWith("http://") && !new File(paths).exists()) {
                        path = FileUtils.SDPATH + paths.substring(paths.lastIndexOf("/"));
                    }
                    try {
                        path = FileUtils.saveBitmap(ImageUtils.revitionImageSize(path), path.substring(path.lastIndexOf("/")));
                        datas.add(path);

                    } catch (IOException e) {
                        e.printStackTrace();
                        handler.obtainMessage(2).sendToTarget();
                    }
                }

                handler.obtainMessage(1, datas).sendToTarget();
            }
        }.start();
    }

    @TrackClick(value = R.id.tv_alert, location = TITLE, eventName = "提示")//分享奖励提示
    private void clickalert_share(View view) {
        //Toast.makeText(this,"奖励",Toast.LENGTH_SHORT).show();
        final GeneralDialog generalDialog = new GeneralDialog(this, getRootLayout());
        generalDialog.setTitle("提示");
        generalDialog.setMessage("代理帮助转发该商品所获的现金奖励。每个商品可转发多次但只计费一次。奖励价格建议在0.05元或更多。单位：元。");
        generalDialog.setPositiveButton("朕知道了", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generalDialog.dismiss();
            }
        });
        generalDialog.show();

    }

    @TrackClick(value = R.id.tv_alert2, location = TITLE, eventName = "提示")//代理折扣提示
    private void clickalert_deal(View view) {
        //Toast.makeText(this,"代理折扣",Toast.LENGTH_SHORT).show();
        final GeneralDialog generalDialog = new GeneralDialog(this, getRootLayout());
        generalDialog.setTitle("提示");
        generalDialog.setMessage("代理转发的商品成功交易后所获的现金奖励。按每件商品奖励。奖励价格建议在1%或更多。单位：％。");
        generalDialog.setPositiveButton("朕知道了", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generalDialog.dismiss();
            }
        });
        generalDialog.show();
    }


    public void getBack(){
        mSubmint.setEnabled(true);
        mSubmint.setBackgroundColor(Color.parseColor("#ffffff"));

    }

    //提交商品
    private void submintProduct(List<String> datas) {

        final String name = mProductName.getText().toString();
        String price = mProductPrice.getText().toString();
        String desp = mProductDesp.getText().toString();//商品描述

        String shareReward = mShareReward.getText().toString();//分享奖励
        String orderReward = mDealReward.getText().toString();//代理折扣
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "商品名称不能为空", Toast.LENGTH_SHORT).show();
            getBack();
            return;
        }
        if (TextUtils.isEmpty(price)) {
            Toast.makeText(getApplicationContext(), "商品价格不能为空", Toast.LENGTH_SHORT).show();
            getBack();
            return;
        }
        if (TextUtils.isEmpty(mProductNum.getText().toString()) || "0".equals(mProductNum.getText().toString())) {
            Toast.makeText(getApplicationContext(), "商品库存不能为0", Toast.LENGTH_SHORT).show();
            getBack();
            return;
        }

        if (TextUtils.isEmpty(desp)) {
            Toast.makeText(getApplicationContext(), "商品描述不能为空", Toast.LENGTH_SHORT).show();
            getBack();
            return;
        }

        Long nums = Long.valueOf(mProductNum.getText().toString());
        if (nums > Integer.MAX_VALUE) {
            mProductNum.setText("0");
            Toast.makeText(getApplicationContext(), "请重新填写商品库存(数量过大)", Toast.LENGTH_SHORT).show();
            getBack();
            return;
        }

        if (TextUtils.isEmpty(shareReward)) {
            Toast.makeText(getApplicationContext(), "分享奖励不能为空", Toast.LENGTH_SHORT).show();
            getBack();
            return;
        }
        if (TextUtils.isEmpty(orderReward)) {
            Toast.makeText(getApplicationContext(), "代理折扣不能为空", Toast.LENGTH_SHORT).show();
            getBack();
            return;
        }

        String id = "";
        if (mProBean != null) {
            id = mProBean.getId();
        }
//        ArrayList<String> datas = new ArrayList<>();
//        for (String paths : mImages) {
//            if (paths.startsWith("http://") && !new File(paths).exists()) {
//                datas.add(FileUtils.SDPATH + paths.substring(paths.lastIndexOf("/")));
//            } else {
//                datas.add(paths);
//            }
//        }


        mSubmint.setEnabled(false);
        mSubmint.setBackgroundColor(Color.parseColor("#4d000000"));
        final LoadingDialog dialog = new LoadingDialog(this);
        dialog.show(-1);
        HttpRequest.post(getApplicationContext(), id, name, price, mProductNum.getText().toString(), desp, "1", shareReward, orderReward, Constants.CURRENT_USER.getUserId(), datas,

                new RequestCallBack() {
                    @Override
                    public void onSuccess(ResponseInfo result) {
                        LogUtils.i("上传成功" + result.result.toString());
                        dialog.setProgress("100%");
                        try {
                            File f = new File(Environment.getExternalStorageDirectory(), "tc.txt");
                            FileWriter writer = new FileWriter(f);
                            writer.write(result.result.toString());
                            writer.close();
                            dialog.dismiss();
                            mSubmint.setEnabled(true);
                            FileUtils.deleteDir(); //删除一下fromats文件夹。
                            JSONObject e = new JSONObject((String) result.result);
                            String code = e.getString("code");
                            String msg = e.getString("message");
                            Intent intent = new Intent(getApplicationContext(), PublishSucessActivity.class);
                            intent.putExtra("datas", e.getString("data"));
                            intent.putExtra("startFrom", str); //区别新增商品，编辑商品 分别进入发布成功页。

                            startActivity(intent);
                            LogUtils.i("操作成功：" + msg);
                            finish();
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        mSubmint.setEnabled(true);
                        dialog.dismiss();

                        LogUtils.i(error + error.getMessage() + "上传失败" + msg.toString());

                        Toast.makeText(getApplicationContext(), "上传失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        dialog.setProgress(((current * 100 - 1) / total) + "%");
                    }
                }
        );
    }


    /**
     * 弹出对话框，选择 相册 或 拍照。
     */
    private void showImages() {
        final ImageDialog dialog = new ImageDialog(ProductEditActivity.this, R.style.Transparent);
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.take_images) { //相机
                    //mPhotoPath = "img-" + System.currentTimeMillis() + ".jpg";
                    if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                        // mPhotoPath = Environment.getExternalStorageDirectory().getPath() + "/" + mPhotoPath;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, TAKE_PRICTURE);
                    } else {
                        Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
                    }
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoPath);
                } else if (v.getId() == R.id.images) { //相册

                    Intent intent = new Intent(ProductEditActivity.this, ImageGridActivity.class);
                    intent.putExtra("count", adapter.getListCount());
                    startActivityForResult(intent, GET_PRICTURE);
                }
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 100: //图片浏览页面：ProductImgsActivity
                    mImages = (ArrayList<String>) data.getSerializableExtra("imgs");
                    adapter.notifyDataSetChanged(mImages);
                    break;
                case GET_PRICTURE://GET_PICTURE 相册
                    int size = 0;
                    if (mImages == null) {
                        mImages = new ArrayList<>();
                    }
                    size = mImages.size();
                    ArrayList<String> list = (ArrayList<String>) data.getSerializableExtra("images");
                    if (size + list.size() > 9) {
                        Toast.makeText(this, "最多上传9张图片", Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    for (String path : list) {
//                        String ph = null;
//                        try {
//                            ph = FileUtils.saveBitmap(ImageUtils.revitionImageSize(path),path.substring(path.lastIndexOf("/")));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        String ph = ImageUtils.cQuality(path);
//                        mImages.add(path);
//                    }
                    mImages.addAll(list);
                    adapter.notifyDataSetChanged(mImages);

                    break;
                case TAKE_PRICTURE://TAKE_PICTURE 拍照

                    Uri uri = data.getData();
                    if (uri == null) {
                        //use bundle to get data
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                            String path = FileUtils.saveBitmap(photo, "img-" + System.currentTimeMillis() + ".jpg");
                            if (!mImages.contains(path)) {
                                mImages.add(path);
                                adapter.notifyDataSetChanged(mImages);
                            }
                            break;
                        } else {
                            Toast.makeText(getApplicationContext(), "err****", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } else {
                        //to do find the path of pic by uri
                        Toast.makeText(getApplicationContext(), "拍照失败", Toast.LENGTH_LONG).show();
                        // uri.getPath();
                    }

//                   String path = ImageUtils.cQuality(mPhotoPath);
//                    mPhotoPath = path;
//                    if (!mImages.contains(mPhotoPath)) {
//                        mImages.add(mPhotoPath);
//                        adapter.notifyDataSetChanged(mImages);
//                    }

                    break;
                default:
            }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public View getRootLayout() {
        return (findViewById(android.R.id.content)).getRootView();
    }
}