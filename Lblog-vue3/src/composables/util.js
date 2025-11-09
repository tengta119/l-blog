import 'element-plus/es/components/message/style/css'

// 消息提示
export function showMessage(message = '提示内容', type = 'success') {
    return ElMessage({
        message,
        type,
    })
}
