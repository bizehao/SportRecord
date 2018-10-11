package com.bzh.sportrecord.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
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
import com.bzh.sportrecord.model.ApiUserInfos;
import com.bzh.sportrecord.model.Friend;

import java.util.Base64;
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
    private List<ApiUserInfos.DataBean> friends;
    private Context context;

    public AddFriendsRecycleViewAdapter(Context context, List<ApiUserInfos.DataBean> friends) {
        this.friends = friends;
        this.context = context;
        setFriends(friends);
    }

    public void setFriends(List<ApiUserInfos.DataBean> friends) {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull AddFriendsRecycleViewAdapter.ViewHolder viewHolder, int i) {
        System.out.println("执行一次");
        ApiUserInfos.DataBean friend = friends.get(i);
        if (friend.isExit()) {
            System.out.println("进行判断");
            viewHolder.friendAdd.setText("已添加");
            viewHolder.friendAdd.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
            viewHolder.friendAdd.setEnabled(false);
        } else {
            viewHolder.friendAdd.setText("添加");
            viewHolder.friendAdd.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
            viewHolder.friendAdd.setEnabled(true);
            if (listener != null) {
                viewHolder.friendAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.setOnClickListener(friend, i);
                    }
                });
            }
        }
        viewHolder.name.setText(friend.getUsername());
        if(friend.getHeadportrait() != null){
            //解码
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytes = decoder.decode(friend.getHeadportrait());
            Bitmap bitMap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            Glide.with(context).load(bitMap).into(viewHolder.imageView);
        }else {
            Glide.with(context).load(R.drawable.user_icon).into(viewHolder.imageView);
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

        private CircleImageView imageView; //头像
        private TextView name; //用户name
        private AppCompatButton friendAdd; //添加按钮

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.addfriends_icon);
            name = itemView.findViewById(R.id.addfriends_name);
            friendAdd = itemView.findViewById(R.id.addfriends_button);
        }
    }

    //事件接口
    public interface evenClickListener {

        void setOnClickListener(ApiUserInfos.DataBean dataBean, int position);
    }

    public void onRefreshView(int position) {
        friends.get(position).setExit(true);
        notifyItemChanged(position);
    }
}
