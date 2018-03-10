package com.projects.melih.popularmovies.ui.movielist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.projects.melih.popularmovies.common.Constants;
import com.projects.melih.popularmovies.common.Utils;
import com.projects.melih.popularmovies.model.Movie;
import com.projects.melih.popularmovies.network.MovieAPI;
import com.projects.melih.popularmovies.repository.NetworkState;
import com.projects.melih.popularmovies.repository.TopRatedMoviesDataSourceFactory;

/**
 * Created by Melih Gültekin on 1.03.2018
 */

class TopRatedMoviesViewModel extends AndroidViewModel {
    private final TopRatedMoviesDataSourceFactory topRatedMoviesSourceFactory = new TopRatedMoviesDataSourceFactory(MovieAPI.getMovieService());

    private LiveData<PagedList<Movie>> pagedList;
    private MutableLiveData<NetworkState> networkState;
    private LiveData<NetworkState> refreshState;

    public TopRatedMoviesViewModel(@NonNull Application application) {
        super(application);
    }

    LiveData<PagedList<Movie>> getPagedList() {
        return pagedList;
    }

    MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    LiveData<NetworkState> getRefreshState() {
        return refreshState;
    }

    void sortByTopRated(boolean shouldRefresh) {
        if (shouldRefresh) {
            final TopRatedMoviesDataSourceFactory.PageKeyedMovieDataSource value = topRatedMoviesSourceFactory.getSourceLiveData().getValue();
            if (value != null) {
                value.invalidate();
            }
        } else if (pagedList == null) {
            pagedList = new LivePagedListBuilder<>(topRatedMoviesSourceFactory, Constants.PAGE_SIZE).build();
            networkState = (MutableLiveData<NetworkState>) Transformations.switchMap(topRatedMoviesSourceFactory.getSourceLiveData(), TopRatedMoviesDataSourceFactory.PageKeyedMovieDataSource::getNetworkState);
            refreshState = Transformations.switchMap(topRatedMoviesSourceFactory.getSourceLiveData(), TopRatedMoviesDataSourceFactory.PageKeyedMovieDataSource::getInitialLoad);
        }
        if (!Utils.isNetworkConnected(getApplication().getApplicationContext())) {
            networkState.postValue(NetworkState.NO_NETWORK);
        }
    }
}