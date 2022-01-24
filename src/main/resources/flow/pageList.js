var context = new Map();
context.set('total', 26);
context.set('list', '[{a:1, b_a:2}]');
context.set('pageQuery', false);
context.set('pageSize', 10);
context.set('page', 1);

var total = context.get('total');
// 获取结果集
var list = context.get('list');
// 是否分页查询
var pageQuery = context.get('pageQuery');
// 页大小
var pageSize = context.get('pageSize')
// 当前页
var page = context.get('page');
// 如果不是分页查询
if(pageQuery == undefined && pageQuery ==false) {
    page = 1;
    pageSize = total;
}
// 处理list
var arrayList = eval(list);
// 处理后的list
var retList = [];
for(var i=0; i < arrayList.length; i++) {
    var row = arrayList[i];
    // 处理对象
    var retObj = {};
    for(var key in row) {
        // 获取驼峰key
        var arr = key.split("_");
        var humpKey = arr[0];
        for(var i=0;i<arr.length;i++){
            humpKey = humpKey + arr[i].slice(0,1).toUpperCase() + arr[i].slice(1);
        }
        // 写入返回的对象
        retObj[humpKey] = row[key];
    }
    retList.push(retObj);
}
// 封装结果
var res = {};
res['total'] = total;
res['page'] = page;
res['pageSize'] = pageSize;
res['pageCount'] = Math.ceil(total / pageSize);
res['list'] = retList;
// 返回结果
//setResult('result',JSON.stringify(res));
