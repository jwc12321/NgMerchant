package com.nenggou.slsm.financing;


import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/7/24.
 */
@Module
public class FinancingModule {
    private FinancingContract.FinancindListView financindListView;
    private FinancingContract.PayFcOrderView payFcOrderView;
    private FinancingContract.FcOrderDetailView fcOrderDetailView;
    private FinancingContract.FcOrderListView fcOrderListView;
    private FinancingContract.FcWalletView fcWalletView;
    private FinancingContract.TurnOutBalanceView turnOutBalanceView;
    private FinancingContract.TurnOutRecordView turnOutRecordView;

    public FinancingModule(FinancingContract.FinancindListView financindListView) {
        this.financindListView = financindListView;
    }

    public FinancingModule(FinancingContract.PayFcOrderView payFcOrderView) {
        this.payFcOrderView = payFcOrderView;
    }

    public FinancingModule(FinancingContract.FcOrderDetailView fcOrderDetailView) {
        this.fcOrderDetailView = fcOrderDetailView;
    }

    public FinancingModule(FinancingContract.FcOrderListView fcOrderListView) {
        this.fcOrderListView = fcOrderListView;
    }

    public FinancingModule(FinancingContract.FcWalletView fcWalletView) {
        this.fcWalletView = fcWalletView;
    }

    public FinancingModule(FinancingContract.TurnOutBalanceView turnOutBalanceView) {
        this.turnOutBalanceView = turnOutBalanceView;
    }

    public FinancingModule(FinancingContract.TurnOutRecordView turnOutRecordView) {
        this.turnOutRecordView = turnOutRecordView;
    }

    @Provides
    FinancingContract.FinancindListView provideFinancindListView(){
        return financindListView;
    }

    @Provides
    FinancingContract.PayFcOrderView providePayFcOrderView(){
        return payFcOrderView;
    }

    @Provides
    FinancingContract.FcOrderDetailView provideFcOrderDetailView(){
        return fcOrderDetailView;
    }

    @Provides
    FinancingContract.FcOrderListView provideFcOrderListView(){
        return fcOrderListView;
    }

    @Provides
    FinancingContract.FcWalletView provideFcWalletView(){
        return fcWalletView;
    }

    @Provides
    FinancingContract.TurnOutBalanceView provideTurnOutBalanceView(){
        return turnOutBalanceView;
    }

    @Provides
    FinancingContract.TurnOutRecordView provideTurnOutRecordView(){
        return turnOutRecordView;
    }
}
