package com.shrutiswati.banasthalibot.ui;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shrutiswati.banasthalibot.R;
import com.shrutiswati.banasthalibot.helpers.BanasthaliBotUtils;
import com.shrutiswati.banasthalibot.models.ChatMessage;

import java.util.ArrayList;

import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private RecyclerView mRVChat;
    private EditText mETComposer;
    private ImageView mIVSend;

    private AIConfiguration config;
    private AIRequest aiRequest;
    private AIDataService aiDataService;

    public ArrayList<ChatMessage> mChatList;
    ChatAdapter mChatAdapter;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVars();
        initApiAi();
        setListeners();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRVChat.setHasFixedSize(true);
        mRVChat.setAdapter(mChatAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(false);
        mRVChat.setLayoutManager(linearLayoutManager);
    }

    private void setListeners() {
        mETComposer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(mETComposer.getText().length() > 0){
                    mIVSend.setImageResource(R.drawable.ic_send_white_24dp);
                } else {
                    mIVSend.setImageResource(R.drawable.ic_mic_white_24dp);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mIVSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mETComposer.getText().length() > 0){
                    insertMessage(mETComposer.getText().toString(), BanasthaliBotUtils.CHAT_USER);
                    new APIAIAsyncTask(mETComposer.getText().toString().trim()).execute();
                } else {
                    //voice stuff here
                }
                mETComposer.setText("");
            }
        });
    }

    private void initVars() {
        mRVChat = (RecyclerView)getView().findViewById(R.id.rv_chat);
        mETComposer = (EditText)getView().findViewById(R.id.et_chat_composer);
        mIVSend = (ImageView)getView().findViewById(R.id.iv_send_button);

        mChatList = new ArrayList<>();
        mChatAdapter = new ChatAdapter();
    }

    class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.VHChatAdapter>{
        @Override
        public VHChatAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
            return new VHChatAdapter(view);
        }

        @Override
        public void onBindViewHolder(VHChatAdapter holder, int position) {
            if(mChatList.get(position).getSentBy().equals(BanasthaliBotUtils.CHAT_USER)){
                holder.leftText.setVisibility(View.GONE);
                holder.rightText.setVisibility(View.VISIBLE);
                holder.rightText.setText(mChatList.get(position).getMessage());
            } else {
                holder.leftText.setVisibility(View.VISIBLE);
                holder.rightText.setVisibility(View.GONE);
                holder.leftText.setText(mChatList.get(position).getMessage());
            }
        }

        @Override
        public int getItemCount() {
            return mChatList.size();
        }

        class VHChatAdapter extends RecyclerView.ViewHolder{
            TextView leftText,rightText;
            public VHChatAdapter(View itemView) {
                super(itemView);
                leftText = (TextView)itemView.findViewById(R.id.leftText);
                rightText = (TextView)itemView.findViewById(R.id.rightText);
            }
        }
    }

    private class APIAIAsyncTask extends AsyncTask<AIRequest, Void, AIResponse> {

        String query = "";

        APIAIAsyncTask(String query) {
            this.query = query;
        }


        @Override
        protected AIResponse doInBackground(AIRequest... aiRequests) {
            try {
                if(!TextUtils.isEmpty(query)) {
                    aiRequest.setQuery(query);
                }
                return aiDataService.request(aiRequest);
            } catch (AIServiceException e) {
                Log.e("ASK_AI", e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(AIResponse response) {
            if (response != null) {
                Result result = response.getResult();
                String reply = result.getFulfillment().getSpeech();
                insertMessage(reply, BanasthaliBotUtils.CHAT_BOT);
            }
        }
    }

    private void initApiAi() {
        config = new AIConfiguration(BanasthaliBotUtils.DIALOG_FLOW_KEY,
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiDataService = new AIDataService(getActivity(), config);
        aiRequest = new AIRequest();
    }

    private void insertMessage(String message, String sentBy){
        long millis = System.currentTimeMillis();
        ChatMessage msg = new ChatMessage(message, millis, sentBy);
        mChatList.add(msg);
        mChatAdapter.notifyItemInserted(mChatList.size() - 1);
        mRVChat.scrollToPosition(mChatList.size() - 1);
    }
}
