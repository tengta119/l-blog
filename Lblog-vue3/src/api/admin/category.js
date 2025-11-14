import axios from "@/axios";

// 增加文章分类
export function addCategory(name) {
    return axios.post('/category/add', name)
}

// 获取文章分类列表
export function getCategoryPageList(data) {
    return axios.post('/category/list', data)
}

// 删除文章分类
export function deleteCategory(id) {
    return axios.post('/category/delete', id)
}

// 获取文章分类下拉列表
export function getCategorySelect() {
    return axios.post('/category/select/list')
}