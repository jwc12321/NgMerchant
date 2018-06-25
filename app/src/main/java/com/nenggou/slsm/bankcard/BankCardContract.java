package com.nenggou.slsm.bankcard;

import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;
import com.nenggou.slsm.data.entity.BankCardInfo;

import java.util.List;

/**
 * Created by JWC on 2018/6/23.
 */

public interface BankCardContract {
    interface BankCardListPresenter extends BasePresenter{
        void getBankCardList(String refreshType);
    }
    interface BankCardListView extends BaseView{
        void renderBankCardList(List<BankCardInfo> bankCardInfos);
    }

    interface AddbankcardPresenter extends BasePresenter{
        void addBankCard(String cardname, String cardbank, String carddbank, String cardno, String telbank);
    }
    interface AddbankcardView extends BaseView<AddbankcardPresenter>{
        void addSuccess();
    }

    interface PutForwardPresenter extends BasePresenter{
        void putForward(String amount,String type,String bankid);
    }

    interface PutForwardView extends BaseView<PutForwardPresenter>{
        void purForwardSuccess();
    }
}
