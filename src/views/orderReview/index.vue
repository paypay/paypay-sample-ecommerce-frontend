<template>
  <div class="orderreview">
    <div class="orderreview-contents">
      <div class="main-content">
        <div class="shopping-cart">
          <p class="cart-heading">{{ $t("cake_shop.shopping_cart") }}</p>
          <div
            class="cart-contents bottom-border"
            :key="index"
            v-for="(item, index) in cartItems"
          >
            <div class="cart-image">
              <img
                alt="Mississippi Mud Pie"
                class="cart-image-content"
                :src="getImagePath(item.image)"
              />
            </div>
            <div class="cart-text" v-bind:title="$t(item.title)">
              {{ $t(item.title) }}
            </div>
            <div class="cart-price">￥{{ item.price }}</div>
            <div class="cart-close" @click="removeFromCart(item.id)">
              <img
                alt="Mississippi Mud Pie"
                src="@/assets/images/close_btn.png"
              />
            </div>
          </div>
        </div>
        <div class="order-description">
          <div class="review-order">
            <p class="order-heading">{{ $t("cake_shop.review_order") }}</p>
            <div class="order-details">
              <div
                class="item-details"
                :key="index"
                v-for="(item, index) in cartItems"
              >
                <p class="item-text">{{ $t(item.title) }}</p>
                <p class="item-price">￥{{ item.price.toFixed(2) }}</p>
              </div>
            </div>
            <div class="order-total">
              <div class="subtotal">
                <p class="subtotal-text">{{ $t("cake_shop.sub_total") }}</p>
                <p class="subtotal-price">￥{{ getSum.toFixed(2) }}</p>
              </div>
              <div class="delivery">
                <p class="delivery-text">{{ $t("cake_shop.delivery") }}</p>
                <p class="delivery-price">￥0</p>
              </div>
            </div>
            <div class="total-amount">
              <p class="total-amount-text">{{ $t("cake_shop.total") }}</p>
              <p class="total-amount-price">￥{{ getSum.toFixed(2) }}</p>
            </div>
          </div>
          <div class="order-button" @click="this.handlePayment">
            <button class="paypay-logo">
              <img alt="Paypay" src="@/assets/images/logo_paypay.svg" />
            </button>
            <button class="paypay-text">
              <span class="button-style">{{ $t("cake_shop.pay") }}</span>
            </button>
          </div>
          <p class="paypay-click">{{ $t("cake_shop.pay_click") }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Header from "@/components/dashboard/header.vue";
import Footer from "@/components/dashboard/footer.vue";
import { Getter, namespace, Mutation } from "vuex-class";
import { iItem } from "@/common/interface/item";

const CartModule = namespace("cart");

@Component
export default class OrderReview extends Vue {
  @CartModule.Getter cartItems!: iItem[];
  @CartModule.Getter getSum!: number;
  @CartModule.Mutation removeFromCart: any;
  @CartModule.Action requestPayment: any;
  // @CartGetter hasItemInCart!: (id:number) => boolean
  //TODO: make a separate  comp
  getImagePath(name: string): string {
    return require(`@/assets/images/${name}`);
  }

  handlePayment(): void {
    pp.transaction.makePayment({
      orderItems: this.cartItems.map((item) => {
        return {
          name: item.title,
          category: "pastries",
          quantity: 1,
          productId: item.id,
          unitPrice: {
            amount: item.price,
            currency: "JPY",
          },
        };
      }),
      amount: {
        amount: this.cartItems.reduce((acc: number, item: iItem) => {
          acc += item.price;
          return acc;
        }, 0),
        currency: "JPY",
      },
      fail: () => {
        console.log('makePayment failed');
      },
      success: () => {
        console.log('sucess');
        this.$router.push({
          name: "orderpayment",
        });
      },
    });
  }
}
</script>

<style lang="scss">
@import "@/styles/variables";

.orderreview {
  .orderreview-contents {
    background-image: url("./../../assets/images/Oval.png"),
      url("./../../assets/images/ovalright.png");
    background-position: left top, right top;
    background-repeat: no-repeat, no-repeat;
    padding: 60px 3% 220px 7%;
    background-color: $lightgrey;

    .main-content {
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      overflow: auto;
      position: relative;

      .shopping-cart {
        .cart-heading {
          font-size: 24px;
          font-weight: bold;
          color: $grey;
          padding-bottom: 16px;
        }
      }

      .order-description {
        .review-order {
          border-radius: 16px;
          background-color: $white;
          box-shadow: 0 9px 46px 8px rgba(17, 22, 26, 0.08),
            0 11px 15px -7px rgba(17, 22, 26, 0.2);
          padding: 18px 25px 50px 25px;

          .order-heading {
            font-size: 30px;
            font-weight: bold;
            color: $grey;
            padding-bottom: 28px;
          }

          .order-details {
            border-bottom: 2px solid $bold;

            .item-details {
              display: flex;
              justify-content: space-between;
              padding-bottom: 25px;

              .item-text {
                font-size: 18px;
                color: $lightblack;
                width: 50%;
              }

              .item-price {
                font-size: 20px;
                font-weight: 300;
                color: $darkash;
              }
            }
          }

          .order-total {
            border-bottom: 2px solid $bold;
            padding-top: 20px;

            .subtotal {
              display: flex;
              justify-content: space-between;
              padding-bottom: 20px;

              .subtotal-text {
                font-size: 18px;
                color: $lightblack;
              }

              .subtotal-price {
                font-size: 20px;
                font-weight: 500;
                color: $darkash;
              }
            }

            .delivery {
              display: flex;
              justify-content: space-between;
              padding-bottom: 20px;

              .delivery-text {
                font-size: 18px;
                color: $lightblack;
              }

              .delivery-price {
                font-size: 20px;
                font-weight: 500;
                color: $darkash;
              }
            }
          }

          .total-amount {
            display: flex;
            justify-content: space-between;
            padding-top: 30px;

            .total-amount-text {
              font-size: 18px;
              font-weight: bold;
              color: $red;
            }

            .total-amount-price {
              font-size: 20px;
              font-weight: 500;
              color: $red;
            }
          }
        }

        .order-button {
          margin-top: 31px;
          border-radius: 10px;
          box-shadow: 0 9px 46px 8px rgba(17, 22, 26, 0.08),
            0 11px 15px -7px rgba(17, 22, 26, 0.2);

          .paypay-logo {
            cursor: pointer;
            padding: 13px 24px 13px 27px;
            border: none;
            background-color: $red;
            border-radius: 10px 0px 0px 10px;
            margin-right: 1px;
          }

          .paypay-text {
            cursor: pointer;
            font-size: 20px;
            font-weight: 500;
            color: $white;
            border: none;
            padding: 19px 75px 11px 45px;
            background-color: $red;
            border-radius: 0px 10px 10px 0px;

            .button-style {
              position: relative;
              top: -5px;
            }
          }
        }

        .paypay-click {
          margin-top: 14px;
          font-size: 14px;
          color: $lightpurple;
        }
      }
    }
  }

  .cart-contents {
    display: flex;
    align-items: center;
    padding-bottom: 30px;
    padding-top: 32px;

    .cart-image {
      .cart-image-content {
        max-height: 100px;
        max-width: 120px;
        width: 100%;
        height: 100%;
        border-radius: 8px;
      }
    }

    .cart-text {
      padding-left: 2%;
      font-size: 20px;
      font-weight: bold;
      color: $black;
      width: 40%;
      text-overflow: ellipsis;
      overflow: hidden;
    }

    .cart-price {
      font-size: 20px;
      font-weight: bold;
      color: $red;
      padding-left: 3%;
    }

    .cart-close {
      padding-left: 10%;
      cursor: pointer;
      padding-top: 1%;
    }
  }

  .bottom-border {
    border-bottom: 1px solid $lightash;
  }
}
</style>
