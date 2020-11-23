import { iItem } from "@/common/interface/item";
import { RootState } from '@/store';
import { ActionTree, GetterTree, MutationTree } from "vuex";
import { makePayment } from "@/api/cakes";


export interface State {
    selectedItems: iItem[]
}


const initState = {
    selectedItems: [],
}


type CartGetter = GetterTree<State, RootState>
type CartMutation = MutationTree<State>
type CartAction = ActionTree<State, RootState>

// getters
const getters: CartGetter = {
    cartItems: (state: State) => state.selectedItems,
    hasItemInCart: (state: State) => (id: number) => {
        return !!state.selectedItems.find(item => item.id === id)
    },
    getSum: (state: State) => state.selectedItems.reduce((acc: number, item: iItem) => {
        acc += item.price;
        return acc
    }, 0)
}

export const mutations: CartMutation = {
    addToCart(state, newItem) {
        const itemCopy = Object.assign({}, newItem)
        state.selectedItems.push(itemCopy)
        pp.requestPermission({
            permission: [
                {
                    name: 'mini_app_user_full_name',
                    type: 'must'
                },
                {
                    name: 'mini_app_user_email_address',
                    type: 'must'
                },
                {
                    name: 'mini_app_user_phone_number',
                    type: 'must'
                },
                {
                    name: 'mini_app_user_kana_name',
                    type: 'optional'
                },
                {
                    name: 'mini_app_user_nickname',
                    type: 'optional'
                }
            ],
            complete: () => {
                console.log('requestPermission complete')
            }
        })
    },
    removeFromCart(state, id) {
        const items = state.selectedItems.filter(item => item.id != id);
        state.selectedItems = [...items]
    },

}


export const actions: CartAction = {
    requestPayment({ state, commit }) {
        makePayment({
            "orderItems": state.selectedItems.map(item => {
                return {
                    "name": item.title,
                    "category": "pastries",
                    "quantity": 1,
                    "productId": item.id,
                    "unitPrice": {
                        "amount": item.price,
                        "currency": "JPY"
                    }
                }
            }),
            "amount": {
                "amount": state.selectedItems.reduce((acc: number, item: iItem) => {
                    acc += item.price;
                    return acc
                }, 0),
                "currency": "JPY"
            }
        }).then(res => {
            if (res.statusText === "OK" || res.status == 200) {
                location.href = res.data.data.url
            }
        })
    },

}




export default {
    state: initState,
    getters,
    mutations,
    actions,
    namespaced: true
}

