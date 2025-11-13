<template>
     <!-- - 
      AdminTagList 本身是一个 fixed 元素，并设置了 z-50 （ src/layouts/admin/components/AdminTagList.vue:3 ）。当弹窗的遮罩层与它同级或更低层级时，标签栏会盖在遮罩上，因此不会变暗。
-     UpdatePasswordModal 最初被放在 AdminHeader 内部渲染，受其父级的层叠上下文影响，遮罩的 z-index 与标签栏竞争，导致标签栏不被遮罩覆盖。 -->
  <teleport to="body">
  <transition name="fade">
    <div v-if="show" class="fixed inset-0 z-[1000] flex items-center justify-center bg-gray-900 bg-opacity-50 p-4" @click.self="close">
      <div class="relative w-full max-w-md p-8 border border-gray-100 shadow-xl rounded-xl bg-white">
        <button @click="close" aria-label="关闭" class="absolute top-3 right-3 text-gray-500 hover:text-gray-700 focus:outline-none">✕</button>
        <div class="text-center px-4">
          <div class="mt-2 text-lg font-medium">修改密码</div>
          <form @submit.prevent="submit" class="mt-6 space-y-4">
            <div>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                  <svg class="w-4 h-4 text-gray-500" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20"><path d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 5a3 3 0 1 1 0 6 3 3 0 0 1 0-6Zm0 13a8.949 8.949 0 0 1-4.951-1.488A3.987 3.987 0 0 1 9 13h2a3.987 3.987 0 0 1 3.951 3.512A8.949 8.949 0 0 1 10 18Z"/></svg>
                </div>
                <input
                  type="text"
                  v-model="form.account"
                  maxlength="50"
                  placeholder="邮箱或手机号"
                  class="appearance-none bg-transparent border border-gray-300 rounded-lg w-full pl-10 pr-3 py-2 text-gray-700 leading-tight focus:outline-none focus:border-blue-500"
                  :class="{'border-red-500 focus:border-red-500': errors.account}"
                  @blur="validateAccount"
                  required
                >
              </div>
              <p v-if="errors.account" class="mt-1 text-xs text-red-600 text-left">{{ errors.account }}</p>
            </div>

            <div>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                  <svg class="w-4 h-4 text-gray-500" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20"><path d="M5 8a5 5 0 0 1 10 0v2h1a2 2 0 0 1 2 2v5a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2v-5a2 2 0 0 1 2-2h1V8Zm2 2h6V8a3 3 0 1 0-6 0v2Z"/></svg>
                </div>
                <input
                  type="password"
                  v-model="form.newPassword"
                  maxlength="50"
                  placeholder="新密码"
                  class="appearance-none bg-transparent border border-gray-300 rounded-lg w-full pl-10 pr-3 py-2 text-gray-700 leading-tight focus:outline-none focus:border-blue-500"
                  :class="{'border-red-500 focus:border-red-500': errors.newPassword}"
                  @blur="validateNewPassword"
                  required
                >
              </div>
              <p v-if="errors.newPassword" class="mt-1 text-xs text-red-600 text-left">{{ errors.newPassword }}</p>
            </div>

            <div>
              <div class="flex items-center gap-2">
                <div class="relative flex-1">
                  <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                    <svg class="w-4 h-4 text-gray-500" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20"><path d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 3.5a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3Zm0 12.5a6.986 6.986 0 0 1-4-1.25c.05-1.65 3-2.55 4-2.55s3.95.9 4 2.55A6.986 6.986 0 0 1 10 16Z"/></svg>
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

            <div class="mt-4">
              <button
                @click="submit"
                class="w-full bg-green-500 hover:bg-green-600 text-white font-medium py-2.5 px-4 rounded-lg focus:outline-none focus:shadow-outline disabled:opacity-50 disabled:cursor-not-allowed"
                :disabled="loading"
              >
                <span v-if="!loading">确定修改</span>
                <span v-else class="inline-flex items-center justify-center gap-2">
                  <svg class="w-4 h-4 animate-spin" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"><circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"/><path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v4a4 4 0 00-4 4H4z"/></svg>
                  提交中...
                </span>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </transition>
  </teleport>
</template>

<script setup>
import { ref, reactive, onBeforeUnmount } from 'vue'
import { showMessage } from '@/composables/util'
import { updatePassword, requestCode } from '@/api/admin/user.js'

const show = ref(false)
const loading = ref(false)
const form = reactive({ account: '', newPassword: '', code: '' })
const errors = reactive({ account: '', newPassword: '', code: '' })

const codeBtnText = ref('获取验证码')
const codeDisabled = ref(false)
let codeTimer = null
const countdown = ref(60)

const emit = defineEmits(['close'])

const open = () => { show.value = true }
const close = () => {
  show.value = false
  emit('close')
  errors.account = ''
  errors.newPassword = ''
  errors.code = ''
  if (codeTimer) { clearInterval(codeTimer); codeTimer = null }
  codeDisabled.value = false
  codeBtnText.value = '获取验证码'
  countdown.value = 60
}

defineExpose({ open })

const validateAccount = () => {
  errors.account = ''
  if (!form.account) { errors.account = '请输入邮箱或手机号'; return false }
  if (form.account.includes('@')) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    if (!emailRegex.test(form.account)) { errors.account = '请输入有效的邮箱地址'; return false }
  } else {
    const phoneRegex = /^1\d{10}$/
    if (!phoneRegex.test(form.account)) { errors.account = '请输入有效的手机号（11位）'; return false }
  }
  return true
}

const validateNewPassword = () => {
  errors.newPassword = ''
  if (!form.newPassword) { errors.newPassword = '请输入新密码'; return false }
  if (form.newPassword.length < 6) { errors.newPassword = '密码长度至少为6位'; return false }
  return true
}

const validateCode = () => {
  errors.code = ''
  if (!form.code) { errors.code = '请输入验证码'; return false }
  const codeRegex = /^\d{6}$/
  if (!codeRegex.test(form.code)) { errors.code = '验证码为6位数字'; return false }
  return true
}

const sendCode = async () => {
  try {
    const ok = validateAccount()
    if (!ok) return
    let type = '1'
    let phone = null
    let email = null
    if (form.account.includes('@')) { type = '2'; email = form.account } else { type = '1'; phone = form.account }
    codeDisabled.value = true
    codeBtnText.value = '发送中...'
    const resp = await requestCode(type, phone, email)
    const data = resp?.data
    if (!data || data.code !== '0000') { showMessage(data?.msg || '发送验证码失败', data?.info || 'error'); codeDisabled.value = false; codeBtnText.value = '获取验证码'; return }
    showMessage('验证码已发送', 'success')
    countdown.value = 60
    codeBtnText.value = `重新获取(${countdown.value}s)`
    codeTimer = setInterval(() => {
      countdown.value -= 1
      if (countdown.value <= 0) { clearInterval(codeTimer); codeTimer = null; codeDisabled.value = false; codeBtnText.value = '获取验证码'; return }
      codeBtnText.value = `重新获取(${countdown.value}s)`
    }, 1000)
  } catch (e) {
    codeDisabled.value = false
    codeBtnText.value = '获取验证码'
    showMessage(e?.message || '发送验证码失败', 'error')
  }
}

const submit = async () => {
  try {
    const okA = validateAccount()
    const okP = validateNewPassword()
    const okC = validateCode()
    if (!okA || !okP || !okC) return
    let type = '1'
    let phone = null
    let email = null
    if (form.account.includes('@')) { type = '2'; email = form.account } else { type = '1'; phone = form.account }
    loading.value = true
    const resp = await updatePassword(type, phone, email, form.newPassword, form.code)
    const data = resp?.data
    if (!data || data.code !== '0000') { showMessage(data?.msg || '修改失败', data?.info || 'error'); return }
    showMessage('密码修改成功', 'success')
    close()
  } catch (e) {
    showMessage(e?.message || '修改失败', 'error')
  } finally {
    loading.value = false
  }
}

onBeforeUnmount(() => { if (codeTimer) { clearInterval(codeTimer); codeTimer = null } })
</script>

<style scoped>
.fade-enter-active,.fade-leave-active{transition:opacity .2s ease}
.fade-enter-from,.fade-leave-to{opacity:0}
</style>

