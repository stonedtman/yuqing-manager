var dom = document.getElementById("zx-box-1");
var myChart = echarts.init(dom);

option = {
    title : {
        text: '最新统计10支股票消息面情绪指数排行榜',
        x:'center',
        top: '-1%',
        fontSize: '15'
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    legend: {
    	top: '5%',
        data:['重大利好','利好','中性','利空','重大利空']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            data : ['五粮液','一汽大众','江南重工','乐视网','巨人网络',]
        }
    ],
    yAxis : [
        {
            type : 'value',
            splitLine:{show: false},//去除网格线
        }
    ],
    series : [
        {
            name:'重大利好',
            type:'bar',
            "itemStyle": {
                "normal": {
                    "color": "#ea4410",
                }
            },
            data:[19, 6, 6,7,6]
        },
        {
            name:'利好',
            type:'bar',
            "itemStyle": {
                "normal": {
                    "color": "#ff9800",
                }
            },
            data:[51, 49, 32,34,65,23,65,23,43,68]
        },
        {
            name:'中性',
            type:'bar',
            "itemStyle": {
                "normal": {
                    "color": "#c5d106",
                }
            },
            data:[12, 31, 42,87,98,34,65,76,31,87]
        },
        {
            name:'利空',
            type:'bar',
            "itemStyle": {
                "normal": {
                    "color": "#4cba2a",
                }
            },
            data:[13, 6, 10,32,54,,76,67,98,32,54]
        },
        {
            name:'重大利空',
            type:'bar',
            "itemStyle": {
                "normal": {
                    "color": "#2a8e29",
                }
            },
            data:[5, 8, 10,32,54,65,4,75,43,23],
        },
    ]
};

if (option && typeof option === "object") {
    myChart.setOption(option, true);
}