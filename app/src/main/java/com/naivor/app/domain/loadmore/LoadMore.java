package com.naivor.app.domain.loadmore;

import retrofit2.http.PUT;

/**
 * 加载的辅助类
 * <p>
 * Created by tianlai on 17-3-16.
 */

public class LoadMore {
    private static int INDEX = 0;

    private int index;
    private boolean hasMore = true;

    public static void init(int index) {
        INDEX = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getNext() {
        return index++;
    }

    public void reset() {
        index = INDEX;
        hasMore = true;
    }

    public boolean hasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    static interface Presenter {
        void load();

        void refresh();

        void loadMore();

    }

    static interface View {

        void refreshComplete();

        void loadMoreComplete();

        void loadError();
    }


    /**
     * 状态
     */
    enum State {
        ORIGIN, LOADING, COMPLETE, NOMOREDATA,LOADERROR
    }
}
