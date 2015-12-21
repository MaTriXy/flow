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

  public Installer surviveProcessDeath(StateParceler parceler) {
    checkNotNull(parceler, "parceler may not be null");
    this.parceler = parceler;
    return this;
  }

  public Installer useDispatcher(Flow.Dispatcher dispatcher, Object defaultState) {
    checkNotNull(dispatcher, "dispatcher may not be null");
    checkNotNull(defaultState, "defaultState may not be null");
    this.dispatcher = dispatcher;
    this.defaultState = defaultState;
    return this;
  }

  public Context install(Context baseContext, Activity activity) {
    if (InternalFragment.find(activity) != null) {
      throw new IllegalStateException("Flow is already installed in this Activity.");
    }
    final Flow.Dispatcher dis = checkNotNull(dispatcher, "dispatcher not set");
    final History defaultHistory = History.single(defaultState);
    final Application app = (Application) baseContext.getApplicationContext();
    InternalFragment.install(app, activity, parceler, defaultHistory, dis);
    return new InternalContextWrapper(baseContext, activity);
  }
}
