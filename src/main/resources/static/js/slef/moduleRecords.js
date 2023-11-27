$(function () {
    systemSubModuleList()
    subModuleUseChart()
    loading(1)
})

function systemSubModuleList() {
    $.ajax({
        type: "get",
        url: ctxPath + "home/systemSubModuleList",
        dataType: "json",
        data: {module: module},
        beforeSend: function () {
            $("#time").html('<option value="0">全部</option>');
        },
        success: function (res) {
            if(res.status==200){
                let data = res.data
                var htmlStr = '';
                for (let i = 0; i < data.length; i++) {
                    htmlStr = `
                        <option value="${data[i]}">${data[i]}</option>
                    `
                    $("#time").append(htmlStr);
                }
            }
        }
    })
}

$("#time").change(function () {
    subModuleUseChart()
})
function subModuleUseChart() {
    let subModule = $("#time option:selected").val();
    let paramsdata = {
        module: module
    }
    if(subModule!=0){
        paramsdata.subModule = subModule
    }
    $.ajax({
        type: "get",
        url: ctxPath + "home/subModuleUseChart",
        dataType: "json",
        data: paramsdata,
        success: function (res) {
            if(res.status==200){
                let data = res.data
                let xAxisData = []
                let seriesData = []
                for (let i = 0; i < data.length; i++) {
                    xAxisData.push(data[i].moduleName)
                    seriesData.push(data[i].count)
                }
                barmap(xAxisData,seriesData)
            }
        }
    })
}

function barmap(xAxisData,seriesData) {
    var myChart = echarts.init(document.querySelector('#usage'));
    var option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        grid: {
            top: '7%',
            left: '2%',
            right: '2%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: xAxisData,
            axisLine: {
                lineStyle: {
                    color: "#999"
                }
            },
            axisLabel: {
                color: "#333"
            }
        },
        yAxis: {
            type: 'value',
            axisLine: {
                lineStyle: {
                    color: "#999"
                }
            },
            axisLabel: {
                color: "#333"
            },
            splitLine: {
                show: false,
            },
        },
        series: [
            {
                data: seriesData,
                type: 'bar',
                color: "#64b5f6",
                barMaxWidth: 40
            }
        ]
    };
    myChart.setOption(option);
}

$("#time1").change(function () {
    loading(1)
})
function loading(pagenum) {
    let time = $("#time1 option:selected").val();
    $.ajax({
        type: "get",
        url: ctxPath + "home/moduleUseRecord",
        dataType: "json",
        data: {module: module, pageNum: pagenum, pageSize: 10, days: time},
        beforeSend: function () {
            $("#moduleRecordsList").html('');
        },
        success: function (res) {
            var status = res.status;
            var total = res.data.total;
            var pages = res.data.pages;
            var pageNum = res.data.pageNum;
            if (pageNum == 1) {
                paging(total, pages, pageNum);
            }
            if (status == 200) {
                var data = res.data;
                var htmlStr = '';
                for (var i = 0; i < data.length; i++) {
                    var dataJson = data[i];

                    //子模块
                    var submodule = dataJson.submodule;

                    //类型
                    var type = dataJson.type

                    //使用总次数
                    var total = dataJson.total;

                    //最新使用时间
                    if (dataJson.lastUseTime != null && dataJson.lastUseTime != '') {
                        var lastUseTime = dataJson.lastUseTime;
                    }else{
                        var lastUseTime = '—————————';
                    }

                    //最新使用用户
                    var lastUsedUser = dataJson.lastUsedUser;

                    //使用最多用户
                    var mostUsedUser = dataJson.mostUsedUser

                    //使用次数
                    var count = dataJson.count;

                    htmlStr = `
                        <tr class="textAlign">
                            <td>${submodule}</td>
                            <td>${type}</td>
                            <td>${total}</td>
                            <td>${lastUseTime}</td>
                            <td>${lastUsedUser}</td>
                            <td>${mostUsedUser}</td>
                            <td>${count}</td>
                        </tr>
                    `
                    $("#moduleRecordsList").append(htmlStr);
                }
            } else {
                $("#moduleRecordsList").html('<tr><td colspan="11">暂无数据！</td></tr>');
            }
        },
    });
}

function paging(totalData, totalPage, pagenum) {
    $('#box').paging({
        initPageNo: pagenum, // 初始页码
        totalPages: totalPage, //总页数
        totalCount: '合计' + totalData + '条数据', // 条目总数
        slideSpeed: 600, // 缓动速度 单位毫秒
        jump: true, //是否支持跳转
        callback: function (page) {
            // 回调函数
            //loading(page);
        }
    });
}

function JumpToPage(pagenum) {
    loading(pagenum);
}

function stampTOtime(data) {
//data为时间戳
    var date = new Date(data);
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
    var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
    var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
    var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());

    var strDate = Y + M + D + h + m + s; //转换为年月日时分秒
    // var strDate = Y+M+D; //转换为年月日

    return strDate;
}
