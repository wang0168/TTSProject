package chexin.project.tts.chexin.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.PublicMenuBean;
import cn.jpush.android.api.JPushInterface;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.utils.TextUtils;

/**
 * Created by sjb on 2016/3/25.
 */
public class PublicStyleAdapter extends TTSBaseAdapterRecyclerView<PublicMenuBean> {
    private List<PublicMenuBean> data;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private OnButtonClickListener buttonListener;
    private OnCheckButtonClickListener checkButtonClickListener;

    public void setCheckButtonClickListener(OnCheckButtonClickListener checkButtonClickListener) {
        this.checkButtonClickListener = checkButtonClickListener;
    }

    public interface OnButtonClickListener {
        void onClick(View v, int position);
    }

    public interface OnCheckButtonClickListener {
        void onChecked(View v, boolean isChecked, int position);
    }

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.buttonListener = listener;
    }

    public PublicStyleAdapter(Context context, List<PublicMenuBean> data) {
        super(context, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.public_style_item, null, false));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        final ViewHolder holder = (ViewHolder) viewHolder;
        if (data.get(position).getIconId() == 0) {
            holder.img.setVisibility(View.GONE);
        } else {
            holder.img.setVisibility(View.VISIBLE);
            holder.img.setBackgroundResource(data.get(position).getIconId());
        }
        holder.name.setText(data.get(position).getName());
        if (!TextUtils.isEmpty(data.get(position).getOther())) {
            holder.other.setText(data.get(position).getOther());
        }
        if (data.get(position).isRemoveRightIcon()) {
            holder.nextImg.setVisibility(View.GONE);
        } else {
            holder.nextImg.setVisibility(View.VISIBLE);
            holder.nextImg.setBackgroundResource(data.get(position).getIconRightId() == 0 ? R.drawable.next : data.get(position).getIconRightId());
            if (data.get(position).getIconRightId() != 0 && "推送消息".equals(data.get(position).getName())) {
                SharedPreferences preferences = context.getSharedPreferences("push", Context.MODE_PRIVATE);
                if (preferences.getBoolean("status", true)) {
                    holder.nextImg.setChecked(true);
                } else {
                    holder.nextImg.setChecked(false);
                }
                holder.nextImg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        sharedPreferences = context.getSharedPreferences("push", Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        if (isChecked) {
                            JPushInterface.resumePush(context);
                            editor.putBoolean("status", true);
                        } else {
                            editor.putBoolean("status", false);
                            JPushInterface.stopPush(context);
                        }
                        editor.commit();
                    }
                });
            }
            if (data.get(position).getIconRightId() != 0 && "系统消息".equals(data.get(position).getName())) {
                holder.nextImg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (checkButtonClickListener != null) {
                            checkButtonClickListener.onChecked(buttonView, isChecked, position);
                        }
                    }
                });
            }
        }
        if (data.get(position).isMargin()) {
            holder.margin.setVisibility(View.VISIBLE);
        } else {
            holder.margin.setVisibility(View.GONE);
        }

        if (data.get(position).isLine()) {
            holder.line.setVisibility(View.VISIBLE);
        } else {
            holder.line.setVisibility(View.GONE);
        }
    }

    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView other;
        CheckBox nextImg;
        View margin;
        View line;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            other = (TextView) itemView.findViewById(R.id.other);
            nextImg = (CheckBox) itemView.findViewById(R.id.nextImg);
            margin = (View) itemView.findViewById(R.id.margin);
            line = (View) itemView.findViewById(R.id.line);
        }
    }
}
