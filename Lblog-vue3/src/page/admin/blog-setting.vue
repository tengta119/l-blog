<template>
    <div>
        <el-card shadow="never">
            <el-form :model="formUser" ref="formUserRef" :rules="rules" label-width="120px">

                <el-form-item label="作者" prop="author">
                    <el-input v-model="formUser.author" placeholder="请输入作者"></el-input>
                </el-form-item>

                <el-form-item label="作者头像" prop="avatar">
                    <el-upload class="avatar-uploader" action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                        :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                        <img v-if="formUser.avatar" :src="formUser.avatar" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>

                <el-form-item label="手机号" prop="phone">
                    <el-input v-model="formUser.phone" placeholder="请输入手机号"></el-input>
                </el-form-item>

                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="formUser.email" placeholder="请输入邮箱"></el-input>
                </el-form-item>

                <el-form-item label="自我介绍" prop="avatar">
                    <el-input v-model="formUser.introduction" placeholder="请输入头像" type="textarea"></el-input>
                </el-form-item>

                <el-form-item label="性别" prop="sex">
                    <el-radio-group v-model="formUser.sex">
                        <el-radio label="1">男</el-radio>
                        <el-radio label="2">女</el-radio>
                    </el-radio-group>
                </el-form-item>



            </el-form>
        </el-card>

        <el-card shadow="never" class="mt-5">
            <el-form :model="formBlog" ref="formBlogRef" :rules="rules" label-width="120px">
                <el-form-item label="博客名称" prop="name">
                    <el-input v-model="formBlog.name" placeholder="请输入博客名称"></el-input>
                </el-form-item>
                <el-form-item label="博客logo" prop="logo">
                    <el-upload class="avatar-uploader" action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                        :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                        <img v-if="formBlog.logo" :src="formBlog.logo" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
            </el-form>  
        </el-card>

    </div>
</template>


<script setup>
import { reactive } from 'vue';
import { updateUserInfo, getUserInfo } from '@/api/admin/user';
import {Plus} from '@element-plus/icons-vue'
import { getBlogSettings } from '@/api/admin/blogSettings';

const formUser = reactive({
    author: '',
    avatar: '',
    phone: '',
    email: '',
    introduction: '',
    birthday: '2025-11-18',
    backgroundImg: '',
    sex: 1
})

function initUserInfo() {
    getUserInfo().then(res => {
        console.log(res)
        const data = res.data
        if (data.code == '0000') {
            const userData = data.data
            formUser.author = userData.author
            formUser.avatar = userData.avatar
            formUser.phone = userData.phone
            formUser.email = userData.email
            formUser.introduction = userData.introduction
            formUser.birthday = userData.birthday
            formUser.backgroundImg = userData.backgroundImg
            formUser.sex = userData.sex
        }
    })
}
initUserInfo()

const formBlog = reactive({
    logo: '',
    name: ''
})

function initBlogSettings() {
    getBlogSettings().then(res => {
        console.log(res)
        const data = res.data
        if (data.code == '0000') {
            const blogSettings = data.data
            formBlog.logo = blogSettings.logo
            formBlog.name = blogSettings.name
        }
    })
}
initBlogSettings()

</script>

<style scoped>
.avatar-uploader .avatar {
    width: 100px;
    height: 100px;
    display: block;
}

.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100px;
    height: 100px;
    text-align: center;
}
</style>