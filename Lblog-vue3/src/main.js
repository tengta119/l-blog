import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
// 导入全局路由守卫
import '@/permission'
const pinia = createPinia()
const app = createApp(App)
app.use(router)
app.use(pinia)
pinia.use(piniaPluginPersistedstate)
app.mount('#app')
