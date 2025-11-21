import axios from "@/axios";

// 增加文章分类
export function addTag(data) {
    return axios.post('/admin/tag/add', data)
}

// 获取文章分类列表
export function getTagPageList(data) {
    return axios.post('/admin/tag/list', data)
}

// 删除文章分类
export function deleteTag(data) {
    return axios.post('/admin/tag/delete', data)
}

// 获取文章分类下拉列表
export function getTagSelect() {
    return axios.post('/admin/tag/select/list')
}

// 根据标签名模糊查询
export function searchTags(key) {
    return axios.post("/admin/tag/select/list", key)
}

