<template>
    <div class="w-full py-5 px-2 mb-3 bg-white border border-gray-200 rounded-lg dark:bg-gray-800 dark:border-gray-700">
        <div class="flex flex-col items-center">
            <!-- 博主头像 -->
            <img class="w-14 h-14 mb-3 rounded-full shadow"
                :src="blogSettingsStore.blogSettings.avatar"/>
            <!-- 博主昵称 -->
            <h5 class="mb-1 text-xl font-medium text-gray-900 dark:text-white">{{ blogSettingsStore.blogSettings.author }}</h5>
            <!-- 介绍语 -->
            <span class="text-sm text-gray-500 dark:text-gray-400">{{ blogSettingsStore.blogSettings.introduction }}</span>
            
        </div>
    </div>
</template>

<script setup>
import { useBlogSettingsStore } from '@/stores/blogsettings'
import { initTooltips } from 'flowbite'
import { onMounted } from 'vue'

// 初始化 Flowbit 组件
onMounted(() => {
    initTooltips();
})

// 引入博客设置信息 store
const blogSettingsStore = useBlogSettingsStore()
</script>

