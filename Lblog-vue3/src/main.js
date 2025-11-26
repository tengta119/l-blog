import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import 'viewerjs/dist/viewer.css'
import VueViewer from 'v-viewer'
// 导入全局路由守卫
import '@/permission'
const pinia = createPinia()
const app = createApp(App)
app.use(router)
app.use(pinia)
app.use(VueViewer)
pinia.use(piniaPluginPersistedstate)
app.mount('#app')
