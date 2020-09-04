package jp.ne.paypay.sample.model;

import jp.ne.paypay.model.MerchantOrderItem;
import jp.ne.paypay.model.MoneyAmount;

import java.util.List;

public class QrCodeRequest {
    private MoneyAmount amount;
    private List<MerchantOrderItem> orderItems;

    public MoneyAmount getAmount() {
        return amount;
    }

    public QrCodeRequest setAmount(MoneyAmount amount) {
        this.amount = amount;
        return this;
    }

    public List<MerchantOrderItem> getOrderItems() {
        return orderItems;
    }

    public QrCodeRequest setOrderItems(List<MerchantOrderItem> orderItems) {
        this.orderItems = orderItems;
        return this;
    }
}
