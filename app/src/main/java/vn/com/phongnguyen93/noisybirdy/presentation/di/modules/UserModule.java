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

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import vn.com.phongnguyen93.noisybirdy.domain.executor.PostExecutionThread;
import vn.com.phongnguyen93.noisybirdy.domain.executor.ThreadExecutor;
import vn.com.phongnguyen93.noisybirdy.domain.interactor.GetTimeline;
import vn.com.phongnguyen93.noisybirdy.domain.interactor.PostTweet;
import vn.com.phongnguyen93.noisybirdy.domain.interactor.UseCase;
import vn.com.phongnguyen93.noisybirdy.domain.repository.Repository;
import vn.com.phongnguyen93.noisybirdy.presentation.di.PerActivity;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class UserModule {
  private int limit = -1;

  public UserModule() {
  }

  public UserModule(int limit) {
    this.limit = limit;
  }

  @Provides @PerActivity @Named("tweetList") UseCase provideGetTimeLineUseCase(
      Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new GetTimeline(repository, threadExecutor, postExecutionThread, limit);
  }

}