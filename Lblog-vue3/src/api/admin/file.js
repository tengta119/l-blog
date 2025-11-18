import axios from "@/axios";

export function uploadFile(data) {
    return axios.post('/oss/upload', data)
}