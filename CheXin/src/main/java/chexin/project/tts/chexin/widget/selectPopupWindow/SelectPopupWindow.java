package chexin.project.tts.chexin.widget.selectPopupWindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import java.util.List;

import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.ConfigBean;


/**
 * Created by lenove on 2016/4/7.
 */
public class SelectPopupWindow extends PopupWindow implements View.OnClickListener {
    private Context context;
    private View view = null;
    private List<ConfigBean> mData;
    int type = 1;//1:城市联动 2：城市区联动
    private RecyclerView recyclerView;
    private TextView tvCancel, tvConfirm, tvProvince, tvCity, tvDistrict, tvOptions, tvTitle;
    private LinearLayout layoutArea;
    private String title;

    public SelectPopupWindow(Context context, List<ConfigBean> list, int type, String title) {
        super(context);
        this.context = context;
        mData = list;
        this.type = type;
        this.title = title;
        onCreate();
    }

    private void onCreate() {
        findAllView();
        int h = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
        int w = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        this.setContentView(view);
        this.setWidth(w);
        this.setHeight(h);
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        adapterData();
    }

    private void findAllView() {
        view = LayoutInflater.from(context).inflate(R.layout.layout_select_popupwindow, null, false);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(title);

        tvCancel = (TextView) view.findViewById(R.id.cancel_action);
        tvConfirm = (TextView) view.findViewById(R.id.confirm_action);
        layoutArea = (LinearLayout) view.findViewById(R.id.layout_area);
        tvOptions = (TextView) view.findViewById(R.id.tv_options);
        tvProvince = (TextView) view.findViewById(R.id.tv_province);
        tvCity = (TextView) view.findViewById(R.id.tv_city);
        tvDistrict = (TextView) view.findViewById(R.id.tv_district);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_select);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        if (type == 1) {
            layoutArea.setVisibility(View.VISIBLE);
            tvOptions.setVisibility(View.GONE);
        } else {
            layoutArea.setVisibility(View.GONE);
            tvOptions.setVisibility(View.VISIBLE);
        }
    }

    private void adapterData() {
        recyclerView.setAdapter(new SelectAdapter());
    }

    public interface OnClickListener {
        void doClick(String province, String city, String area, String other, int pos);
    }

    public interface OnItemClickListener {
        void doItemClick(String province, String city, String area);
    }

    OnClickListener listener;
    OnItemClickListener itemClickListener;

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setOnClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.CENTER, 0, 0);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_action:
                this.dismiss();
                break;
            case R.id.confirm_action:
                if (type == 1) {
                    listener.doClick(tvProvince.getText().toString(), tvCity.getText().toString(), tvDistrict.getText().toString(), null, 0);
                } else if (type == 2) {
                    listener.doClick(null, null, null, tvOptions.getText().toString(), (int) tvOptions.getTag());
                }
                this.dismiss();
                break;
        }

    }


    public class SelectAdapter extends RecyclerView.Adapter<SelectPopupWindow.SelectViewHolder> {
        int mark = 0;

        @Override
        public SelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SelectViewHolder(LayoutInflater.from(context).inflate(R.layout.item_select, null, false));
        }

        @Override
        public void onBindViewHolder(final SelectViewHolder holder, final int position) {
            holder.textView.setText(mData.get(position).getName());
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type == 1) {


                        if (mark == 0) {
                            tvProvince.setText(mData.get(position).getName());

                            if (mData.get(position).getCityBeans() != null) {
//                            holder.textView.setText(mData.get(position).getName());
                                mData = mData.get(position).getCityBeans();
                            }

                        } else if (mark == 1) {
                            tvCity.setText(mData.get(position).getName());
                            if (mData.get(position).getCountryBeans() != null) {
//                            holder.textView.setText(mData.get(position).getName());
                                mData = mData.get(position).getCountryBeans();
                            }
                        } else {
                            tvDistrict.setText(mData.get(position).getName());
                        }
                        mark++;
                        notifyDataSetChanged();

                    } else {
                        tvOptions.setText(mData.get(position).getName());
                        tvOptions.setTag(position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    public class SelectViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public SelectViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.cityName);
        }
    }
}
