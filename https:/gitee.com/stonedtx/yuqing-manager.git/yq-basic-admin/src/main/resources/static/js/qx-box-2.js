var dom = document.getElementById("qx-box-2");
var myChart = echarts.init(dom);

option = {
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    color:['#ea4410','#ff9800','#c5d106','#4cba2a','#2a8e29'],
    /*legend: {
        orient: 'vertical',
        left: 'left',
        data: ['重大利好','利好','中性','利空','重大利空'],
    },*/
    series : [
        {
            name: '访问来源',
            type: 'pie',
            radius : '65%',
            center: ['45%', '60%'],
            data:[
                {value:335, name:'重大利好'},
                {value:310, name:'利好'},
                {value:234, name:'中性'},
                {value:135, name:'利空'},
                {value:135, name:'重大利空'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};

if (option && typeof option === "object") {
    myChart.setOption(option, true);
}