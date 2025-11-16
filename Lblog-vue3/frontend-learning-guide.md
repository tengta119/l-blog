# Lblog Vue3 项目学习指南

本指南面向前端初学者，结合本项目的真实代码，系统梳理需要掌握的知识点，并提供实践建议与代码索引，帮助你快速上手并形成完整认知。

## 项目概览

- 技术栈：`Vue 3`、`Pinia`、`Vue Router`、`Element Plus`、`TailwindCSS`、`Flowbite`、`Axios`、`Vite`
- 包管理脚本：
  - 开发：`npm run dev`
  - 构建：`npm run build`
  - 预览：`npm run preview`
- 入口与挂载：`src/main.js` 创建应用，注册 `router` 与 `pinia` 并挂载到 `#app`

## 目录结构与模块职责

- `src/main.js`：应用入口，安装路由和状态管理
- `src/router/index.js`：路由表与路由实例创建，含后台子路由与页面标题设置
- `src/permission.js`：全局路由守卫，基于 Token 做后台访问拦截（src/permission.js:6）
- `src/stores/`：Pinia 状态库（用户、菜单等）
- `src/layouts/admin/`：后台布局（侧边栏、头部、标签栏、主内容）
- `src/page/admin/`：后台具体页面（文章、分类、标签等）
- `src/components/`：通用组件（登录弹窗、修改密码弹窗等）
- `src/api/`：后端接口封装（基于同一 `axios` 实例）
- `src/axios.js`：全局 Axios 实例与拦截器（请求头注入、错误提示）（src/axios.js:1, src/axios.js:36）
- `src/composables/`：可复用的工具（消息提示、Cookie 操作、标签页数据、Tab 逻辑等）

## Vue 3 与 Composition API

- 核心概念：`ref`、`reactive`、`computed`、`watch`、`onMounted`、`onBeforeUnmount`、`defineEmits`、`defineExpose`
- 示例：在组件内使用 `ref/Reactive` 管理表单与状态（如登录、分类列表）
- KeepAlive：缓存路由组件，提升页面切换体验（src/layouts/admin/admin.vue:18）

## Pinia 状态管理

- 创建 Store：`defineStore`（src/stores/menu.js:3, src/stores/user.js:5）
- 使用 Store：在组件中 `useXxxStore()` 获取并读写状态
- 异步更新：Store 内部操作接口需 `async/await`，避免同步处理 Promise
  - 参考：`setUserInfo` 正确的异步实现（src/stores/user.js:9）
- 持久化：项目中对 `menu`、`user` 等使用了持久化选项，刷新后仍保留状态（src/stores/menu.js:14, src/stores/user.js:29）

## 路由与守卫

- 路由声明：后台使用嵌套路由，将业务页作为 `Admin` 的子路由（src/router/index.js:18）
- 守卫：拦截未登录用户访问后台，重定向到首页（src/permission.js:6）
- 页面标题：在路由后置守卫动态设置 `document.title`（src/permission.js:23）

## Axios 使用与拦截器

- 实例：在 `src/axios.js` 创建统一实例并配置 `baseURL/timeout`（src/axios.js:6）
- 请求拦截：自动注入 `Authorization` 头（src/axios.js:12）
- 响应拦截：统一返回 `response`，错误情况弹出提示（src/axios.js:36）
- 约定：接口统一返回 `{ code, data, msg, info }`，前端以 `code === '0000'` 判断成功
- 解析 JSON 字符串：若后端返回纯字符串（且 `Content-Type` 不正确），需手动 `JSON.parse(resp.data)`

## 认证与 Token

- Cookie 管理：通过 `@vueuse/integrations/useCookies` 存取 `Authorization`（src/composables/auth.js:6）
- 登录流程：调用登录接口、存储用户 ID 与 Token、拉取用户信息、跳转后台（src/components/LoginModal.vue）
- 用户信息：登录后调用 `getUserInfo` 更新 `userStore`（src/stores/user.js:9）

## UI 组件库与样式

- Element Plus：表格、分页、弹窗、图标等（如分类列表、后台布局）
- TailwindCSS：快速编写样式类（如弹窗容器、布局、颜色）
- Flowbite：部分导航与交互样式（前台导航，src/page/frontend/index.vue）

## 典型业务场景与实践

### 登录弹窗（LoginModal）

- 表单与校验：`ref/reactive` 管理输入与错误，回车提交、验证码发送倒计时（src/components/LoginModal.vue）
- 微信扫码：票据获取与轮询登录状态（src/components/LoginModal.vue）
- 成功后：存储 Token 与用户 ID，提示、跳转后台、关闭弹窗

### 修改密码弹窗（UpdatePasswordModal）

- 账号类型判断与接口调用：邮箱 `type='2'`，手机号 `type='1'`（src/components/UpdatePasswordModal.vue）
- 发送验证码与倒计时、表单校验、提交更新密码
- Teleport 与遮罩层级：弹窗通过 `teleport` 到 `body`，提升遮罩层级，避免被固定标签栏覆盖（src/components/UpdatePasswordModal.vue:1）

### 后台布局（Admin）

- 侧边栏菜单：路由驱动的菜单，折叠状态由 `menuStore` 控制（src/layouts/admin/components/AdminMenu.vue）
- 顶部头部：刷新、全屏、用户下拉、打开修改密码弹窗（src/layouts/admin/components/AdminHeader.vue）
- 标签导航：`fixed` 标签栏与标签页管理（src/layouts/admin/components/AdminTagList.vue:3, src/composables/useTagList.js）

### 分类列表分页（Category List）

- 分页组件：`el-pagination` 双向绑定当前页与页大小，分别处理 `@current-change/@size-change`（src/page/admin/category-list.vue:47, src/page/admin/category-list.vue:151）
- 请求参数：将 `current/size` 与查询条件传给后端（src/page/admin/category-list.vue:173）
- 响应处理：更新 `tableData` 与 `total`，避免用后端返回覆盖前端页码（src/page/admin/category-list.vue:188）

### 日期格式化

- 去除 ISO 时间中的 `T`：在列插槽中 `String(row.createTime).replace('T', ' ')`（src/page/admin/category-list.vue:37）
- 统一格式：可使用 `Date` 对象格式化为 `YYYY-MM-DD HH:mm:ss`（示例参见答复）

## 组件通信与事件

- 自定义事件：`defineEmits` 触发父组件行为（弹窗关闭等）
- 暴露方法：`defineExpose` 让父组件拿到子组件的 `open()`（src/components/LoginModal.vue, src/layouts/admin/components/AdminHeader.vue:52）
- `ref` 获取子组件实例：在父组件中 `ref` 引用并调用（src/layouts/admin/components/AdminHeader.vue:52）

## 常见坑与排查

- 异步请求必须 `await`：接口函数返回 `Promise`，直接访问会得到 `undefined` 或对象引用（src/stores/user.js:9）
- 正确读取 Axios 响应：拦截器返回 `response`，数据在 `resp.data`（src/axios.js:36）
- `.value` 的使用：`ref` 的实际值在 `.value` 上，读写路径要正确（如 `userInfo.value.nickname`）
- 层叠上下文与遮罩：`fixed` 元素加高 `z-index` 会覆盖遮罩，需 Teleport 到 `body` 并提升层级（src/layouts/admin/components/AdminTagList.vue:3, src/components/UpdatePasswordModal.vue:1）
- 分页事件：`@current-change` 需同步页码再请求，否则可能一直停留在第一页（src/page/admin/category-list.vue:151）
- Content-Type 与 JSON 解析：确保后端设置 `application/json`，否则前端需手动 `JSON.parse`

## 学习路线与练习清单

1. 环境与工具
   - 熟悉 `Vite` 项目结构与脚本，运行与构建
   - 阅读 `package.json` 依赖与版本

2. Vue 3 基础
   - 掌握 `ref/reactive`、生命周期、模板语法、`v-model`、插槽
   - 在简单组件中实现表单交互与校验

3. 路由与守卫
   - 添加一个新后台页面与路由，设置 `meta.title`
   - 在守卫中基于 Token 控制访问，验证跳转逻辑（src/permission.js:6）

4. Pinia 状态
   - 新建一个 Store（比如通知条数），在多个组件间共享并更新
   - 在 Store 中编写异步方法与错误处理

5. Axios 与接口
   - 在 `src/api/xxx.js` 中新增接口函数并接入全局实例
   - 实现加载状态、错误提示与成功反馈

6. UI 组件与样式
   - 使用 Element Plus 表格与分页构建列表
   - 用 Tailwind 快速布局卡片与弹窗

7. 复杂交互
   - 完成验证码倒计时、弹窗打开关闭、Teleport 到 `body`
   - 实现 Tab 标签页增删与切换（src/composables/useTagList.js）

8. 工程实践
   - 统一日志与错误处理；避免在生产环境输出过多日志
   - 统一时间格式与国际化准备（如后续引入 dayjs/i18n）

## 参考代码索引

- Axios 实例与拦截器：`src/axios.js:1`, `src/axios.js:12`, `src/axios.js:36`
- 路由守卫：`src/permission.js:6`, `src/permission.js:23`
- 用户 Store 异步获取：`src/stores/user.js:9`
- 菜单 Store 持久化：`src/stores/menu.js:14`
- 修改密码弹窗 Teleport：`src/components/UpdatePasswordModal.vue:1`
- 标签导航层级与样式：`src/layouts/admin/components/AdminTagList.vue:3`
- 分类列表分页：`src/page/admin/category-list.vue:47`, `src/page/admin/category-list.vue:151`, `src/page/admin/category-list.vue:188`
- 用户接口封装：`src/api/admin/user.js`

## 结语

学习的关键是“照着项目动手做”，建议从一个页面入手，完成“接口对接 → 状态管理 → 组件交互 → 样式完善”的闭环，并在过程中记录遇到的问题与解决方案。随着练习的积累，你会逐步熟悉现代前端开发的完整流程与工程化能力。