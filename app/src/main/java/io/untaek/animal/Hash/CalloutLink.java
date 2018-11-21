package io.untaek.animal.Hash;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

public class CalloutLink extends ClickableSpan {
    public interface  ClickEventListener{
        void onClickEvent(String data);
    }

    Context context;
    ClickEventListener mClickEventListener = null;

    public CalloutLink(Context ctx) {
        super();
        context = ctx;
    }

    public void setOnClickEventListener(ClickEventListener listener) {
        mClickEventListener = listener;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setARGB(255, 51, 51, 51);
        ds.setTypeface(Typeface.DEFAULT_BOLD);

    }

    @Override
    public void onClick(View widget) {
        TextView tv = (TextView) widget;
        Spanned s = (Spanned) tv.getText();
        int start = s.getSpanStart(this);
        int end = s.getSpanEnd(this);
        String theWord = s.subSequence(start + 1, end).toString();
        mClickEventListener.onClickEvent(theWord);
    }
}
