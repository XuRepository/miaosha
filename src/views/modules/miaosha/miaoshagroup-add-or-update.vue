<template>
  <el-dialog
    :title="!dataForm.groupId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="headCount">
      <el-input v-model="dataForm.headCount" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="targetCount">
      <el-input v-model="dataForm.targetCount" placeholder=""></el-input>
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
          groupId: 0,
          headCount: '',
          targetCount: ''
        },
        dataRule: {
          headCount: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          targetCount: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.groupId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.groupId) {
            this.$http({
              url: this.$http.adornUrl(`/miaosha/miaoshagroup/info/${this.dataForm.groupId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.headCount = data.miaoshaGroup.headCount
                this.dataForm.targetCount = data.miaoshaGroup.targetCount
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
              url: this.$http.adornUrl(`/miaosha/miaoshagroup/${!this.dataForm.groupId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'groupId': this.dataForm.groupId || undefined,
                'headCount': this.dataForm.headCount,
                'targetCount': this.dataForm.targetCount
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
