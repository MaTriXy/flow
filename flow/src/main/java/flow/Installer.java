package flow;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import static flow.Preconditions.checkNotNull;

public final class Installer {
  private StateParceler parceler;
  private Object defaultState;
  private Flow.Dispatcher dispatcher;

  Installer() {
  }

  public Installer stateParceler(StateParceler parceler) {
    checkNotNull(parceler, "parceler may not be null");
    this.parceler = parceler;
    return this;
  }

  public Installer dispatcher(Flow.Dispatcher dispatcher) {
    checkNotNull(dispatcher, "dispatcher may not be null");
    this.dispatcher = dispatcher;
    return this;
  }

  public Installer defaultState(Object defaultState) {
    checkNotNull(defaultState, "defaultState may not be null");
    this.defaultState = defaultState;
    return this;
  }

  public Context install(Context baseContext, Activity activity) {
    if (InternalFragment.find(activity) != null) {
      throw new IllegalStateException("Flow is already installed in this Activity.");
    }
    final Flow.Dispatcher dis = dispatcher == null ? new DefaultDispatcher(activity) : dispatcher;
    final Object defState = defaultState == null ? "Hello, World!" : defaultState;

    final History defaultHistory =
        History.single(defState);
    final Application app = (Application) baseContext.getApplicationContext();
    InternalFragment.install(app, activity, parceler, defaultHistory, dis);

    return new InternalContextWrapper(baseContext, activity);
  }
}
