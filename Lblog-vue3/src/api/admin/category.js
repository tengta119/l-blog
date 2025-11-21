import axios from "@/axios";

// 增加文章分类
export function addCategory(data) {
    return axios.post('/admin/category/add', data)
}

// 获取文章分类列表
export function getCategoryPageList(data) {
    return axios.post('/admin/category/list', data)
}

// 删除文章分类
export function deleteCategory(data) {
    return axios.post('/admin/category/delete', data)
}

// 获取文章分类下拉列表
export function getCategorySelect() {
    return axios.post('/admin/category/select/list')
}