<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="80px"
    >
      <el-form-item label="用户ID" prop="userId">
        <el-input v-model="dataForm.userId" placeholder="用户ID"></el-input>
      </el-form-item>
      <el-form-item label="商品ID" prop="goodsId">
        <el-input v-model="dataForm.goodsId" placeholder="商品ID"></el-input>
      </el-form-item>
      <el-form-item label="收货地址ID" prop="deliveryAddrId">
        <el-input
          v-model="dataForm.deliveryAddrId"
          placeholder="收货地址ID"
        ></el-input>
      </el-form-item>
      <el-form-item label="" prop="goodsImg">
        <el-input v-model="dataForm.goodsImg" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="商品名称" prop="goodsName">
        <el-input
          v-model="dataForm.goodsName"
          placeholder="冗余过来的商品名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="商品数量" prop="goodsCount">
        <el-input
          v-model="dataForm.goodsCount"
          placeholder="商品数量"
        ></el-input>
      </el-form-item>
      <el-form-item label="商品单价" prop="goodsPrice">
        <el-input
          v-model="dataForm.goodsPrice"
          placeholder="商品单价"
        ></el-input>
      </el-form-item>
      <el-form-item label="来源设备" prop="orderChannel">
        <el-input
          v-model="dataForm.orderChannel"
          placeholder="1pc, 2android, 3ios"
        ></el-input>
      </el-form-item>
      <el-form-item label="订单状态" prop="status">
        <el-input
          v-model="dataForm.status"
          placeholder="订单状态, 0新建未支付, 1已支付, 2已发货, 3已收货, 4已退款, 5已完成"
        ></el-input>
      </el-form-item>
      <el-form-item label="创建时间" prop="createDate">
        <el-input
          v-model="dataForm.createDate"
          placeholder="订单的创建时间"
        ></el-input>
      </el-form-item>
      <el-form-item label="支付时间" prop="payDate">
        <el-input v-model="dataForm.payDate" placeholder="支付时间"></el-input>
      </el-form-item>
      <el-form-item label="" prop="address">
        <el-input v-model="dataForm.address" placeholder=""></el-input>
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
        userId: "",
        goodsId: "",
        deliveryAddrId: "",
        goodsImg: "",
        goodsName: "",
        goodsCount: "",
        goodsPrice: "",
        orderChannel: "",
        status: "",
        createDate: "",
        payDate: "",
        address: "",
      },
      dataRule: {
        userId: [
          { required: true, message: "用户ID不能为空", trigger: "blur" },
        ],
        goodsId: [
          { required: true, message: "商品ID不能为空", trigger: "blur" },
        ],
        deliveryAddrId: [
          { required: true, message: "收货地址ID不能为空", trigger: "blur" },
        ],
        goodsImg: [{ required: true, message: "不能为空", trigger: "blur" }],
        goodsName: [
          {
            required: true,
            message: "冗余过来的商品名称不能为空",
            trigger: "blur",
          },
        ],
        goodsCount: [
          { required: true, message: "商品数量不能为空", trigger: "blur" },
        ],
        goodsPrice: [
          { required: true, message: "商品单价不能为空", trigger: "blur" },
        ],
        orderChannel: [
          {
            required: true,
            message: "1pc, 2android, 3ios不能为空",
            trigger: "blur",
          },
        ],
        status: [
          {
            required: true,
            message:
              "订单状态, 0新建未支付, 1已支付, 2已发货, 3已收货, 4已退款, 5已完成不能为空",
            trigger: "blur",
          },
        ],
        createDate: [
          {
            required: true,
            message: "订单的创建时间不能为空",
            trigger: "blur",
          },
        ],
        payDate: [
          { required: true, message: "支付时间不能为空", trigger: "blur" },
        ],
        address: [{ required: true, message: "不能为空", trigger: "blur" }],
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
            url: this.$http.adornUrl(
              `/miaosha/orderinfo/info/${this.dataForm.id}`
            ),
            method: "get",
            params: this.$http.adornParams(),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.userId = data.orderInfo.userId;
              this.dataForm.goodsId = data.orderInfo.goodsId;
              this.dataForm.deliveryAddrId = data.orderInfo.deliveryAddrId;
              this.dataForm.goodsImg = data.orderInfo.goodsImg;
              this.dataForm.goodsName = data.orderInfo.goodsName;
              this.dataForm.goodsCount = data.orderInfo.goodsCount;
              this.dataForm.goodsPrice = data.orderInfo.goodsPrice;
              this.dataForm.orderChannel = data.orderInfo.orderChannel;
              this.dataForm.status = data.orderInfo.status;
              this.dataForm.createDate = data.orderInfo.createDate;
              this.dataForm.payDate = data.orderInfo.payDate;
              this.dataForm.address = data.orderInfo.address;
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
              `/miaosha/orderinfo/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              id: this.dataForm.id || undefined,
              userId: this.dataForm.userId,
              goodsId: this.dataForm.goodsId,
              deliveryAddrId: this.dataForm.deliveryAddrId,
              goodsImg: this.dataForm.goodsImg,
              goodsName: this.dataForm.goodsName,
              goodsCount: this.dataForm.goodsCount,
              goodsPrice: this.dataForm.goodsPrice,
              orderChannel: this.dataForm.orderChannel,
              status: this.dataForm.status,
              createDate: this.dataForm.createDate,
              payDate: this.dataForm.payDate,
              address: this.dataForm.address,
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
