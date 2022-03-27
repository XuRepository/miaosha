<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="商品id" prop="goodsId">
      <el-input v-model="dataForm.goodsId" placeholder="商品id"></el-input>
    </el-form-item>
    <el-form-item label="秒杀价" prop="miaoshaPrice">
      <el-input v-model="dataForm.miaoshaPrice" placeholder="秒杀价"></el-input>
    </el-form-item>
    <el-form-item label="库存数量" prop="stockCount">
      <el-input v-model="dataForm.stockCount" placeholder="库存数量"></el-input>
    </el-form-item>
    <el-form-item label="秒杀开始时间" prop="startDate">
      <el-input v-model="dataForm.startDate" placeholder="秒杀开始时间"></el-input>
    </el-form-item>
    <el-form-item label="秒杀结束时间" prop="endDate">
      <el-input v-model="dataForm.endDate" placeholder="秒杀结束时间"></el-input>
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
          goodsId: '',
          miaoshaPrice: '',
          stockCount: '',
          startDate: '',
          endDate: ''
        },
        dataRule: {
          goodsId: [
            { required: true, message: '商品id不能为空', trigger: 'blur' }
          ],
          miaoshaPrice: [
            { required: true, message: '秒杀价不能为空', trigger: 'blur' }
          ],
          stockCount: [
            { required: true, message: '库存数量不能为空', trigger: 'blur' }
          ],
          startDate: [
            { required: true, message: '秒杀开始时间不能为空', trigger: 'blur' }
          ],
          endDate: [
            { required: true, message: '秒杀结束时间不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/miaosha/miaoshagoods/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.goodsId = data.miaoshaGoods.goodsId
                this.dataForm.miaoshaPrice = data.miaoshaGoods.miaoshaPrice
                this.dataForm.stockCount = data.miaoshaGoods.stockCount
                this.dataForm.startDate = data.miaoshaGoods.startDate
                this.dataForm.endDate = data.miaoshaGoods.endDate
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
              url: this.$http.adornUrl(`/miaosha/miaoshagoods/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'goodsId': this.dataForm.goodsId,
                'miaoshaPrice': this.dataForm.miaoshaPrice,
                'stockCount': this.dataForm.stockCount,
                'startDate': this.dataForm.startDate,
                'endDate': this.dataForm.endDate
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
