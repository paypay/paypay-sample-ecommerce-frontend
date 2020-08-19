import {iItem} from "@/common/interface/item";
import {RootState} from '@/store';
import {ActionTree, GetterTree, MutationTree} from "vuex";
import {getCakes} from "@/api/cakes";


export interface State {
    all: iItem[]
}


type CakeAction = ActionTree<State, RootState>



const initState = {
    all: [],
}


type ItemMutation = MutationTree<State>

type ItemGetter = GetterTree<State, RootState>


// getters
const getters:ItemGetter = {
    allItems: (state: State) => state.all,
}


export const mutations: ItemMutation = {

    cakesLoaded(state,payload:iItem[]){
        state.all = [...payload]
    }
}


export const actions:CakeAction = {
    fetchCakes({commit}):any{
        getCakes().then((res)=>{
            if(res.statusText === 'OK'){
                commit('cakesLoaded', res.data);
            }else{
                commit('cakesLoaded', []);
            }
        })
    }
}


export default {
    state: initState,
    getters,
    mutations,
    actions,
    namespaced: true
}

