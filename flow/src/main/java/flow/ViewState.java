/*
 * Copyright 2015 Square Inc.
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
package flow;

import android.view.View;

public abstract class ViewState {
  public static final ViewState NULL = new ViewState() {
    @Override public void save(View view) {
    }

    @Override public void restore(View view) {
    }
  };

  ViewState() {
    // No external instances.
  }

  public abstract void save(View view);

  public abstract void restore(View view);
}
