package com.prostage.dental_manage.chat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prostage.dental_manage.R;
import com.prostage.dental_manage.core.model.UserModel;
import com.prostage.dental_manage.utils.OnSingleClickListener;
import com.prostage.dental_manage.utils.Utils;

import java.util.ArrayList;

import static com.prostage.dental_manage.utils.AppConstants.ARG_CHAT_ROOMS;
import static com.prostage.dental_manage.utils.Utils.PREF_ADMIN_ID;

class ChatAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<UserModel> userModels = new ArrayList<>();
    private OnChatAdapterListener listener;

    ChatAdapter(Context context, ArrayList<UserModel> models) {
        this.context = context;
        this.userModels = models;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        vh = new ChatHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserModel userModel = userModels.get(position);
        int dentistId = Utils.getInt(context, PREF_ADMIN_ID);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child(ARG_CHAT_ROOMS)
                .child(dentistId + "_" + userModel.getUsername());
        if (holder instanceof ChatHolder) {
            ChatHolder chatHolder = (ChatHolder) holder;
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        int unread = 0;
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            boolean read = (boolean) child.child("read").getValue();
                            String senderId = (String) child.child("senderId").getValue();
                            if (userModel.getUsername().equals(senderId) && !read) {
                                unread++;
                            }
                        }
                        if (unread > 0) {
                            ((GradientDrawable) chatHolder.tvNumberChat.getBackground())
                                    .setColor(ContextCompat.getColor(context, R.color.color_background_number_chat));
                            ((GradientDrawable) chatHolder.tvNumberChat.getBackground()).
                                    setStroke((int) Utils.dip2px(context, (float) 0.8),
                                            ContextCompat.getColor(context, R.color.color_background_number_chat));
                            chatHolder.tvNumberChat.setVisibility(View.VISIBLE);
                            chatHolder.tvNumberChat.setText(String.valueOf(unread));
                        } else {
                            chatHolder.tvNumberChat.setVisibility(View.INVISIBLE);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            chatHolder.tvId.setText(String.valueOf(userModel.getUsername()));
            chatHolder.tvName.setText(userModel.getFirstName() + " " + userModel.getLastName());


            if (position % 2 == 0) {
                chatHolder.userLayout.setBackgroundColor(
                        Utils.getColor(context, R.color.colorLightYellow));
            } else {
                chatHolder.userLayout.setBackgroundColor(
                        Utils.getColor(context, R.color.colorLightOrange));
            }
            chatHolder.userLayout.setTag(userModel);
            chatHolder.userLayout.setOnClickListener(mySingleListener);
        }
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    void setList(ArrayList<UserModel> userModels) {
        this.userModels.clear();
        this.userModels.addAll(userModels);
        notifyDataSetChanged();
    }

    private static class ChatHolder extends RecyclerView.ViewHolder {
        final TextView tvId;
        final TextView tvName;
        final TextView tvNumberChat;
        final RelativeLayout userLayout;

        ChatHolder(View view) {
            super(view);
            tvId = view.findViewById(R.id.tv_user_id);
            tvName = view.findViewById(R.id.tv_user_name);
            tvNumberChat = view.findViewById(R.id.tv_number_chat);
            userLayout = view.findViewById(R.id.chat_layout);
        }
    }

    interface OnChatAdapterListener {
        void onShowMessage(UserModel userModel);
    }

    public void setListener(OnChatAdapterListener listener) {
        this.listener = listener;
    }

    private OnSingleClickListener mySingleListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            UserModel userModel = (UserModel) v.getTag();
            if (listener != null && userModel != null) {
                listener.onShowMessage(userModel);
            }
        }
    };
}
