<template>
  <div class="bg-white h-full border border-gray-200 rounded-lg dark:bg-gray-800 dark:border-gray-700">
    <a @click="goArticleDetailPage(article.id)" class="cursor-pointer block">
      <div class="w-full h-48 md:h-56 lg:h-64 overflow-hidden rounded-t-lg bg-gray-100 dark:bg-gray-700">
        <img class="w-full h-full object-cover" :src="article.cover" loading="lazy" />
      </div>
    </a>
    <div class="p-5">
      <div class="mb-3">
        <span
          v-for="(tag, tagIndex) in article.tags"
          :key="tagIndex"
          class="cursor-pointer bg-green-100 text-green-800 text-xs font-medium mr-2 px-2.5 py-0.5 rounded hover:bg-green-200 hover:text-green-900 dark:bg-green-900 dark:text-green-300"
        >
          {{ tag.name }}
        </span>
      </div>
      <a @click="goArticleDetailPage(article.id)" class="cursor-pointer">
        <h2 class="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
          {{ article.title }}
        </h2>
      </a>
      <p v-if="article.summary" class="mb-3 font-normal text-gray-500 dark:text-gray-400">
        {{ article.summary }}
      </p>
      <p class="flex items-center font-normal text-gray-400 text-sm dark:text-gray-400">
        <svg class="inline w-3 h-3 mr-2 text-gray-400 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
            d="M5 1v3m5-3v3m5-3v3M1 7h18M5 11h10M2 3h16a1 1 0 0 1 1 1v14a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1Z" />
        </svg>
        {{ article.createTime.substring(0, 10) }}
        <svg class="inline w-3 h-3 ml-5 mr-2 text-gray-400 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 18 18">
          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
            d="M1 5v11a1 1 0 0 0 1 1h14a1 1 0 0 0 1-1V6a1 1 0 0 0-1-1H1Zm0 0V2a1 1 0 0 1 1-1h5.443a1 1 0 0 1 .8.4l2.7 3.6H1Z" />
        </svg>
        <a
          @click="goCategoryArticleListPage(article.category.id, article.category.name)"
          class="cursor-pointer text-gray-400 hover:underline"
        >
          {{ article.category.name }}
        </a>
      </p>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const props = defineProps({
  article: { type: Object, required: true }
})

const router = useRouter()

const goCategoryArticleListPage = (id, name) => {
  router.push({ path: '/category/article/list', query: { id, name } })
}

const goArticleDetailPage = (articleId) => {
  router.push('/article/' + articleId)
}
</script>