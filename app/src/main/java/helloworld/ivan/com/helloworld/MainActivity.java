package helloworld.ivan.com.helloworld;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements ObservableScrollView.Callbacks{

    private View mTitle;
    private View mContanter;
    private View mTopChild;
    private ObservableScrollView mScrollView;
    private int mHeaderHeightPixels;
    private int mTopchildHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScrollView= (ObservableScrollView) findViewById(R.id.scrollview);
        mScrollView.addCallbacks(this);
        mTitle= (LinearLayout) findViewById(R.id.title);
        mTopChild=findViewById(R.id.topchild);

        mContanter=  findViewById(R.id.continter);




        ViewTreeObserver vto = mScrollView.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.addOnGlobalLayoutListener(mGlobalLayoutListener);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollChanged(int deltaX, int deltaY) {
        int scrollY = mScrollView.getScrollY();
        mTopchildHeight=mTopChild.getHeight();
       // Log.i("Ivanwu","scrollY:"+scrollY  );
        float newTop = Math.max(mTopchildHeight, scrollY);
        //Log.i("Ivanwu","newTop:"+newTop  );
        mTitle.setTranslationY(newTop);
        ViewCompat.setElevation(mTitle,  20);
    }


    private void recomputePhotoAndScrollingMetrics() {
        mHeaderHeightPixels=mTitle.getHeight();
        mTopchildHeight=mTopChild.getHeight();
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams)
                mContanter.getLayoutParams();

        mlp.topMargin = mHeaderHeightPixels+mTopchildHeight ;
        mContanter.setLayoutParams(mlp);
       /* mHeaderHeightPixels = mTitle.getHeight();

        mPhotoHeightPixels = 0;


        ViewGroup.LayoutParams lp;


        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams)
                mDetailsContainer.getLayoutParams();
        if (mlp.topMargin != mHeaderHeightPixels + mPhotoHeightPixels) {
            mlp.topMargin = mHeaderHeightPixels + mPhotoHeightPixels;
            mDetailsContainer.setLayoutParams(mlp);
        }*/

        onScrollChanged(0, 0); // trigger scroll handling
    }



    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener
            = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {

            recomputePhotoAndScrollingMetrics();
        }
    };
}
