import axios from "@/axios";

export function getBlogSettings() {
    return axios.post('/settings/find')
}

export function updateBlogSettings(data) {
    return axios.post('/settings/settings', data)
}