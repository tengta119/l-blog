import axios from "@/axios";

export function getBlogSettings() {
    return axios.post('/settings/find')
}