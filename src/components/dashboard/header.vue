<template>
  <div class="header">
    <div class="header-contents">
      <router-link to="/">
        <div class="header-logo">
          <img src="@/assets/images/Logo.png" alt="logo" />
        </div>
      </router-link>
      <router-link to="/orderreview">
        <div class="header-cart">
          <img src="@/assets/images/cart.png" alt="cart" />
          <div class="cart-item">{{cartItems.length}}</div>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import {Getter, namespace} from "vuex-class";
import {iItem} from "@/common/interface/item";
const CartGetter = namespace('cart', Getter)
@Component({
  components: {}
})
export default class Header extends Vue {
  @CartGetter cartItems!: iItem[]
}
</script>

<style lang="scss" >
@import "@/styles/variables";
.header {
  position: sticky;
  top: 0;
  width: 100%;
  background-color: $white;
  z-index: 999;
  .header-contents {
    display: flex;
    justify-content: space-between;
    padding: 26px 7%;
    .header-logo {
      cursor: pointer;
    }
    .header-cart {
      position: relative;
      padding-top: 19px;
      cursor: pointer;
      .cart-item {
        position: absolute;
        top: 12px;
        left: 18px;
        background-color: $red;
        text-align: center;
        border-radius: 50%;

        padding: 1px 6px;
        color: $white;
        font-weight: bold;
      }
    }
  }
}
</style>