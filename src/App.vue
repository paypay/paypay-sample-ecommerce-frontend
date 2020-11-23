<template>
  <div id="app">
    <Header />
    <router-view />
    <Footer />
  </div>
</template>

<script>
import { Component, Vue } from "vue-property-decorator";
import Header from "@/components/dashboard/header";
import Footer from "@/components/dashboard/footer";
@Component({
  components: {
    Header,
    Footer,
  },
})
export default class App extends Vue {
  mounted() {
    let jsBridge = document.createElement("script");
    jsBridge.setAttribute(
      "src",
      "https://static.paypay.ne.jp/libs/jsbridge@latest/bundle-miniapp.js"
    );
    document.head.appendChild(jsBridge);
  }
}
</script>
