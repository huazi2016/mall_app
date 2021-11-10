package com.mall.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mall.demo.R;
import com.mall.demo.base.activity.BaseActivity;
import com.mall.demo.bean.MsgListBo;
import com.mall.demo.chat.ChatKeyboardLayout;
import com.mall.demo.chat.HadEditText;
import com.mall.demo.net.DataManager;
import com.mall.demo.net.MainPresenter;
import com.mall.demo.net.NetCallBack;
import com.mall.demo.utils.LogUtils;
import com.mall.demo.utils.MyConstant;
import com.tencent.mmkv.MMKV;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChatActivity extends BaseActivity {

    @BindView(R.id.ivCommonBack)
    AppCompatImageView ivCommonBack;
    @BindView(R.id.tvCommonTitle)
    AppCompatTextView tvCommonTitle;
    @BindView(R.id.rcChatMsg)
    RecyclerView rcChatMsg;
    @BindView(R.id.chat_keyboaed_layout)
    ChatKeyboardLayout chatkeyLayout;
    @BindView(R.id.chat_rootview)
    RelativeLayout chatRootview;

    private MainPresenter msgPresenter;
    private final static String CHAT_ID = "chat_id";
    private final static String RECEIVER = "receiver";
    private final static String SENDER = "sender";
    private MsgListAdapter msgAdapter;
    private final List<MsgListBo> dataList = new ArrayList();
    private String userName = "";
    private String receiver = "";
    private String sender = "";
    private String msgId = "";
    private boolean isSeller;
    private String role;
    private String account;

    public static void launchActivity(Activity activity, String id, String sender, String receiver) {
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.putExtra(CHAT_ID, id);
        intent.putExtra(SENDER, sender);
        intent.putExtra(RECEIVER, receiver);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initRecycleView();
        account = MMKV.defaultMMKV().decodeString(MyConstant.ACCOUNT);
        role = MMKV.defaultMMKV().decodeString(MyConstant.ROLE);
        isSeller = MMKV.defaultMMKV().decodeString(MyConstant.ROLE).equalsIgnoreCase("卖家");
        ivCommonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        chatkeyLayout.getInputEditText().setHint("请输入内容");
        setKeyBoardListener();
        if (getIntent() != null) {
            msgId = getIntent().getStringExtra(CHAT_ID);
            sender = getIntent().getStringExtra(SENDER);
            receiver = getIntent().getStringExtra(RECEIVER);
            tvCommonTitle.setText(sender);
            getMsgList(sender);
        }
    }

    private void setKeyBoardListener() {
        chatkeyLayout.setOnChatKeyBoardListener(new ChatKeyboardLayout.OnChatKeyBoardListener() {
            @Override
            public void onSendButtonClicked(ImageView sendBtn, HadEditText editText, ProgressBar progressBar, String text) {
                //点击发送回调
                if (TextUtils.isEmpty(text)) {
                    ToastUtils.showShort("输入内容不能为空");
                } else {
                    editText.setText("");
                    sendMessage(msgId, sender, text);
                }
            }

            @Override
            public void onInputTextChanged(String text) {

            }

            @Override
            public void onKeyboardHeightChanged(int height) {
                //键盘弹起回调
                rcChatMsg.scrollToPosition(dataList.size() - 1);
            }

            @Override
            public boolean onLeftIconClicked(View view) {
                return false;
            }

            @Override
            public boolean onRightIconClicked(View view) {
                return false;
            }
        });
    }

    @Override
    protected void initPresenter() {
        msgPresenter = new MainPresenter(new DataManager());
    }

    private void initRecycleView() {
        rcChatMsg.setLayoutManager(new LinearLayoutManager(context));
        msgAdapter = new MsgListAdapter(R.layout.item_chat_msg, dataList);
        rcChatMsg.setAdapter(msgAdapter);
    }

    private void getMsgList(String receiver) {
        if (msgPresenter == null) {
            msgPresenter = new MainPresenter(new DataManager());
        }
        msgPresenter.postMessageHistory(receiver, new NetCallBack<List<MsgListBo>>() {
            @Override
            public void onLoadSuccess(List<MsgListBo> resultList) {
                dataList.clear();
                dataList.addAll(resultList);
                msgAdapter.notifyDataSetChanged();
                rcChatMsg.scrollToPosition(dataList.size() - 1);
            }

            @Override
            public void onLoadFailed(String errMsg) {
                LogUtils.e("getMsgList_err==" + errMsg);
            }
        });
    }

    private class MsgListAdapter extends BaseQuickAdapter<MsgListBo, BaseViewHolder> {

        public MsgListAdapter(int layoutResId, @Nullable List<MsgListBo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, MsgListBo msgBo) {
            LinearLayout llLeftMsg = holder.getView(R.id.llLeftMsg);
            LinearLayout llRightMsg = holder.getView(R.id.llRightMsg);
            AppCompatTextView tvLeftContent = holder.getView(R.id.tvLeftContent);
            AppCompatTextView tvRightContent = holder.getView(R.id.tvRightContent);
            if (account.equalsIgnoreCase(receiver)) {
                llLeftMsg.setVisibility(View.GONE);
                llRightMsg.setVisibility(View.VISIBLE);
                String sendText = msgBo.content;
                tvRightContent.setText(sendText);
            } else {
                llLeftMsg.setVisibility(View.VISIBLE);
                llRightMsg.setVisibility(View.GONE);
                String receiveText = msgBo.content;
                tvLeftContent.setText(receiveText);
            }
        }
    }

    private void sendMessage(String msgId, String receiver, String content) {
        msgPresenter.postSendMessage(msgId, receiver, content, new NetCallBack<MsgListBo>() {
            @Override
            public void onLoadSuccess(MsgListBo msgBo) {
                dataList.add(msgBo);
                msgAdapter.notifyDataSetChanged();
                rcChatMsg.scrollToPosition(dataList.size() - 1);
            }

            @Override
            public void onLoadFailed(String errMsg) {
                LogUtils.e("getMsgList_err==" + errMsg);
            }
        });
    }
}
