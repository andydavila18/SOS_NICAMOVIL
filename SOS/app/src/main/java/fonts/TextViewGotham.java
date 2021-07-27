package fonts;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

public class TextViewGotham extends AppCompatTextView
{
    public TextViewGotham(Context context, AttributeSet attrs, int defStyle)
    {
        super(context,attrs,defStyle);
        init();
    }

    public TextViewGotham(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewGotham(Context context) {
        super(context);
        init();
    }

    private void init()
    {
        if (!isInEditMode())
        {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/GothamRoundedMedium.otf");
            setTypeface(tf);
        }
    }

}

