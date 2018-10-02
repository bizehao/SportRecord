package com.bzh.sportrecord.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.model.Friend;
import com.bzh.sportrecord.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/30 10:47
 */
public class FriendsRecycleViewAdapter extends RecyclerView.Adapter<FriendsRecycleViewAdapter.ViewHolder> {

    private evenClickListener listener;
    private List<Friend> friends;
    private Context context;

    private HashMap<String, Integer> letterIndexes = new HashMap<>();//字母不一样的map集合

    public FriendsRecycleViewAdapter(List<Friend> friends, Context context) {
        this.friends = friends;
        this.context = context;

        for (int i = 0; i < friends.size(); i++) {
            //当前城市拼音首字母
            String currentLetter = PinyinUtils.getFirstLetter(friends.get(i).getPinyin());
            //上个首字母，如果不存在设为""
            String previousLetter = i >= 1 ? PinyinUtils.getFirstLetter(friends.get(i - 1).getPinyin()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)) {
                letterIndexes.put(currentLetter, i);
            }
        }
    }


    /**
     * 获取字母索引的位置
     *
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter) {
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friend, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Friend friend = friends.get(i);
        viewHolder.friendName.setText(friend.getName());
        Glide.with(context).load(R.drawable.user_icon).into(viewHolder.imageView);

        String currentLetter = PinyinUtils.getFirstLetter(friend.getPinyin());
        String previousLetter = i >= 1 ? PinyinUtils.getFirstLetter(friends.get(i - 1).getPinyin()) : "";
        if (!TextUtils.equals(currentLetter, previousLetter)) {
            viewHolder.Initials.setVisibility(View.VISIBLE);
            viewHolder.Initials.setText(currentLetter);
        } else {
            viewHolder.Initials.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Initials; //首字母
        private ImageView imageView; //头像
        private TextView friendName; //用户姓名

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Initials = itemView.findViewById(R.id.friend_initials);
            imageView = itemView.findViewById(R.id.friend_icon);
            friendName = itemView.findViewById(R.id.friend_name);
            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.setOnClickListener(v);
                    }
                });
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        listener.setOnLongClickListener(v);
                        return true;
                    }
                });
            }
        }
    }

    //设置事件
    public void setListener(evenClickListener listener) {
        this.listener = listener;
    }

    //事件接口
    public interface evenClickListener {

        void setOnClickListener(View view);

        void setOnLongClickListener(View view);
    }

}
