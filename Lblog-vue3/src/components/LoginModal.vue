<template>
    <transition name="fade">
        <div v-if="show" class="fixed inset-0 z-50 flex items-center justify-center bg-gray-900 bg-opacity-50 p-4" @click.self="close">
            <div class="relative w-full max-w-md p-8 border border-gray-100 shadow-xl rounded-xl bg-white">
                <button @click="close" aria-label="关闭" class="absolute top-3 right-3 text-gray-500 hover:text-gray-700 focus:outline-none">✕</button>
            <div class="text-center px-4">
                <!-- 用户信息显示 -->
                <div v-if="currentUser" class="mb-4 p-3 bg-blue-50 rounded-lg">
                    <div class="text-sm text-blue-800">
                        <div>当前登录用户ID: {{ currentUser.id }}</div>
                        <div v-if="currentUser.phone">手机号: {{ currentUser.phone }}</div>
                        <div v-if="currentUser.email">邮箱: {{ currentUser.email }}</div>
                    </div>
                    <button @click="logout" class="mt-2 text-xs text-red-600 hover:text-red-800">点击登出</button>
                </div>
                
                <div class="mt-2">
                    <div class="text-2xl font-semibold text-gray-900">登录</div>
                </div>
                <div class="mt-6">
                    <form @submit.prevent="userLogin" ref="formRef" class="space-y-4">
                        <!-- 账户 -->
                        <div class="mb-4">
                            <div class="relative">
                                <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                                    <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                                        <path d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 5a3 3 0 1 1 0 6 3 3 0 0 1 0-6Zm0 13a8.949 8.949 0 0 1-4.951-1.488A3.987 3.987 0 0 1 9 13h2a3.987 3.987 0 0 1 3.951 3.512A8.949 8.949 0 0 1 10 18Z"/>
                                    </svg>
                                </div>
                                <input 
                                    type="text" 
                                    v-model="form.username" 
                                    maxlength="20" 
                                    placeholder="用户名/邮箱" 
                                    class="appearance-none bg-transparent border border-gray-300 rounded-lg w-full pl-10 pr-3 py-2 text-gray-700 leading-tight focus:outline-none focus:border-blue-500"
                                    :class="{'border-red-500 focus:border-red-500': errors.username}"
                                    @blur="validateUsername"
                                    required
                                >
                            </div>
                            <p v-if="errors.username" class="mt-1 text-xs text-red-600 text-left">{{ errors.username }}</p>
                        </div>
                        <!-- 密码 -->
                        <div class="mb-4">
                            <div class="relative">
                                <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                                    <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 18 20">
                                        <path d="M17.876.517A1.837 1.837 0 0 0 16.282 0H3.718A1.837 1.837 0 0 0 2.124.517L.517 2.124A1.837 1.837 0 0 0 0 3.718v12.564A1.837 1.837 0 0 0 .517 17.876l1.607 1.607a1.837 1.837 0 0 0 1.594.517h12.564a1.837 1.837 0 0 0 1.594-.517l1.607-1.607A1.837 1.837 0 0 0 20 16.282V3.718a1.837 1.837 0 0 0-.517-1.594L17.876.517ZM15 10a1 1 0 0 1-1 1H6a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1h8a1 1 0 0 1 1 1v4Z"/>
                                    </svg>
                                </div>
                                <input 
                                    type="password" 
                                    v-model="form.password" 
                                    maxlength="20" 
                                    placeholder="密码" 
                                    class="appearance-none bg-transparent border border-gray-300 rounded-lg w-full pl-10 pr-3 py-2 text-gray-700 leading-tight focus:outline-none focus:border-blue-500"
                                    :class="{'border-red-500 focus:border-red-500': errors.password}"
                                    @blur="validatePassword"
                                    required
                                >
                            </div>
                            <p v-if="errors.password" class="mt-1 text-xs text-red-600 text-left">{{ errors.password }}</p>
                        </div>
                        <!-- 验证码 -->
                        <div class="mb-4">
                            <div class="flex items-center gap-2">
                                <div class="relative flex-1">
                                    <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                                        <svg class="w-4 h-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                                            <path d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 3.5a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3Zm0 12.5a6.986 6.986 0 0 1-4-1.25c.05-1.65 3-2.55 4-2.55s3.95.9 4 2.55A6.986 6.986 0 0 1 10 16Z"/>
                                        </svg>
                                    </div>
                                    <input 
                                        type="text" 
                                        v-model="form.code" 
                                        maxlength="6" 
                                        placeholder="验证码" 
                                        class="appearance-none bg-transparent border border-gray-300 rounded-lg w-full pl-10 pr-3 py-2 text-gray-700 leading-tight focus:outline-none focus:border-blue-500"
                                        :class="{'border-red-500 focus:border-red-500': errors.code}"
                                        @blur="validateCode"
                                    >
                                </div>
                                <button type="button" @click="sendCode" :disabled="codeDisabled" class="ml-auto bg-blue-500 hover:bg-blue-600 disabled:opacity-50 disabled:cursor-not-allowed text-white text-sm px-3 py-2 rounded focus:outline-none focus:shadow-outline">{{ codeBtnText }}</button>
                            </div>
                            <p v-if="errors.code" class="mt-1 text-xs text-red-600 text-left">{{ errors.code }}</p>
                        </div>
                        <div class="flex justify-between items-center">
                            <div class="flex items-center">
                                <input 
                                    type="checkbox" 
                                    id="remember" 
                                    v-model="form.remember" 
                                    class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500"
                                >
                                <label for="remember" class="ml-2 text-sm text-gray-600">记住我</label>
                            </div>
                            <a href="#" class="text-sm text-blue-600 hover:underline" @click.prevent="forgotPassword">忘记密码？</a>
                        </div>
                    </form>
                </div>
                <div class="mt-6">
                    <button 
                        @click="userLogin" 
                        class="w-full bg-green-500 hover:bg-green-600 text-white font-medium py-2.5 px-4 rounded-lg focus:outline-none focus:shadow-outline disabled:opacity-50 disabled:cursor-not-allowed"
                        :disabled="loading"
                    >
                        <span v-if="!loading">立即登录</span>
                        <span v-else class="inline-flex items-center justify-center gap-2">
                            <svg class="w-4 h-4 animate-spin" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v4a4 4 0 00-4 4H4z"></path>
                            </svg>
                            登录中...
                        </span>
                    </button>
                </div>
            </div>
            </div>
        </div>
    </transition>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { login, requestCode } from '@/api/admin/user.js';
import { showMessage} from '@/composables/util'
import { ref, reactive, onMounted, onBeforeUnmount } from 'vue'
import { setToken, removeToken } from '@/composables/auth'

const show = ref(false);
// 登录按钮加载
const loading = ref(false)
const formRef = ref(null);
const currentUser = ref(null);
const router = useRouter();

const form = reactive({
    username: '',
    password: '',
    code: '',
    remember: false
});

const errors = reactive({
    username: '',
    password: '',
    code: ''
});

const codeBtnText = ref('获取验证码');
const codeDisabled = ref(false);
let codeTimer = null;
const countdown = ref(60);

const emit = defineEmits(['close']);

const close = () => {
    show.value = false;
    currentUser.value = null;
    emit('close');
    // reset errors and code button state
    errors.username = '';
    errors.password = '';
    errors.code = '';
    if (codeTimer) {
        clearInterval(codeTimer);
        codeTimer = null;
    }
    codeDisabled.value = false;
    codeBtnText.value = '获取验证码';
    countdown.value = 60;
};

const open = () => {
    show.value = true;
    currentUser.value = getCurrentUser();
};

defineExpose({
    open
});


// 按回车键后，执行登录事件
function onKeyUp(e) {
    console.log(e)
    if (e.key == 'Enter') {
        userLogin()
    }
}

// 添加键盘监听
onMounted(() => {
    console.log('添加键盘监听')
    document.addEventListener('keyup', onKeyUp)
})

// 移除键盘监听
onBeforeUnmount(() => {
    document.removeEventListener('keyup', onKeyUp)
})

const userLogin = async () => {
    try {

        // Validation
        const validU = validateUsername();
        const validP = validatePassword();
        const validC = validateCode();
        if (!validU || !validP || !validC) {
            return;
        }

        // Determine login type
        let type = 'email'; // or 'phone'
        let email = null;
        let phone = null;

        if (form.username.includes('@')) {
            type = '2';
            email = form.username;
        } else {
            type = '1';
            phone = form.username;
        }

        loading.value = true
        const response = await login(type, phone, email, form.code || null, form.password);
        
        console.log('登录成功，响应数据:', response);

        if (response?.data?.code !== '0000') {
            showMessage(response?.data?.msg || '登录失败', response?.data?.info || 'error');
            return;
        }

        const userData = response.data.data;
        console.log('登录成功，用户数据:', userData);
        
        // Store user data in localStorage or state management
        if (form.remember) {
            localStorage.setItem('userId', userData.id);
            localStorage.setItem('userPhone', userData.phone || '');
            localStorage.setItem('userEmail', userData.email || '');
        } else {
            sessionStorage.setItem('userId', userData.id);
            sessionStorage.setItem('userPhone', userData.phone || '');
            sessionStorage.setItem('userEmail', userData.email || '');
        }
        setToken(userData.token || '');
        
        showMessage(`登录成功, 用户 id: ${userData.id}`, 'success');
        await router.push('/admin');
        close();

    } catch (error) {
        console.error('登录失败:', error);
        showMessage(error?.message || '登录失败', 'error');
    } finally {
        loading.value = false
    }
};

// 发送验证码
const sendCode = async () => {
    try {
        if (!validateUsername()) {
            return;
        }

        let type = '1';
        let email = null;
        let phone = null;

        if (form.username.includes('@')) {
            type = '2';
            email = form.username;
        } else {
            type = '1';
            phone = form.username;
        }

        await requestCode(type, phone, email);
        // Start countdown
        codeDisabled.value = true;
        countdown.value = 60;
        codeBtnText.value = `已发送(${countdown.value}s)`;
        codeTimer = setInterval(() => {
            countdown.value -= 1;
            codeBtnText.value = `已发送(${countdown.value}s)`;
            if (countdown.value <= 0) {
                clearInterval(codeTimer);
                codeTimer = null;
                codeDisabled.value = false;
                codeBtnText.value = '重新获取';
                countdown.value = 60;
            }
        }, 1000);
    } catch (error) {
        console.error('发送验证码失败:', error);
        alert('发送验证码失败，请稍后再试');
    }
};

// 校验函数
const validateUsername = () => {
    errors.username = '';
    if (!form.username) {
        errors.username = '请输入用户名或邮箱';
        return false;
    }
    if (form.username.includes('@')) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(form.username)) {
            errors.username = '请输入有效的邮箱地址';
            return false;
        }
    } else {
        const phoneRegex = /^1\d{10}$/;
        if (!phoneRegex.test(form.username)) {
            errors.username = '请输入有效的手机号（11位）';
            return false;
        }
    }
    return true;
};

const validatePassword = () => {
    errors.password = '';
    if (!form.password) {
        errors.password = '请输入密码';
        return false;
    }
    if (form.password.length < 6) {
        errors.password = '密码长度至少为6位';
        return false;
    }
    return true;
};

const validateCode = () => {
    errors.code = '';
    if (form.code) {
        const codeRegex = /^\d{6}$/;
        if (!codeRegex.test(form.code)) {
            errors.code = '验证码为6位数字';
            return false;
        }
    }
    return true;
};

// 获取当前登录用户信息
const getCurrentUser = () => {
    const userId = localStorage.getItem('userId') || sessionStorage.getItem('userId');
    const userPhone = localStorage.getItem('userPhone') || sessionStorage.getItem('userPhone');
    const userEmail = localStorage.getItem('userEmail') || sessionStorage.getItem('userEmail');
    
    if (userId) {
        return {
            id: userId,
            phone: userPhone,
            email: userEmail
        };
    }
    return null;
};

// 登出功能
const logout = () => {
    localStorage.removeItem('userId');
    localStorage.removeItem('userPhone');
    localStorage.removeItem('userEmail');
    sessionStorage.removeItem('userId');
    sessionStorage.removeItem('userPhone');
    sessionStorage.removeItem('userEmail');
    removeToken();
    alert('已登出');
    close();
};

</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.2s ease;
}
.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}
</style>