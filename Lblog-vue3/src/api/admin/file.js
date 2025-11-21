import axios from "@/axios";

export function uploadFile(data) {
    return axios.post('/admin/oss/upload', data)
}