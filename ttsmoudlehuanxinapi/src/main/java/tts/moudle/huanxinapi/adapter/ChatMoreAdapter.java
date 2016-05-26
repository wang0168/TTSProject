package tts.moudle.huanxinapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import tts.moudle.huanxinapi.bean.MoreBean;
import  moudle.project.tts.ttsmoudlehuanxinapi.R;

public class ChatMoreAdapter  extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private List<MoreBean> data;
	private Context context;

	public ChatMoreAdapter(Context context, List<MoreBean> data) {
		this.context = context;
		this.data = data;
		this.layoutInflater = LayoutInflater.from(context);
	}

	public final class Zujian {
		public TextView name;
		public ImageView img;
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	/**
	 * 获得某一位置的数据
	 */
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	/**
	 * 获得唯一标识
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Zujian zujian = null;
		if (convertView == null) {
			zujian = new Zujian();
			// 获得组件，实例化组件
			convertView = layoutInflater.inflate(R.layout.chat_more_item, null);
			zujian.img = (ImageView) convertView.findViewById(R.id.img);
			zujian.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(zujian);
		} else {
			zujian = (Zujian) convertView.getTag();
		}

		zujian.img.setBackgroundResource(data.get(position).getImg());
		zujian.name.setText(data.get(position).getName());
		return convertView;
	}
}


