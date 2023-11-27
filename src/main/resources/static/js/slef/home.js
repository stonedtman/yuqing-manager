$(function () {
    userTrendChart()
    loading(1)
    // systemHotModuleRanking()
})
function userTrendChart() {
    $.ajax({
        type: "get",
        url: ctxPath + "home/userTrendChart",
        dataType: "json",
        success: function (res) {
            if(res.status==200){
                let data = res.data
                let xAxisData = []
                let seriesData = []
                for (let i = 0; i < data.length; i++) {
                    xAxisData.push(data[i].date)
                    seriesData.push(data[i].count)
                }
                linemap(xAxisData,seriesData)
            }
        }
    })
}
function linemap(xAxisData,seriesData) {
    var myChart = echarts.init(document.querySelector('#growth_trends'));
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
            minInterval: 1,
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

let user_ranking_sort = false
$(".user_ranking_sort").click(function () {
    if(user_ranking_sort){
        user_ranking_sort = false
        $(this)[0].children[0].className = ""
        $(this)[0].children[1].className = "highlight"
    }else{
        user_ranking_sort = true
        $(this)[0].children[0].className = "highlight"
        $(this)[0].children[1].className = ""
    }
    loading(1)
})

$("#time").change(function () {
    loading(1)
})

function loading(pagenum) {
    let keyword = $("#keyword").val();
    let time = $("#time option:selected").val();
    $.ajax({
        type: "get",
        url: ctxPath + "home/userUseRanking",
        dataType: "json",
        data: {pageNum: pagenum, pageSize: 10, username: keyword, days: time, isASC: user_ranking_sort},
        beforeSend: function () {
            $("#userRankingList").html('');
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
                var data = res.data.list;
                var htmlStr = '';
                for (var i = 0; i < data.length; i++) {
                    var dataJson = data[i];

                    //姓名
                    var username = dataJson.username

                    //用户id
                    var id = dataJson.id;

                    //写作宝
                    var xieFlag = "<span style='color: red'>未绑定</span>"
                    if (dataJson.xieFlag == 1) {
                        xieFlag = "<span style='color: green'>已绑定</span>";
                    }else{
                        xieFlag = "<span style='color: red'>未绑定</span>";
                    }

                    //微信公众号
                    var wechatFlag = "<span style='color: red'>未关注</span>"
                    if (dataJson.wechatFlag == 1) {
                        wechatFlag = "<span style='color: green'>已关注</span>";
                    }else{
                        wechatFlag = "<span style='color: red'>未关注</span>";
                    }

                    //nlp
                    var nlpFlag = "<span style='color: red'>未绑定</span>"
                    if (dataJson.nlpFlag == 1) {
                        nlpFlag = "<span style='color: green'>已绑定</span>";
                    }else{
                        nlpFlag = "<span style='color: red'>未绑定</span>";
                    }

                    //使用次数
                    var count = dataJson.count||0;

                    //最后一次登录时间
                    if (dataJson.endLoginTime != null && dataJson.endLoginTime != '') {
                        var endLoginTime = stampTOtime(dataJson.endLoginTime);
                    }else{
                        var endLoginTime = '—————————';
                    }

                    htmlStr = `
                        <tr class="textAlign">
                            <td><a href="/userRecords">${username}</a></td>
                            <td>${id}</td>
                            <td>${xieFlag}</td>
                            <td>${wechatFlag}</td>
                            <td>${nlpFlag}</td>
                            <td>${count}</td>
                            <td>${endLoginTime}</td>
                        </tr>
                    `
                    $("#userRankingList").append(htmlStr);
                }
            } else {
                $("#userRankingList").html('<tr><td colspan="11" style="text-align: center">暂无数据！</td></tr>');
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

//搜索事件
$("#keyword").keydown(function (res) {
    if (res.keyCode == 13)
        loading(1);
})


let module_ranking_sort = false
let module_ranking_sort1 = false
$(".module_ranking_sort").click(function () {
    let moduleRankingSort1 = document.querySelector(".module_ranking_sort1")
    if(module_ranking_sort){
        module_ranking_sort = false
        $(this)[0].children[0].className = ""
        $(this)[0].children[1].className = "highlight"

        moduleRankingSort1.children[0].className = ""
        moduleRankingSort1.children[1].className = ""
    }else{
        module_ranking_sort = true
        $(this)[0].children[0].className = "highlight"
        $(this)[0].children[1].className = ""

        moduleRankingSort1.children[0].className = ""
        moduleRankingSort1.children[1].className = ""
    }
    systemHotModuleRanking()
})

$(".module_ranking_sort1").click(function () {
    let moduleRankingSort = document.querySelector(".module_ranking_sort")
    if(module_ranking_sort1){
        module_ranking_sort1 = false
        $(this)[0].children[0].className = ""
        $(this)[0].children[1].className = "highlight"

        moduleRankingSort.children[0].className = ""
        moduleRankingSort.children[1].className = ""
    }else{
        module_ranking_sort1 = true
        $(this)[0].children[0].className = "highlight"
        $(this)[0].children[1].className = ""

        moduleRankingSort.children[0].className = ""
        moduleRankingSort.children[1].className = ""
    }
    systemHotModuleRanking()
})

$("#time").change(function () {
    systemHotModuleRanking()
})
function systemHotModuleRanking() {
    let time = $("#time1 option:selected").val();
    return
    $.ajax({
        type: "get",
        url: ctxPath + "home/systemHotModuleRanking",
        dataType: "json",
        data: {days: time, isASC: module_ranking_sort},
        beforeSend: function () {
            $("#moduleRankingList").html('');
        },
        success: function (res) {
            var status = res.status;
            console.log(res)
            return
            if (status == 200) {
                var data = res.data.list;
                var htmlStr = '';
                for (var i = 0; i < data.length; i++) {
                    var dataJson = data[i];

                    //姓名
                    var username = dataJson.username

                    //用户id
                    var id = dataJson.id;

                    //写作宝
                    var xieFlag = "<span style='color: red'>未绑定</span>"
                    if (dataJson.xieFlag == 1) {
                        xieFlag = "<span style='color: green'>已绑定</span>";
                    }else{
                        xieFlag = "<span style='color: red'>未绑定</span>";
                    }

                    //微信公众号
                    var wechatFlag = "<span style='color: red'>未关注</span>"
                    if (dataJson.wechatFlag == 1) {
                        wechatFlag = "<span style='color: green'>已关注</span>";
                    }else{
                        wechatFlag = "<span style='color: red'>未关注</span>";
                    }

                    //nlp
                    var nlpFlag = "<span style='color: red'>未绑定</span>"
                    if (dataJson.nlpFlag == 1) {
                        nlpFlag = "<span style='color: green'>已绑定</span>";
                    }else{
                        nlpFlag = "<span style='color: red'>未绑定</span>";
                    }

                    //使用次数
                    var count = dataJson.count||0;

                    //最后一次登录时间
                    if (dataJson.endLoginTime != null && dataJson.endLoginTime != '') {
                        var endLoginTime = stampTOtime(dataJson.endLoginTime);
                    }else{
                        var endLoginTime = '—————————';
                    }

                    htmlStr = `
                        <tr class="textAlign">
                            <td><a href="/userRecords">${username}</a></td>
                            <td>${id}</td>
                            <td>${xieFlag}</td>
                            <td>${wechatFlag}</td>
                            <td>${nlpFlag}</td>
                            <td>${count}</td>
                            <td>${endLoginTime}</td>
                        </tr>
                    `
                    $("#moduleRankingList").append(htmlStr);
                }
            } else {
                $("#moduleRankingList").html('<tr><td colspan="11" style="text-align: center">暂无数据！</td></tr>');
            }
        },
    });
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
