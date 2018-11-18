package io.untaek.animal.Hash;

import android.content.Context;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HashTagAdapter  {

//    public SpannableString hashChange(final Context context, String text) {
//        ArrayList<int[]> hashes = getSpans(text, '#');
//
//        SpannableString commentsContent =
//                new SpannableString(text);
//
//        for(int i = 0; i < hashes.size(); i++) {
//            int[] span = hashes.get(i);
//            int hashTagStart = span[0];
//            int hashTagEnd = span[1];
//
//            Hashtag hashTag = new Hashtag(context);
//            hashTag.setOnClickEventListener(new Hashtag.ClickEventListener() {
//                @Override
//                public void onClickEvent(String data) {
//                    Log.d("clickable", "ㅇㅇ");
//                }
//            });
//
//            commentsContent.setSpan(new Hashtag(context ),
//                    hashTagStart,
//                    hashTagEnd, 0);
//        }
//        return commentsContent;
//
//    }

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

    public void setContent(final Context context, final TextView textView, String tag){

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
                }
            });

            tagsContent.setSpan(new CalloutLink(context),
                    calloutStart,
                    calloutEnd, 0);
        }

        if(textView != null) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setText(tagsContent);
        }
    }
}
