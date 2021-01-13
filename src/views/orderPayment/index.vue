<template>
  <div class="orderpayment">
    <div class="orderpayment-contents">
      <div class="main-content">
        <router-link to="/">
          <div class="payment-header">
            <img src="@/assets/images/icn_back.png" alt="Back" />
            <span class="header-text">{{ $t("cake_shop.home") }}</span>
          </div>
        </router-link>
        <div class="main-header">{{ $t("cake_shop.order_success") }}</div>
        <p class="main-date">{{ tranasctionDate }}</p>
        <div class="payment-image">
          <img src="@/assets/images/Image 1.png" alt="Cake" />
          <div class="payment-tick">
            <img src="@/assets/images/GreenTick-success.png" alt="Tick" />
          </div>
        </div>
        <p class="amount-paid">{{ $t("cake_shop.total_amount") }}</p>
        <p class="payment-amount" v-if="orderStatus.amount">
          ￥ {{ orderStatus.amount.amount.toFixed(2) }}
        </p>

        <div class="cake-description">
          <div
            class="cake-details"
            :key="index"
            v-for="(item, index) in orderStatus.orderItems"
          >
            <p class="cake-name">{{ $t(item.name) }}</p>
            <p class="cake-price">￥{{ item.unitPrice.amount.toFixed(2) }}</p>
          </div>
        </div>
        <div class="merchantid" v-if="orderStatus">
          <span class="merchant-text">{{ $t("cake_shop.merchant") }}</span>
          <span class="merchant-number">{{
            orderStatus.merchantPaymentId
          }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
// import Header from "@/components/dashboard/header.vue";
// import Footer from "@/components/dashboard/footer.vue";
import { namespace } from "vuex-class";
import { iOrderItem } from "@/common/interface/item";
// import { format } from "date-fns";
const OrderModule = namespace("order");

@Component
export default class OrderPayment extends Vue {
  @OrderModule.Getter orderStatus!: iOrderItem;
  @OrderModule.Action fetchOrderStatus: any;
  mounted() {
    this.fetchOrderStatus(this.$route.params.id);
  }
}
</script>

<style lang="scss" >
@import "@/styles/variables";
.orderpayment {
  .orderpayment-contents {
    background-image: url("./../../assets/images/Oval.png"),
      url("./../../assets/images/ovalright.png");
    background-position: left top, right top;
    background-repeat: no-repeat, no-repeat;
    padding: 55px 10% 78px 10%;
    background-color: $lightgrey;
    display: flex;
    justify-content: center;
    .main-content {
      width: 635px;
      border-radius: 16px;
      background-color: $white;
      box-shadow: 0 9px 46px 8px rgba(17, 22, 26, 0.08),
        0 11px 15px -7px rgba(17, 22, 26, 0.2);
      .payment-header {
        padding: 21px 0px 0px 13px;
        .header-text {
          font-size: 16px;
          color: $red;
          padding-left: 9px;
          position: relative;
          top: -3px;
        }
      }
      .main-header {
        font-size: 30px;
        font-weight: bold;
        text-align: center;
        color: $grey;
      }
      .main-date {
        text-align: center;
        font-size: 14px;
        color: $darkblack;
        padding-top: 8px;
        padding-bottom: 15px;
      }
      .payment-image {
        text-align: center;
        .payment-tick {
          position: relative;
          top: -45px;
        }
      }
      .amount-paid {
        text-align: center;
        position: relative;
        top: -25px;
        font-size: 18px;
        color: $matgrey;
        font-weight: bold;
      }
      .payment-amount {
        font-size: 20px;
        font-weight: bold;
        color: $red;
        text-align: center;
        margin-top: -18px;
      }

      .cake-description {
        background-color: $lightviolet;
        margin-top: 26px;
        padding-top: 8px;

        .cake-details {
          display: flex;
          justify-content: center;
          padding-bottom: 5px;

          .cake-name {
            padding-left: 40px;
            font-size: 18px;
            color: $darkblack;
            padding-top: 2px;
            border-bottom: 2px solid #e6e8ef;
            padding-bottom: 5px;
            width: 30%;
          }

          .cake-names {
            padding-left: 40px;
            font-size: 18px;
            color: $darkblack;
            padding-top: 2px;

            padding-bottom: 5px;
            width: 30%;
          }
          .cake-price {
            padding-left: 10px;
            color: $darkash;
            font-size: 20px;
            font-weight: 300;
            border-bottom: 2px solid #e6e8ef;
            padding-bottom: 5px;
            width: 18%;
          }
          .cake-prices {
            padding-left: 10px;
            color: $darkash;
            font-size: 20px;
            font-weight: 300;
            padding-bottom: 5px;
            width: 18%;
          }
        }
      }
      .merchantid {
        padding-top: 24px;
        text-align: center;
        .merchant-text {
          font-size: 12px;
          color: $silver;
        }
        .merchant-number {
          font-size: 12px;
          color: $darkblack;
        }
      }
      .transactionid {
        padding-top: 7px;
        text-align: center;
        .transaction-text {
          font-size: 12px;
          color: $silver;
        }
        .transaction-number {
          font-size: 12px;
          color: $darkblack;
        }
      }
      .paymentid {
        padding-top: 7px;
        padding-bottom: 35px;
        text-align: center;

        .paymentid-text {
          font-size: 12px;
          color: $silver;
        }
        .paymentid-number {
          font-size: 12px;
          color: $darkblack;
        }
      }
    }
  }
  a {
    text-decoration: none;
  }
  .bottom-border {
    border-bottom: none;
  }
}
</style>