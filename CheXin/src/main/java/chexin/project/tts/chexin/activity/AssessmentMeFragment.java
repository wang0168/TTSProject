package chexin.project.tts.chexin.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import chexin.project.tts.chexin.BaseFragment;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.adapter.AssessmentListAdapter;
import chexin.project.tts.chexin.bean.AssessmentBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

/**
 * 对我评价
 * Created by sjb on 2016/3/28.
 */
public class AssessmentMeFragment extends BaseFragment {
    private RecyclerViewAutoRefreshUpgraded assessmentList;
    private AssessmentListAdapter assessmentListAdapter;
    private List<AssessmentBean> mData;
    private int currentPage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.assessment_fragment, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findAllView();
        startRequestData(getData);
    }

    private void findAllView() {
        assessmentList = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.assessmentList);
    }


    private void adapterAssessment() {
        assessmentList.setLayoutManager(new LinearLayoutManager(getActivity()));
        assessmentListAdapter = new AssessmentListAdapter(getActivity(), mData);
        assessmentList.setAdapter(assessmentListAdapter);
        assessmentList.setHeadVisible(true);
        assessmentList.setLoadMore(true);
        assessmentList.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
            @Override
            public void onLoadMore() {
                currentPage++;
                startRequestData(loadMore);
            }

            @Override
            public void onRefreshData() {
                startRequestData(getData);
            }
        });
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        if (index == getData) {
            currentPage = 1;
            params = new ArrayMap<>();
            params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
            params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
            params.put("type", "2");
            params.put("page", "1");
            getDataWithPost(getData, Host.hostUrl + " goods.api?getAssessments", params);
        }
        if (index == loadMore) {
            params = new ArrayMap<>();
            params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
            params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
            params.put("type", "2");
            params.put("page", currentPage + "");
            getDataWithPost(getData, Host.hostUrl + " goods.api?getAssessments", params);
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (index == getData) {
            mData = new Gson().fromJson(response, new TypeToken<List<AssessmentBean>>() {
            }.getType());
            if (mData.size() == 0) {
                CustomUtils.showTipShort(getActivity(), "暂无数据");
            }
            adapterAssessment();
        }
        if (index == loadMore) {
            List<AssessmentBean> temp = new Gson().fromJson(response, new TypeToken<List<AssessmentBean>>() {
            }.getType());
            if (temp.size() == 0) {
                CustomUtils.showTipShort(getActivity(), "暂无更多数据");
                return;
            }
            mData.addAll(temp);
            assessmentList.notifyDataSetChanged();
        }
        assessmentList.setOnRefreshFinished(true);
    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        assessmentList.setOnRefreshFinished(true);
    }
}
