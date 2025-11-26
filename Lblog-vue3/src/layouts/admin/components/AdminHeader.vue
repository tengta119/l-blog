<template>
    <!-- 设置背景色为白色、高度为 64px，padding-right 为 4， border-bottom 为 slate 200 -->
    <div class="bg-white h-[64px] flex pr-4 border-b border-slate-100">
		<!-- 左边栏收缩、展开 -->
        <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gray-700 hover:bg-gray-200" @click="handleMenuWidth">
            <el-icon>
                <Fold v-if="menuStore.menuWidth == '250px'"/>
                <Expand v-else />
            </el-icon>
        </div>

        <!-- 右边容器 -->
        <div class="ml-auto flex">

			<!-- 点击刷新页面 -->
            <el-tooltip class="box-item" effect="dark" content="刷新" placement="bottom">
                <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gray-700 hover:bg-gray-200"
                    @click="handleRefresh">
                    <el-icon>
                        <Refresh />
                    </el-icon>
                </div>
            </el-tooltip>

            <!-- 点击跳转前台首页 -->
            <el-tooltip class="box-item" effect="dark" content="跳转前台" placement="bottom">
                <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gray-700 hover:bg-gray-200"
                    @click="router.push('/')">
                    <el-icon>
                        <Monitor />
                    </el-icon>
                </div>
            </el-tooltip>

			<!-- 点击全屏展示 -->
            <el-tooltip class="box-item" effect="dark" content="全屏" placement="bottom">
                <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gray-700 mr-2 hover:bg-gray-200" @click="toggle">
                    <el-icon>
                        <FullScreen v-if="!isFullscreen"/>
                        <Aim v-else/>
                    </el-icon>
                </div>
            </el-tooltip>

            <!-- 登录用户头像 -->
            <el-dropdown class="flex items-center justify-center">
                <span class="el-dropdown-link flex items-center justify-center text-gray-700 text-xs">
                    <!-- 头像 Avatar -->
                    <el-avatar class="mr-2" :size="25"
                        src="https://img.quanxiaoha.com/quanxiaoha/f97361c0429d4bb1bc276ab835843065.jpg" />
                    {{ userStore.userInfo.author }}
                    <el-icon class="el-icon--right">
                        <ArrowDown />
                    </el-icon>
                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item @click="openUpdatePasswordModal">修改密码</el-dropdown-item>
                        <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </div>
    <!-- 引入更新密码弹窗组件 -->
    <UpdatePasswordModal ref="updatePasswordModalRef" />
</template>

<script setup > 
import { Fold, FullScreen, ArrowDown, Expand, Aim, Refresh, Monitor } from '@element-plus/icons-vue'
import { useMenuStore } from '@/stores/menu'
import { useFullscreen } from '@vueuse/core'
import router from '@/router'
import { showMessage} from '@/composables/util'
import { removeToken } from '@/composables/auth'
import { logout } from '@/api/admin/user'
import { useUserStore } from '@/stores/user'
import {ref} from 'vue'
import UpdatePasswordModal from '@/components/UpdatePasswordModal.vue'
const updatePasswordModalRef = ref(null)

const openUpdatePasswordModal = () => {
    updatePasswordModalRef.value.open()
}

// 引入了用户 Store
const userStore = useUserStore()
console.log('userStore.userInfo:' + userStore.userInfo.value)
// 引入了菜单 store
const menuStore = useMenuStore()
const { isFullscreen, toggle } = useFullscreen()
const handleRefresh = () => location.reload()
// icon 点击事件
const handleMenuWidth = () => {
    // 动态设置菜单的宽度大小
    menuStore.handleMenuWidth()
}
// 退出登录
const handleLogout = async () => {
    await logout()
    removeToken();
    userStore.logout()
    showMessage('已登出', 'success');
    router.push('/')
}


</script>