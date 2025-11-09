import axios from "@/axios";

// 登录接口
export function login(type, phone = null, email = null, code = null, password) {
    return axios.post("/login", {type, phone, email, code, password})
}

export function requestCode(type, phone = null, email = null) {
    return axios.post("/code/send", {type, phone, email})
}
