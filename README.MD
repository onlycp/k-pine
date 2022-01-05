# 1 介绍 

基础框架采用了SpringBoot2作为了快速开发平台，目前主要依赖了如下组件

* spring-boot-starter-web: Web开发基础依赖包
* spring-boot-starter-aop: AOP拦截处理
* knife4j-spring-boot-starter: Swagger的接口调试界面
* lombok
* jxl: Excel处理(后期支持替换成Poi,可选配)


# 2 业务开发包规范

- com.kingsware.kdev.biz [顶级包]
  - {module} [模块名]
    - argv [前端传后台]
    - ret [后台传前端]
    - model [数据库表Model]
    - service [业务接口类]
      - impl [业务实现类]
    - web [控制器]

# 3 Base基类

## 3.1 Model

* BaseModel: Model基类
* BaseManageModel: 管理类Model，继承于BaseModel，包括了基础的id、who_created、when_created、who_modified、when_modified 字段

## 3.2 Ret

* BaseRet: 通用的返回类，通过泛型实现返回data数据
* BaseManageRet: 管理类Ret，包括了基础的id、who_created、when_created、who_modified、when_modified 字段

## 3.3 Argv

* BaseArgv: argv基类
* BasePageArgv: 查询基类，里面包括了分页查询常用的字段

## 3.4 Service

* BaseService： 业务基类接口
* BaseServiceImpl： 业务基类实现嗯

## 3.5 Controller

* BaseController： 控制类基类


# 4.ORM 

适配kdb，提供快速的增删改查方法

## 4.1 注解介绍

* `@Table`:  表定义，如果不加，就默认为class的骆峰字符串
* `@Column`:  列定义，如果不加，就默认为field的骆峰字符串
* `@AutoEnum`: 自动生成值枚举，`ID`表示生成ID、`WHO`表示生成当成用户ID、`WHEM`表示生成当前时间
* `@ColumnIgnore`:  忽略列
* `@LogicDelete`:  逻辑删除（待实现）

## 4.2 数据库操作类
    具体见DB类

## 4.3 唯一性校验

```java
// 唯一性校验
DBChecker<KwMechanism> checker =DBChecker.build(model, KwMechanism.class);
// 银行名称
checker.uni("bankName", I18n.t("KwMechanism.bankName.unique", "银行名称必须唯一"));
// 银行代码
checker.uni("bankNumber", I18n.t("KwMechanism.bankNumber.unique", "银行代码必须唯一"));
// 银行简称
checker.uni("bankShort", I18n.t("KwMechanism.bankShort.unique", "银行简称必须唯一"));
// 执行校验
checker.checkUnique();
```

# 5. CURD

```java
@Service
public class KwMechanismServiceImpl extends BaseServiceImpl implements KwMechanismService {

    @Override
    public KwMechanismRet get(String id) {
        // 查询model
        KwMechanism model = DB.findById(KwMechanism.class, id);
        // 转换成ret对象
        return (KwMechanismRet) model2Ret(model, KwMechanismRet.class);
    }

    @Override
    public void add(KwMechanismArgv argv) {
        KwMechanism model = BeanUtils.copyObject(argv, KwMechanism.class);
        // 校验
        checkUnique(model);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(KwMechanismArgv argv) {
        KwMechanism model = DB.findById(KwMechanism.class, argv.getId());
        model.setBankNumber(argv.getBankNumber());
        model.setBankName(argv.getBankName());
        model.setBankType(argv.getBankType());
        model.setBankShort(argv.getBankShort());
        // 校验
        checkUnique(model);
        // 保存
        DB.update(model);
    }

    /**
     * 校验唯一性
     * @param model 模型
     */
    private void checkUnique(KwMechanism model) {
        // 唯一性校验
        DBChecker<KwMechanism> checker =DBChecker.build(model, KwMechanism.class);
        // 银行名称
        checker.uni("bankName", I18n.t("KwMechanism.bankName.unique", "银行名称必须唯一"));
        // 银行代码
        checker.uni("bankNumber", I18n.t("KwMechanism.bankNumber.unique", "银行代码必须唯一"));
        // 银行简称
        checker.uni("bankShort", I18n.t("KwMechanism.bankShort.unique", "银行简称必须唯一"));
        // 执行校验
        checker.checkUnique();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<KwMechanismRet> query(KwMechanismQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from kw_mechanism where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getBankName())) {
            wrapper.addCondition("bank_name", Op.LIKE, "%" +argv.getBankName() +"%");
        }
        if (argv.getBankType() != null) {
            wrapper.addCondition("bank_type", Op.EQ, argv.getBankType());
        }
        return (PageDataRet<KwMechanismRet>) query(wrapper.getSql(), wrapper.getParams(), argv, KwMechanism.class, KwMechanismRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(KwMechanism.class, id);
        }
    }
}

```


# 6. 后端开发建议流程

`创建Model类` --> `创建Ret类` --> `创建Argv类(分为增改的Argv和查询的Argv)` --> `创建service接口(可以拷贝已有的代码，然后统一查找替换)`--> `创建service实现(可以拷贝已有的代码，然后统一查找替换)`--> `创建Controller实现(可以拷贝已有的代码，然后统一查找替换)`







