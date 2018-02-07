package com.shrutiswati.banasthalibot.ui;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shrutiswati.banasthalibot.R;
import com.shrutiswati.banasthalibot.db.BanasthaliBotRealmController;
import com.shrutiswati.banasthalibot.db.tables.ChatTable;
import com.shrutiswati.banasthalibot.helpers.BanasthaliBotPreferences;
import com.shrutiswati.banasthalibot.helpers.BanasthaliBotUtils;
import com.shrutiswati.banasthalibot.models.ChatMessage;

import java.util.ArrayList;
import java.util.List;

import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import io.realm.RealmCollection;

import static android.app.Activity.RESULT_OK;


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

    public List<ChatMessage> mChatList;
    ChatAdapter mChatAdapter;

    private String userID;

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
                if (mETComposer.getText().length() > 0) {
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
                if (mETComposer.getText().length() > 0) {
                    insertMessage(mETComposer.getText().toString(), BanasthaliBotUtils.CHAT_USER);
                    new APIAIAsyncTask(mETComposer.getText().toString().trim()).execute();
                } else {
                    promptSpeechInput();
                }
                mETComposer.setText("");
            }
        });
    }

    private void initVars() {
        mRVChat = (RecyclerView) getView().findViewById(R.id.rv_chat);
        mETComposer = (EditText) getView().findViewById(R.id.et_chat_composer);
        mIVSend = (ImageView) getView().findViewById(R.id.iv_send_button);

        userID = BanasthaliBotPreferences.getInstance(getContext()).getStringPreferences("username");

        mChatList = BanasthaliBotRealmController.getInstance().getAllChatMessages(userID);
        mChatAdapter = new ChatAdapter();

    }

    class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.VHChatAdapter> {
        @Override
        public VHChatAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
            return new VHChatAdapter(view);
        }

        @Override
        public void onBindViewHolder(VHChatAdapter holder, int position) {
            if (mChatList.get(position).getSentBy().equals(BanasthaliBotUtils.CHAT_USER)) {
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

        class VHChatAdapter extends RecyclerView.ViewHolder {
            TextView leftText, rightText;

            public VHChatAdapter(View itemView) {
                super(itemView);
                leftText = (TextView) itemView.findViewById(R.id.leftText);
                rightText = (TextView) itemView.findViewById(R.id.rightText);
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
                if (!TextUtils.isEmpty(query)) {
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

    private void insertMessage(String message, String sentBy) {
        long millis = System.currentTimeMillis();
        ChatMessage msg = new ChatMessage(message, millis, sentBy);
        mChatList.add(msg);
        mChatAdapter.notifyItemInserted(mChatList.size() - 1);
        mRVChat.scrollToPosition(mChatList.size() - 1);
        BanasthaliBotRealmController.getInstance().insertChatMessageToDB(msg, userID);
    }

    //region speech stuff

    /**
     * Showing google speech input dialog
     */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 100);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 100);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 100);
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ask a Question");
        try {
            startActivityForResult(intent, BanasthaliBotUtils.REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Log.e("ASK_AI", a.getMessage());
            Toast.makeText(getActivity(),
                    "We're sorry but speech input is not supported on your device",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case BanasthaliBotUtils.REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String res = "";
                    try {
                        res = result.get(0).substring(0, 1).toUpperCase() + result.get(0).substring(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mETComposer.setText(res);
                    mETComposer.setSelection(mETComposer.getText().length());
                }
                break;
            }
        }
    }

    //endregion
   /* public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public boolean onCreateOptionMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.About_us:
                Intent intent = new Intent(getContext(), AboutUs.class);
                startActivity(intent);
                break;
            case  R.id.Logout:
                AlertDialog.Builder a_builder=new AlertDialog.Builder(getActivity().getApplicationContext());
                a_builder.setMessage("Are you sure you want to logout?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                })
                 .setNegativeButton("No", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         dialogInterface.cancel();

                     }
                 });
                AlertDialog alert =a_builder.create();
                alert.setTitle("Alert!");
                alert.show();
                    break;


        }
        return true;
    }*/

}
