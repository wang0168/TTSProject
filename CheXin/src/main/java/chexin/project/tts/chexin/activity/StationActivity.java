package chexin.project.tts.chexin.activity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.common.ConfigMoudle;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import chexin.project.tts.chexin.widget.selectPopupWindow.SelectPopupWindow;
import tts.moudle.api.Host;
import tts.moudle.api.activity.CustomPictureSelectorView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.ImgBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;

/**
 * 入驻专线
 */
public class StationActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_array_1)
    ImageButton imgArray1;
    @Bind(R.id.img_array_2)
    ImageButton imgArray2;
    @Bind(R.id.img_array_3)
    ImageButton imgArray3;
    @Bind(R.id.img_array_4)
    ImageButton imgArray4;
    @Bind(R.id.btn_upload_company_img)
    Button btnUploadCompanyImg;
    @Bind(R.id.img_logo)
    ImageButton imgLogo;
    @Bind(R.id.btn_upload_logo)
    Button btnUploadLogo;
    @Bind(R.id.edit_from)
    EditText editFrom;
    @Bind(R.id.btn_from)
    Button btnFrom;
    @Bind(R.id.edit_to)
    EditText editTo;
    @Bind(R.id.btn_to)
    Button btnTo;
    @Bind(R.id.edit_company_name)
    EditText editCompanyName;
    @Bind(R.id.edit_company_address)
    EditText editCompanyAddress;
    @Bind(R.id.edit_telephone1)
    EditText editTelephone1;
    @Bind(R.id.edit_telephone2)
    EditText editTelephone2;
    @Bind(R.id.edit_radiation_city)
    EditText editRadiationCity;
    @Bind(R.id.btn_action)
    Button btnAction;
    private SelectPopupWindow selectPopupWindow;
    private String FromProvince = "";
    private String FromCity = "";
    private String FromCountry = "";
    private String ToProvince = "";
    private String ToCity = "";
    private String ToCountry = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("企业信息"));
//        btnUploadCompanyImg.setOnClickListener(this);
//        btnUploadLogo.setOnClickListener(this);
        btnFrom.setOnClickListener(this);
        btnTo.setOnClickListener(this);
        btnAction.setOnClickListener(this);
        imgArray1.setOnClickListener(this);
        imgArray2.setOnClickListener(this);
        imgArray3.setOnClickListener(this);
        imgArray4.setOnClickListener(this);
        imgLogo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_upload_company_img:
                startRequestData(submitData);
                break;
            case R.id.btn_upload_logo:
                startRequestData(submitData);
                break;
            case R.id.btn_from:
                selectPopupWindow = new SelectPopupWindow(this, ConfigMoudle.getInstance().getProvinceBeans(), 1, "始发地");
                selectPopupWindow.showAtLocation(btnTo, Gravity.CENTER, 0, 0);
                selectPopupWindow.setOnClickListener(new SelectPopupWindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area, String other, int pos) {
                        FromProvince = province;
                        FromCity = city;
                        FromCountry = area;
                        String strTemp = province + city + area;
                        editFrom.setText(strTemp);
                        editFrom.setSelection(strTemp.length());
                    }
                });
                break;
            case R.id.btn_to:
                selectPopupWindow = new SelectPopupWindow(this, ConfigMoudle.getInstance().getProvinceBeans(), 1, "目的地");
                selectPopupWindow.showAtLocation(btnTo, Gravity.CENTER, 0, 0);
                selectPopupWindow.setOnClickListener(new SelectPopupWindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area, String other, int pos) {
                        ToProvince = province;
                        ToCity = city;
                        ToCountry = area;
                        String strTemp = province + city + area;
                        editTo.setText(strTemp);
                        editTo.setSelection(strTemp.length());
                    }
                });
                break;
            case R.id.btn_action:
                startRequestData(submitData);
                break;
            case R.id.img_array_1:
                intent = new Intent(this, CustomPictureSelectorView.class);
                intent.putExtra("maxCount", 1);
                startActivityForResult(intent, 101);
                break;
            case R.id.img_array_2:
                intent = new Intent(this, CustomPictureSelectorView.class);
                intent.putExtra("maxCount", 1);
                startActivityForResult(intent, 102);

                break;
            case R.id.img_array_3:
                intent = new Intent(this, CustomPictureSelectorView.class);
                intent.putExtra("maxCount", 1);
                startActivityForResult(intent, 103);
                break;
            case R.id.img_array_4:
                intent = new Intent(this, CustomPictureSelectorView.class);
                intent.putExtra("maxCount", 1);
                startActivityForResult(intent, 104);
                break;
            case R.id.img_logo:
                intent = new Intent(this, CustomPictureSelectorView.class);
                intent.putExtra("maxCount", 1);
                startActivityForResult(intent, 201);
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case submitData:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("FromProvince", FromProvince);
                params.put("FromCity", FromCity);
                params.put("FromCountry", FromCountry);
                params.put("ToProvince", ToProvince);
                params.put("ToCity", ToCity);
                params.put("ToCountry", ToCountry);
                if (!TextUtils.isEmpty(editCompanyName.getText().toString())) {
                    params.put("company_name", editCompanyName.getText().toString());
                }
                if (!TextUtils.isEmpty(editCompanyAddress.getText().toString())) {
                    params.put("company_address", editCompanyAddress.getText().toString());
                }
                if (!TextUtils.isEmpty(editTelephone1.getText().toString())) {
                    params.put("fixed_telephone1", editTelephone1.getText().toString());
                }
                if (!TextUtils.isEmpty(editTelephone2.getText().toString())) {
                    params.put("fixed_telephone2", editTelephone2.getText().toString());
                }
                if (!TextUtils.isEmpty(editRadiationCity.getText().toString())) {
                    params.put("city_name", editRadiationCity.getText().toString());
                }

                List<PostFormBuilder.FileInput> files = new ArrayList<>();
                if (!TextUtils.isEmpty(logoPath)) {
                    files.add(new PostFormBuilder.FileInput("company_logo", "company_logo.jpg", new File(logoPath)));
                }
                if (!TextUtils.isEmpty(imgOtherPath1)) {
                    files.add(new PostFormBuilder.FileInput("company_img1", "company_img1.jpg", new File(imgOtherPath1)));
                }
                if (!TextUtils.isEmpty(imgOtherPath2)) {
                    files.add(new PostFormBuilder.FileInput("company_img2", "company_img2.jpg", new File(imgOtherPath2)));
                }
                if (!TextUtils.isEmpty(imgOtherPath3)) {
                    files.add(new PostFormBuilder.FileInput("company_img3", "company_img3.jpg", new File(imgOtherPath3)));
                }
                if (!TextUtils.isEmpty(imgOtherPath4)) {
                    files.add(new PostFormBuilder.FileInput("company_img4", "company_img4.jpg", new File(imgOtherPath4)));
                }
                mProgressDialog.show();
                uploadFile(submitData, Host.hostUrl + "route.api?applySpecialRoute", params, files);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        mProgressDialog.dismiss();
        CustomUtils.showTipShort(this, "提交成功！");
        finish();
    }

    List<ImgBean> logo;
    private String logoPath;
    List<ImgBean> imgOther1;
    List<ImgBean> imgOther2;
    List<ImgBean> imgOther3;
    List<ImgBean> imgOther4;
    private String imgOtherPath1;
    private String imgOtherPath2;
    private String imgOtherPath3;
    private String imgOtherPath4;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 201:

                    logo = (List<ImgBean>) data.getSerializableExtra("imgBeans");
                    if (logo.size() > 0) {
                        logoPath = logo.get(0).getPath();
                        Glide.with(this).load(logoPath).diskCacheStrategy(DiskCacheStrategy.ALL).
                                into(imgLogo);
                    }
                    break;
                case 101:
                    imgOther1 = (List<ImgBean>) data.getSerializableExtra("imgBeans");
                    if (imgOther1.size() > 0) {
                        imgOtherPath1 = imgOther1.get(0).getPath();
                        Glide.with(this).load(imgOtherPath1).diskCacheStrategy(DiskCacheStrategy.ALL).
                                into(imgArray1);
                    }
                    break;
                case 102:
                    imgOther2 = (List<ImgBean>) data.getSerializableExtra("imgBeans");
                    if (imgOther2.size() > 0) {
                        imgOtherPath2 = imgOther2.get(0).getPath();
                        Glide.with(this).load(imgOtherPath2).diskCacheStrategy(DiskCacheStrategy.ALL).
                                into(imgArray2);
                    }
                    break;
                case 103:
                    imgOther3 = (List<ImgBean>) data.getSerializableExtra("imgBeans");
                    if (imgOther3.size() > 0) {
                        imgOtherPath3 = imgOther3.get(0).getPath();
                        Glide.with(this).load(imgOtherPath3).diskCacheStrategy(DiskCacheStrategy.ALL).
                                into(imgArray3);
                    }
                    break;
                case 104:
                    imgOther4 = (List<ImgBean>) data.getSerializableExtra("imgBeans");
                    if (imgOther4.size() > 0) {
                        imgOtherPath4 = imgOther4.get(0).getPath();
                        Glide.with(this).load(imgOtherPath4).diskCacheStrategy(DiskCacheStrategy.ALL).
                                into(imgArray4);
                    }
                    break;
            }
        }
    }
}
