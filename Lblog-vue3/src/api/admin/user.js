import axios from "@/axios";

// 登录接口
export function login(type, phone = null, email = null, code = null, password) {
    return axios.post("/login/login", {type, phone, email, code, password})
}

export function updatePassword(type, phone, email, newPassword, verificationCode) {
    return axios.post("/login/updatePassword", {type, phone, email, newPassword, verificationCode})
}

// 微信登录：轮询登录状态
export function checkWxLogin(ticket) {
    return axios.post("/login/check", { ticket })
}

export function logout() {
    return axios.post("/login/logout")
}

export function requestCode(type, phone = null, email = null) {
    return axios.post("/code/send", {type, phone, email})
}

// 微信登录：获取票据
export function requestWxTicket() {
    return axios.post("/code/wxTicket")
}

export function requestUserInfo() {
    return axios.post("/user/info")
}





