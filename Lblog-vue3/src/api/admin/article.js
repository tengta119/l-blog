import axios from "@/axios";

// 获取文章分页数据
export function getArticlePageList(data) {
    return axios.post("/article/list", data)
}

// 删除文章
export function deleteArticle(id) {
    return axios.post("/article/delete", id)
}

export function getCategorySelectList() {
    return axios.post("/category/select/list")
}

// 发布文章
export function publishArticle(data) {
    return axios.post("/article/publish", data)
}

// 获取文章详情
export function getArticleDetail(id) {
    return axios.post("/article/detail", id)
}

export function updateArticle(data) {
    return axios.post("/article/update", data)
}



