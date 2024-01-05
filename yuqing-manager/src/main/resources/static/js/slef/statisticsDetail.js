$(function () {
    getDataChart()
    loading(1)
})

function getDataChart() {
    $.ajax({
        type: "get",
        url: ctxPath + "data/getDataChart",
        dataType: "json",
        data: {sourceWebsite: sourceName},
        beforeSend: function () {
            $("#data_trend").html('');
        },
        success: function (res) {
            if(res.status==200){
                $(".data_volume")[0].children[0].children[0].innerHTML = res.data.todayDataCount
                $(".data_volume")[0].children[1].children[0].innerHTML = res.data.weekDataCount
                $(".data_volume")[0].children[2].children[0].innerHTML = res.data.dataCount
                let data = res.data.dataChartDayVOList
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
    var myChart = echarts.init(document.querySelector('#data_trend'));
    var option = {
        title: {
            text: '七日内数据更新量走势情况',
            textStyle: {
                fontSize: 13,
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            top: '9%',
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
                symbolSize : 6,
                itemStyle:{
                    color: "#64b5f6",

                },
                emphasis: {
                    itemStyle: {
                        color:'#64b5f6',//这里设置的拐点颜色
                        borderColor: "#787878", //  拐点边框颜色
                        borderWidth: 1, //  拐点边框宽度
                    }
                },
                type: 'line',
                showSymbol: false,
                smooth: true
            }
        ]
    };
    myChart.setOption(option);
}

function loading(pagenum) {
    $.ajax({
        type: "get",
        url: ctxPath + "data/getArticleList",
        dataType: "json",
        data: {sourceWebsite: sourceName, pageNum: pagenum, pageSize: 10},
        beforeSend: function () {
            $("#articleList").html('');
        },
        success: function (res) {
            var status = res.status;
            var total = res.data.total;
            var pages = res.data.pages;
            var pageNum = res.data.pageNum;
            if (pagenum == 1) {
                paging(total, pages, pageNum);
            }
            if (status == 200) {
                var data = res.data.list;
                var htmlStr = '';
                for (var i = 0; i < data.length; i++) {
                    var dataJson = data[i];

                    var id = dataJson.id;

                    //文章标题
                    var title = dataJson.title;

                    //作者
                    var author = dataJson.author

                    //采集时间
                    if (dataJson.spiderTime != null && dataJson.spiderTime != '') {
                        var spiderTime = dataJson.spiderTime;
                    }else{
                        var spiderTime = '—————————';
                    }

                    //发布时间
                    if (dataJson.publishTime != null && dataJson.publishTime != '') {
                        var publishTime = dataJson.publishTime;
                    }else{
                        var publishTime = '—————————';
                    }

                    htmlStr = `
                        <tr class="textAlign">
                            <td><a href="https://open-yuqing.stonedt.com/monitor/detail/${id}" target="_blank">${title}</a></td>
                            <td>${author}</td>
                            <td>${spiderTime}</td>
                            <td>${publishTime}</td>
                        </tr>
                    `
                    $("#articleList").append(htmlStr);
                }
            } else {
                $("#articleList").html('<tr><td colspan="11">暂无数据！</td></tr>');
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
