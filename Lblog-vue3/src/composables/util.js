import 'element-plus/es/components/message/style/css'
import nprogress from "nprogress"
import 'nprogress/nprogress.css'
// 消息提示
export function showMessage(message = '提示内容', type = 'success') {
    return ElMessage({
        message,
        type,
    })
}

// 显示页面加载 Loading
export function showPageLoading() {
    nprogress.start()
}

// 隐藏页面加载 Loading
export function hidePageLoading() {
    nprogress.done()
}
