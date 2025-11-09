import axios from "@/axios";

// 登录接口
export function login(type, phone = null, email = null, code = null, password) {
    return axios.post("/login", {type, phone, email, code, password})
}

export function requestCode(type, phone = null, email = null) {
    return axios.post("/code/send", {type, phone, email})
}

// 微信登录：获取票据
export function requestWxTicket() {
    return axios.post("/code/wxTicket")
}

// 微信登录：轮询登录状态
export function checkWxLogin(ticket) {
    return axios.post("/login/check", { ticket })
}



