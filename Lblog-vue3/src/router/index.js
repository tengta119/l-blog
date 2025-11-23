import Index from '@/page/frontend/index.vue'
import AdminIndex from '@/page/admin/index.vue'
import AdminArticleList from '@/page/admin/article-list.vue'
import AdminCategoryList from '@/page/admin/category-list.vue'
import AdminTagList from '@/page/admin/tag-list.vue'
import AdminBlogSetting from '@/page/admin/blog-setting.vue'
import { createRouter, createWebHashHistory } from 'vue-router'
import Admin from '@/layouts/admin/admin.vue'
import ArchiveList from '@/page/frontend/archive-list.vue'
// 统一在这里声明所有路由
const routes = [
    {
        path: '/', // 路由地址
        component: Index, // 对应组件
        meta: { // meta 信息
            title: 'Lblog 首页' // 页面标题
        }
    },
    {
        path: '/archive/list', // 归档页
        component: ArchiveList,
        meta: { // meta 信息
            title: 'Weblog 归档页'
        }
    },
    {
        path: "/admin", // 后台首页
        component: Admin, // 对应 admin.vue 布局文件
        meta: {
            title: 'Admin 后台首页'
        },
        // 使用到 admin.vue 布局的，都需要放置在其子路由下面
        children: [
            {
                path: "/admin/index",
                component: AdminIndex,
                meta: {
                    title: '仪表盘'
                }
            },
            {
                path: "/admin/article/list",
                component: AdminArticleList,
                meta: {
                    title: '文章管理'
                }
            },
            {
                path: "/admin/category/list",
                component: AdminCategoryList,
                meta: {
                    title: '分类管理'
                }
            },
            {
                path: "/admin/tag/list",
                component: AdminTagList,
                meta: {
                    title: '标签管理'
                }
            },
            {
                path: "/admin/blog/setting",
                component: AdminBlogSetting,
                meta: {
                    title: '博客设置'
                }
            }
        ]
    }
]

// 创建路由
const router = createRouter({
    // 指定路由的历史管理方式，hash 模式指的是 URL 的路径是通过 hash 符号（#）进行标识
    history: createWebHashHistory(),
    // routes: routes 的缩写
    routes, 
})

// ES6 模块导出语句，它用于将 router 对象导出，以便其他文件可以导入和使用这个对象
export default router
