<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    class="mod-config"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="80px"
    >
      <el-form-item label="商品名称" prop="goodsName">
        <el-input
          v-model="dataForm.goodsName"
          placeholder="商品名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="商品标题" prop="goodsTitle">
        <el-input
          v-model="dataForm.goodsTitle"
          placeholder="商品标题"
        ></el-input>
      </el-form-item>
      <el-form-item label="商品图片" prop="goodsImg">
        <el-input v-model="dataForm.goodsImg" placeholder="商品图片"></el-input>
      </el-form-item>
      <el-form-item label="详情介绍" prop="goodsDetail">
        <el-input
          v-model="dataForm.goodsDetail"
          placeholder="详情介绍"
        ></el-input>
      </el-form-item>
      <el-form-item label="商品单价" prop="goodsPrice">
        <el-input
          v-model="dataForm.goodsPrice"
          placeholder="商品单价"
        ></el-input>
      </el-form-item>
      <el-form-item label="商品库存" prop="goodsStock">
        <el-input
          v-model="dataForm.goodsStock"
          placeholder="商品库存, -1表示没有限制"
        ></el-input>
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
  data() {
    return {
      visible: false,
      dataForm: {
        id: 0,
        goodsName: "",
        goodsTitle: "",
        goodsImg: "",
        goodsDetail: "",
        goodsPrice: "",
        goodsStock: "",
      },
      dataRule: {
        goodsName: [
          { required: true, message: "商品名称不能为空", trigger: "blur" },
        ],
        goodsTitle: [
          { required: true, message: "商品标题不能为空", trigger: "blur" },
        ],
        goodsImg: [
          { required: true, message: "商品的图片不能为空", trigger: "blur" },
        ],
        goodsDetail: [
          {
            required: true,
            message: "商品的详情介绍不能为空",
            trigger: "blur",
          },
        ],
        goodsPrice: [
          { required: true, message: "商品单价不能为空", trigger: "blur" },
        ],
        goodsStock: [
          {
            required: true,
            message: "商品库存, -1表示没有限制不能为空",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    init(id) {
      this.dataForm.id = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/miaosha/goods/info/${this.dataForm.id}`),
            method: "get",
            params: this.$http.adornParams(),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.goodsName = data.goods.goodsName;
              this.dataForm.goodsTitle = data.goods.goodsTitle;
              this.dataForm.goodsImg = data.goods.goodsImg;
              this.dataForm.goodsDetail = data.goods.goodsDetail;
              this.dataForm.goodsPrice = data.goods.goodsPrice;
              this.dataForm.goodsStock = data.goods.goodsStock;
            }
          });
        }
      });
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(
              `/miaosha/goods/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              id: this.dataForm.id || undefined,
              goodsName: this.dataForm.goodsName,
              goodsTitle: this.dataForm.goodsTitle,
              goodsImg: this.dataForm.goodsImg,
              goodsDetail: this.dataForm.goodsDetail,
              goodsPrice: this.dataForm.goodsPrice,
              goodsStock: this.dataForm.goodsStock,
            }),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.visible = false;
                  this.$emit("refreshDataList");
                },
              });
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    },
  },
};
</script>
