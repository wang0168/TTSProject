package tts.project.igg.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.EditText;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.utils.CustomUtils;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;
import tts.project.igg.common.MyAccountMoudle;

/**
 * 编辑页面
 * Created by chen on 2016/3/15.
 */
public class EditActivity extends BaseActivity {
    private EditText editText;
    private EditText edit_picker;

    private String oldStr;
    private String paramKey;
    private String title;
    private TimePickerView pvTime;
    private OptionsPickerView pvOptions;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        oldStr = getIntent().getStringExtra("oldStr");
        title = getIntent().getStringExtra("title");
        editText = (EditText) findViewById(R.id.edit_input);
        edit_picker = (EditText) findViewById(R.id.edit_picker);
        setTitle(new BarBean().setMsg(title));
        MenuItemBean menuItemBean = new MenuItemBean().setTitle("保存").setType("1");
        addMenu(menuItemBean);

        if ("昵称".equals(title)) {
            edit_picker.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            paramKey = "nick_name";
        } else if ("性别".equals(title)) {
            edit_picker.setText(oldStr);
            paramKey = "sex";
            edit_picker.setVisibility(View.VISIBLE);
            editText.setVisibility(View.GONE);
            pvOptions = new OptionsPickerView(this);
            pvOptions.setTitle("选择性别");
            final ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("男");
            arrayList.add("女");
            pvOptions.setPicker(arrayList);
            pvOptions.setCyclic(false);
            pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    edit_picker.setText(arrayList.get(options1));
                }
            });
            edit_picker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pvOptions.show();
                }
            });
        } else if ("手机号".equals(title)) {
            paramKey = "phone";
            edit_picker.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
        } else if ("工作单位".equals(title)) {
            paramKey = "job_unit";
            edit_picker.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
        } else if ("职位".equals(title)) {
            paramKey = "position";
            edit_picker.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
        } else if ("爱好".equals(title)) {
            paramKey = "hobby";
            edit_picker.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
        } else if ("出生日期".equals(title)) {
            paramKey = "age";
            edit_picker.setVisibility(View.VISIBLE);
            editText.setVisibility(View.GONE);
            pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
            pvTime.setTime(new Date());
            pvTime.setCyclic(false);
            pvTime.setCancelable(true);
            pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

                @Override
                public void onTimeSelect(Date date) {
                    edit_picker.setText(getTime(date));
                }
            });
            edit_picker.setText(oldStr);

            edit_picker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pvTime.show();
                }
            });
        }
        editText.setText(oldStr);
        editText.setSelection(oldStr.length());

    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
        super.doMenu(menuItemBean);
        startRequestData(submitData);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        if (index == submitData) {
            params = new ArrayMap<>();
            params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
            params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
            if ("sex".equals(paramKey)) {
                if ("男".equals(edit_picker.getText().toString())) {
                    params.put("sex", "1");
                } else {
                    params.put("sex", "0");
                }
            } else if ("age".equals(paramKey)) {
                params.put("age", edit_picker.getText().toString());
            } else {
                params.put(paramKey, editText.getText().toString());
            }
            List<PostFormBuilder.FileInput> files = new ArrayList<>();
            uploadFile(submitData, Host.hostUrl + "memberInterface.api?updateMemberDetail", params, files, true);
        }

    }

    public String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        CustomUtils.showTipShort(this, "修改成功");
//        Intent intent = new Intent();
//        if ("address".equals(paramKey)) {
//
//            setResult(RESULT_OK, intent.putExtra("newStr", mCurrentProviceName + mCurrentCityName + mCurrentDistrictName));
//        } else {
        setResult(RESULT_OK);
//        }

        finish();
    }


}
