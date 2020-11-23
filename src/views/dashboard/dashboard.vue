<template>
  <div class="dashboard">
    <Header />
    <div class="dashboard-items">
      <div class="header-image">
        <p class="header-text">
          {{ $t('cake_shop.header_text_one') }}
          <br />
          {{ $t('cake_shop.header_text_two') }}
        </p>
      </div>
      <div class="header-bar"></div>
      <div class="dashboard-content">
        <div class="main-content">
          <div class="main-image">
            <img alt="Cake" src="@/assets/images/cakelogo.png" />
          </div>
          <p class="main-text">{{ $t('cake_shop.main_text') }}</p>
          <div class="main-cakes">
            <div class="cake-contents">
              <Item
                v-for="(item, index) in allItems"
                :key="index"
                :attibutes="item"
              ></Item>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import Header from '@/components/dashboard/header.vue'
import Footer from '@/components/dashboard/footer.vue'
import Item from '@/components/item/index.vue'

import { Getter, namespace, Action } from 'vuex-class'
import { iItem } from '@/common/interface/item'

const ItemModule = namespace('items')

@Component({
  components: {
    Item
  }
})
export default class Dashboard extends Vue {
  @ItemModule.Getter allItems!: iItem[]
  @ItemModule.Action fetchCakes: any

  mounted() {
    this.fetchCakes()
    //setTimeout(function() {, 3000)
  }
}
</script>

<style lang="scss">
@import '@/styles/variables';

.dashboard {
  height: calc(100vh + 107px);

  .header {
    background-color: rgba(255, 255, 255, 0.8);
  }

  .dashboard-items {
    position: relative;
    top: -107px;

    .header-image {
      background-image: url('./../../assets/images/banner.png');
      height: 100%;
      background-position: center;
      background-repeat: no-repeat;
      background-size: cover;

      .header-text {
        font-size: 30px;
        font-weight: bold;
        padding: 181px 0px 110px 7%;
      }
    }

    .header-bar {
      height: 4px;
      background-color: $yellow;
    }

    .dashboard-content {
      .main-content {
        background-image: url('./../../assets/images/Oval.png'),
          url('./../../assets/images/ovalright.png');
        background-position: left top, right top;
        background-repeat: no-repeat, no-repeat;
        padding: 46px 7% 170px 7%;
        background-color: $lightgrey;

        .main-image {
          text-align: center;
          padding-bottom: 16px;
        }

        .main-text {
          text-align: center;
          font-weight: bold;
          color: $grey;
          font-size: 40px;
          padding-bottom: 37px;
        }

        .main-cakes {
          display: flex;
          justify-content: center;

          .cake-contents {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
          }
        }
      }
    }
  }

  .cakes-padding {
    padding-left: 20px;
  }

  .bottom-padding {
    padding-bottom: 20px;
  }
}
</style>
