# 共享电单车服务端

### 依赖关系
* SpringBoot 2.0
* Mysql
* Redis
* lombok

### 权限认证的sign的计算方法
* 所有请求参数的KEY去掉token,按字母顺序排序
* 排好序后取出相应value值拼接
* 拼接后的字符串后再拼接signMat的值
* 将得到的字符串算出其32位MD5值就是sign的值
