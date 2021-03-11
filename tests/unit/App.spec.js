import { shallowMount, mount, createLocalVue } from "@vue/test-utils"
import App from "@/App.vue"
import VueRouter from "vue-router"
import Vuex from "vuex";
import Dashboard from '@/views/dashboard/dashboard.vue'
import {routes} from "@/router/index"

import Store from '@/store/index.ts';

const localVue = createLocalVue()
localVue.use(VueRouter)
localVue.use(Vuex)
const store = Store;

describe("App", () => {
    it("renders a dashboard component", async () => {
        const router = new VueRouter({ routes })
        const wrapper = mount(App, {
            mocks: {
                $t: (key) => key,
            },
            store,
            localVue,
            router
        })

        router.push("/")
        await wrapper.vm.$nextTick()

        expect(wrapper.findComponent(Dashboard).exists()).toBe(true)
    })
})