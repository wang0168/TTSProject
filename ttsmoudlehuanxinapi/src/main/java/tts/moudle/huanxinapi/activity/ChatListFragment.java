package tts.moudle.huanxinapi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import tts.moudle.api.TTSBaseFragment;
import tts.moudle.huanxinapi.adapter.ChatListAdapter;
import moudle.project.tts.ttsmoudlehuanxinapi.R;

public class ChatListFragment extends TTSBaseFragment {
	private ListView chatList;
	private ChatListAdapter adapter;
	private List<EMConversation> conversationList = new ArrayList<EMConversation>();
	public static int color = 0;// 标题背景色
	public static int leftDrawable = 0;// 返回背景
	public static String title = "会话列表";
	public static int sendDrawable = 0;// 发送背景
	public static String sendUser = "";
	private View rootView;
	private TextView title_text;
	private static boolean IsLoad;
	private Button title_left_btn;
	private RelativeLayout RLTitle;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.chat_list_activity, null);
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (!IsLoad) {
			IsLoad = true;
			findAllView();
			adapterChat();
		}
	}

	private void findAllView() {
		/*RLTitle = (RelativeLayout) rootView.findViewById(R.id.RLTitle);
		RLTitle.setBackgroundColor(color);

		title_left_btn = (Button) rootView.findViewById(R.id.title_left_btn);
		title_left_btn.setVisibility(View.GONE);
		title_text = (TextView) rootView.findViewById(R.id.title_text);
		title_text.setText(title);*/
	};

	private void adapterChat() {
		chatList = (ListView) rootView.findViewById(R.id.chatList);

		conversationList.addAll(loadConversationsWithRecentChat());
		adapter = new ChatListAdapter(getActivity(), 1, conversationList);
		// 设置adapter
		chatList.setAdapter(adapter);
		chatList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				EMConversation conversation = adapter.getItem(position);
				String username = conversation.getUserName();
				Intent intent = new Intent(getActivity(), ChatActivity.class);
				intent.putExtra("receiveUser", username);
				intent.putExtra("sendUser", sendUser);
				intent.putExtra("color", getResources().getColor(R.color.RGB255_255_255));// 设置标题背景色
				intent.putExtra("leftDrawable", R.drawable.title_back);// 设置标题返回图片
				intent.putExtra("sendDrawable", R.drawable.button_normal);// 发送按钮背景图片
				startActivityForResult(intent, 1000);
			}
		});
	}

	/**
	 * 获取所有会话
	 *
	 * @return +
	 */
	private List<EMConversation> loadConversationsWithRecentChat() {
		// 获取所有会话，包括陌生人
		Hashtable<String, EMConversation> conversations = EMChatManager.getInstance().getAllConversations();
		// 过滤掉messages size为0的conversation
		/**
		 * 如果在排序过程中有新消息收到，lastMsgTime会发生变化 影响排序过程，Collection.sort会产生异常
		 * 保证Conversation在Sort过程中最后一条消息的时间不变 避免并发问题
		 */
		List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
		synchronized (conversations) {
			for (EMConversation conversation : conversations.values()) {
				if (conversation.getAllMessages().size() != 0) {
					// if(conversation.getType() !=
					// EMConversationType.ChatRoom){
					sortList.add(
							new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
					// }
				}
			}
		}
		try {
			// Internal is TimSort algorithm, has bug
			sortConversationByLastChatTime(sortList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<EMConversation> list = new ArrayList<EMConversation>();
		for (Pair<Long, EMConversation> sortItem : sortList) {
			list.add(sortItem.second);
		}
		return list;
	}

	/**
	 * 根据最后一条消息的时间排序
	 *
	 */
	private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
		Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
			@Override
			public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

				if (con1.first == con2.first) {
					return 0;
				} else if (con2.first > con1.first) {
					return 1;
				} else {
					return -1;
				}
			}

		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1000:
			conversationList.clear();
			conversationList.addAll(loadConversationsWithRecentChat());
			adapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	}
}
