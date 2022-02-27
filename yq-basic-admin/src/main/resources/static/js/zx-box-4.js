var dom = document.getElementById("zx-box-4");
var myChart = echarts.init(dom);

option = {
    backgroundColor: '#fff',
    title: {
        text: '7日股市大盘与全网资讯情绪对比数据曲线图',
        textStyle: {
            fontWeight: 'normal',
            fontSize: 16,
            color: '#3e4956'
        },
        left: '6%'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            lineStyle: {
                color: '#57617B'
            }
        }
    },
    /*legend: {
        icon: 'rect',
        itemWidth: 14,
        itemHeight: 5,
        itemGap: 13,
        data: ['利好', '利空'],
        right: '4%',
        textStyle: {
            fontSize: 12,
            color: '#3e4956'
        }
    },*/
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [{
        type: 'category',
        boundaryGap: false,
        axisTick: {
            show: false
        },
        axisLine: {
            lineStyle: {
                color: '#57617B'
            }
        },
        data: ['03.21', '03.22', '03.23', '03.24', '03.25', '03.26', '03.27']
    }],
    yAxis: [{
        type: 'value',
        splitLine:{show: false},//去除网格线
        axisTick: {
            show: false
        },
        
        axisLine: {
            show:true,
            lineStyle: {
                color: '#57617B'
            }
        },
        axisLabel: {
            margin: 10,
            textStyle: {
                fontSize: 14
            }
        },
/*        splitLine: {
            lineStyle: {
                color: '#57617B'
            }
        }*/
    }],
    series: [{
        name: '利好',
        type: 'line',
        smooth: true,
        lineStyle: {
            normal: {
                width: 2
            }
        },
   
        itemStyle: {
            normal: {
                color: '#ff9800'
            }
        },
        data: [15.2,18.4,13.5,20.6,19.1,16.8,18.6]
    }, 
    
    {
        name: '利空',
        type: 'line',
        smooth: true,
        lineStyle: {
            normal: {
                width: 2
            }
        },
       
        itemStyle: {
            normal: {
                color: '#4cba2a'
            }
        },
        data: [13.3,16.2,12.3,15.0,10.6,14.6,17.0]
    },]
};

if (option && typeof option === "object") {
    myChart.setOption(option, true);
}