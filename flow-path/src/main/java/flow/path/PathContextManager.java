package flow.path;

import android.content.Context;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// TODO RA-11068
// TODO ray: If you can delete com.squareup.flow.redux.ReduxLayer#getVisibleContext then you probably did

final class PathContextManager {
  private final Map<Path, ContextEntry> nodes = new LinkedHashMap<>();
  private final PathContextFactory factory;

  PathContextManager(PathContextFactory factory, Context rootContext) {
    this.factory = factory;
    nodes.put(Path.ROOT, new ContextEntry(rootContext));
  }

  public Context getContext(Context rootContext, Path path) {
    List<Path> elements = path.elements();
    // We walk down the elements, reusing existing contexts for the elements we encounter.  As soon
    // as we encounter an element that doesn't already have a context, we stop.
    // Note: we will always have at least one shared element, the root.
    ContextEntry node = null;
    for (Path element : elements) {
      ContextEntry existing = nodes.get(element);
      if (existing == null) {
        Context parent = node == null ? rootContext : node.context;
        Context context = factory.setUpContext(element, parent);
        existing = new ContextEntry(context);
        nodes.put(element, existing);
        node = existing;
      }
      node.uses++;
    }
    return node.context;
  }

  public void tearDown(Context context) {
    Path path = Path.get(context);
    boolean tearDownDone = false;
    for (Path element : path.elements()) {
      ContextEntry node = nodes.get(element);
      node.uses--;

      if (node.uses > 0) {
        if (tearDownDone) throw new IllegalStateException("uh oh");
        continue;
      }

      if (!tearDownDone) {
        factory.tearDownContext(node.context);
        tearDownDone = true;
      }
      nodes.remove(element);
    }
  }

  private static final class ContextEntry {
    final Context context;
    /** Includes uses as a leaf and as a direct parent. */
    int uses = 0;

    private ContextEntry(Context context) {
      this.context = context;
    }
  }
}
