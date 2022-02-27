var dom = document.getElementById("zx-box-2");
var myChart = echarts.init(dom);

var areaStyle = {
    itemStyle: {
        normal: {
            color: 'rgba(241, 28, 93, 0.05)'
        }
    },
}
var option = {
    title: {
        text: '最新高频词对应的情绪数据分析排行榜',
       // subtext: 'gogoogogogo',
        x: 'left',
        align: 'right'
    },

    backgroundColor: '#fff',
    color: ['#ea4410', '#ff9800', '#c5d106', '#4cba2a', '#2a8e29'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    legend: {
        x: 'center',
        top: '8%',
        data: ['重大利好', '利好', '中性', '利空', '重大利空']
    },
    grid: { //图表的位置
        top: '20%',
        left: '3%',
        right: '4%',
        bottom: '5%',
        containLabel: true
    },
    yAxis: [{
        type: 'value',
        splitLine: {
            show: true,
            lineStyle: {
                color: ['#f2f2f2']
            }
        },
    }],
    xAxis: [{
        type: 'category',
        data: ['高频1', '高频2', '高频3', '高频4', '高频5', '高频6','高频7', '高频8', '高频9']
    }],
    series: [{
            name: '重大利好',
            type: 'bar',
            stack: '总量',
            barWidth: '30px',
            data: [62, 212, 101, 23, 54, 43, 120, 43,322],
            markArea: areaStyle
        },
        {
            name: '利好',
            type: 'bar',
            stack: '总量',
            data: [65, 232, 251, 56, 87, 87, 31, 32,254],
            markArea: areaStyle
        },
        {
            name: '中性',
            type: 'bar',
            stack: '总量',
            data: [75, 232, 231, 134, 190, 34, 110, 0,121],
            markArea: areaStyle
        },
        {
            name: '利空',
            type: 'bar',
            stack: '总量',
            data: [87, 132, 101, 34, 45, 150, 210, 76,121],
            markArea: areaStyle
        },
        {
            name: '重大利空',
            type: 'bar',
            stack: '总量',
            data: [120, 252, 201, 134, 60, 64, 150, 31,212],
            markArea: areaStyle
        }
    ]
};

if (option && typeof option === "object") {
    myChart.setOption(option, true);
}