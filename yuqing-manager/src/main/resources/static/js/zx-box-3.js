var dom = document.getElementById("zx-box-3");
var myChart = echarts.init(dom);

var datas = [];

var myDate10 = moment().subtract('days', 0).format('MM-DD');
var myDate9 = moment().subtract('days', 1).format('MM-DD');
var myDate8 = moment().subtract('days', 2).format('MM-DD');
var myDate7 = moment().subtract('days', 3).format('MM-DD');
var myDate6 = moment().subtract('days', 4).format('MM-DD');
var myDate5 = moment().subtract('days', 5).format('MM-DD');
var myDate4 = moment().subtract('days', 6).format('MM-DD');

  datas.push(myDate4);
  datas.push(myDate5);
  datas.push(myDate6);
  datas.push(myDate7);
  datas.push(myDate8);
  datas.push(myDate9);
  datas.push(myDate10);

option = {
    backgroundColor: '#fff',
    title: {
        text: '',
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
    legend: {
        icon: 'rect',
        itemWidth: 14,
        itemHeight: 5,
        itemGap: 13,
        data: ['重大利好','利好','中性','利空', '重大利空'],
        right: '4%',
        textStyle: {
            fontSize: 12,
            color: '#3e4956'
        }
    },
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
        data: datas
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
        name: '重大利好',
        type: 'line',
        smooth: true,
        lineStyle: {
            normal: {
                width: 2
            }
        },
   
        itemStyle: {
            normal: {
                color: '#ea4410'
            }
        },
        data: [5.2,8.4,3.5,4.6,9.1,6.8,8.6]
    }, 
    {
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
        data: [11.2,12.4,13.5,14.6,15.1,16.8,18.6]
    }, 
    {
        name: '中性',
        type: 'line',
        smooth: true,
        lineStyle: {
            normal: {
                width: 2
            }
        },
   
        itemStyle: {
            normal: {
                color: '#c5d106'
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
        data: [18.2,17.4,16.5,15.6,14.1,13.8,10.6]
    }, 
    
    {
        name: '重大利空',
        type: 'line',
        smooth: true,
        lineStyle: {
            normal: {
                width: 2
            }
        },
       
        itemStyle: {
            normal: {
                color: '#2a8e29'
            }
        },
        data: [13.3,16.2,12.3,15.0,10.6,14.6,17.0]
    },]
};

if (option && typeof option === "object") {
    myChart.setOption(option, true);
}