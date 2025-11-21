import axios from "@/axios";

export function getBlogSettings() {
    return axios.post('admin/settings/find')
}

export function updateBlogSettings(data) {
    return axios.post('admin/settings/settings', data)
}