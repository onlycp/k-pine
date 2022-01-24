var total = context.get('total');
var list = context.get('list');
var pageQuery = context.get('pageQuery');
var pageSize = context.get('pageSize')
var page = context.get('page');
if(pageQuery == undefined && pageQuery ==false) {
    page = 1;
    pageSize = total;
}
var arrayList = eval(list);
var retList = [];
for(var i=0; i < arrayList.length; i++) {
    var row = arrayList[i];
    var retObj = {};
    for(var key in row) {
        var arr = key.split("_");
        var humpKey = arr[0];
        for(var i=0;i<arr.length;i++){
            humpKey = humpKey + arr[i].slice(0,1).toUpperCase() + arr[i].slice(1);
        }
        retObj[humpKey] = row[key];
    }
    retList.push(retObj);
}
var res = {};
res['total'] = total;
res['page'] = page;
res['pageSize'] = pageSize;
res['pageCount'] = Math.ceil(total / pageSize);
res['list'] = retList;
setResult('result',JSON.stringify(res));
