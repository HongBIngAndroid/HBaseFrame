package com.lf.tempcore.tempModule.TempMVPCommImpl;

import com.lf.tempcore.tempModule.tempMVPCommI.TempPullablePresenterI;
import com.lf.tempcore.tempModule.tempMVPCommI.TempPullableViewI;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemoteApiFactory;
import com.lf.tempcore.tempResponse.TempResponse;

import rx.Observable;

/**
 * Created by longf on 2016/5/6.
 */
public abstract class TempPullablePresenterImpl<RESPONSE extends TempResponse> implements TempPullablePresenterI {
    private int currentPage=0;
    private int size=10;
    private final int INIT =0,REFRESH=1,LOADMORE=2;
    private TempPullableViewI mView;
    public TempPullablePresenterImpl(TempPullableViewI v){
        this.mView =v;
    }
    @Override
    public void requestInit() {
        currentPage=0;
        queryData(INIT);
    }

    @Override
    public void requestRefresh() {
        currentPage=0;
        queryData(REFRESH);
    }

    @Override
    public void requestLoadmore() {
        queryData(LOADMORE);
    }

    private void queryData(final int status){
        if (mView!=null) {
            mView.showPro();
        }
        TempRemoteApiFactory.executeMethod(createObservable(getQueryPage(),getSize(),getCurrentPage()), new TempRemoteApiFactory.OnCallBack<RESPONSE>() {
            @Override
            public void onSucceed(RESPONSE data) {
                if (mView!=null&&data.getFlag()==1) {
                    switch (status) {
                        case INIT:

//                            setCurrentPage();
                            mView.onInit(data);
                            break;
                        case REFRESH:

                            mView.onRefresh(data);
                            break;
                        case LOADMORE:

                            mView.onLoadmore(data);
                            break;
                    }

                }
            }

            @Override
            public void onCompleted() {
                if (mView!=null) {
                    mView.dismissPro();
                    switch (status) {
                        case INIT:
                            currentPage++;
                            break;
                        case REFRESH:
                            currentPage++;
                            mView.refreshStatus(true);
                            break;
                        case LOADMORE:
                            currentPage++;
                            mView.loadMoreStatus(true);
                            break;
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (mView!=null) {
                    mView.dismissPro();
//                    mView.showConnectError();
                    switch (status) {
                        case INIT:
                            break;
                        case REFRESH:
                            mView.refreshStatus(false);
                            break;
                        case LOADMORE:
                            mView.loadMoreStatus(false);
                            break;
                    }
                }
            }
        });
    }
    public abstract Observable<RESPONSE> createObservable(int queryPage, int querysize, int currentPage) ;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getQueryPage() {
        return currentPage+1;
    }

    public int getCurrentPage() {
        return this.currentPage ;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
