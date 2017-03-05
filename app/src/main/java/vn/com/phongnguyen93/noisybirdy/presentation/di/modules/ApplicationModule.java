/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vn.com.phongnguyen93.noisybirdy.presentation.di.modules;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import vn.com.phongnguyen93.noisybirdy.data.DataRepository;
import vn.com.phongnguyen93.noisybirdy.data.api.RestApi;
import vn.com.phongnguyen93.noisybirdy.data.api.RestApiImpl;
import vn.com.phongnguyen93.noisybirdy.data.cache.TweetCache;
import vn.com.phongnguyen93.noisybirdy.data.cache.TweetCacheImpl;
import vn.com.phongnguyen93.noisybirdy.data.executor.JobExecutor;
import vn.com.phongnguyen93.noisybirdy.domain.executor.PostExecutionThread;
import vn.com.phongnguyen93.noisybirdy.domain.executor.ThreadExecutor;
import vn.com.phongnguyen93.noisybirdy.domain.repository.Repository;
import vn.com.phongnguyen93.noisybirdy.presentation.NoisyBirdyApplication;
import vn.com.phongnguyen93.noisybirdy.presentation.UIThread;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module public class ApplicationModule {
  private final NoisyBirdyApplication application;

  public ApplicationModule(NoisyBirdyApplication application) {
    this.application = application;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides @Singleton TweetCache provideTweetCache(TweetCacheImpl tweetCache) {
    return tweetCache;
  }

  @Provides @Singleton Repository provideRepository(DataRepository userDataRepository) {
    return userDataRepository;
  }

}
