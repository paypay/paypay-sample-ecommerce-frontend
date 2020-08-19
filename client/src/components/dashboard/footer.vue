<template>
  <div class="footer">
    <p class="footer-text">{{ $t("cake_shop.footer") }}</p>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component({
  components: {}
})
export default class Footer extends Vue {}
</script>

<style lang="scss" >
@import "@/styles/variables";
.footer {
  background-color: $lightyellow;
  width: 100%;
  padding: 36px 0px 55px 7%;
  .footer-text {
    font-size: 16px;
    color: $ash;
  }
}
</style>