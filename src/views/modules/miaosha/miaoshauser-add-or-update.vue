<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="nickname">
      <el-input v-model="dataForm.nickname" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="MD5(MD5(pass明文+固定salt) + salt)" prop="password">
      <el-input v-model="dataForm.password" placeholder="MD5(MD5(pass明文+固定salt) + salt)"></el-input>
    </el-form-item>
    <el-form-item label="" prop="salt">
      <el-input v-model="dataForm.salt" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="头像,云存储的ID" prop="head">
      <el-input v-model="dataForm.head" placeholder="头像,云存储的ID"></el-input>
    </el-form-item>
    <el-form-item label="注册时间" prop="registerDate">
      <el-input v-model="dataForm.registerDate" placeholder="注册时间"></el-input>
    </el-form-item>
    <el-form-item label="上次登录时间" prop="lastLoginDate">
      <el-input v-model="dataForm.lastLoginDate" placeholder="上次登录时间"></el-input>
    </el-form-item>
    <el-form-item label="登录次数" prop="loginCount">
      <el-input v-model="dataForm.loginCount" placeholder="登录次数"></el-input>
    </el-form-item>
    <el-form-item label="" prop="address">
      <el-input v-model="dataForm.address" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="groupId">
      <el-input v-model="dataForm.groupId" placeholder=""></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          nickname: '',
          password: '',
          salt: '',
          head: '',
          registerDate: '',
          lastLoginDate: '',
          loginCount: '',
          address: '',
          groupId: ''
        },
        dataRule: {
          nickname: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: 'MD5(MD5(pass明文+固定salt) + salt)不能为空', trigger: 'blur' }
          ],
          salt: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          head: [
            { required: true, message: '头像,云存储的ID不能为空', trigger: 'blur' }
          ],
          registerDate: [
            { required: true, message: '注册时间不能为空', trigger: 'blur' }
          ],
          lastLoginDate: [
            { required: true, message: '上次登录时间不能为空', trigger: 'blur' }
          ],
          loginCount: [
            { required: true, message: '登录次数不能为空', trigger: 'blur' }
          ],
          address: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          groupId: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/miaosha/miaoshauser/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.nickname = data.miaoshaUser.nickname
                this.dataForm.password = data.miaoshaUser.password
                this.dataForm.salt = data.miaoshaUser.salt
                this.dataForm.head = data.miaoshaUser.head
                this.dataForm.registerDate = data.miaoshaUser.registerDate
                this.dataForm.lastLoginDate = data.miaoshaUser.lastLoginDate
                this.dataForm.loginCount = data.miaoshaUser.loginCount
                this.dataForm.address = data.miaoshaUser.address
                this.dataForm.groupId = data.miaoshaUser.groupId
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/miaosha/miaoshauser/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'nickname': this.dataForm.nickname,
                'password': this.dataForm.password,
                'salt': this.dataForm.salt,
                'head': this.dataForm.head,
                'registerDate': this.dataForm.registerDate,
                'lastLoginDate': this.dataForm.lastLoginDate,
                'loginCount': this.dataForm.loginCount,
                'address': this.dataForm.address,
                'groupId': this.dataForm.groupId
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
