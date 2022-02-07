
/**
 * 将下划线转为骆峰
 * @param key
 * @returns {*}
 */
function addModelField(key, type, format, models) {
    models.push({'key': key, 'type': type, 'format': format})
}

/**
 * 将数据转为分页数据
 */
function execute() {
    var models = [];
    addModelField("createAccountTime", "Timestamp", "yyyy-MM-dd", models);
    addModelField("cancelAccountTime", "Timestamp", "yyyy-MM-dd", models);
    setResult('result', JSON.stringify(res));
}

execute();


