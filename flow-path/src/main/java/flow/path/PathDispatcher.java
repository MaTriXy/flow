package flow.path;

import flow.Flow;
import flow.ViewState;

// TODO: not path-specific at all!
public final class PathDispatcher implements Flow.Dispatcher {

  private final PathContainer container;

  public PathDispatcher(PathContainer container) {
    this.container = container;
  }

  @Override public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {
    Path path = traversal.destination.top();
    ViewState viewState = traversal.destination.topViewState();
    container.setPath(path, traversal.direction, viewState, callback);
  }
}
