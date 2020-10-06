import {iOrderItem, iUnitPrice} from "@/common/interface/item";
import {RootState} from '@/store';
import {ActionTree, GetterTree, MutationTree} from "vuex";
import {getOrderStatus} from "@/api/cakes";


export interface State {
    amount: iUnitPrice,
    orderItems: iOrderItem[],
    merchantPaymentId:string,
}

const initState = {
    amount: null,
    orderItems: []
}


type OrderGetter = GetterTree<State, RootState>
type OrderAction = ActionTree<State, RootState>
type OrderMutation = MutationTree<State>

// getters
const getters: OrderGetter = {
    orderStatus(state): State {
        return {
            ...state
        }
    }
}

export const mutations: OrderMutation = {
    updateOrderStatus(state, data) {
        state.amount = data.amount;
        state.orderItems = data.orderItems;
        state.merchantPaymentId = data.merchantPaymentId;
    },

}


export const actions: OrderAction = {
    fetchOrderStatus({state, commit}, id) {
        getOrderStatus(id).then(res => {
            if (res.statusText === "OK" || res.status == 200) {
                commit('updateOrderStatus', {
                    amount: res.data.data.amount,
                    orderItems: res.data.data.orderItems,
                    merchantPaymentId:res.data.data.merchantPaymentId,
                });
            }
        })
    }
}


export default {
    state: initState,
    getters,
    actions,
    mutations,
    namespaced: true
}

