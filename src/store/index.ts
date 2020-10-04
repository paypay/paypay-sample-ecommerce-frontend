import Vue from 'vue'
import Vuex from 'vuex'
import items, { State as itemsState } from './modules/items'
import cart ,{State as cartState} from './modules/cart'
import order ,{State as orderState} from './modules/order'

Vue.use(Vuex);

export interface RootState {
    items: itemsState,
    cart:cartState,
    order:orderState
}


export default new Vuex.Store({
    modules: {
        items,
        cart,
        order
    },
})



