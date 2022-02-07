
/**
 * 将下划线转为骆峰
 * @param key
 * @returns {*}
 */
function line2Hump(key) {
    var arr = key.split("_");
    var humpKey = arr[0];
    for (var j = 1; j < arr.length; j++) {
        humpKey = humpKey + arr[j].slice(0, 1).toUpperCase() + arr[j].slice(1);
    }
    return humpKey
}

/**
 * 将数据转为分页数据
 */
function execute() {
    var total = context.get('total');
    var list = context.get('list');
    var pageQuery = context.get('pageQuery');
    var pageSize = context.get('pageSize');
    var page = context.get('page');
    var arrayList = eval(list);
    var arrayLength = arrayList.length;
    if (pageQuery == undefined || pageQuery == 'false' || total == undefined) {
        page = 1;
        pageSize = arrayLength;
        total = arrayLength;
    }
    else {
        page = parseInt(page);
        pageSize = parseInt(pageSize);
        total = parseInt(total)
    }
    var retList = [];
    for (var i = 0; i < arrayLength; i++) {
        var row = arrayList[i];
        var retObj = {};
        for (var key in row) {
            var humpKey = line2Hump(key);
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
    setResult('result', JSON.stringify(res));
}

execute();


