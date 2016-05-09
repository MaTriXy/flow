package flow.sample.multikey;

import android.support.annotation.NonNull;
import flow.MultiKey;
import java.util.Collections;
import java.util.List;

public final class DialogScreen implements MultiKey {
  final Object mainContent;

  public DialogScreen(Object mainContent) {
    this.mainContent = mainContent;
  }

  @Override public String toString() {
    return "Do you really want to see screen two?";
  }

  @NonNull @Override public List<Object> getKeys() {
    return Collections.singletonList(mainContent);
  }
}
