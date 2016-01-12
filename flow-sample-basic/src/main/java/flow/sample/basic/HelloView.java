package flow.sample.basic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public final class HelloView extends LinearLayout {

  public HelloView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setOrientation(VERTICAL);
  }

  @Override protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
    super.dispatchRestoreInstanceState(container);
  }

  @Override public void restoreHierarchyState(SparseArray<Parcelable> container) {
    super.restoreHierarchyState(container);
  }

  @Override protected void onRestoreInstanceState(Parcelable state) {
    super.onRestoreInstanceState(state);
  }

  @SuppressLint("SetTextI18n") @Override protected void onFinishInflate() {
    super.onFinishInflate();
    HelloScreen screen = Screens.getScreen(getContext());
    ((TextView) findViewById(R.id.hello_name)).setText("Hello " + screen.name);

    final TextView counter = (TextView) findViewById(R.id.hello_counter);
    findViewById(R.id.hello_increment).setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        String text = counter.getText().toString();
        if (text.isEmpty()) text = "0";
        Integer current = Integer.valueOf(text);
        counter.setText(String.valueOf(current + 1));
      }
    });
  }
}
