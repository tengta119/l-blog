<template>
    <Header></Header>

    <!-- 主内容区域 -->
    <main class="container max-w-screen-xl mx-auto p-4">
        <!-- grid 表格布局，分为 4 列 -->
        <div class="grid grid-cols-4 gap-7">
            <!-- 左边栏，占用 3 列 -->
            <div class="col-span-4 md:col-span-3 mb-3">
                <!-- 文章列表，瀑布流布局，自动换列 -->
                <div class="columns-1 md:columns-2 lg:columns-2 gap-4">
                    <div v-for="(article, index) in articles" :key="index" class="break-inside-avoid mb-4">
                        <ArticleCard :article="article" />
                    </div>
                </div>
                <!-- 分页 -->
                <div ref="infiniteScrollSentinel" class="mt-4 h-4"></div>
            </div>


            <!-- 右边侧边栏，占用一列 -->
            <aside class="col-span-4 md:col-span-1">
                <!-- 博主信息 -->
                <UserInfoCard></UserInfoCard>

                <!-- 分类 -->
                <CategoryListCard></CategoryListCard>

                <!-- 标签 -->
                <TagListCard></TagListCard>

        </aside>
    </div>

    </main>

    <Footer></Footer>
</template>

<script setup>
import Header from '@/layouts/frontend/components/Header.vue'
import Footer from '@/layouts/frontend/components/Footer.vue'
import { initTooltips } from 'flowbite'
import { onMounted, onUnmounted, ref } from 'vue'
import { getArticlePageList } from '@/api/frontend/article'
import UserInfoCard from '@/layouts/frontend/components/UserInfoCard.vue'
import CategoryListCard from '@/layouts/frontend/components/CategoryListCard.vue'
import TagListCard from '@/layouts/frontend/components/TagListCard.vue'
import ArticleCard from '@/layouts/frontend/components/ArticleCard.vue'
import { useRouter } from 'vue-router'
// 引入路由
const router = useRouter()

// initialize components based on data attribute selectors
onMounted(() => {
    initTooltips();
    const rootMargin = '200px';
    const io = new IntersectionObserver((entries) => {
        if (entries.some(e => e.isIntersecting)) {
            loadNextPage();
        }
    }, { root: null, rootMargin, threshold: 0 });
    observer.value = io;
})

onUnmounted(() => {
    if (observer.value) {
        observer.value.disconnect();
    }
})

// 文章集合
const articles = ref([])
// 当前页码
const current = ref(1)
// 每页显示的文章数
const size = ref(10)
// 总文章数
const total = ref(0)
// 总共多少页
const pages = ref(0)
const infiniteScrollSentinel = ref(null)
const loading = ref(false)
const observer = ref(null)
const initialized = ref(false)


function getArticles(currentNo, append = false) {
    if (currentNo < 1 || (pages.value > 0 && currentNo > pages.value)) return
    loading.value = true
    getArticlePageList({ current: currentNo, size: size.value }).then((res) => {
        const data = res.data
        if (data.code == '0000') {
            if (append) {
                articles.value = articles.value.concat(data.data)
            } else {
                articles.value = data.data
            }
            current.value = data.current
            size.value = data.size
            total.value = data.total
            pages.value = data.pages
            if (!append) initialized.value = true
            if (!append && observer.value && infiniteScrollSentinel.value) {
                observer.value.observe(infiniteScrollSentinel.value)
            }
        }
    }).finally(() => {
        loading.value = false
    })
}
getArticles(current.value)
function loadNextPage() {
    if (loading.value) return
    if (!initialized.value) return
    const next = current.value + 1
    if (pages.value > 0 && next > pages.value) return
    getArticles(next, true)
}

// 跳转分类文章列表页
const goCategoryArticleListPage = (id, name) => {
    // 跳转时通过 query 携带参数（分类 ID、分类名称）
    router.push({path: '/category/article/list', query: {id, name}})
}

// 跳转文章详情页
const goArticleDetailPage = (articleId) => {
    router.push('/article/' + articleId)
}
</script>
