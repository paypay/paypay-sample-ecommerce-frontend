import { shallowMount, mount, createLocalVue } from "@vue/test-utils"
import App from "@/App.vue"
import VueRouter from "vue-router"
import Dashboard from '../views/dashboard/dashboard.vue'
import {routes} from "./router/index"

const localVue = createLocalVue()
localVue.use(VueRouter)

describe("App", () => {
    it("renders a dashboard component", async () => {
        const router = new VueRouter({ routes })
        const wrapper = mount(App, {
            localVue,
            router
        })

        router.push("/")
        await wrapper.vm.$nextTick()

        expect(wrapper.findComponent(Dashboard).exists()).toBe(true)
    })
})