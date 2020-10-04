import Vue from 'vue'
import VueI18n from 'vue-i18n'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';
import i18n from './i18n'
import router from './router'
import store from './store'
import '@/styles/index.scss' // global css

Vue.use(ElementUI);
Vue.config.productionTip = false

new Vue({
  router,
  i18n,
  store,
  render: h => h(App)
}).$mount('#app')
