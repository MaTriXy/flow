/*
 * Copyright 2014 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package flow.path;

import android.support.annotation.Nullable;
import android.view.View;
import flow.Flow.Direction;
import flow.Flow.TraversalCallback;
import flow.ViewState;

/**
 * Handles swapping paths within a container view, as well as flow mechanics, allowing supported
 * container views to be largely declarative.
 */
// TODO: this isn't path-specific at all!
public abstract class PathContainer {

  public static final class State {
    private final Path path;
    private final ViewState viewState;

    private State(Path path, ViewState viewState) {
      this.path = path;
      this.viewState = viewState;
    }

    public Path getPath() {
      return path;
    }

    public void saveViewState(View view) {
      viewState.save(view);
    }
  }

  @Nullable private State currentState;

  /**
   * Replace any current view to show the given path. Allows display of {@link Path}s other
   * than in response to Flow dispatches.
   */
  public final void setPath(Path path, Direction direction, TraversalCallback callback) {
    setPath(path, direction, ViewState.NULL, callback);
  }

  public final void setPath(Path path, Direction direction, ViewState viewState,
      final TraversalCallback callback) {

    // If we already have the path we want, short circuit.
    if (currentState != null && currentState.path.equals(path)) {
      callback.onTraversalCompleted();
      return;
    }

    State outgoingState = currentState;
    final State incomingState = new State(path, viewState);
    changePath(outgoingState, incomingState, direction, new TraversalCallback() {
      @Override public void onTraversalCompleted() {
        currentState = incomingState;
        callback.onTraversalCompleted();
      }
    });
  }

  protected abstract void changePath(@Nullable State outgoingState,
      State incomingState, Direction direction, TraversalCallback callback);
}
