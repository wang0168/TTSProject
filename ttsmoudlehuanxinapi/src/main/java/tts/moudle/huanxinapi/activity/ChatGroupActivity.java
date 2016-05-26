package tts.moudle.huanxinapi.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMError;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMConversation.EMConversationType;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.ImageMessageBody;
import com.easemob.chat.LocationMessageBody;
import com.easemob.chat.NormalFileMessageBody;
import com.easemob.chat.TextMessageBody;
import com.easemob.chat.VideoMessageBody;
import com.easemob.chat.VoiceMessageBody;
import com.easemob.util.EMLog;
import com.easemob.util.PathUtil;
import com.easemob.util.VoiceRecorder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tts.moudle.api.TTSBaseActivity;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.baidumapapi.activity.BaiduMapActivity;
import tts.moudle.huanxinapi.GroupRemoveListener;
import tts.moudle.huanxinapi.VoicePlayClickListener;
import tts.moudle.huanxinapi.adapter.ChatGroupAdapter;
import tts.moudle.huanxinapi.adapter.ChatMoreAdapter;
import tts.moudle.huanxinapi.adapter.ExpressionAdapter;
import tts.moudle.huanxinapi.adapter.ExpressionPackAdapter;
import tts.moudle.huanxinapi.bean.HxUserBean;
import tts.moudle.huanxinapi.bean.MoreBean;
import tts.moudle.huanxinapi.utils.CommonUtils;
import tts.moudle.huanxinapi.utils.SmileUtils;
import tts.moudle.huanxinapi.widget.ExpandGridView;
import  moudle.project.tts.ttsmoudlehuanxinapi.R;
public class ChatGroupActivity extends TTSBaseActivity implements EMEventListener {
	private static final String TAG = "ChatGroupActivity";
	private static final int REQUEST_CODE_EMPTY_HISTORY = 2;
	public static final int REQUEST_CODE_CONTEXT_MENU = 3;
	private static final int REQUEST_CODE_MAP = 4;
	public static final int REQUEST_CODE_TEXT = 5;
	public static final int REQUEST_CODE_VOICE = 6;
	public static final int REQUEST_CODE_PICTURE = 7;
	public static final int REQUEST_CODE_LOCATION = 8;
	public static final int REQUEST_CODE_NET_DISK = 9;
	public static final int REQUEST_CODE_FILE = 10;
	public static final int REQUEST_CODE_COPY_AND_PASTE = 11;
	public static final int REQUEST_CODE_PICK_VIDEO = 12;
	public static final int REQUEST_CODE_DOWNLOAD_VIDEO = 13;
	public static final int REQUEST_CODE_VIDEO = 14;
	public static final int REQUEST_CODE_DOWNLOAD_VOICE = 15;
	public static final int REQUEST_CODE_SELECT_USER_CARD = 16;
	public static final int REQUEST_CODE_SEND_USER_CARD = 17;
	public static final int REQUEST_CODE_CAMERA = 18;
	public static final int REQUEST_CODE_LOCAL = 19;
	public static final int REQUEST_CODE_CLICK_DESTORY_IMG = 20;
	public static final int REQUEST_CODE_GROUP_DETAIL = 21;
	public static final int REQUEST_CODE_SELECT_VIDEO = 23;
	public static final int REQUEST_CODE_SELECT_FILE = 24;
	public static final int REQUEST_CODE_ADD_TO_BLACKLIST = 25;

	public static final int RESULT_CODE_COPY = 1;
	public static final int RESULT_CODE_DELETE = 2;
	public static final int RESULT_CODE_FORWARD = 3;
	public static final int RESULT_CODE_OPEN = 4;
	public static final int RESULT_CODE_DWONLOAD = 5;
	public static final int RESULT_CODE_TO_CLOUD = 6;
	public static final int RESULT_CODE_EXIT_GROUP = 7;

	public static final int CHATTYPE_SINGLE = 1;
	public static final int CHATTYPE_GROUP = 2;
	public static final int CHATTYPE_CHATROOM = 3;
	private final int video_call = 1001;
	private final int voice_call = 1002;
	private ListView listView = null;
	private ViewPager expressionPager;
	private ImageView iv_emoticons_normal;
	private ImageView iv_emoticons_checked;
	private Button voice_btn;
	private Button keyboard_btn;
	private LinearLayout btn_press_to_speak;
	private GridView RLMore;
	private Button more_btn;
	private Button Send_btn;
	private File cameraFile;
	private View recordingContainer;
	private TextView recordingHint;
	private VoiceRecorder voiceRecorder;
	private ImageView micImage;
	private Drawable[] micImages;
	private TextView title_text;
	private EMConversation conversation;
	private EditText ET_Msg;
	private String Msg = "";
	private ChatGroupAdapter msgAdapter;
	private InputMethodManager manager;
	private List<String> reslist;
	private RelativeLayout RLExpression;
	public String playMsgId;
	private SwipeRefreshLayout swipeRefreshLayout;
	private LinearLayout LLTitle;
	private HxUserBean hxUserBean;
	private SoundPool soundPool;
	private List<MoreBean> moreBeans = new ArrayList<MoreBean>();
	private PowerManager.WakeLock wakeLock;
	private HashMap sound = new HashMap();

	private GroupListener groupListener;
	public EMGroup group;
	private Handler micImageHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			// 切换msg切换图片
			micImage.setImageDrawable(micImages[msg.what]);
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_activity);
		soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
		sound.put(1, soundPool.load(this, R.raw.voice5473, 1));
		getIntentData();
		initChat();
		findAllView();
		initMore();
		adpaterMsg();
		adpaterExpression();
		adapterRefresh();
		// hideKeyboard();
	}

	private void getIntentData() {
		Intent intent = getIntent();
		hxUserBean = (HxUserBean) intent.getSerializableExtra("hxUserBean");
	}

	/**
	 * 填充更多数据
	 */
	private void initMore() {
		// 图片按钮
		if (hxUserBean == null || !hxUserBean.isCloseImg()) {
			MoreBean imgMoreBean = new MoreBean();
			imgMoreBean.setImg(R.drawable.chat_image_selector);
			imgMoreBean.setName("图片");
			imgMoreBean.setTag("1");
			moreBeans.add(imgMoreBean);
		}

		// 拍照按钮
		if (hxUserBean == null || !hxUserBean.isCloseTakic()) {
			MoreBean takepicMoreBean = new MoreBean();
			takepicMoreBean.setImg(R.drawable.chat_takepic_selector);
			takepicMoreBean.setName("拍照");
			takepicMoreBean.setTag("2");
			moreBeans.add(takepicMoreBean);
		}

		// 位置按钮
		if (hxUserBean == null || !hxUserBean.isCloseLocal()) {
			MoreBean locationMoreBean = new MoreBean();
			locationMoreBean.setImg(R.drawable.chat_location_selector);
			locationMoreBean.setName("位置");
			locationMoreBean.setTag("3");
			moreBeans.add(locationMoreBean);
		}

		// 视频消息按钮
		if (hxUserBean == null || !hxUserBean.isCloseVideo()) {
			MoreBean videoMoreBean = new MoreBean();
			videoMoreBean.setImg(R.drawable.chat_video_selector);
			videoMoreBean.setName("视频");
			videoMoreBean.setTag("6");
			moreBeans.add(videoMoreBean);
		}
		// 文件消息按钮
		if (hxUserBean == null || !hxUserBean.isCloseFile()) {
			MoreBean fileMoreBean = new MoreBean();
			fileMoreBean.setImg(R.drawable.chat_file_selector);
			fileMoreBean.setName("文件");
			fileMoreBean.setTag("7");
			moreBeans.add(fileMoreBean);
		}
		ChatMoreAdapter adapter = new ChatMoreAdapter(this, moreBeans);
		RLMore.setAdapter(adapter);
		RLMore.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (moreBeans.get(position).getTag().equals("1")) {
					selectPicFromLocal();
				} else if (moreBeans.get(position).getTag().equals("2")) {
					selectPicFromCamera();// 点击照相图标
				} else if (moreBeans.get(position).getTag().equals("3")) {
					startActivityForResult(new Intent(ChatGroupActivity.this, BaiduMapActivity.class),
							REQUEST_CODE_MAP);
				} else if (moreBeans.get(position).getTag().equals("6")) {
					Intent intent = new Intent(ChatGroupActivity.this, ImageGridActivity.class);
					startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
				} else if (moreBeans.get(position).getTag().equals("7")) {
					selectFileFromLocal();
				}
			}
		});
	}

	// 下拉框 刷新适配
	private void adapterRefresh() {
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.chat_swipe_layout);
		swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						if (listView.getFirstVisiblePosition() == 0) {
							List<EMMessage> messages;
							try {
								messages = conversation.loadMoreMsgFromDB(msgAdapter.getItem(0).getMsgId(), 10);
								/*
								 * if (chatType == CHATTYPE_SINGLE){ messages =
								 * conversation
								 * .loadMoreMsgFromDB(adapter.getItem
								 * (0).getMsgId(), pagesize); } else{ messages =
								 * conversation
								 * .loadMoreGroupMsgFromDB(adapter.getItem
								 * (0).getMsgId(), pagesize); }
								 */
							} catch (Exception e1) {
								swipeRefreshLayout.setRefreshing(false);
								return;
							}

							if (messages.size() > 0) {
								msgAdapter.notifyDataSetChanged();
								msgAdapter.refreshSeekTo(messages.size() - 1);
								if (messages.size() != 10) {
									Toast.makeText(ChatGroupActivity.this,
											getResources().getString(R.string.no_more_messages), Toast.LENGTH_SHORT)
											.show();
									// haveMoreData = false;
								}
							} else {
								Toast.makeText(ChatGroupActivity.this,
										getResources().getString(R.string.no_more_messages), Toast.LENGTH_SHORT).show();
								// haveMoreData = false;
							}

						}
						swipeRefreshLayout.setRefreshing(false);
					}
				}, 1000);
			}
		});
	}

	// 适配聊天表情
	private void adpaterExpression() {
		// 表情list
		reslist = getExpressionRes(35);
		List<View> views = new ArrayList<View>();
		expressionPager = (ViewPager) findViewById(R.id.expressionPager);
		View gv1 = getGridChildView(1);
		View gv2 = getGridChildView(2);
		views.add(gv1);
		views.add(gv2);
		expressionPager.setAdapter(new ExpressionAdapter(views));
	}

	// 适配聊天信息
	private void adpaterMsg() {
		listView = (ListView) findViewById(R.id.UserList);
		msgAdapter = new ChatGroupAdapter(this, hxUserBean);
		listView.setAdapter(msgAdapter);
		msgAdapter.refreshSelectLast();
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});
	}

	private void findAllView() {
		setTitle(new BarBean().setMsg(hxUserBean.getFriendName() == null ? "" : hxUserBean.getFriendName()));
		// 下面发送语音
		wakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE))
				.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "demo");
		recordingContainer = findViewById(R.id.recording_container);
		recordingHint = (TextView) findViewById(R.id.recording_hint);
		voiceRecorder = new VoiceRecorder(micImageHandler);
		micImage = (ImageView) findViewById(R.id.mic_image);
		// 动画资源文件,用于录制语音时
		micImages = new Drawable[] { getResources().getDrawable(R.drawable.record_animate_01),
				getResources().getDrawable(R.drawable.record_animate_02),
				getResources().getDrawable(R.drawable.record_animate_03),
				getResources().getDrawable(R.drawable.record_animate_04),
				getResources().getDrawable(R.drawable.record_animate_05),
				getResources().getDrawable(R.drawable.record_animate_06),
				getResources().getDrawable(R.drawable.record_animate_07),
				getResources().getDrawable(R.drawable.record_animate_08),
				getResources().getDrawable(R.drawable.record_animate_09),
				getResources().getDrawable(R.drawable.record_animate_10),
				getResources().getDrawable(R.drawable.record_animate_11),
				getResources().getDrawable(R.drawable.record_animate_12),
				getResources().getDrawable(R.drawable.record_animate_13),
				getResources().getDrawable(R.drawable.record_animate_14), };
		// 到此截止

		ET_Msg = (EditText) this.findViewById(R.id.ET_Msg);
		Send_btn = (Button) findViewById(R.id.Send_btn);
		// 监听文字框
		ET_Msg.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!TextUtils.isEmpty(s)) {
					more_btn.setVisibility(View.GONE);
					Send_btn.setVisibility(View.VISIBLE);
				} else {
					more_btn.setVisibility(View.VISIBLE);
					Send_btn.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		iv_emoticons_normal = (ImageView) findViewById(R.id.iv_emoticons_normal);
		iv_emoticons_checked = (ImageView) findViewById(R.id.iv_emoticons_checked);

		RLExpression = (RelativeLayout) findViewById(R.id.RLExpression);
		voice_btn = (Button) findViewById(R.id.voice_btn);
		keyboard_btn = (Button) findViewById(R.id.keyboard_btn);
		btn_press_to_speak = (LinearLayout) findViewById(R.id.btn_press_to_speak);
		btn_press_to_speak.setOnTouchListener(new PressToSpeakListen());

		RLMore = (GridView) findViewById(R.id.RLMore);
		more_btn = (Button) findViewById(R.id.more_btn);
	}

	private void initChat() {
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		conversation = EMChatManager.getInstance().getConversationByType(hxUserBean.getGroupBean().getGroupId(),
				EMConversationType.GroupChat);
		conversation.markAllMessagesAsRead();
	}

	protected void onGroupViewCreation() {
		setTitle(hxUserBean.getGroupBean().getGroupName());
		// 监听当前会话的群聊解散被T事件
		groupListener = new GroupListener();
		EMGroupManager.getInstance().addGroupChangeListener(groupListener);
	}

	/**
	 * 监测群组解散或者被T事件
	 * 
	 */
	class GroupListener extends GroupRemoveListener {
		@Override
		public void onUserRemoved(final String groupId, String groupName) {
			runOnUiThread(new Runnable() {
				String st13 = getResources().getString(R.string.you_are_group);

				public void run() {
					if (hxUserBean.getGroupBean().getGroupId().equals(groupId)) {
						CustomUtils.showTipShort(ChatGroupActivity.this, st13);
						finish();
					}
				}
			});
		}

		@Override
		public void onGroupDestroy(final String groupId, String groupName) {
			// 群组解散正好在此页面，提示群组被解散，并finish此页面
			runOnUiThread(new Runnable() {
				String st14 = getResources().getString(R.string.the_current_group);

				public void run() {
					if (hxUserBean.getGroupBean().getGroupId().equals(groupId)) {
						CustomUtils.showTipShort(ChatGroupActivity.this, st14);
						finish();
					}
				}
			});
		}

	}

	public void doClick(View v) {
		if (v.getId() == R.id.Send_btn) {
			Msg = ET_Msg.getText().toString();
			if (Msg.equalsIgnoreCase("")) {
				Toast.makeText(this, "发送不能为空", Toast.LENGTH_LONG);
			} else {
				sendMsg(Msg);
				hideKeyboard();
			}
		} else if (v.getId() == R.id.ET_Msg) {
			iv_emoticons_normal.setVisibility(View.VISIBLE);
			iv_emoticons_checked.setVisibility(View.INVISIBLE);
			RLExpression.setVisibility(View.GONE);
			RLMore.setVisibility(View.GONE);
		} else if (v.getId() == R.id.iv_emoticons_normal) {
			iv_emoticons_normal.setVisibility(View.INVISIBLE);
			iv_emoticons_checked.setVisibility(View.VISIBLE);
			RLExpression.setVisibility(View.VISIBLE);
			RLMore.setVisibility(View.GONE);
			hideKeyboard();
		} else if (v.getId() == R.id.iv_emoticons_checked) {
			iv_emoticons_normal.setVisibility(View.VISIBLE);
			iv_emoticons_checked.setVisibility(View.INVISIBLE);
			RLExpression.setVisibility(View.GONE);
			RLMore.setVisibility(View.GONE);
			hideKeyboard();
		} else if (v.getId() == R.id.voice_btn) {
			voice_btn.setVisibility(View.INVISIBLE);
			keyboard_btn.setVisibility(View.VISIBLE);
			btn_press_to_speak.setVisibility(View.VISIBLE);
			iv_emoticons_checked.setVisibility(View.INVISIBLE);
			iv_emoticons_normal.setVisibility(View.INVISIBLE);
			ET_Msg.setVisibility(View.INVISIBLE);
			RLExpression.setVisibility(View.GONE);
			RLMore.setVisibility(View.GONE);
			hideKeyboard();
		} else if (v.getId() == R.id.keyboard_btn) {
			voice_btn.setVisibility(View.VISIBLE);
			keyboard_btn.setVisibility(View.INVISIBLE);
			btn_press_to_speak.setVisibility(View.INVISIBLE);
			iv_emoticons_checked.setVisibility(View.INVISIBLE);
			iv_emoticons_normal.setVisibility(View.VISIBLE);
			ET_Msg.setVisibility(View.VISIBLE);
			RLExpression.setVisibility(View.GONE);
			RLMore.setVisibility(View.GONE);
			hideKeyboard();
		} else if (v.getId() == R.id.more_btn) {
			if (RLMore.getVisibility() == View.GONE) {
				RLMore.setVisibility(View.VISIBLE);
				RLExpression.setVisibility(View.GONE);
				if (keyboard_btn.getVisibility() == View.INVISIBLE) {
					iv_emoticons_checked.setVisibility(View.INVISIBLE);
					iv_emoticons_normal.setVisibility(View.VISIBLE);
				} else {
					iv_emoticons_checked.setVisibility(View.INVISIBLE);
					iv_emoticons_normal.setVisibility(View.INVISIBLE);
				}
				hideKeyboard();
			} else {
				RLMore.setVisibility(View.GONE);
				RLExpression.setVisibility(View.GONE);
				if (keyboard_btn.getVisibility() == View.INVISIBLE) {
					iv_emoticons_checked.setVisibility(View.INVISIBLE);
					iv_emoticons_normal.setVisibility(View.VISIBLE);
				} else {
					iv_emoticons_checked.setVisibility(View.INVISIBLE);
					iv_emoticons_normal.setVisibility(View.INVISIBLE);
				}
				hideKeyboard();

			}
		}
		listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);// 显示到最后一行
	}

	/**
	 * 照相获取图片
	 */
	public void selectPicFromCamera() {
		if (!CommonUtils.isExitsSdcard()) {
			String st = getResources().getString(R.string.sd_card_does_not_exist);
			Toast.makeText(getApplicationContext(), st,  Toast.LENGTH_SHORT).show();
			return;
		}

		cameraFile = new File(PathUtil.getInstance().getImagePath(), "user" + ".jpg");
		cameraFile.getParentFile().mkdirs();
		startActivityForResult(
				new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
				REQUEST_CODE_CAMERA);
	}

	/**
	 * 从图库获取图片
	 */
	public void selectPicFromLocal() {
		Intent intent;
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");

		} else {
			intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		startActivityForResult(intent, REQUEST_CODE_LOCAL);
	}

	/**
	 * 根据图库图片uri发送图片
	 * 
	 * @param selectedImage
	 */
	private void sendPicByUri(Uri selectedImage) {
		// String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);
		String st8 = getResources().getString(R.string.cant_find_pictures);
		if (cursor != null) {
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex("_data");
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			cursor = null;
			if (picturePath == null || picturePath.equals("null")) {
				Toast toast = Toast.makeText(this, st8, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}
			sendPicture(picturePath);
		} else {
			File file = new File(selectedImage.getPath());
			if (!file.exists()) {
				Toast toast = Toast.makeText(this, st8, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}
			sendPicture(file.getAbsolutePath());
		}

	}

	/**
	 * 选择文件
	 */
	private void selectFileFromLocal() {
		Intent intent = null;
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("*/*");
			intent.addCategory(Intent.CATEGORY_OPENABLE);

		} else {
			intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
	}

	/**
	 * 发送图片
	 * 
	 * @param filePath
	 */
	private void sendPicture(final String filePath) {
		// create and add image message in view
		final EMMessage message = EMMessage.createSendMessage(EMMessage.Type.IMAGE);
		/*
		 * // 如果是群聊，设置chattype,默认是单聊 if (chatType == CHATTYPE_GROUP){
		 * message.setChatType(ChatType.GroupChat); }else if(chatType ==
		 * CHATTYPE_CHATROOM){ message.setChatType(ChatType.ChatRoom); }
		 */
		message.setChatType(ChatType.GroupChat);
		message.setReceipt(hxUserBean.getGroupBean().getGroupId());
		ImageMessageBody body = new ImageMessageBody(new File(filePath));
		// 默认超过100k的图片会压缩后发给对方，可以设置成发送原图
		// body.setSendOriginalImage(true);
		message.addBody(body);
		conversation.addMessage(message);

		listView.setAdapter(msgAdapter);
		msgAdapter.refreshSelectLast();
		// setResult(RESULT_OK);
		// more(more);
	}

	public List<String> getExpressionRes(int getSum) {
		List<String> reslist = new ArrayList<String>();
		for (int x = 1; x <= getSum; x++) {
			String filename = "ee_" + x;

			reslist.add(filename);

		}
		return reslist;

	}

	/**
	 * 按住说话listener
	 * 
	 */
	class PressToSpeakListen implements View.OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (!CommonUtils.isExitsSdcard()) {
					String st4 = getResources().getString(R.string.Send_voice_need_sdcard_support);
					Toast.makeText(ChatGroupActivity.this, st4, Toast.LENGTH_SHORT).show();
					return false;
				}
				try {
					v.setPressed(true);
					wakeLock.acquire();
					if (VoicePlayClickListener.isPlaying)
						VoicePlayClickListener.currentPlayListener.stopPlayVoice();
					recordingContainer.setVisibility(View.VISIBLE);
					recordingHint.setText(getString(R.string.move_up_to_cancel));
					recordingHint.setBackgroundColor(Color.TRANSPARENT);
					voiceRecorder.startRecording(null, hxUserBean.getGroupBean().getGroupId(), getApplicationContext());
				} catch (Exception e) {
					e.printStackTrace();
					v.setPressed(false);
					if (wakeLock.isHeld())
						wakeLock.release();
					if (voiceRecorder != null)
						voiceRecorder.discardRecording();
					recordingContainer.setVisibility(View.INVISIBLE);
					Toast.makeText(ChatGroupActivity.this, R.string.recoding_fail, Toast.LENGTH_SHORT).show();
					return false;
				}

				return true;
			case MotionEvent.ACTION_MOVE: {
				if (event.getY() < 0) {
					recordingHint.setText(getString(R.string.release_to_cancel));
					recordingHint.setBackgroundResource(R.drawable.recording_text_hint_bg);
				} else {
					recordingHint.setText(getString(R.string.move_up_to_cancel));
					recordingHint.setBackgroundColor(Color.TRANSPARENT);
				}
				return true;
			}
			case MotionEvent.ACTION_UP:
				v.setPressed(false);
				recordingContainer.setVisibility(View.INVISIBLE);
				if (wakeLock.isHeld())
					wakeLock.release();
				if (event.getY() < 0) {
					// discard the recorded audio.
					voiceRecorder.discardRecording();

				} else {
					// stop recording and send voice file
					String st1 = getResources().getString(R.string.Recording_without_permission);
					String st2 = getResources().getString(R.string.The_recording_time_is_too_short);
					String st3 = getResources().getString(R.string.send_failure_please);
					try {
						int length = voiceRecorder.stopRecoding();
						if (length > 0) {
							sendVoice(voiceRecorder.getVoiceFilePath(),
									voiceRecorder.getVoiceFileName(hxUserBean.getGroupBean().getGroupId()),
									Integer.toString(length), false);
						} else if (length == EMError.INVALID_FILE) {
							Toast.makeText(getApplicationContext(), st1, Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(), st2, Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						e.printStackTrace();
						Toast.makeText(ChatGroupActivity.this, st3, Toast.LENGTH_SHORT).show();
					}

				}
				return true;
			default:
				recordingContainer.setVisibility(View.INVISIBLE);
				if (voiceRecorder != null)
					voiceRecorder.discardRecording();
				return false;
			}
		}
	}

	/**
	 * 获取表情的gridview的子view
	 * 
	 * @param i
	 * @return
	 */
	private View getGridChildView(int i) {
		View view = View.inflate(this, R.layout.expression_gridview, null);
		ExpandGridView gv = (ExpandGridView) view.findViewById(R.id.gridview);
		List<String> list = new ArrayList<String>();
		if (i == 1) {
			List<String> list1 = reslist.subList(0, 20);
			list.addAll(list1);
		} else if (i == 2) {
			list.addAll(reslist.subList(20, reslist.size()));
		}
		list.add("delete_expression");
		final ExpressionPackAdapter expressionAdapter = new ExpressionPackAdapter(this, 1, list);
		gv.setAdapter(expressionAdapter);
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String filename = expressionAdapter.getItem(position);
				try {
					// 文字输入框可见时，才可输入表情
					// 按住说话可见，不让输入表情
					// if (buttonSetModeKeyboard.getVisibility() !=
					// View.VISIBLE) {

					if (filename != "delete_expression") { // 不是删除键，显示表情
						// 这里用的反射，所以混淆的时候不要混淆SmileUtils这个类
						Class clz = Class.forName("tts.moudle.api.hxapi.utils.SmileUtils");
						Field field = clz.getField(filename);
						ET_Msg.append(SmileUtils.getSmiledText(ChatGroupActivity.this, (String) field.get(null)));
					} else { // 删除文字或者表情
						if (!TextUtils.isEmpty(ET_Msg.getText())) {

							int selectionStart = ET_Msg.getSelectionStart();// 获取光标的位置
							if (selectionStart > 0) {
								String body = ET_Msg.getText().toString();
								String tempStr = body.substring(0, selectionStart);
								int i = tempStr.lastIndexOf("[");// 获取最后一个表情的位置
								if (i != -1) {
									CharSequence cs = tempStr.substring(i, selectionStart);
									if (SmileUtils.containsKey(cs.toString()))
										ET_Msg.getEditableText().delete(i, selectionStart);
									else
										ET_Msg.getEditableText().delete(selectionStart - 1, selectionStart);
								} else {
									ET_Msg.getEditableText().delete(selectionStart - 1, selectionStart);
								}
							}
						}

					}
					// }
				} catch (Exception e) {
				}

			}
		});
		return view;
	}

	/**
	 * 发送语音
	 * 
	 * @param filePath
	 * @param fileName
	 * @param length
	 * @param isResend
	 */
	private void sendVoice(String filePath, String fileName, String length, boolean isResend) {
		if (!(new File(filePath).exists())) {
			return;
		}
		try {
			final EMMessage message = EMMessage.createSendMessage(EMMessage.Type.VOICE);
			/*
			 * // 如果是群聊，设置chattype,默认是单聊 if (chatType == CHATTYPE_GROUP){
			 * message.setChatType(ChatType.GroupChat); }else if(chatType ==
			 * CHATTYPE_CHATROOM){ message.setChatType(ChatType.ChatRoom); }
			 */
			message.setChatType(ChatType.GroupChat);
			message.setReceipt(hxUserBean.getGroupBean().getGroupId());
			int len = Integer.parseInt(length);
			VoiceMessageBody body = new VoiceMessageBody(new File(filePath), len);
			message.addBody(body);

			conversation.addMessage(message);
			msgAdapter.refreshSelectLast();
			// setResult(RESULT_OK);
			// send file
			// sendVoiceSub(filePath, fileName, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * *
	 */
	private void sendMsg(String Msg) {

		if (Msg.length() > 0) {
			EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);

			TextMessageBody txtBody = new TextMessageBody(Msg);
			// 设置消息body
			message.addBody(txtBody);
			// 设置要发给谁,用户username或者群聊groupid
			message.setChatType(ChatType.GroupChat);
			message.setReceipt(hxUserBean.getGroupBean().getGroupId());
			// 把messgage加到conversation中
			conversation.addMessage(message);
			// 通知adapter有消息变动，adapter会根据加入的这条message显示消息和调用sdk的发送方法
			msgAdapter.refreshSelectLast();
			ET_Msg.setText("");

			// setResult(RESULT_OK);
		}
	}

	/**
	 * 发送位置信息
	 * 
	 * @param latitude
	 * @param longitude
	 * @param imagePath
	 * @param locationAddress
	 */
	private void sendLocationMsg(double latitude, double longitude, String imagePath, String locationAddress) {
		EMMessage message = EMMessage.createSendMessage(EMMessage.Type.LOCATION);

		LocationMessageBody locBody = new LocationMessageBody(locationAddress, latitude, longitude);
		message.addBody(locBody);
		message.setChatType(ChatType.GroupChat);
		message.setReceipt(hxUserBean.getGroupBean().getGroupId());
		conversation.addMessage(message);
		listView.setAdapter(msgAdapter);
		msgAdapter.refreshSelectLast();
		// setResult(RESULT_OK);
	}

	/**
	 * 发送视频消息
	 */
	private void sendVideo(final String filePath, final String thumbPath, final int length) {
		final File videoFile = new File(filePath);
		if (!videoFile.exists()) {
			return;
		}
		try {
			EMMessage message = EMMessage.createSendMessage(EMMessage.Type.VIDEO);
			message.setChatType(ChatType.GroupChat);
			message.setReceipt(hxUserBean.getGroupBean().getGroupId());
			VideoMessageBody body = new VideoMessageBody(videoFile, thumbPath, length, videoFile.length());
			message.addBody(body);
			message.setAttribute("em_robot_message", true);
			conversation.addMessage(message);
			msgAdapter.refreshSelectLast();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 隐藏软键盘
	 */
	private void hideKeyboard() {
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public ListView getListView() {
		return listView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_SELECT_VIDEO) { // 发送本地选择的视频
			if (resultCode == RESULT_OK) {
				int duration = data.getIntExtra("dur", 0);
				String videoPath = data.getStringExtra("path");
				File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
				Bitmap bitmap = null;
				FileOutputStream fos = null;
				try {
					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}
					bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
					if (bitmap == null) {
						EMLog.d("ChatGroupActivity", "problem load video thumbnail bitmap,use default icon");
						bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_panel_video_icon);
					}
					fos = new FileOutputStream(file);

					bitmap.compress(CompressFormat.JPEG, 100, fos);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						fos = null;
					}
					if (bitmap != null) {
						bitmap.recycle();
						bitmap = null;
					}

				}
				sendVideo(videoPath, file.getAbsolutePath(), duration / 1000);
			}

		} else if (requestCode == REQUEST_CODE_LOCAL) { // 发送本地图片
			if (resultCode == RESULT_OK) { // 清空消息
				if (data != null) {
					Uri selectedImage = data.getData();
					if (selectedImage != null) {
						sendPicByUri(selectedImage);
					}
				}
			}
		} else if (requestCode == REQUEST_CODE_SELECT_FILE) { // 发送选择的文件
			if (resultCode == RESULT_OK) { // 清空消息
				if (data != null) {
					Uri uri = data.getData();
					if (uri != null) {
						sendFile(uri);
					}
				}
			}

		} else if (requestCode == REQUEST_CODE_CAMERA) { // 发送照片
			if (resultCode == RESULT_OK) { // 清空消息
				if (cameraFile != null && cameraFile.exists())
					sendPicture(cameraFile.getAbsolutePath());
			}
		} else if (requestCode == REQUEST_CODE_MAP) { // 地图
			if (resultCode == RESULT_OK) { // 清空消息
				double latitude = data.getDoubleExtra("latitude", 0);
				double longitude = data.getDoubleExtra("longitude", 0);
				String locationAddress = data.getStringExtra("address");
				if (locationAddress != null && !locationAddress.equals("")) {
					toggleMore();
					sendLocationMsg(latitude, longitude, "", locationAddress);
				} else {
					String st = getResources().getString(R.string.unable_to_get_loaction);
					Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
				}
				// 重发消息
			}

		} else if (requestCode == video_call) {
			msgAdapter.refreshSelectLast();
		} else if (requestCode == voice_call) {
			msgAdapter.refreshSelectLast();
		}

	}

	/**
	 * 发送文件
	 * 
	 * @param uri
	 */
	private void sendFile(Uri uri) {
		String filePath = null;
		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] projection = { "_data" };
			Cursor cursor = null;

			try {
				cursor = getContentResolver().query(uri, projection, null, null, null);
				int column_index = cursor.getColumnIndexOrThrow("_data");
				if (cursor.moveToFirst()) {
					filePath = cursor.getString(column_index);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			filePath = uri.getPath();
		}
		File file = new File(filePath);
		if (file == null || !file.exists()) {
			String st7 = getResources().getString(R.string.File_does_not_exist);
			Toast.makeText(getApplicationContext(), st7, Toast.LENGTH_SHORT).show();
			return;
		}
		if (file.length() > 10 * 1024 * 1024) {
			String st6 = getResources().getString(R.string.The_file_is_not_greater_than_10_m);
			Toast.makeText(getApplicationContext(), st6, Toast.LENGTH_SHORT).show();
			return;
		}

		// 创建一个文件消息
		EMMessage message = EMMessage.createSendMessage(EMMessage.Type.FILE);
		message.setChatType(ChatType.GroupChat);
		message.setReceipt(hxUserBean.getGroupBean().getGroupId());
		// add message body
		NormalFileMessageBody body = new NormalFileMessageBody(new File(filePath));
		message.addBody(body);
		message.setAttribute("em_robot_message", true);

		conversation.addMessage(message);
		listView.setAdapter(msgAdapter);
		msgAdapter.refreshSelectLast();
		setResult(RESULT_OK);
	}

	/**
	 * 显示或隐藏图标按钮页
	 *
	 */
	public void toggleMore() {
		RLMore.setVisibility(View.GONE);
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		EMChatManager.getInstance().registerEventListener(this,
				new EMNotifierEvent.Event[] { EMNotifierEvent.Event.EventNewMessage,
						EMNotifierEvent.Event.EventOfflineMessage, EMNotifierEvent.Event.EventDeliveryAck,
						EMNotifierEvent.Event.EventReadAck });
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EMChatManager.getInstance().unregisterEventListener(this);
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
			if (username.equals(hxUserBean.getGroupBean().getGroupId())) {
				refreshUIWithNewMessage();
				// 声音和震动提示有新消息
				// HXSDKHelper.getInstance().getNotifier().viberateAndPlayTone(message);
			} else {
				// 如果消息不是和当前聊天ID的消息
				// HXSDKHelper.getInstance().getNotifier().onNewMsg(message);
			}

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
		if (msgAdapter == null) {
			return;
		}

		runOnUiThread(new Runnable() {
			public void run() {
				msgAdapter.refresh();
			}
		});
	}

	private void refreshUIWithNewMessage() {
		if (msgAdapter == null) {
			return;
		}

		runOnUiThread(new Runnable() {
			public void run() {
				soundPool.play((int) sound.get(1), 1, 1, 0, 0, 1);
				msgAdapter.refreshSelectLast();
			}
		});
	}
}
