<template>
  <div class="mod-home">
    <img src="~@/assets/img/logo3.png" />
    <h3>项目背景</h3>
    <ul>
      <li>在电商领域，存在着典型的秒杀业务场景。</li>
      <li>
        秒杀场景，简单的来说就是一件商品的购买人数远远大于这件商品的库存，而且这件商品在很短的时间内就会被抢购一空的场景。
      </li>
      <li>
        每年618、双十一、京东和淘宝大促，很多商品价格较低，库存一定，但客户需求量较大，客户需要在极短的时间抢到需要的商品。
      </li>
      <li>
        商家预设固定数量的低价或免单商品，供消费者在指定开始时间购买商品，秒杀系统应需而出。
      </li>

      <!-- <li>renren-fast-vue基于vue、element-ui构建开发，实现<a href="https://gitee.com/renrenio/renren-fast" target="_blank">renren-fast</a>后台管理前端功能，提供一套更优的前端解决方案</li>
      <li>前后端分离，通过token进行数据交互，可独立部署</li>
      <li>主题定制，通过scss变量统一一站式定制</li>
      <li>动态菜单，通过菜单管理统一管理访问路由</li>
      <li>数据切换，通过mock配置对接口数据／mock模拟数据进行切换</li>
      <li>发布时，可动态配置CDN静态资源／切换新旧版本</li>
      <li>演示地址：<a href="http://demo.open.renren.io/renren-fast" target="_blank">http://demo.open.renren.io/renren-fast</a> (账号密码：admin/admin)</li> -->
    </ul>
    <h3>项目简介</h3>
    <ul>
      <li>
        从架构视角来看，秒杀系统本质是一个高性能、高一致、高可用的三高系统。
      </li>
      <li>
        高性能：秒杀涉及高读和高写的支持，高读就意味着尽量"少读"或"读少"，高写就涉及到数据拆分。
      </li>
      <li>
        一致性：秒杀的核心关注是商品库存，有限的商品在同一时间被多个请求同时扣减，而且要保证准确性。
      </li>
      <li>
        高可用：分布式系统在运行过程中的情况是非常复杂的，需要考虑如何保障应用在复杂环境下还能高效稳定运行。
      </li>
    </ul>
    <!-- <h3>获取帮助</h3>
    <ul>
      <li>官方社区：<a href="https://www.renren.io/community" target="_blank">https://www.renren.io/community</a></li>
      <li>前端Git地址：<a href="https://github.com/renrenio/renren-fast-vue" target="_blank">https://github.com/renrenio/renren-fast-vue</a></li>
      <li>后台Git地址：<a href="https://gitee.com/renrenio/renren-fast" target="_blank">https://gitee.com/renrenio/renren-fast</a></li>
      <li>代码生成器：<a href="https://gitee.com/renrenio/renren-generator" target="_blank">https://gitee.com/renrenio/renren-generator</a></li>
      <li>如需关注项目最新动态，请Watch、Star项目，同时也是对项目最好的支持</li>
    </ul>
    <h3>官方QQ群</h3>
    <ul>
      <li>高级群：324780204(大牛云集，跟大牛学习新技能)</li>
      <li>普通群：145799952(学习交流，互相解答各种疑问)</li>
    </ul> -->
  </div>
</template>

<script>
export default {};
</script>

<style>
.mod-home {
  line-height: 1.5;
}
</style>

