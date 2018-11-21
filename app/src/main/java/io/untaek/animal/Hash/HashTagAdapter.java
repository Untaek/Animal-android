package io.untaek.animal.Hash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.untaek.animal.firebase.HashTagDetail;
import io.untaek.animal.firebase.UserDetail;
import io.untaek.animal.firebase.dummy;

public class HashTagAdapter  {

    private ArrayList<int[]> getSpans(String text, char prefix) {
        ArrayList<int[]> array = new ArrayList<int[]>();

        Pattern p = Pattern.compile( prefix + "\\w+");
        Matcher matcher = p.matcher(text);

        while (matcher.find()) {
            int[] currentSpan = new int[2];
            currentSpan[0] = matcher.start();
            currentSpan[1] = matcher.end();
            array.add(currentSpan);
        }

        return array;
    }

    public void setContent(final Context context, final TextView textView, String tag) {

        int i = 0;
        ArrayList<int[]> hashtagSpans = getSpans(tag, '#');
        ArrayList<int[]> calloutSpans = getSpans(tag, '@');

        SpannableString tagsContent = new SpannableString(tag);

        for(i = 0; i < hashtagSpans.size(); i++){
            int[] span = hashtagSpans.get(i);
            int hashTagStart = span[0];
            int hashTagEnd = span[1];

            Hashtag hashTag = new Hashtag(context);
            hashTag.setOnClickEventListener(new Hashtag.ClickEventListener() {
                @Override
                public void onClickEvent(String data) {
                    Intent intent = new Intent(context, HashtagActivity.class);
                    intent.putExtra("data", hashInfo(data));
                    context.startActivity(intent);

                }
            });

            tagsContent.setSpan(hashTag,
                    hashTagStart,
                    hashTagEnd,
                    0);
        }

        for(i = 0; i < calloutSpans.size(); i++) {
            int[] span = calloutSpans.get(i);
            int calloutStart = span[0];
            int calloutEnd = span[1];

            CalloutLink calloutLink = new CalloutLink(context);
            calloutLink.setOnClickEventListener(new CalloutLink.ClickEventListener() {
                @Override
                public void onClickEvent(String data) {
                    Intent intent = new Intent(context, CalloutActivity.class);
                    Log.d("users", data);
                    intent.putExtra("data", userInfo(data));
                    context.startActivity(intent);
                }
            });

            tagsContent.setSpan(calloutLink,
                    calloutStart,
                    calloutEnd, 0);
        }

        if(textView != null) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setText(tagsContent);
        }
    }

    public UserDetail userInfo(String name) {
        ArrayList<UserDetail> users = dummy.INSTANCE.getUsersDetail();

        for(int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(name)) {
               Log.d("users", users.get(i).getUserName());
                return users.get(i);
            }
        }
        return null;
    }

    public HashTagDetail hashInfo(String hash) {
        ArrayList<HashTagDetail> hashes = dummy.INSTANCE.getHashTagsDetail();

        for(int i = 0; i < hashes.size(); i++)
        {
            if (hashes.get(i).getTags().equals(hash))
                return hashes.get(i);
        }
        return null;
    }
}
