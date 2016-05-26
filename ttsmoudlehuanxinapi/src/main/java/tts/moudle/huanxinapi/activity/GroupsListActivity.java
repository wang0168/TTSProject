package tts.moudle.huanxinapi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;

import java.util.List;

import tts.moudle.api.TTSBaseActivity;
import tts.moudle.api.bean.BarBean;
import tts.moudle.huanxinapi.adapter.GroupAdapter;
import tts.moudle.huanxinapi.bean.GroupBean;
import tts.moudle.huanxinapi.bean.HxUserBean;
import  moudle.project.tts.ttsmoudlehuanxinapi.R;

public class GroupsListActivity extends TTSBaseActivity {
	protected List<EMGroup> grouplist;
	private ListView groupsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groups_list_activity);
		setTitle(new BarBean().setMsg("群聊列表"));
		groupsList = (ListView) findViewById(R.id.groupsList);
		grouplist = EMGroupManager.getInstance().getAllGroups();
		GroupAdapter groupAdapter = new GroupAdapter(this, grouplist);
		groupsList.setAdapter(groupAdapter);
		groupsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				HxUserBean hxUserBean = new HxUserBean();
				GroupBean groupBean = new GroupBean();
				groupBean.setGroupId(grouplist.get(position).getGroupId());
				groupBean.setGroupName(grouplist.get(position).getGroupName());
				hxUserBean.setGroupBean(groupBean);
				Bundle bundle = new Bundle();
				bundle.putSerializable("hxUserBean", hxUserBean);
				Intent intent = new Intent(GroupsListActivity.this, ChatGroupActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
}
