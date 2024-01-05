$(function () {
    $('#head').html('日志管理')
    $('#navigation').html('日志管理')
    loading(1)
})

function loading(pagenum) {
    let dataObject = new Object();
    let time = $("#time option:selected").val();

    let keyword = $("#keyword").val();

    dataObject.time = time;
    dataObject.page = pagenum;
    dataObject.keyword = keyword;

    $.ajax({
        type: "POST",
        url: ctxPath + "userlog/getLogList",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(dataObject),
        beforeSend: function () {
            $("#projectlist").html('');
        },
        success: function (res) {
            console.log(res)
            let code = res.code;
            if (code == 200) {
                let data = res.data;
                let page = res.page;
                let totalPage = res.totalPage;
                let totalCount = res.totalCount;
                if (page == 1) {
                    paging(totalCount, totalPage, page)
                }
                $("#projectlist").html('');
                if (data.length > 0) {
                    for (let i = 0; i < data.length; i++) {
                        let dataJson = data[i];
                        let end_login_time = dataJson.end_login_time;
                        debugger;
                        if (end_login_time != "" && end_login_time != undefined){
                            end_login_time = stampTOtime(end_login_time);
                        }else {
                            end_login_time = "--";
                        }
                        // let organization_code = dataJson.organization_code;
                        let organization_name = dataJson.organization_name!==undefined?dataJson.organization_name:"————————————"
                        let organization_short = dataJson.organization_short!==undefined?dataJson.organization_short:"————————————";
                        // let organization_type = dataJson.organization_type;
                        // let telephone = dataJson.telephone;
                        let user_id = dataJson.user_id;
                        let username = dataJson.username;
                        let organization_id = dataJson.organization_id;
                        let term_of_validity = dataJson.term_of_validity!==undefined?stampTOtime(dataJson.term_of_validity):'无限期';
                        term_of_validity = term_of_validity.substring(0 , 10);
                        let loginCount = dataJson.login_count;

                        let htmlStr = '<tr class="textAlign">' +
                            '<td>' + user_id + '</td>' +
                            '<td>' + username + '</td>' +
                            '<td>' + organization_short + '</td>' +
                            '<td data-id="' + organization_id + '">' + organization_name + '</td>' +
                            '<td>' + term_of_validity + '</td>' +
                            '<td>' + loginCount + '</td>' +
                            '<td>' + end_login_time + '</td>' +
                            '</tr>'
                        $('#projectlist').append(htmlStr)
                    }
                } else {
                    let htmlStr = '<tr class="textAlign"><td colspan="6">暂无数据</td></tr>'
                    $('#projectlist').append(htmlStr)
                }
            } else {
                let htmlStr = '<tr class="textAlign"><td colspan="6">暂无数据</td></tr>'
                $('#projectlist').append(htmlStr)
            }
            /*totalData 总数据 totalPage 总页数*/
            // if (pagenum == 1) {
            //     paging(data.total, data.pages, pagenum)
            // }
            // var list = data.list
            // if (list.length > 0) {
            //     for (var i = 0; i < list.length; i++) {
            //         let projecttype = '用户方案'
            //         if (list[i].projecttype == 0) {
            //             projecttype = '系统方案'
            //         }
            //         var keywords = list[i].keywords
            //         var shieldingWords = list[i].shieldingWords
            //         let htmlStr = '<tr class="textAlign">' +
            //             '<td>' + projecttype + '</td>' +
            //             '<td>' + list[i].projectname + '</td>' +
            //             '<td>' + keywords + '</td>' +
            //             '<td>' + shieldingWords + '</td>' +
            //             '<td>' + list[i].username + '</td>' +
            //             '<td>' +
            //             '<div class="btn-group">' +
            //             '<a class="btn-primary btn btn-xs" onclick=\'popup("' + list[i].id + '","' + list[i].projectname + '")\'>修改方案名称</a>' +
            //             '<a class="btn-danger btn btn-xs" onclick="isDelPop(' + list[i].id + ')">删除</a>' +
            //             '</div>' +
            //             '</td>' +
            //             '</tr>'
            //         $('#projectlist').append(htmlStr)
            //     }
            // } else {
            //     let htmlStr = '<tr class="textAlign"><td colspan="6">暂无数据</td></tr>'
            //     $('#projectlist').append(htmlStr)
            // }
        },
    });
}


$("#time").change(function(){
    loading(1);
});

function paging(totalData, totalPage, pagenum) {
    $('#box').paging({
        initPageNo: pagenum, // 初始页码
        totalPages: totalPage, //总页数
        totalCount: '合计' + totalData + '条数据', // 条目总数
        slideSpeed: 600, // 缓动速度 单位毫秒
        jump: true, //是否支持跳转
        callback: function (page) {
            // 回调函数
           // loading(page);
        }
    });
}

function JumpToPage(pagenum) {
    loading(pagenum);
}

function popup(id, projectname) {
    let pop = '<div id="pop">' +
        '<div id="popHeard">' +
        '<span>修改方案名称</span>' +
        '</div>' +
        '<div id="popCon">' +
        '<input type="text" id="popSelect" placeholder="请输入新方案名称" value="' + projectname + '" ></input>' +
        '</div>' +
        '<div id="popFooter">' +
        '<button id="popN" onclick="popremove()">取消</button>' +
        '<button id="popY" onclick="popSubmit(' + id + ')">确定</button>' +
        '</div>' +
        '</div>';
    $("body").append(pop)
}

function showVal(event) {
    let val = event.target.innerHTML;
    $("#popSelect").val(val);
}

function popremove() {
    $('#pop').remove()
}

function popSubmit(id) {
    $.ajax({
        type: "POST",
        url: "pro/editProject",
        dataType: "json",
        data: {projectname: $('#popSelect').val(), id: id},
        success: function (data) {
            /*totalData 总数据 totalPage 总页数*/
            console.log(data)
            if (data.code == 200) {
                $('#pop').remove()
                loading(1)
            } else {
                alert('更新失败')
            }
        },
    });
}

function isDelPop(id) {
    let pop = '<div id="pop">' +
        '<div id="popHeard">' +
        '<span>确认删除此方案？</span>' +
        '</div>' +
        '<div id="popCon" style="height: 0;">' +
        '</div>' +
        '<div id="popFooter">' +
        '<button id="popN" onclick="popremove()">取消</button>' +
        '<button id="popY" onclick="delProject(' + id + ')">确定</button>' +
        '</div>' +
        '</div>';
    $("body").append(pop)
}

function delProject(id) {
    $.ajax({
        type: "POST",
        url: "pro/delProject",
        dataType: "json",
        data: {id: id},
        success: function (data) {
            /*totalData 总数据 totalPage 总页数*/
            console.log(data)
            if (data.code == 200) {
                $('#pop').remove()
                loading(1)
            } else {
                alert('更新失败')
            }
        },
    });
}


function stampTOtime(data){
//data为时间戳
    var date = new Date(data);
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = (date.getDate() < 10 ? '0'+date.getDate() : date.getDate()) + ' ';
    var h = (date.getHours() < 10 ? '0'+date.getHours() : date.getHours()) + ':';
    var m = (date.getMinutes() < 10 ? '0'+date.getMinutes() : date.getMinutes()) + ':';
    var s = (date.getSeconds() < 10 ? '0'+date.getSeconds() : date.getSeconds());
debugger;
    var strDate = Y+M+D+h+m+s; //转换为年月日时分秒
    // var strDate = Y+M+D; //转换为年月日

    return strDate;
}




//搜索事件
$("#keyword").keydown(function (res) {
    if (res.keyCode == 13)
        loading(1);
})
/*
$('#searchButton').click(function () {
    loading(1);
})*/
