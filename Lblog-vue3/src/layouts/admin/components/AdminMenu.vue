<template>
    <div class="bg-slate-800 h-full text-white menu-container transition-[width] duration-200 overflow-y-auto overflow-x-hidden" :style="{ width: menuStore.menuWidth }">
        <!-- 顶部 Logo, 指定高度为 64px, 和右边的 Header 头保持一样高 -->
        <div class="flex items-center justify-center h-[64px]">
            <img src="@/assets/lblog-logo.png" alt="logo" class="h-[64px]">
        </div>

	  <!-- 下方菜单 -->
        <el-menu :default-active="defaultActive" @select="handleSelect" :collapse="isCollapse" :collapse-transition="false">
            <template v-for="(item, index) in menus" :key="index">
                <el-menu-item :index="item.path">
                    <el-icon>
                        <!-- 动态图标（通过名称映射为组件） -->
                        <component :is="MenuIcons[item.icon]"></component>
                    </el-icon>
                    <span>{{ item.name }}</span>
                </el-menu-item>
            </template>
        </el-menu>
</div>
</template>

<script setup>
import { Monitor, Document, FolderOpened, PriceTag, Setting } from '@element-plus/icons-vue'
import { ref, computed  } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useMenuStore } from '@/stores/menu'

const route = useRoute()
const router = useRouter()
const menuStore = useMenuStore()
// 是否折叠
const isCollapse = computed(() =>  !(menuStore.menuWidth == '250px'))
// 根据路由地址判断哪个菜单被选中
const defaultActive = ref(route.path)

const handleSelect = (path) => {
    console.log(path)
    router.push(path)
}

// 图标映射，便于通过名称引用对应的组件
const MenuIcons = { Monitor, Document, FolderOpened, PriceTag, Setting }
const menus = [
    {
        'name': '仪表盘',
        'icon': 'Monitor',
        'path': '/admin/index'
    },
    {
        'name': '文章管理',
        'icon': 'Document',
        'path': '/admin/article/list',
    },
    {
        'name': '分类管理',
        'icon': 'FolderOpened',
        'path': '/admin/category/list',
    },
    {
        'name': '标签管理',
        'icon': 'PriceTag',
        'path': '/admin/tag/list',
    },
    {
        'name': '博客设置',
        'icon': 'Setting',
        'path': '/admin/blog/setting',
    },
]
</script>


<style>
.el-menu {
    background-color: rgb(30 41 59 / 1);
    border-right: 0;
    height: calc(100% - 64px);
    overflow-x: hidden; /* 防止横向滚动条在收缩时出现 */
}

.el-sub-menu__title {
    color: #fff;
}

.el-sub-menu__title:hover {
    background-color: #ffffff10;
}

.el-menu-item.is-active {
    background-color: var(--el-color-primary);
    color: #fff;
}

.el-menu-item.is-active:hover {
    background-color: var(--el-color-primary);
}

.el-menu-item {
    color: #fff;
}

.el-menu-item:hover {
    background-color: #ffffff10;
}

</style>
