import Vue from 'vue'
import VueRouter from 'vue-router'
import Dashboard from '../views/dashboard/dashboard.vue'
import OrderPayment from '../views/orderPayment/index.vue'
import OrderReview from '../views/orderReview/index.vue'
Vue.use(VueRouter)

export const routes = [
  {
    path: '/',
    name: 'dashboard',
    component: Dashboard

  },
  {
    path: '/orderreview',
    name: 'orderreview',
    component: OrderReview
  },
  {
    path: '/orderpayment/:id',
    name: 'orderpayment',
    component: OrderPayment
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
