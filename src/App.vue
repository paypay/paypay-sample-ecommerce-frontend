<template>
  <div id="app">
    <Header />
    <router-view/>
    <Footer/>
  </div>
</template>

<script>
  import {Component, Vue} from 'vue-property-decorator';
  import Header from "@/components/dashboard/header";
  import Footer from "@/components/dashboard/footer";
  @Component({
    components: {
      Header,
      Footer
    }
  })
  export default class App extends Vue{

  }
</script>
