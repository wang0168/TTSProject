package tts.moudle.huanxinapi.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMConversation.EMConversationType;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import tts.moudle.api.TTSBaseActivity;
import tts.moudle.api.bean.BarBean;
import tts.moudle.huanxinapi.adapter.ChatListAdapter;
import tts.moudle.huanxinapi.bean.GroupBean;
import tts.moudle.huanxinapi.bean.HxUserBean;
import  moudle.project.tts.ttsmoudlehuanxinapi.R;

public class ChatListActivity extends TTSBaseActivity implements EMEventListener {
	private ListView chatList;
	private ChatListAdapter adapter;
	private List<EMConversation> conversationList = new ArrayList<EMConversation>();
	private HxUserBean hxUserBean;
	private LinearLayout LLTitle;
	private SoundPool soundPool;
	private HashMap sound = new HashMap();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_list_activity);
		soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
		sound.put(1, soundPool.load(this, R.raw.voice5473, 1));
		getIntentData();
		findAllView();
		adapterChat();
	}

	private void getIntentData() {
		Intent intent = getIntent();
		hxUserBean = (HxUserBean) intent.getSerializableExtra("hxUserBean");
	}

	private void findAllView() {
		setTitle(new BarBean().setMsg("会话列表"));
	}

	private void adapterChat() {
		chatList = (ListView) findViewById(R.id.chatList);
		conversationList.addAll(loadConversationsWithRecentChat());
		adapter = new ChatListAdapter(this, 1, conversationList);
		// 设置adapter
		chatList.setAdapter(adapter);
		chatList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				EMConversation conversation = adapter.getItem(position);
				String username = conversation.getUserName();
				HxUserBean hxUserBean1 = hxUserBean;
				if (conversation.getType() == EMConversationType.GroupChat) {
					GroupBean groupBean = new GroupBean();
					groupBean.setGroupId(username);
					groupBean.setGroupName(
							EMGroupManager.getInstance().getGroup(username) == null ? "群聊"
									: EMGroupManager.getInstance().getGroup(username)
											.getGroupName());
					hxUserBean1.setGroupBean(groupBean);
					Bundle bundle = new Bundle();
					bundle.putSerializable("hxUserBean", hxUserBean1);
					Intent intent = new Intent(ChatListActivity.this, ChatGroupActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				} else if (conversation.getType() == EMConversationType.Chat) {
					{
						hxUserBean1.setFriendAccount(username);
						hxUserBean1.setFriendName("路人甲");
						Bundle bundle = new Bundle();
						bundle.putSerializable("hxUserBean", hxUserBean1);
						Intent intent = new Intent(ChatListActivity.this, ChatActivity.class);
						intent.putExtras(bundle);
						startActivityForResult(intent, 1000);
					}
				}
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
	 * a
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			setResult(RESULT_CANCELED);
			finish();
		}
		return super.onKeyDown(keyCode, event);
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

	/**
	 * 事件监听
	 * 
	 * see {@link EMNotifierEvent}
	 */
	@Override
	public void onEvent(EMNotifierEvent event) {
		switch (event.getEvent()) {
		case EventNewMessage: {
			// 获取到message
			EMMessage message = (EMMessage) event.getData();

			String username = null;
			// 群组消息
			if (message.getChatType() == ChatType.GroupChat || message.getChatType() == ChatType.ChatRoom) {
				username = message.getTo();
			} else {
				// 单聊消息
				username = message.getFrom();
			}

			// 如果是当前会话的消息，刷新聊天页面

			refreshUIWithNewMessage(username);

			break;
		}
		case EventDeliveryAck: {
			// 获取到message
			EMMessage message = (EMMessage) event.getData();
			refreshUI();
			break;
		}
		case EventReadAck: {
			// 获取到message
			EMMessage message = (EMMessage) event.getData();
			refreshUI();
			break;
		}
		case EventOfflineMessage: {
			// a list of offline messages
			// List<EMMessage> offlineMessages = (List<EMMessage>)
			// event.getData();
			refreshUI();
			break;
		}
		default:
			break;
		}

	}

	private void refreshUI() {
		if (adapter == null) {
			return;
		}

		runOnUiThread(new Runnable() {
			public void run() {
				soundPool.play((int) sound.get(1), 1, 1, 0, 0, 1);
				conversationList = loadConversationsWithRecentChat();
				adapter.notifyDataSetChanged();
			}
		});
	}

	private void refreshUIWithNewMessage(String name) {
		if (adapter == null) {
			return;
		}

		runOnUiThread(new Runnable() {
			public void run() {
				soundPool.play((int) sound.get(1), 1, 1, 0, 0, 1);
				conversationList = loadConversationsWithRecentChat();
				adapter.notifyDataSetChanged();
			}
		});
	}

}
