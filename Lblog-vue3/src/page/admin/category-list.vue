<template>
    <div>
        <!-- 表头分页查询条件， shadow="never" 指定 card 卡片组件没有阴影 -->
        <el-card shadow="never" class="mb-5">
            <!-- flex 布局，内容垂直居中 -->
            <div class="flex items-center">
                <el-text>分类名称</el-text>
                <div class="ml-3 w-52 mr-5"><el-input v-model="searchCategoryName" placeholder="请输入（模糊查询）" /></div>

                <el-text>创建日期</el-text>
                <div class="ml-3 w-30 mr-5">
                    <!-- 日期选择组件（区间选择） -->
                    <el-date-picker v-model="pickDate" type="daterange" range-separator="至" start-placeholder="开始时间"
                        end-placeholder="结束时间" size="default" :shortcuts="shortcuts" @change="datepickerChange" />
                </div>

                <el-button type="primary" class="ml-3" :icon="Search" @click="getTableData">查询</el-button>
                <el-button class="ml-3" :icon="RefreshRight" @click="reset">重置</el-button>
            </div>
        </el-card>

        <el-card shadow="never">
            <!-- 新增按钮 -->
            <div class="mb-5">
                <el-button type="primary" @click="addCategoryBtnClick">
                    <el-icon class="mr-1">
                        <Plus />
                    </el-icon>
                    新增</el-button>
            </div>

            <!-- 分页列表 -->
            <el-table :data="tableData" border stripe style="width: 100%" v-loading="tableLoading">
                <el-table-column prop="name" label="分类名称" width="180" />
                <el-table-column prop="createTime" label="创建时间" width="180">
                    <template #default="{ row }">
                        {{ String(row.createTime).replace('T', ' ') }}
                    </template>
                </el-table-column>
                <el-table-column label="操作" >
                    <template #default="scope">
                        <el-button type="danger" size="small" @click="deleteCategorySubmit(scope.row)">
                            <el-icon class="mr-1">
                                <Delete />
                            </el-icon>
                            删除
                        </el-button>
                	</template>
                </el-table-column>
            </el-table>

            <div class="mt-10 flex justify-center">
                <el-pagination v-model:current-page="current" v-model:page-size="size" :page-sizes="[10, 20, 50]"
                :small="false" :background="true" layout="total, sizes, prev, pager, next, jumper"
                :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"  />
            </div>

        </el-card>

    </div>

    <FormDialog ref="formDialogRef" title="添加文章分类" destroyOnClose @submit="onSubmit">
        <el-form ref="formRef" :rules="rules" :model="form">
                    <el-form-item label="分类名称" prop="name" label-width="80px" size="large">
                        <el-input v-model="form.name" placeholder="请输入分类名称" maxlength="20" show-word-limit clearable/>
                    </el-form-item>
                </el-form>
    </FormDialog>

</template>



<script setup>
// 引入所需图标
import { Search, RefreshRight, Plus, Delete } from '@element-plus/icons-vue'
import { ref, reactive } from 'vue'
import moment from 'moment'
import { getCategoryPageList, addCategory, deleteCategory } from '@/api/admin/category'
// 分页查询的分类名称
const searchCategoryName = ref('')
import FormDialog from '@/components/FormDialog.vue'
// 日期
const pickDate = ref('')
import { showMessage, showModel  } from '@/composables/util'
// 查询条件：开始结束时间
const startDate = reactive({})
const endDate = reactive({})
const formDialogRef = ref(null)

// 表格加载 Loading
const tableLoading = ref(false)
// 新增分类按钮点击事件
const addCategoryBtnClick = () => {
    formDialogRef.value.open()
}
// 监听日期组件改变事件，并将开始结束时间设置到变量中
const datepickerChange = (e) => {
    startDate.value = moment(e[0]).format('YYYY-MM-DD')
    endDate.value = moment(e[1]).format('YYYY-MM-DD')

    console.log('开始时间：' + startDate.value + ', 结束时间：' + endDate.value)
}

// 表格数据
const tableData = ref([])
// 当前页码，给了一个默认值 1
const current = ref(1)
// 总数据量，给了个默认值 0
const total = ref(0)
// 每页显示的数据量，给了个默认值 10
const size = ref(10)

const formRef = ref(null)
// 对话框是否显示
const dialogVisible = ref(false)

// 添加文章分类表单对象
const form = reactive({
    name: ''
})

// 规则校验
const rules = {
    name: [
        {
            required: true,
            message: '分类名称不能为空',
            trigger: 'blur',
        },
        { min: 1, max: 20, message: '分类名称字数要求大于 1 个字符，小于 20 个字符', trigger: 'blur' },
    ]
}

const onSubmit = () => {
    // 先验证 form 表单字段
    formRef.value.validate((valid) => {
        if (!valid) {
            console.log('表单验证不通过')
            return false
        }
        formDialogRef.value.showBtnLoading()
		// 请求添加分类接口
        addCategory(form).then((res) => {
            const data = res.data
            if (data.code == '0000') {
                showMessage('添加成功')
                // 将表单中分类名称置空
                form.name = ''
                // 隐藏对话框
                formDialogRef.value.close()
                // 重新请求分页接口，渲染数据
                getTableData()
            } else {
                // 获取服务端返回的错误消息
                let message = data.info || '添加失败'
                // 提示错误消息
                showMessage(message, 'error')
            }
        }).finally(() => {
            formDialogRef.value.closeBtnLoading()
        })

    })
}

// 重置查询条件
const reset = () => {
    searchCategoryName.value = ''
    pickDate.value = ''
    startDate.value = null
    endDate.value = null
    getTableData()
}

 // 每页展示数量变更事件
const handleSizeChange = (chooseSize) => {
    size.value = chooseSize
    getTableData()
}

const handleCurrentChange = (page) => {
    current.value = page
    getTableData()
}

const deleteCategorySubmit = (row) => {
    console.log(row.id)
    showModel('是否确定要删除该分类？').then(() => {
        deleteCategory(row.id)
        deleteCategory({id: row.id}).then((res) => {
            const data = res.data
            if (data.code == '0000') {
                showMessage('删除成功')
                // 重新请求分页接口，渲染数据
                getTableData()
            } else {
                // 获取服务端返回的错误消息
                let message = data.info || '删除失败'
                // 提示错误消息
                showMessage(message, 'error')
            }
        })
    }).catch(() => {
        console.log('取消了')
    })
}

// 获取分页数据
function getTableData() {

    // 显示表格 loading
    tableLoading.value = true
    // 调用后台分页接口，并传入所需参数
    console.log(searchCategoryName.value, startDate.value, endDate.value, current.value, size.value)
    if (current.value == 0) {
        current.value = 1
    }
    getCategoryPageList({name: searchCategoryName.value, startDate: startDate.value, endDate: endDate.value, current: current.value, size: size.value})
    .then((res) => {
        console.log(res)

        const data = res.data
        if (data.code == '0000') {
            tableData.value = data.data
            total.value = data.total
        }
    }).finally(() => {
        // 隐藏表格 loading
        tableLoading.value = false
    })
}
getTableData()

const shortcuts = [
    {
        text: '最近一周',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            return [start, end]
        },
    },
    {
        text: '最近一个月',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            return [start, end]
        },
    },
    {
        text: '最近三个月',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            return [start, end]
        },
    },
]

</script>