package fonts;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class TextViewMontserrat extends AppCompatTextView
{
    public TextViewMontserrat(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewMontserrat(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewMontserrat(Context context) {
        super(context);
        init();
    }

    private void init()
    {
        if (!isInEditMode())
        {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/MavenProBold.ttf");
            setTypeface(tf);
        }
    }

}

