package com.bzh.sportrecord.ui.adapter;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import com.bzh.sportrecord.data.model.FriendsInfo;
import com.bzh.sportrecord.model.Friend;
import com.bzh.sportrecord.utils.CommonUtil;
import com.bzh.sportrecord.utils.PinyinUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author 毕泽浩
 * @Description: 好友列表适配器
 * @time 2018/9/30 10:47
 */
public class FriendsRecycleViewAdapter extends RecyclerView.Adapter<FriendsRecycleViewAdapter.ViewHolder> {

    private evenClickListener listener; //点击 长按 事件
    private List<Friend> friends;
    private Context context;

    private HashMap<String, Integer> letterIndexes = new HashMap<>();//字母不一样的map集合

    public FriendsRecycleViewAdapter(Context context, List<FriendsInfo> friendsInfos) {
        this.context = context;
        setFriends(friendsInfos);
    }

    public void setFriends(List<FriendsInfo> friendsInfos) {
        List<Friend> friends = new ArrayList<>();
        if (friendsInfos != null) {
            for (int i = 0; i < friendsInfos.size(); i++) {
                FriendsInfo friendsInfo = friendsInfos.get(i);
                friends.add(new Friend(friendsInfo.getUsername(),
                        friendsInfo.getRemarkname(),
                        friendsInfo.getHeadportrait(),
                        PinyinUtils.getPinYin(friendsInfo.getRemarkname())));
            }
            sort(friends);
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
        this.friends = friends;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Friend friend = friends.get(i);
        viewHolder.friendName.setText(friend.getRemarks());
        String pic = friend.getImage();
        if (pic != null) {
            //解码
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytes = decoder.decode(friend.getImage());
            //Bitmap bitMap = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes));
            Bitmap bitMap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            //viewHolder.imageView.setImageBitmap(bitMap);
            //BitmapDrawable bd= new BitmapDrawable(context.getResources(), bitMap);
            Glide.with(context).load(bitMap).into(viewHolder.imageView);
        } else {
            Glide.with(context).load(R.drawable.user_icon).into(viewHolder.imageView);
        }
        String currentLetter = PinyinUtils.getFirstLetter(friend.getPinyin());
        String previousLetter = i >= 1 ? PinyinUtils.getFirstLetter(friends.get(i - 1).getPinyin()) : "";
        if (!TextUtils.equals(currentLetter, previousLetter)) {
            viewHolder.Initials.setVisibility(View.VISIBLE);
            viewHolder.Initials.setText(currentLetter);
        } else {
            viewHolder.Initials.setVisibility(View.GONE);
        }

        if (listener != null) {
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.setOnClickListener(v,i, friend);
                }
            });
            viewHolder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.setOnLongClickListener(v,i, friend);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (friends != null) {
            return friends.size();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Initials; //首字母
        private CircleImageView imageView; //头像
        private TextView friendName; //用户姓名
        private LinearLayout linearLayout; //头像名称的布局

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Initials = itemView.findViewById(R.id.friend_initials);
            imageView = itemView.findViewById(R.id.friend_icon);
            friendName = itemView.findViewById(R.id.friend_name);
            linearLayout = itemView.findViewById(R.id.friend_layout);
        }
    }

    //设置事件
    public void setListener(evenClickListener listener) {
        this.listener = listener;
    }

    //事件接口
    public interface evenClickListener {

        void setOnClickListener(View view,int position, Friend friend);

        void setOnLongClickListener(View view,int position, Friend friend);
    }

    //排序
    public void sort(List<Friend> friends) {
        List<Friend> f1 = new ArrayList<>(); //字母集合
        List<Friend> f2 = new ArrayList<>(); //数组集合
        for (Friend friend : friends) {
            char py = friend.getPinyin().charAt(0);
            if ((py >= 'a' && py <= 'z') || (py >= 'A' && py <= 'Z')) {
                f1.add(friend);
            } else {
                f2.add(friend);
            }
        }
        Collections.sort(f1);
        Collections.sort(f2);
        friends.clear();
        friends.addAll(f1);
        friends.addAll(f2);
    }

}
