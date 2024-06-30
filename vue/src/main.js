import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import '@/assets/css/global.css'
import axios from "axios";
import ECharts from 'vue-echarts'
import 'echarts'
import * as echarts from 'echarts/core';
const app = createApp(App)

axios.get('/config.json').then((res)=>{
    app.config.globalProperties.$config = res.data
})
export const globals = app.config.globalProperties

app.use(router)
app.use(ElementPlus, {
    locale: zhCn,
})
app.mount('#app')
app.component('Echarts',ECharts)



for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}