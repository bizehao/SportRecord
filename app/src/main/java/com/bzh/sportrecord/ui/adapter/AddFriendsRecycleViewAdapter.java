package com.bzh.sportrecord.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.model.Friend;

import java.util.List;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/7 10:57
 */
public class AddFriendsRecycleViewAdapter extends RecyclerView.Adapter<AddFriendsRecycleViewAdapter.ViewHolder> {

    private evenClickListener listener; //点击 长按 事件
    private List<Friend> friends;
    private Context context;

    public AddFriendsRecycleViewAdapter(Context context, List<Friend> friends) {
        this.friends = friends;
        this.context = context;
        setFriends(friends);
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public void setListener(evenClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddFriendsRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_addfriends, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddFriendsRecycleViewAdapter.ViewHolder viewHolder, int i) {
        Friend friend = friends.get(i);
        viewHolder.name.setText(friend.getName());
        Glide.with(context).load(R.drawable.user_icon).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        if(friends != null){
            return friends.size();

        }else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imageView; //头像
        private TextView name; //用户name
        private AppCompatButton friendAdd; //用户姓名

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.addfriends_icon);
            name = itemView.findViewById(R.id.addfriends_name);
            friendAdd = itemView.findViewById(R.id.addfriends_button);
            if (listener != null) {
                friendAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.setOnClickListener(v);
                    }
                });
            }
        }
    }

    //事件接口
    public interface evenClickListener {

        void setOnClickListener(View view);
    }
}
