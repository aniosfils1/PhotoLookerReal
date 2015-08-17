package com.example.myfirst.photolooker;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
/**
 * Created by Myfirst on 8/17/2015.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DecimalFormat df = new DecimalFormat("#,##0");

        // get data from position
        InstagramPhoto photo = getItem(position);

        // lookup recycle
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        // load userName
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUsername);
        tvUserName.setText(photo.username);

        // load location icon
        ImageView ivLocation = (ImageView) convertView.findViewById(R.id.ivLocation);
        ivLocation.setVisibility(photo.location.length() == 0 ? View.GONE : View.VISIBLE);

        // load location text
        TextView tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);
        tvLocation.setText(photo.location);
        tvLocation.setVisibility(photo.location.length() == 0 ? View.GONE : View.VISIBLE);

        // load created time
        TextView tvCreatedTime = (TextView) convertView.findViewById(R.id.tvCreatedTime);
        PrettyTime pt = new PrettyTime();
        tvCreatedTime.setText(String.format("\uD83D\uDD51 %s", pt.format(new Date(photo.createdTime))));

        // load tvCommentCount
        TextView tvCommentCount = (TextView) convertView.findViewById(R.id.tvCommentCount);
        tvCommentCount.setText(String.format("? %s comments", df.format(photo.commentsCount)));

        // load caption
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        if (photo.caption != "") {
            tvCaption.setText(photo.caption);
            tvCaption.setVisibility(View.VISIBLE);
        } else {
            tvCaption.setVisibility(View.GONE);
        }

        // load comment 1
        ImageView ivComment1 = (ImageView) convertView.findViewById(R.id.ivComment1);
        TextView tvComment1 = (TextView) convertView.findViewById(R.id.tvComment1);
        if (photo.comments.size() >= 1) {
            ivComment1.setImageResource(0);
            ivComment1.setBackgroundColor(0);
            Picasso.with(getContext())
                    .load(photo.comments.get(0).userPicUrl)
                    .placeholder(R.drawable.progress_animation)
                    .fit()
                    .centerCrop()
                    .transform(new RoundedTransformation(200, 10))
                    .into(ivComment1);
            ivComment1.setVisibility(View.VISIBLE);
            //
            tvComment1.setText(Html.fromHtml("<font color=\"#283596\">" + photo.comments.get(0).username + "</font> " + photo.comments.get(0).comment));
            tvComment1.setVisibility(View.VISIBLE);
        } else {
            ivComment1.setVisibility(View.GONE);
            tvComment1.setVisibility(View.GONE);
        }

        // load comment 2
        ImageView ivComment2 = (ImageView) convertView.findViewById(R.id.ivComment2);
        TextView tvComment2 = (TextView) convertView.findViewById(R.id.tvComment2);
        if (photo.comments.size() >= 2) {
            ivComment2.setImageResource(0);
            ivComment2.setBackgroundColor(0);
            Picasso.with(getContext())
                    .load(photo.comments.get(1).userPicUrl)
                    .placeholder(R.drawable.progress_animation)
                    .fit()
                    .centerCrop()
                    .transform(new RoundedTransformation(200, 10))
                    .into(ivComment2);
            ivComment2.setVisibility(View.VISIBLE);
            //
            tvComment2.setText(Html.fromHtml("<font color=\"#283596\">" + photo.comments.get(1).username + "</font> " + photo.comments.get(1).comment));
            tvComment2.setVisibility(View.VISIBLE);
        } else {
            ivComment2.setVisibility(View.GONE);
            tvComment2.setVisibility(View.GONE);
        }

        // load likeCount
        TextView tvLikeCount = (TextView) convertView.findViewById(R.id.tvLikeCount);
        tvLikeCount.setText(String.format("\uD83D\uDC9C %s likes", df.format(photo.likeCount)));

        // load userPic
        ImageView ivUserPic = (ImageView) convertView.findViewById(R.id.ivUserPic);
        ivUserPic.setImageResource(0);
        ivUserPic.setBackgroundColor(0);
        Picasso.with(getContext())
                .load(photo.userPicUrl)
                .placeholder(R.drawable.progress_animation)
                .fit()
                .centerCrop()
                .transform(new RoundedTransformation(200, 10))
                .into(ivUserPic);

        // load photo
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ivPhoto.setImageResource(0);
        ivPhoto.setBackgroundColor(0);
        Picasso.with(getContext())
                .load(photo.imageUrl)
                .placeholder(R.drawable.progress_animation)
                .fit()
                .centerCrop()
                .into(ivPhoto);

        return convertView;
    }
}

