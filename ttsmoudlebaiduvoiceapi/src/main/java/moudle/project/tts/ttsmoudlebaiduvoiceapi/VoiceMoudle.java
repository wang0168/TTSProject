package moudle.project.tts.ttsmoudlebaiduvoiceapi;

import android.content.Context;
import android.os.Environment;
import android.os.Message;
import android.widget.Toast;

import com.baidu.tts.answer.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizeBag;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjb on 2016/2/18.
 */
public class VoiceMoudle {
    private SpeechSynthesizer mSpeechSynthesizer;
    private String mSampleDirPath;
    private static final String SAMPLE_DIR_NAME = "baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license";
    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";

    private static class Moudle {
        protected final static VoiceMoudle mInstance = new VoiceMoudle();
    }

    public static VoiceMoudle getInstance() {
        return Moudle.mInstance;
    }

    private String appId = "";
    private String appKey = "";
    private String appSercent = "";

    public void initBaiduVoiceSDK(Context context, String appId, String appKey, String appSercent) {
        this.appId = appId;
        this.appKey = appKey;
        this.appSercent = appSercent;
        initialEnv(context);
        initialTts(context);
    }

    public void speak(String msg) {
        int result = this.mSpeechSynthesizer.speak(msg);
        if (result < 0) {
            //toPrint("error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        this.mSpeechSynthesizer.pause();
    }

    /**
     * 重启
     */
    public void resume() {
        this.mSpeechSynthesizer.resume();
    }

    /**
     * 停止
     */
    public void stop() {
        this.mSpeechSynthesizer.stop();
    }
    /**
     * 释放
     */
    public void release() {
        this.mSpeechSynthesizer.release();
    }
    /**
     * 只合成 不播放
     * @param msg
     */
    public void synthesize(String msg) {
        int result = this.mSpeechSynthesizer.synthesize(msg);
        if (result < 0) {
            //toPrint("error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    /**
     * 批量播放
     * @param bags   通过getSpeechSynthesizeBag获得SpeechSynthesizeBag
     */
    public void batchSpeak(List<SpeechSynthesizeBag> bags) {
        /*List<SpeechSynthesizeBag> bags = new ArrayList<SpeechSynthesizeBag>();
        bags.add(getSpeechSynthesizeBag("123456", "0"));
        bags.add(getSpeechSynthesizeBag("你好", "1"));
        bags.add(getSpeechSynthesizeBag("使用百度语音合成SDK", "2"));
        bags.add(getSpeechSynthesizeBag("hello", "3"));
        bags.add(getSpeechSynthesizeBag("这是一个demo工程", "4"));*/
        int result = this.mSpeechSynthesizer.batchSpeak(bags);
        if (result < 0) {
            //toPrint("error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    public SpeechSynthesizeBag getSpeechSynthesizeBag(String text, String utteranceId) {
        SpeechSynthesizeBag speechSynthesizeBag = new SpeechSynthesizeBag();
        speechSynthesizeBag.setText(text);
        speechSynthesizeBag.setUtteranceId(utteranceId);
        return speechSynthesizeBag;
    }
    public SpeechSynthesizeBag getSpeechSynthesizeBag(String text) {
        SpeechSynthesizeBag speechSynthesizeBag = new SpeechSynthesizeBag();
        speechSynthesizeBag.setText(text);
        return speechSynthesizeBag;
    }

    private void initialEnv(Context context) {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        }
        makeDir(mSampleDirPath);
        copyFromAssetsToSdcard(context, false, SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(context, false, SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(context, false, TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME);
        copyFromAssetsToSdcard(context, false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);
        copyFromAssetsToSdcard(context, false, "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(context, false, "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(context, false, "english/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_TEXT_MODEL_NAME);
    }

    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 将sample工程需要的资源文件拷贝到SD卡中使用（授权文件为临时授权文件，请注册正式授权）
     *
     * @param isCover 是否覆盖已存在的目标文件
     * @param source
     * @param dest
     */
    private void copyFromAssetsToSdcard(Context context, boolean isCover, String source, String dest) {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = context.getResources().getAssets().open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void initialTts(Context context) {
        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        this.mSpeechSynthesizer.setContext(context);
        this.mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
            /*
          * @param arg0
          */
            @Override
            public void onSynthesizeStart(String utteranceId) {
                // toPrint("onSynthesizeStart utteranceId=" + utteranceId);
            }

            /*
             * @param arg0
             *
             * @param arg1
             *
             * @param arg2
             */
            @Override
            public void onSynthesizeDataArrived(String utteranceId, byte[] data, int progress) {
                // toPrint("onSynthesizeDataArrived");
            }

            /*
             * @param arg0
             */
            @Override
            public void onSynthesizeFinish(String utteranceId) {
                //toPrint("onSynthesizeFinish utteranceId=" + utteranceId);
            }

            /*
             * @param arg0
             */
            @Override
            public void onSpeechStart(String utteranceId) {
                //toPrint("onSpeechStart utteranceId=" + utteranceId);
            }

            /*
             * @param arg0
             *
             * @param arg1
             */
            @Override
            public void onSpeechProgressChanged(String utteranceId, int progress) {
                // toPrint("onSpeechProgressChanged");
            }

            /*
             * @param arg0
             */
            @Override
            public void onSpeechFinish(String utteranceId) {
                //toPrint("onSpeechFinish utteranceId=" + utteranceId);
            }

            /*
             * @param arg0
             *
             * @param arg1
             */
            @Override
            public void onError(String utteranceId, SpeechError error) {
                //toPrint("onError error=" + "(" + error.code + ")" + error.description + "--utteranceId=" + utteranceId);
            }
        });
        // 文本模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
                + TEXT_MODEL_NAME);
        // 声学模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
    /*    // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/"
                + LICENSE_FILE_NAME);*/
        // 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
        this.mSpeechSynthesizer.setAppId(appId);
        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
        this.mSpeechSynthesizer.setApiKey(appKey, appSercent);
        // 设置在线发音人参数（还可设置其他参数）
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, SpeechSynthesizer.SPEAKER_FEMALE);
        // 设置Mix模式的合成策略
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 授权检测接口(可以不使用，只是验证授权是否成功)
        AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);
        if (authInfo.isSuccess()) {
            //toPrint("auth success");
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            //toPrint("auth failed errorMsg=" + errorMsg);
        }
        // 初始化tts
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        // 加载离线英文资源（提供离线英文合成功能）
        int result =
                mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath
                        + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        //toPrint("loadEnglishModel result=" + result);
    }


}
