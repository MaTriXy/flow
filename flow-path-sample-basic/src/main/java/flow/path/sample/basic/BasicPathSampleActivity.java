package flow.path.sample.basic;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import flow.Flow;
import flow.path.PathContainer;
import flow.path.PathDispatcher;

public class BasicPathSampleActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.basic_path_frame);
  }

  @Override protected void attachBaseContext(Context newBase) {

    newBase = Flow.configure(newBase, this) //
        .dispatcher(new PathDispatcher(new PathContainer() {
          @Override protected void changePath(@Nullable State outgoingState,
              State incomingState, Flow.Direction direction, Flow.TraversalCallback callback) {
            ViewGroup frame = (ViewGroup) findViewById(R.id.basic_path_frame);
          }
        })).install();

    super.attachBaseContext(newBase);
  }
}
