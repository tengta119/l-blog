import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getUserInfo } from '@/api/admin/user'

export const useUserStore = defineStore('user', () => {
  // 用户信息（初始化为空对象，避免访问属性时报错）
  const userInfo = ref({})

  // 设置用户信息（异步函数，处理API请求）
  async function setUserInfo() {
    try {
      // 等待API请求完成（getUserInfo返回Promise，需用await获取结果）
      const res = await getUserInfo() 
      console.log('getUserInfo res:', res) // 用逗号分隔，避免对象被转为字符串

      // 假设后端返回格式为 { code: '0000', data: { nickname: 'xxx', ... } }
      if (res.data.code == '0000') { 
        userInfo.value = res.data.data // 将后端返回的data赋值给userInfo
        console.log('userInfo nickname:', userInfo.value.nickname) // 正确访问路径
      } else {
        console.error('获取用户信息失败：', res.data.info || '未知错误')
      }
    } catch (error) {
      // 捕获请求异常（如网络错误、后端500等）
      console.error('请求用户信息出错：', error)
    }
  }

  return { userInfo, setUserInfo }
}, {
  persist: true,
})
