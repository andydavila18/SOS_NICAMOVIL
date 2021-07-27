package fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class TextViewMaven extends AppCompatTextView
{
    public TextViewMaven(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewMaven(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewMaven(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        if (!isInEditMode())
        {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/MontserratSemiBold.otf");
            setTypeface(tf);
        }
    }

}

